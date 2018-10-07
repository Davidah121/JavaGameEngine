package openGLEngine;

import org.lwjgl.glfw.GLFWKeyCallback;

public class Keyboard extends GLFWKeyCallback
{

	public boolean[] keyUp = new boolean[370];
	public boolean[] keyDown = new boolean[370];
	public boolean[] keyPressed = new boolean[370];
	
	//To variables to indicate that some key has been pressed
	//and what key that is.
	public int lastKey = 0;
	public boolean keyHasBeenPressed = false;
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		// TODO Auto-generated method stub
		
		if(action==0)
		{
			//release
			try
			{
				setKeyUp(key);
			}
			catch(Exception e)
			{
				System.err.println("KEY OUT OF BOUNDS");
				e.printStackTrace();
			}
		}
		if(action==1)
		{
			//pressed
			try
			{
				lastKey = key;
				keyHasBeenPressed=true;
				setKeyPressed(key);
			}
			catch(Exception e)
			{
				System.err.println("KEY OUT OF BOUNDS");
				e.printStackTrace();
			}
		}
	}
	
	public void update()
	{
		keyHasBeenPressed = false;
		for(int i=0;i<keyUp.length;i++)
		{
			if (keyDown[i]==true)
			{
				keyPressed[i]=false;
				keyUp[i]=false;
			}
			else if (keyUp[i]==true)
			{
				keyUp[i]=false;
				keyPressed[i]=false;
				keyDown[i]=false;
			}
		}
	}
	
	private void setKeyUp(int key)
	{
		keyUp[key]=true;
		keyDown[key]=false;
		keyPressed[key]=false;
	}
	
	private void setKeyPressed(int key)
	{
		keyPressed[key]=true;
		keyDown[key]=true;
		keyUp[key]=false;
	}
	
}