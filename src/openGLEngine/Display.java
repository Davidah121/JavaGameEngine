package openGLEngine;

import static org.lwjgl.system.MemoryUtil.NULL;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL21;

/*
 * Things to do
 * Add audio ~ mostly done
 * add helperFunctions - ex. more in game renderer
 * Better Resource Management ~ all textures, images, surfaces, sounds, and whatever else is stored in certain arraylist. Prevent creation if it already exists.
 * finish level class
 * add skeletons
 * add collision hulls
 * make input better
 * make shaders better
 * make textures better
 * fix particle class
 * work on Surface class
 * Notes - should not have to use multiple shaders for drawing. Use one for all built in drawing
 * Should have default textures for different drawing. ex. Like Game Maker Studio's particles and so if a model does not have a texture, it is white.
 * optimize
 * make editors
 */

public class Display {

	private static int width = 640;
	private static int height = 480;
	private static String title = "Title";
	private static long window;
	
	private static int FPS=0;
	private static int FPSCount=0;
	private static double passedTime=0;
	
	private static double rawFPS=0;
	private static double FPSCap = 60;
	
	private static double startTime = 0;
	private static double endTime = 0;
	
	private static double deltaTime = 1;
	private static double rawDeltaTime = 0;
	
	private static double deltaValue = 0;
	private static double updateTime = 0;
	private static double renderTime = 0;
	
	private static int SystemWidth = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
	private static int SystemHeight = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
	private static Vec2f centerPoint = new Vec2f(GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint());
	
	private static Vec4f clearColor = new Vec4f(0.5f, 0.5f, 0.5f, 0.0f);
	
	private static boolean shouldPrintData = false;
	
	/**
	 * Creates a window capable of using OpenGL.
	 * @param width
	 * @param height
	 * @param title
	 */
	public static void createWindow(int width, int height, String title, boolean fullscreen)
	{
		Display.width = width;
		Display.height = height;
		Display.title = title;
		
		//GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, 4);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
		
		if(fullscreen==false)
		{
			window = GLFW.glfwCreateWindow(width,height,title,NULL,NULL);
		}
		else
		{
			window = GLFW.glfwCreateWindow(width, height, title, GLFW.glfwGetPrimaryMonitor(), NULL);
		}
		if(window==NULL)
		{
			throw new IllegalStateException("Failed to create window");
		}
		
		GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		
		GLFW.glfwSetWindowPos(window,(videoMode.width() - width)/2, (videoMode.height()-height)/2 );
		
		GLFW.glfwMakeContextCurrent(window);
		GL.createCapabilities();
		
		GLFW.glfwShowWindow(window);
		
		Input.initInput(window);
		
		clearWindow();
		//GL11.glEnable(GL13.GL_MULTISAMPLE);
		
		
	}
	
	/**
	 * Request focus for the window.
	 */
	public static void requestFocus()
	{
		GLFW.glfwFocusWindow(window);
	}
	
	/**
	 * Gets the width of the window.
	 * @return
	 */
	public static int getWidth()
	{
		return width;
	}
	
	/**
	 * Gets the height of the window.
	 * @return
	 */
	public static int getHeight()
	{
		return height;
	}
	
	/**
	 * Sets VSync.
	 * 0 = no VSync : FPSCap is set to 120
	 * 1 = VSync for 60fps : FPSCap is set to 60fps as well
	 * 2 = VSync for 30fps : FPSCap is set to 30fps as well
	 * -1 = no VSync : FPSCap is set to 5000
	 * @param value
	 */
	public static void setVSync(int value)
	{
		//0=no 1=60 fps 2=30 fps
		
		switch(value)
		{
		case -1:
			FPSCap = 5000;
			GLFW.glfwSwapInterval(0);
			System.out.println("VSYNC SET TO 0");
			break;
		case 0:
			FPSCap = 120;
			GLFW.glfwSwapInterval(value);
			System.out.println("VSYNC SET TO 0");
			break;
		case 1:
			FPSCap = 60;
			GLFW.glfwSwapInterval(value);
			System.out.println("VSYNC SET TO 1");
			break;
		case 2:
			FPSCap = 30;
			GLFW.glfwSwapInterval(value);
			System.out.println("VSYNC SET TO 2");
			break;
		default:
			FPSCap = 120;
			GLFW.glfwSwapInterval(0);
			System.out.println("VSYNC SET TO 0");
		}
	}
	
	/**
	 * Sets whether or not to print the time it takes to finish a
	 * game loop. Prints out Update loop time and Render loop time.
	 * @param value
	 */
	public static void setShouldPrintSystemData(boolean value)
	{
		shouldPrintData=value;
	}
	
	/**
	 * Determines if the window should close.
	 * @return
	 */
	public static boolean windowCloseRequested()
	{
		return GLFW.glfwWindowShouldClose(window);
	}
	
	/**
	 * returns the id of the window.
	 * @return
	 */
	public static long getWindow()
	{
		return window;
	}
	
	/**
	 * returns the color that the window will clear to when the
	 * method clearWindow() is called.
	 * @return
	 */
	public static Vec4f getClearColor()
	{
		return clearColor;
	}
	
	/**
	 * Clears the window to the clearColor.
	 */
	public static void clearWindow()
	{
		GL11.glClearColor((float)clearColor.x, (float)clearColor.y, (float)clearColor.z, (float)clearColor.w);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	/**
	 * Sets the color that the window will clear to when the method
	 * clearWindow() is called.
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public static void setClearColor(float r, float g, float b, float a)
	{
		clearColor.x=r;
		clearColor.y=g;
		clearColor.z=b;
		clearColor.w=a;
	}
	
	/**
	 * Sets the color that the window will clear to when the method
	 * clearWindow() is called.
	 * @param color
	 */
	public static void setClearColor(Vec4f color)
	{
		clearColor = color;
	}
	
	/**
	 * Gets the current FPS.
	 * @return
	 */
	public static int getFPS()
	{
		return Math.round(FPS);
	}
	
	/**
	 * Gets the raw FPS. This value is not how many frames
	 * you are rendering, but a measure of how many could have
	 * been rendered by your hardware.
	 * 
	 * Does not include the method to swap screen buffers since 
	 * this method will artificially increase the time it takes 
	 * to complete a game cycle so that it lines up with the
	 * monitor's refresh rate.
	 * @return
	 */
	public static double getRawFPS()
	{
		return rawFPS;
	}
	
	/**
	 * Prints the Update Time, Render Time, and Total Time of
	 * the current Game Loop. Should be called after the loop has
	 * finished.
	 */
	private static void printSystemData()
	{
		System.out.println("Update Time: "+updateTime);
		System.out.println("Render Time: "+renderTime);
		System.out.println("Total Time: "+rawDeltaTime);
	}
	
	/**
	 * Gets the max width of the monitor being used.
	 * @return
	 */
	public static int getSystemWidth()
	{
		return SystemWidth;
	}
	
	/**
	 * Gets the max height of the monitor being used.
	 * @return
	 */
	public static int getSystemHeight()
	{
		return SystemHeight;
	}
	
	/**
	 * Gets the center point of the monitor being used.
	 * @return
	 */
	public static Vec2f getCenterOfScreen()
	{
		return centerPoint;
	}
	
	/**
	 * Returns the System time passed. It works just like
	 * System.nanoTime() but this method is from GLFW and
	 * returns a double.
	 * @return
	 */
	public static double getTime()
	{
		return GLFW.glfwGetTime();
	}
	
	/**
	 * Starts to update the game and window.
	 * VSync is automatically set to 1.
	 * This method can not be overridden as it
	 * keeps the window from freezing and keeping the game
	 * events in sync.
	 */
	protected static void update()
	{
		setVSync(1);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		while(!GLFW.glfwWindowShouldClose(window))
		{
			GLFW.glfwPollEvents();
			
			startTime = getTime();
			Input.update();
			
			double tempStartTime = getTime();
			Game.update();
			updateTime = getTime()-tempStartTime;
			
			tempStartTime = getTime();
			Game.render();
			renderTime = getTime()-tempStartTime;
			
			
			Input.reset();
			
			rawDeltaTime = getTime()-startTime;
			
			GLFW.glfwSwapBuffers(Display.getWindow());
			endTime = getTime();
			
			
			while((deltaTime = getTime()-startTime) < 1.0/(FPSCap))
			{
				//
			}
			
			passedTime += deltaTime;
			
			FPSCount++;
			
			if (passedTime>=1)
			{
				FPS = FPSCount;
				FPSCount=0;
				passedTime=0;
			}
			
			rawFPS = (1.0/rawDeltaTime);
			
			if(shouldPrintData==true)
				printSystemData();
		}
	}
	
	/**
	 * Destroys the window and resources tied to it.
	 * Call this method last.
	 */
	protected static void dispose()
	{
		GLFW.glfwDestroyWindow(window);
	}
}
