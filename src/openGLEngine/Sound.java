package openGLEngine;

import java.io.File;
import java.io.Serializable;
import java.net.URISyntaxException;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.*;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;


public class Sound implements Serializable{

	private Media h;
	private MediaPlayer mp;
	private boolean looping=false;
	private double loopStart=0;
	private double loopEnd=0;
	private int timesLooped=0; 
	private double trackDuration=0;
	
	//GameStuff
	private double x=0;
	private double y=0;
	private double z=0;
	
	private boolean globalSound = false;
	
	private double volume = 1.0;
	
	
	//Thread Stuff. No other purpose other than to make sure the sound thread works
	private boolean hasDeleted=false;
	private boolean started = false;
	///////////////////////////////////////////////////////////////////////////////
	
	private double totalTime = 0;
	private String fileName;
	
	private SoundThread currentThread;
	
	private static JFXPanel panel;
	
	public static void initSound()
	{
		panel = new javafx.embed.swing.JFXPanel();
	}
	
	public static void terminate()
	{
		panel.removeAll();
		Platform.exit();
	}
	
	public Sound(String fileName)
	{
		
		this.fileName=fileName;
		
		File f = new File(fileName);
		
		if(f.exists())
		{
			h = new Media(f.toURI().toString());
			mp = new MediaPlayer(h);
			
			currentThread = new SoundThread(this);
		}
		else
		{
			
			try {
				h = new Media( ClassLoader.getSystemClassLoader().getResource(fileName).toURI().toString() );
				mp = new MediaPlayer(h);
				
				currentThread = new SoundThread(this);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public String getFileName()
	{
		return fileName;
	}
	
	public double getLoopStart()
	{
		return loopStart;
	}
	
	public double getLoopEnd()
	{
		return loopEnd;
	}
	
	public int getTimesLooped()
	{
		return timesLooped;
	}
	
	public void setLoopStart(double value)
	{
		this.loopStart=value;
	}
	
	public void setLoopEnd(double value)
	{
		this.loopEnd=value;
	}
	
	public void addTimesLooped()
	{
		timesLooped++;
	}
	
	public boolean hasStarted()
	{
		return started;
	}
	
	public void setStarted()
	{
		started=false;
	}
	
	public void addTotalTime(double value)
	{
		totalTime+=value;
	}
	
	public void play()
	{
		if(isPlaying()==false)
		{
			if(!currentThread.isRunning())
			{
				currentThread.start();
			}
			
			started=true;
			mp.play();
		}
	}
	
	public void pause()
	{
		mp.pause();
	}
	
	public void stop()
	{
		if(looping==false)
		{
			totalTime=0;
		}
		
		mp.stop();
	}
	
	public double getCurrentTime()
	{
		return mp.getCurrentTime().toSeconds();
	}
	
	public void seek(double time)
	{
		mp.seek(new Duration(time));
	}
	
	public void setBalance(double value)
	{
		mp.setBalance(value);
	}
	
	public double getBalance()
	{
		return mp.getBalance();
	}
	
	public void setVolume(double value)
	{
		mp.setVolume(value);
	}
	
	public void mute(boolean value)
	{
		mp.setMute(value);
	}
	
	public double getTimePlaying()
	{
		return totalTime;
	}
	
	public double getPlaybackRate()
	{
		return mp.getRate();
	}
	
	public void setPlaybackRate(double value)
	{
		mp.setRate(value);
	}
	
	public double getVolume()
	{
		return mp.getVolume();
	}
	
	public void setLooping(boolean value)
	{
		looping=value;
	}
	
	public boolean getLooping()
	{
		return looping;
	}
	
	public boolean isPaused()
	{
		return mp.getStatus().equals(Status.PAUSED);
	}
	
	public boolean isPlayable()
	{
		return mp.getStatus().equals(Status.READY);
	}
	
	public boolean isAvaliable()
	{
		return mp.getStatus().equals(Status.DISPOSED);
	}
	
	public boolean isPlaying()
	{
		return mp.getStatus().equals(Status.PLAYING);
	}
	
	public boolean isStopped()
	{
		return mp.getStatus().equals(Status.STOPPED);
	}
	
	public double getTrackDuration()
	{
		return h.getDuration().toSeconds();
	}
	
	public boolean hasDeleted()
	{
		return hasDeleted;
	}
	
	public void dispose()
	{
		hasDeleted=true;
		currentThread.setRunning();
		
		System.out.println("DISPOSING");
		
		try {
			currentThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mp.stop();
		mp.dispose();
	}
	
}
