package openGLEngine;

import openGLEngine.Display;
import org.lwjgl.*;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFW;

public class Input {
	
	private static Keyboard keys = new Keyboard();
	private static Mouse mouse = new Mouse();
	private static Gamepad gamepad = new Gamepad();
	
	public final static int LEFT_MOUSE_BUTTON = 0;
	public final static int RIGHT_MOUSE_BUTTON = 1;
	public final static int MIDDLE_MOUSE_BUTTON = 2;
	
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
	public final static int DPAD_DOWN_BUTTON = 12;
	public final static int DPAD_LEFT_BUTTON = 13;
	
	//retrieved from glfw.org
	public final static int VK_SPACE = 32;
	
	public final static int VK_LEFT_CONTROL = 341;
	public final static int VK_LEFT_SHIFT = 340;
	public final static int VK_LEFT_ALT = 342;
	
	public final static int VK_RIGHT_CONTROL = 345;
	public final static int VK_RIGHT_SHIFT = 344;
	public final static int VK_RIGHT_ALT = 346;
	
	public final static int VK_SHIFT = -4;
	public final static int VK_CONTROL = -5;
	public final static int VK_ALT = -6;
	public final static int VK_ENTER = -7;
	
	public final static int VK_KEYPAD_0 = 320;
	public final static int VK_KEYPAD_1 = 321;
	public final static int VK_KEYPAD_2 = 322;
	public final static int VK_KEYPAD_3 = 323;
	public final static int VK_KEYPAD_4 = 324;
	public final static int VK_KEYPAD_5 = 325;
	public final static int VK_KEYPAD_6 = 326;
	public final static int VK_KEYPAD_7 = 327;
	public final static int VK_KEYPAD_8 = 328;
	public final static int VK_KEYPAD_9 = 329;
	public final static int VK_KEYPAD_DECIMAL = 330;
	public final static int VK_KEYPAD_DIVIDE = 331;
	public final static int VK_KEYPAD_MULTIPLY = 332;
	public final static int VK_KEYPAD_SUBTRACT = 333;
	public final static int VK_KEYPAD_ADD = 334;
	public final static int VK_KEYPAD_ENTER = 335;
	
	public final static int VK_LEFT_BRACKET = 91;
	public final static int VK_BACKSLASH = 92;
	public final static int VK_RIGHT_BRACKET = 93;
	public final static int VK_GRAVE_ACCENT = 96;
	
	public final static int VK_ESCAPE = 256;
	public final static int VK_RAW_ENTER = 257;
	public final static int VK_TAB = 258;
	public final static int VK_BACKSPACE = 259;
	public final static int VK_INSERT = 260;
	public final static int VK_DELETE = 261;
	
	public final static int VK_RIGHT = 262;
	public final static int VK_LEFT = 263;
	public final static int VK_DOWN = 264;
	public final static int VK_UP = 265;
	
	public final static int VK_PAGE_UP = 266;
	public final static int VK_PAGE_DOWN = 267;
	public final static int VK_HOME = 268;
	public final static int VK_END = 269;
	
	public final static int VK_SLASH = 47;
	public final static int VK_PERIOD = 46;
	public final static int VK_MINUS = 45;
	public final static int VK_COMMA = 44;
	public final static int VK_APOSTROPHE = 39;
	public final static int VK_SEMICOLON = 59;
	public final static int VK_EQUAL = 61;
	
	/**
	 * Initializes all forms of input to be used in by the window.
	 * @param window
	 */
	public static void initInput(long window)
	{
		GLFW.glfwSetKeyCallback(window, keys);
		GLFW.glfwSetMouseButtonCallback(window, mouse.mouseButtons);
		GLFW.glfwSetCursorPosCallback(window, mouse.mousePosition);
		GLFW.glfwSetScrollCallback(window, mouse.scrollWheel);
		GLFW.glfwSetJoystickCallback(gamepad);
	}
	
	///////Keyboard
	/**
	 * Determines if a key is being held down. Not able to separated betweeen
	 * different windows created.
	 * 
	 * VK_SHIFT works for both left shift and right shift.
	 * VK_CONTROL works for both left control and right control.
	 * VK_ALT works for both left alt and right alt.
	 * VK_ENTER works for both the return key and the enter key on the numpad.
	 * @param key
	 * @return
	 */
	public static boolean getKeyDown(int key)
	{
		if(key==VK_SHIFT)
		{
			if(keys.keyDown[VK_LEFT_SHIFT]==true || keys.keyDown[VK_RIGHT_SHIFT]==true)
				return true;
			else
				return false;
		}
		else if(key==VK_CONTROL)
		{
			if(keys.keyDown[VK_LEFT_CONTROL]==true || keys.keyDown[VK_RIGHT_CONTROL]==true)
				return true;
			else
				return false;
		}
		else if(key==VK_ALT)
		{
			if(keys.keyDown[VK_LEFT_ALT]==true || keys.keyDown[VK_RIGHT_ALT]==true)
				return true;
			else
				return false;
		}
		else if(key==VK_ENTER)
		{
			if(keys.keyDown[VK_RAW_ENTER]==true || keys.keyDown[VK_KEYPAD_ENTER]==true)
				return true;
			else
				return false;
		}
		else
		{
			return keys.keyDown[key];
		}
	}
	
	/**
	 * Determines whether the key has been pressed. Last for one update
	 * cycle.
	 * 
	 * VK_SHIFT works for both left shift and right shift.
	 * VK_CONTROL works for both left control and right control.
	 * VK_ALT works for both left alt and right alt.
	 * VK_ENTER works for both the return key and the enter key on the numpad.
	 * @param key
	 * @return
	 */
	public static boolean getKeyPressed(int key)
	{
		if(key==VK_SHIFT)
		{
			if(keys.keyPressed[VK_LEFT_SHIFT]==true || keys.keyPressed[VK_RIGHT_SHIFT]==true)
				return true;
			else
				return false;
		}
		else if(key==VK_CONTROL)
		{
			if(keys.keyPressed[VK_LEFT_CONTROL]==true || keys.keyPressed[VK_RIGHT_CONTROL]==true)
				return true;
			else
				return false;
		}
		else if(key==VK_ALT)
		{
			if(keys.keyPressed[VK_LEFT_ALT]==true || keys.keyPressed[VK_RIGHT_ALT]==true)
				return true;
			else
				return false;
		}
		else if(key==VK_ENTER)
		{
			if(keys.keyPressed[VK_RAW_ENTER]==true || keys.keyPressed[VK_KEYPAD_ENTER]==true)
				return true;
			else
				return false;
		}
		else
		{
			return keys.keyPressed[key];
		}
	}
	
	/**
	 * Determines whether the key has been released. Last for one update 
	 * cycle.
	 * 
	 * VK_SHIFT works for both left shift and right shift.
	 * VK_CONTROL works for both left control and right control.
	 * VK_ALT works for both left alt and right alt.
	 * VK_ENTER works for both the return key and the enter key on the numpad.
	 * @param key
	 * @return
	 */
	public static boolean getKeyUp(int key)
	{
		if(key==VK_SHIFT)
		{
			if(keys.keyUp[VK_LEFT_SHIFT]==true || keys.keyUp[VK_RIGHT_SHIFT]==true)
				return true;
			else
				return false;
		}
		else if(key==VK_CONTROL)
		{
			if(keys.keyUp[VK_LEFT_CONTROL]==true || keys.keyUp[VK_RIGHT_CONTROL]==true)
				return true;
			else
				return false;
		}
		else if(key==VK_ALT)
		{
			if(keys.keyUp[VK_LEFT_ALT]==true || keys.keyUp[VK_RIGHT_ALT]==true)
				return true;
			else
				return false;
		}
		else if(key==VK_ENTER)
		{
			if(keys.keyUp[VK_RAW_ENTER]==true || keys.keyUp[VK_KEYPAD_ENTER]==true)
				return true;
			else
				return false;
		}
		else
		{
			return keys.keyUp[key];
		}
	}
	
	/**
	 * Determines whether or not any key on the keyboard has been pressed.
	 * @return
	 */
	public static boolean getKeyboardHasBeenUsed()
	{
		return keys.keyHasBeenPressed;
	}
	
	/**
	 * Returns the last key pressed. The numpad keys are treated
	 * as different keys. 
	 * @return
	 */
	public static char getLastKeyRaw()
	{
		return (char)keys.lastKey;
	}
	
	/**
	 * Returns the last key pressed.
	 * @return
	 */
	public static char getLastKey()
	{
		switch(keys.lastKey)
		{
		case VK_KEYPAD_0:
			return 48;
		case VK_KEYPAD_1:
			return 49;
		case VK_KEYPAD_2:
			return 50;
		case VK_KEYPAD_3:
			return 51;
		case VK_KEYPAD_4:
			return 52;
		case VK_KEYPAD_5:
			return 53;
		case VK_KEYPAD_6:
			return 54;
		case VK_KEYPAD_7:
			return 55;
		case VK_KEYPAD_8:
			return 56;
		case VK_KEYPAD_9:
			return 57;
		case VK_KEYPAD_DECIMAL:
			return 46;
		case VK_KEYPAD_ADD:
			return 43;
		case VK_KEYPAD_SUBTRACT:
			return 45;
		case VK_KEYPAD_DIVIDE:
			return 47;
		case VK_KEYPAD_MULTIPLY:
			return 42;
		default:
			return (char)keys.lastKey;
		}
	}
	
	///////GamePad
	/**
	 * Determines whether the button specified on the gamepad specified
	 * is being held down.
	 * @param id
	 * @param button
	 * @return
	 */
	public static boolean getButtonDown(int id, int key)
	{
		return gamepad.getButtonDown(id,key);
	}
	
	/**
	 * Determines whether the button specified on the gamepad specified
	 * has been pressed. Only last for one update frame. Happens the
	 * moment the button was pressed.
	 * @param id
	 * @param button
	 * @return
	 */
	public static boolean getButtonPressed(int id, int key)
	{
		return gamepad.getButtonPressed(id, key);
	}
	
	/**
	 * Determines whether the button specified on the gamepad specified
	 * has been released. Only last for one update frame. Happens the
	 * moment the button was released.
	 * @param id
	 * @param button
	 * @return
	 */
	public static boolean getButtonUp(int id, int key)
	{
		return gamepad.getButtonUp(id, key);
	}
	
	/**
	 * Gets the value of the X axis on the left analog stick of
	 * the specified controller.
	 * -1 = fully left
	 * 1 = fully right
	 * @param id
	 * @return
	 */
	public static float getXLeftAxis(int id)
	{
		return gamepad.getXLeftAxis(id);
	}
	
	/**
	 * Gets the value of the Y axis on the left analog stick of
	 * the specified controller.
	 * -1 = fully down
	 * 1 = fully up
	 * @param id
	 * @return
	 */
	public static float getYLeftAxis(int id)
	{
		return gamepad.getYLeftAxis(id);
	}
	
	/**
	 * Gets the value of the X axis on the right analog stick of
	 * the specified controller.
	 * -1 = fully left
	 * 1 = fully right
	 * @param id
	 * @return
	 */
	public static float getXRightAxis(int id)
	{
		return gamepad.getXRightAxis(id);
	}
	
	/**
	 * Gets the value of the Y axis on the right analog stick of
	 * the specified controller.
	 * -1 = fully down
	 * 1 = fully up
	 * @param id
	 * @return
	 */
	public static float getYRightAxis(int id)
	{
		return gamepad.getYRightAxis(id);
	}
	
	/**
	 * Returns the position of the left trigger on the gamepad
	 * with the id specified.
	 * 1 = pressed down fully
	 * 0 = not pressed down
	 * @param id
	 * @return
	 */
	public static float getLeftTrigger(int id)
	{
		return gamepad.getLeftTrigger(id);
	}
	
	/**
	 * Returns the position of the right trigger on the gamepad
	 * with the id specified.
	 * 1 = pressed down fully
	 * 0 = not pressed down
	 * @param id
	 * @return
	 */
	public static float getRightTrigger(int id)
	{
		return gamepad.getRightTrigger(id);
	}
	
	/**
	 * Determines if the gamepad with the corresponding id exists.
	 * @param controllerNum
	 * @return
	 */
	public static boolean gamepadExists(int controllerNum)
	{
		return GLFW.glfwJoystickPresent(controllerNum);
	}
	
	/**
	 * Determines whether or not any button on the corresponding gamepad
	 * has been pressed or if any of the axis have been changed.
	 * @param controllerNum
	 * @return
	 */
	public static boolean gamepadHasBeenPressed(int controllerNum)
	{
		if (GLFW.glfwJoystickPresent(controllerNum))
		{
			return gamepad.hasBeenUsed[controllerNum];
		}
		return false;
	}
	
	//Mouse Stuff
	
	/**
	 * Sets the position of the mouse relative to the window.
	 * @param window
	 * @param x
	 * @param y
	 */
	public static void setMousePos(int x, int y)
	{
		GLFW.glfwSetCursorPos(Display.getWindow(), (int)x, (int)y);
	}
	
	/**
	 * Gets the x position of the mouse relative to the window.
	 * @return
	 */
	public static int getMouseX()
	{
		return (int) mouse.mouseX;
	}
	
	/**
	 * Gets the y position of the mouse relative to the window.
	 * @return
	 */
	public static int getMouseY()
	{
		return (int) mouse.mouseY;
	}
	
	/**
	 * Determines whether the specified mouse button is being 
	 * held down.
	 * @param button
	 * @return
	 */
	public static boolean getMouseButtonDown(int button)
	{
		return mouse.getMouseDown(button);
	}
	
	/**
	 * Determines whether the specified mouse button has been
	 * released. Last for one update cycle.
	 * @param button
	 * @return
	 */
	public static boolean getMouseButtonUp(int button)
	{
		return mouse.getMouseUp(button);
	}
	
	/**
	 * Determines whether the specified mouse button has been
	 * pressed. Last for one update cycle.
	 * @param button
	 * @return
	 */
	public static boolean getMouseButtonPressed(int button)
	{
		return mouse.getMousePressed(button);
	}
	
	/**
	 * Determines whether the mouse wheel has moved up.
	 * @return
	 */
	public static boolean getMouseWheelUp()
	{
		return mouse.getMouseWheelUp();
	}
	
	/**
	 * Determines whether the mouse wheel has moved down.
	 * @return
	 */
	public static boolean getMouseWheelDown()
	{
		return mouse.getMouseWheelDown();
	}
	
	/**
	 * Determines whether the mouse wheel has moved left.
	 * @return
	 */
	public static boolean getMouseWheelLeft()
	{
		return mouse.getMouseWheelLeft();
	}
	
	/**
	 * Determines whether the mouse wheel has moved right.
	 * @return
	 */
	public static boolean getMouseWheelRight()
	{
		return mouse.getMouseWheelRight();
	}
	
	/**
	 * updates the different forms of input if they need to be.
	 * Currently, only the gamepads have to be updated this way.
	 */
	public static void update()
	{
		gamepad.update();
		
	}
	
	/**
	 * Resets the input values so that eventually, all of the input values
	 * will return false if they are supposed to. Terrible explanation, but
	 * don't worry about it. This method needs to be called after the update
	 * cycle to keep everything in order. Is called by default.
	 */
	public static void reset()
	{
		gamepad.resetValues();
		keys.update();
		mouse.update();
	}
	
	
	
}
