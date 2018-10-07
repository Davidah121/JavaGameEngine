package openGLEngine;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWJoystickCallback;


public class Gamepad extends GLFWJoystickCallback
{

	private boolean[][] keyUp = new boolean[16][30];
	private boolean[][] keyDown = new boolean[16][30];
	private boolean[][] keyPressed = new boolean[16][30];
	private float[] xLeftAxis = new float[16];
	private float[] yLeftAxis = new float[16];
	private float[] xRightAxis = new float[16];
	private float[] yRightAxis = new float[16];
	
	private float[] ltAxis = new float[16];
	private float[] rtAxis = new float[16];
	
	private FloatBuffer axisBuff;
	private ByteBuffer buttonBuff;
	
	private boolean exists = false;
	
	public final static int A_BUTTON = 0;
	public final static int B_BUTTON = 1;
	public final static int X_BUTTON = 2;
	public final static int Y_BUTTON = 3;
	public final static int LB_BUTTON = 4;
	public final static int RB_BUTTON = 5;
	public final static int BACK_BUTTON = 6;
	public final static int START_BUTTON = 7;
	public final static int LSTICK_BUTTON = 8;
	public final static int RSTICK_BUTTON = 9;
	public final static int DPAD_UP_BUTTON = 10;
	public final static int DPAD_RIGHT_BUTTON = 11;
	public final static int DPAD_LEFT_BUTTON = 12;
	public final static int DPAD_DOWN_BUTTON = 13;
	
	public boolean[] hasBeenUsed = new boolean[16];
	
	@Override
	public void invoke(int jid, int event) {
		// TODO Auto-generated method stub
		
		if (GLFW.glfwJoystickPresent(jid)==true)
		{
			//hasBeenConnected
		}
		else
		{
			//hasBeenDisconnected
		}
	}
	
	/**
	 * updates each gamepad. Determines the state of the buttons and the 
	 * position of the analog sticks and triggers for a particular gamepad.
	 */
	public void update()
	{
		for(int i=0; i<16; i++)
		{
			if (gamepadExists(i))
			{
				try
				{
					axisBuff = GLFW.glfwGetJoystickAxes(i);
					
					if(xLeftAxis[i]!=axisBuff.get(0))
						hasBeenUsed[i]=true;
					if(yLeftAxis[i]!=axisBuff.get(1))
						hasBeenUsed[i]=true;
					
					if(xRightAxis[i]!=axisBuff.get(2))
						hasBeenUsed[i]=true;
					if(yRightAxis[i]!=axisBuff.get(3))
						hasBeenUsed[i]=true;
					
					if(ltAxis[i]!=axisBuff.get(4))
						hasBeenUsed[i]=true;
					if(rtAxis[i]!=axisBuff.get(5))
						hasBeenUsed[i]=true;
					
					
					xLeftAxis[i]=axisBuff.get(0);
					xRightAxis[i]=axisBuff.get(2);
					
					yLeftAxis[i]=axisBuff.get(1);
					yRightAxis[i]=axisBuff.get(3);
					
					ltAxis[i]=axisBuff.get(4);
					rtAxis[i]=axisBuff.get(5);
					
					buttonBuff = GLFW.glfwGetJoystickButtons(i);
					//0=A
					for(int but=0; but<buttonBuff.capacity(); but++)
					{
						int buttonInQuestion = buttonBuff.get(but);
						if( buttonInQuestion==1 )
						{
							hasBeenUsed[i]=true;
							setButtonPressed(i, but);
						}
						else if ( buttonInQuestion==0 && getButtonDown(i, but) )
						{
							hasBeenUsed[i]=true;
							setButtonUp(i, but);
						}
					}
				}
				catch(Exception e)
				{
					//e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Properly sets the state of a button on each controller.
	 * Makes sure that the button is register up or down for one frame.
	 */
	public void resetValues()
	{
		for(int id=0;id<16;id++)
		{
			//resetButtons
			for(int i=0;i<keyUp.length;i++)
			{
				if (keyDown[id][i]==true)
				{
					keyPressed[id][i]=false;
					keyUp[id][i]=false;
				}
				else if (keyUp[id][i]==true)
				{
					keyUp[id][i]=false;
					keyPressed[id][i]=false;
					keyDown[id][i]=false;
				}
			}
			//resetAxis (not needed)
			
		}
	}
	
	private void setButtonUp(int id, int key)
	{
		keyUp[id][key]=true;
		keyDown[id][key]=false;
		keyPressed[id][key]=false;
	}
	
	
	private void setButtonPressed(int id, int key)
	{
		if(keyDown[id][key]==false)
		{
			keyPressed[id][key]=true;
			keyDown[id][key]=true;
			keyUp[id][key]=false;
		}
		else
		{
			keyPressed[id][key]=false;
			keyDown[id][key]=true;
			keyUp[id][key]=false;
		}
	}
	
	/**
	 * Determines whether the button specified on the gamepad specified
	 * is being held down.
	 * @param id
	 * @param button
	 * @return
	 */
	public boolean getButtonDown(int id, int button)
	{
		return keyDown[id][button];
	}
	
	/**
	 * Determines whether the button specified on the gamepad specified
	 * has been pressed. Only last for one update frame. Happens the
	 * moment the button was pressed.
	 * @param id
	 * @param button
	 * @return
	 */
	public boolean getButtonPressed(int id, int button)
	{
		return keyPressed[id][button];
	}
	
	/**
	 * Determines whether the button specified on the gamepad specified
	 * has been released. Only last for one update frame. Happens the
	 * moment the button was released.
	 * @param id
	 * @param button
	 * @return
	 */
	public boolean getButtonUp(int id, int button)
	{
		return keyUp[id][button];
	}
	
	/**
	 * Gets the value of the X axis on the left analog stick of
	 * the specified controller.
	 * -1 = fully left
	 * 1 = fully right
	 * @param id
	 * @return
	 */
	public float getXLeftAxis(int id)
	{
		return xLeftAxis[id];
	}
	
	/**
	 * Gets the value of the Y axis on the left analog stick of
	 * the specified controller.
	 * -1 = fully down
	 * 1 = fully up
	 * @param id
	 * @return
	 */
	public float getYLeftAxis(int id)
	{
		return yLeftAxis[id];
	}
	
	/**
	 * Gets the value of the X axis on the right analog stick of
	 * the specified controller.
	 * -1 = fully left
	 * 1 = fully right
	 * @param id
	 * @return
	 */
	public float getXRightAxis(int id)
	{
		return xRightAxis[id];
	}
	
	/**
	 * Gets the value of the Y axis on the right analog stick of
	 * the specified controller.
	 * -1 = fully down
	 * 1 = fully up
	 * @param id
	 * @return
	 */
	public float getYRightAxis(int id)
	{
		return yRightAxis[id];
	}
	
	/**
	 * Returns the position of the left trigger on the gamepad
	 * with the id specified.
	 * 1 = pressed down fully
	 * 0 = not pressed down
	 * @param id
	 * @return
	 */
	public float getLeftTrigger(int id)
	{
		return ltAxis[id];
	}
	
	/**
	 * Returns the position of the right trigger on the gamepad
	 * with the id specified.
	 * 1 = pressed down fully
	 * 0 = not pressed down
	 * @param id
	 * @return
	 */
	public float getRightTrigger(int id)
	{
		return rtAxis[id];
	}
	
	/**
	 * Determines if the gamepad with the corresponding id exists.
	 * @param controllerNum
	 * @return
	 */
	public boolean gamepadExists(int controllerNum)
	{
		return GLFW.glfwJoystickPresent(controllerNum);
	}

}