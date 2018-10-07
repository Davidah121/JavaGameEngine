package openGLEngine;

import java.util.ArrayList;

public class SoundThread extends Thread {

	public Sound sound;
	private double endTime;
	private double newTime;
	private boolean running=false;
	
	public SoundThread(Sound sound)
	{
		this.sound = sound;
	}
	
	public void setRunning()
	{
		running=false;
	}
	
	public boolean isRunning()
	{
		return running;
	}
	
	@Override
	public void run()
	{
		newTime=0;
		endTime=0;
		running=true;
		
		while(running==true)
		{
			if (sound.hasStarted())
			{
				startedCode();
			}
			
			if (sound.isStopped())
			{
				//System.out.println("STOPE");
				stoppedCode();
			}
			else if(sound.isPlaying())
			{
				playingCode();
			}
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		System.out.println("IS");
	}
	
	public void startedCode()
	{
		//startStuff
		sound.setStarted();
	}
	
	public void playingCode()
	{
		endTime = newTime;
		newTime = sound.getCurrentTime();
		sound.addTotalTime(newTime-endTime);
		
		if (sound.getLooping())
		{
			if (sound.getCurrentTime() >= sound.getLoopEnd()
				&& sound.getLoopEnd() != 0)
			{
				sound.seek( sound.getLoopStart() );
				sound.addTimesLooped();
				
				endTime=sound.getLoopStart();
				newTime=sound.getLoopStart();
			}
		}
		
		if (sound.getCurrentTime() == sound.getTrackDuration())
		{
			sound.stop();
		}
		else
		{
			//System.out.println(sound.getCurrentTime());
			//System.out.println(sound.getTrackDuration());
		}
	}
	
	public void stoppedCode()
	{
		if (sound.getLooping()==false)
		{
			endTime=sound.getLoopStart();
			newTime=sound.getLoopStart();
			//no need to stop a sound because it is already stopped.
		}
		else
		{
			endTime=sound.getLoopStart();
			newTime=sound.getLoopStart();
			
			sound.play();
			sound.seek(sound.getLoopStart());
			sound.addTimesLooped();
		}
	}
	
}
