package glLevelEditor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;

import javax.swing.*;

import openGLEngine.Game;

public class ConsoleThing extends Thread {

	private String input = null;
	private String preInput = "";
	private JFrame window;
	private int currentLine = 0;
	private ArrayList<String> consoleLines = new ArrayList<String>();
	
	private boolean shouldClose = false;
	
	private int x = 0;
	private int y = 0;
	
	private int width = 640;
	private int height = 480;
	
	public final int maxStringSize = 59;
	
	private BufferedImage img;
	private Graphics2D graphics;
	
	private JTextField textBox = new JTextField();
	
	private boolean wantsInput = false;
	
	private boolean shouldDraw = false;
	
	private boolean entered = false;
	
	private JPanel pane = new JPanel() {
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(img, 0, 0, null);
		}
	};
	
	public ConsoleThing()
	{
		window = new JFrame("CONSOLE");
		window.setSize(width, height);
		
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB); //Just Text
		graphics = img.createGraphics();
		
		window.setResizable(false);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		window.add(textBox);
		window.add(pane);
		
		textBox.addKeyListener( new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					if(entered==false && wantsInput==true)
						entered=true;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		window.addWindowListener( new WindowListener(){

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				if(shouldClose==true)
					window.dispose();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while(shouldClose==false)
		{
			
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(wantsInput==true)
			{
				preInput = textBox.getText();
				
				if(entered==true)
				{
					input = textBox.getText();
					entered=false;
					wantsInput=false;
				}
			}
			else
			{
				textBox.setText("");
			}
			if(shouldDraw==true)
			{
				graphics.setColor(Color.BLACK);
				graphics.fillRect(0, 0, width, height);
				graphics.setColor(Color.WHITE);
				
				graphics.setFont( new Font("Sans Serif", Font.PLAIN, 18));
				
				String currentConsoleString = "";
				String tempString = "";
				int yPos = 0;
				int count = 0;
				
				for(int i=0; i<consoleLines.size(); i++)
				{
					currentConsoleString = consoleLines.get(i);
					count = 0;
					tempString = "";
					for(int i2=0; i2<currentConsoleString.length(); i2++)
					{
						tempString+=currentConsoleString.charAt(i2);
						count++;
						if(tempString.length()>=maxStringSize)
						{
							graphics.drawString(tempString, 0, 20 + 20*yPos);
							tempString="";
							yPos++;
						}
						else if(count == currentConsoleString.length())
						{
							graphics.drawString(tempString, 0, 20 + 20*yPos);
							tempString="";
							yPos++;
						}
					}
				}
				
				graphics.drawString(textBox.getText(), 0, 20 + 20*yPos);
				window.repaint();
				
				shouldDraw=false;
			}
			
		}
		
	}
	
	/**
	 * Sets whether the console should except input from the user.
	 * Can not print additional strings to the console while input is
	 * being queued.
	 * @param value
	 */
	public void setQueueInput(boolean value)
	{
		wantsInput=value;
	}
	
	/**
	 * Returns a value that represents whether or not the console
	 * is currently accepting inputs from the user.
	 * @return
	 */
	public boolean getQueueInput()
	{
		return wantsInput;
	}
	
	public void clearConsole()
	{
		if(shouldDraw==false)
		{
			consoleLines.clear();
		}
	}
	
	/**
	 * Returns the input entered by the user.
	 * If the user has not entered anything, it returns null.
	 * After getting the input, it reverts to null until the user enters something else.
	 * If a user enters something else before you get the current input, it reverts to
	 * the new input.
	 * 
	 * Previous Inputs are not saved, so only ask for inputs after you receive the inputs.
	 * @return
	 */
	public String getInput()
	{
		return input;
	}
	
	/**
	 * Returns the input entered by the user.
	 * This method waits until the user has actually entered something.
	 * This method will always return a valid string, however your program will halt
	 * until you receive a string.
	 * 
	 * @return
	 */
	public String getInputWait()
	{
		setQueueInput(true);
		while(getInput()==null)
		{
			
		}
		return input;
	}
	
	
	public void print(String s)
	{
		if(shouldDraw==false && wantsInput==false)
		{
			String[] tempStrings = s.split("\n");
			for(int i=0; i<tempStrings.length; i++)
			{
				consoleLines.add(tempStrings[i]);
				currentLine++;
			}
		}
	}
	
	public void println(String s)
	{
		if(shouldDraw==false && wantsInput==false)
		{
			String otherString = s+'\n';
			
			String[] tempStrings = otherString.split("\n");
			for(int i=0; i<tempStrings.length; i++)
			{
				consoleLines.add(tempStrings[i]);
				currentLine++;
			}
		}
	}
	
	public void draw()
	{
		if(shouldDraw==false)
			shouldDraw=true;
	}
	
	public void close()
	{
		try
		{
			shouldClose=true;
			shouldDraw=false;
			wantsInput=false;
			consoleLines.clear();
			graphics.dispose();
			window.dispatchEvent( new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
