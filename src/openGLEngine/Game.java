package openGLEngine;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;

public class Game {
	
	private static ArrayList<parentGameObject> objectList = new ArrayList<parentGameObject>();
	private static ArrayList<parentGameObject> objectListSorted = new ArrayList<parentGameObject>();
	
	public static boolean mouseLock = false;
	
	private static Mat4f orthoMat;
	private static Mat4f projectionMat;
	private static Mat4f viewProjectionMat;
	
	public static Level currentLevel = new Level();
	public static Surface currentSurface;
	
	private static Surface applicationSurface;
	private static int renderWidth = Display.getWidth();
	private static int renderHeight = Display.getHeight();
	
	public static Shader currentShader;
	public static Camera currentCamera = new Camera(Camera.MODE_2D);
	
	public static String modelFolder = "Models";
	public static String fontFolder = "Fonts";
	public static String shaderFolder = "Shaders";
	public static String textureFolder = "Textures";
	
	public static final int NO_BLENDING = 0;
	public static final int NORMAL_BLENDING = 1;
	public static final int ADDITIVE_BLENDING = 2;
	public static final int SUBTRACTIVE_BLENDING = 3;
	public static final int SURFACE_DRAW = 4;
	private static int blendMode = 1;
	
	public static boolean is3DEnabled=false;
	
	private static boolean autoDrawSurface = true;
	private static boolean autoPreDraw = true;
	private static boolean autoRender = true;
	private static boolean autoUpdate = true;
	private static boolean autoSortObjects = true;
	
	private static parentGameObject controlObject;
	
	private static int newID = 0;
	
	private static ArrayList<GuiElement> allGuiElements = new ArrayList<GuiElement>();
	
	public static boolean closePopUp = false;
	public static String popUpString = "";
	
	/**
	 * Creates the window for the game at the requested width and height,
	 * Sets the windows title and call the start method.
	 * @param width
	 * @param height
	 * @param title
	 */
	public static void init(int width, int height, String title, boolean fullscreen)
	{
		if ( !glfwInit() )
		{
			throw new IllegalStateException("Failed to initialize GLFW!");
		}
		
		Display.createWindow(width, height, title, fullscreen);
		
		renderWidth = width;
		renderHeight = height;
		
		start();
		
	}
	
	/**
	 * Ends the game. Properly clears memory and closes the window.
	 * It clears out the TexturePages, anything is GameResources,
	 * anything in DefaultResources, all Sound stuff, and calls
	 * the destroy method of all objects in the Game object list.
	 */
	public static void end()
	{
		applicationSurface.dispose();
		
		TexturePage.dispose();
		
		GameResources.dispose();
		DefaultResources.dispose();
		
		currentLevel.dispose();
		
		if(controlObject!=null)
			controlObject.dispose();
		
		Sound.terminate();
		
		reset();
		
		Display.dispose();
		glfwTerminate();
	}
	
	/**
	 * Initializes several variables and initializes sound.
	 */
	public static void start()
	{
		Sound.initSound();
		GameRender.init();
		
		Game.enableCulling();
		Game.cullBackFaces();
		
		setBlending(NORMAL_BLENDING);
		
		applicationSurface = new Surface(Display.getWidth(), Display.getHeight(), Surface.COLOR_AND_DEPTH);
		
		applicationSurface.bind();
		
		createOrthoProjectionMatrix(0, 0, Display.getWidth(), Display.getHeight());
		create3DProjectionMatrix(60, Display.getWidth(), Display.getHeight(), 10, 1000 );
		
		currentShader = DefaultResources.defaultShader;
		
		currentShader.start();
		
		set2DBegin();
		
		currentShader.setUniform("modelMatrix", false, Mat4f.getIdentityMatrix());
		
		int f = currentShader.getUniform("color");
		GL20.glUniform4f(f, 1.0f, 1.0f, 1.0f, 1.0f);
		
		DefaultResources.defaultTexture.bind();
		
	}
	
	/**
	 * starts to run the game. Particularly just calls the 
	 * Display.update method.
	 */
	public static void run()
	{
		Display.update();
	}
	
	/**
	 * Changes the blending method used when drawing a pixel to the
	 * screen.
	 * Possible BlendTypes are as follows
	 * NO_BLENDING, NORMAL_BLENDING, ADDITIVE_BLENDING, SUBTRACTIVE_BLENDING,
	 * SURFACE_DRAW
	 * 
	 * SURFACE_DRAW is a special blending method that is used just for drawing
	 * a surface to another or to the screen.
	 * @param type
	 */
	public static void setBlending(int type)
	{
		switch(type)
		{
		case NO_BLENDING:
			GL11.glDisable(GL11.GL_BLEND);
			blendMode = type;
			break;
		case NORMAL_BLENDING:
			GL11.glEnable(GL11.GL_BLEND);
			GL14.glBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA,
					GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
			blendMode = type;
			break;
		case ADDITIVE_BLENDING:
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE); 
			blendMode = type;
			break;
		case SUBTRACTIVE_BLENDING:
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_ZERO, GL11.GL_ONE_MINUS_SRC_ALPHA);
			blendMode = type;
			break;
		case SURFACE_DRAW:
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
			blendMode = type;
			break;
		default:
			System.err.println("Blend mode: "+type+" does not exists");
			break;
		}
	}
	
	/**
	 * Gets the current blend mode used.
	 * @return
	 */
	public static int getBlendMode()
	{
		return blendMode;
	}
	
	/**
	 * Enables depth testing. Needed in 3d scenes so that things closer to
	 * the camera will appear in front of things further away.
	 */
	public static void enableDepthTesting()
	{
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
	
	public static void enableCulling()
	{
		GL11.glEnable(GL11.GL_CULL_FACE);
	}
	
	public static void disableCulling()
	{
		GL11.glDisable(GL11.GL_CULL_FACE);
	}
	
	public static void cullFrontFaces()
	{
		GL11.glCullFace(GL11.GL_FRONT);
	}
	
	public static void cullBackFaces()
	{
		GL11.glCullFace(GL11.GL_BACK);
	}
	
	/**
	 * Disables depth testing. Disable depth testing before drawing
	 * anything 2D.
	 */
	public static void disableDepthTesting()
	{
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		//GL11.glDepthMask(false); //completely Breaks depth testing. sugoi janai
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}
	
	/**
	 * Disables or Enables writing to the depth buffer.
	 * This function is here for completions sake and could break
	 * depth testing. Use when no 3d is needed.
	 * @param value
	 */
	public static void setDepthWriting(boolean value)
	{
		GL11.glDepthMask(value);
	}
	
	/**
	 * creates an orthographic matrix and sets the games global orthoMat.
	 * Calls a method from GameMath to create the orthographic matrix.
	 * An orthographic matrix is best used for 2D rendering.
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public static void createOrthoProjectionMatrix(int x, int y, int width, int height)
	{
		orthoMat = GameMath.createOrthographicMatrix(x, y, width, height);
	}
	
	/**
	 * Sets the games global orthographic Matrix
	 * @param m
	 */
	public static void setOrthoMatrix(Mat4f m)
	{
		orthoMat = m;
	}
	
	/**
	 * Sets the games global projection Matrix
	 * @param m
	 */
	public static void setProjectionMatrix(Mat4f m)
	{
		projectionMat = m;
	}
	
	/**
	 * creates an projection matrix and sets the games global projectionMat.
	 * Calls a method from GameMath to create the projection matrix.
	 * An projection matrix is best used for 3D rendering.
	 * @param fov
	 * @param width
	 * @param height
	 * @param zNear
	 * @param zFar
	 */
	public static void create3DProjectionMatrix(double fov, double width, double height, double zNear, double zFar)
	{
		projectionMat = GameMath.createProjectionMatrix(fov, width, height, zNear, zFar);
	}
	
	/**
	 * Sets the games global camera.
	 * @param c
	 */
	public static void setCamera( Camera c)
	{
		currentCamera=c;
	}
	
	/**
	 * gets the games global orthographic matrix
	 * @return
	 */
	public static Mat4f getOrthoProjectionMatrix()
	{
		return orthoMat;
	}
	
	/**
	 * gets the games global projection matrix
	 * @return
	 */
	public static Mat4f get3DProjectionMatrix()
	{
		return projectionMat;
	}
	
	/**
	 * gets the games global view matrix combined with a
	 * projection matrix. That projection matrix could be
	 * an 3D projection matrix or an orthographic matrix.
	 * @return
	 */
	public static Mat4f getViewProjectionMatrix()
	{
		return viewProjectionMat;
	}
	
	public static void setViewProjectionMatrix(Mat4f t)
	{
		viewProjectionMat = t;
	}
	
	/**
	 * Enables depth testing.
	 * Sets the games global view projection matrix.
	 * binds the view projection matrix to the currently bound
	 * shader using the uniform name "projectionMatrix".
	 */
	public static void set3DBegin()
	{
		is3DEnabled=true;
		enableDepthTesting();
		
		viewProjectionMat = GameMath.matrixMult(currentCamera.getViewMat(), projectionMat);
		
		currentShader.setUniform("projectionMatrix", false, viewProjectionMat);
	}
	
	/**
	 * Disable depth testing.
	 * Sets the games global view projection matrix.
	 * binds the view projection matrix to the currently bound
	 * shader using the uniform name "projectionMatrix".
	 */
	public static void set2DBegin()
	{
		is3DEnabled=false;
		disableDepthTesting();
		
		viewProjectionMat = GameMath.matrixMult(currentCamera.getViewMat(), orthoMat);
		
		currentShader.setUniform("projectionMatrix", false, viewProjectionMat);
	}
	
	/**
	 * Gets the default Surface used for rendering.
	 * @return
	 */
	public static Surface getApplicationSurface()
	{
		return applicationSurface;
	}
	
	/**
	 * Creates a orthographic matrix that is best used for drawing GUIs. It is based
	 * on the display width and height of the game. It has no additional transformations.
	 * @return
	 */
	public static Mat4f getDisplayOrthoMatrix()
	{
		return GameMath.createOrthographicMatrix(0, 0, renderWidth, renderHeight);
	}
	
	/**
	 * This method allows you to enable any update methods from being called
	 * automatically. This can be used as a method of further control over
	 * the Game loop. The update function for Game will call the update function
	 * for a control object set by the developer and no other.
	 * @param value
	 */
	public static void setAutoUpdate(boolean value)
	{
		autoUpdate = value;
	}
	
	/**
	 * This method allows you to enable any draw methods from being called
	 * automatically. This can be used as a method of further control over
	 * the Game loop. The render function for Game will call the draw function
	 * for a control object set by the developer and no other.
	 * @param value
	 */
	public static void setAutoRender(boolean value)
	{
		autoRender = value;
	}
	
	/**
	 * This method allows you to enable any preDraw methods from being called
	 * automatically. This can be used as a method of further control over
	 * the Game loop.
	 * @param value
	 */
	public static void setAutoPreDraw(boolean value)
	{
		autoPreDraw = value;
	}
	
	/**
	 * This method allows you to enable the default Surface applicationSurface
	 * from being drawn to the screen and prevents it from being bound before
	 * other draw methods are called.
	 * @param value
	 */
	public static void setAutoDrawSurface(boolean value)
	{
		autoDrawSurface = value;
	}
	
	/**
	 * This method allows you to enable automatic sorting of all Game objects
	 * before rendering. It sorts them based on a depth value given to each
	 * object.
	 * @param value
	 */
	public static void setAutoSortObjects(boolean value)
	{
		autoSortObjects = value;
	}
	
	/**
	 * Sets a controller object that can be used to further control the Game
	 * loop. 
	 * @param o
	 */
	public static void setControlObject(parentGameObject o)
	{
		controlObject = o;
	}
	
	public static parentGameObject getControlObject()
	{
		return controlObject;
	}
	
	/**
	 * Clears the applicationSurface and the Display.
	 * Calls all of the draw methods of the game objects.
	 * 
	 * Can disable preDraw methods being automatically drawn.
	 * Can disable automatic applicationSurface drawing if you don't use it.
	 * 
	 * Can completely bypass the render method for your own render method.
	 * It will only call the render method of the control object you specify.
	 */
	public static void render()
	{
		if(autoRender==true)
		{
			applicationSurface.unBind();
			Display.clearWindow();
			
			Surface oldSurface = currentSurface;
			
			applicationSurface.bind();
			applicationSurface.clear();
			
			if(oldSurface!=null)
			{
				if(!oldSurface.equals(applicationSurface))
					oldSurface.bind();
			}
			
			if(!debugClass.isInDebugMode())
			{
				if(autoPreDraw==true)
				{
					for(int i=0;i<objectListSorted.size();i++)
					{
						objectListSorted.get(i).preDraw();
					}
				}
				
				for(int i=0;i<objectListSorted.size();i++)
				{
					objectListSorted.get(i).draw();
				}
			}
			else
			{
				for(int i=0;i<objectListSorted.size();i++)
				{
					objectListSorted.get(i).debugDraw();
				}
			}
			
			debugClass.draw();
			
			
			if(autoDrawSurface==true)
			{
				applicationSurface.unBind();
				
				Vec4f c = GameRender.getColor();
				GameRender.setColor(1f, 1f, 1f, 1f);
				
				GameRender.drawSurface(applicationSurface, 0, 0);
				
				GameRender.setColor(c);
			}
			
			if(currentSurface!=null)
				currentSurface.unBind();
			
		}
		else
		{
			if(controlObject!=null)
				controlObject.draw();
		}
		
	}
	
	/**
	 * Calls all of the update methods of the game objects.
	 * Automatically sorts the Game objects by depth to prepare for
	 * rendering.
	 * 
	 * Can disable automatic sorting of objects by depth.
	 * 
	 * Can completely bypass the update method in favor of your own.
	 * It will only call the update method of the control object you
	 * specify.
	 */
	public static void update()
	{
		if(autoUpdate==true)
		{
			//debugClass.update();
			if(!debugClass.isPaused())
			{
				if(!debugClass.isInDebugMode())
				{
					for(int i=0;i<objectList.size();i++)
					{
						objectList.get(i).update();
					}
				}
				else
				{
					for(int i=0;i<objectList.size();i++)
					{
						objectList.get(i).debugUpdate();
					}
				}
				
				if(autoSortObjects==true)
				{
					sortByDepth();
				}
			}
		}
		else
		{
			if(controlObject!=null)
				controlObject.update();
		}
		
	}
	
	/**
	 * Creates a popUp window that you can type text into.
	 * @param width
	 * @param height
	 * @param title
	 * @return
	 */
	public static String createInputPopUp(String title)
	{
		
		JFrame frame = new JFrame();
		frame.setVisible(false);
		frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		String text = JOptionPane.showInputDialog(frame, title);
		
		frame.dispose();
		return text;
	}
	
	/**
	 * Creates a popUp window that you can type text into.
	 * @param width
	 * @param height
	 * @param title
	 * @return
	 */
	public static void createAlertPopUp(String title)
	{
		JFrame frame = new JFrame();
		frame.setVisible(false);
		frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JOptionPane.showMessageDialog(frame, title);
		frame.dispose();
	}
	
	/**
	 * resets the Game.
	 */
	public static void reset()
	{
		resetID();
		
		for(int i=0;i<objectList.size();i++)
		{
			objectList.get(i).destroy();
		}
		
		objectList.clear();
		objectListSorted.clear();
	}
	
	/**
	 * resets the starting id to 0.
	 */
	public static void resetID()
	{
		newID=0;
	}
	
	/**
	 * Returns the render width of the game. The render width is what the
	 * game will render at. This is not necessarily the same as the display
	 * width. This allows you to render at a lower resolution and up scale 
	 * for better performance or render at a higher resolution and down scale
	 * for anti aliasing.
	 * @return
	 */
	public static int getRenderWidth()
	{
		return renderWidth;
	}
	
	/**
	 * Sets the render width of the game. The render width is what the
	 * game will render at. This is not necessarily the same as the display
	 * width. This allows you to render at a lower resolution and up scale 
	 * for better performance or render at a higher resolution and down scale
	 * for anti aliasing.
	 * @return
	 */
	public static void setRenderWidth(int w)
	{
		renderWidth = w;
	}
	
	/**
	 * Returns the render height of the game. The render height is what the
	 * game will render at. This is not necessarily the same as the display
	 * height. This allows you to render at a lower resolution and up scale 
	 * for better performance or render at a higher resolution and down scale
	 * for anti aliasing.
	 * @return
	 */
	public static int getRenderHeight()
	{
		return renderHeight;
	}
	
	/**
	 * Sets the render height of the game. The render height is what the
	 * game will render at. This is not necessarily the same as the display
	 * height. This allows you to render at a lower resolution and up scale 
	 * for better performance or render at a higher resolution and down scale
	 * for anti aliasing.
	 * @return
	 */
	public static void setRenderHeight(int w)
	{
		renderHeight = w;
	}
	
	/**
	 * returns the objectList used by the Game. All Game Objects
	 * are stored in this. Unsorted by depth.
	 * @return
	 */
	public static ArrayList<parentGameObject> getRawObjectList()
	{
		return objectList;
	}
	
	/**
	 * returns the objectList used by the Game. All Game Objects
	 * are stored in this. sorted by depth.
	 * @return
	 */
	public static ArrayList<parentGameObject> getRawSortedObjectList()
	{
		return objectListSorted;
	}
	
	/**
	 * Adds a GuiElement into a global list so they can be updated by
	 * a control object.
	 * @param e
	 */
	public static void addGuiElement(GuiElement e)
	{
		allGuiElements.add(e);
	}
	
	/**
	 * Deletes a GuiElement in the global list of GuiElements.
	 * @param e
	 */
	public static void deleteGuiElement(GuiElement e)
	{
		int index = allGuiElements.indexOf(e);
		allGuiElements.remove(index);
	}
	
	/**
	 * Gets the size of the global list of GuiElements.
	 * @return
	 */
	public static int getGuiListSize()
	{
		return allGuiElements.size();
	}
	
	/**
	 * Gets a GuiElement at the location specified if it exists.
	 * @param i
	 * @return
	 */
	public static GuiElement getGuiElement(int i)
	{
		if(i>=0 && i<allGuiElements.size())
			return allGuiElements.get(i);
		else
			return null;
	}
	
	/**
	 * Sorts the GuiElement list by the Active GuiElement Object to
	 * help eliminate issues when updating and rendering.
	 */
	public static void sortGuiElements()
	{
		ArrayList<GuiElement> newList = new ArrayList<GuiElement>();
		
		for(int i=0; i<allGuiElements.size(); i++)
		{
			if(allGuiElements.get(i).getActive())
			{
				newList.add(allGuiElements.get(i));
				break;
			}
		}
		
		for(int i=0; i<allGuiElements.size(); i++)
		{
			if(!allGuiElements.get(i).getActive())
				newList.add(allGuiElements.get(i));
		}
		
		allGuiElements = newList;
	}
	
	/**
	 * Adds an object to the Game object list. These objects will be
	 * updated and rendered.
	 * @param o
	 */
	public static void addObject(parentGameObject o)
	{
		objectList.add(o);
		o.setID(newID);
		newID+=1;
	}
	
	/**
	 * Gets an Game object in the unsorted list at the specified location.
	 * @param i
	 * @return
	 */
	public static parentGameObject findObject(int i)
	{
		if(i<objectList.size())
		{
			return objectList.get(i);
		}
		else
		{
			//System.err.println("OUT OF BOUNDS ERROR");
			return null;
		}
	}
	
	/**
	 * Gets an Game object in the sorted list at the specified location.
	 * @param i
	 * @return
	 */
	public static parentGameObject findObjectSorted(int i)
	{
		if(i<objectListSorted.size())
		{
			return objectListSorted.get(i);
		}
		else
		{
			//System.err.println("OUT OF BOUNDS ERROR");
			return null;
		}
	}
	
	/**
	 * Gets the size of the Games object list.
	 * @return
	 */
	public static int getObjectListSize()
	{
		return objectList.size();
	}
	
	/**
	 * clears all of the objects out of the Games object list.
	 * Calls the dispose() method of every object in the list before
	 * clearing the list to avoid memory leaks.
	 */
	public static void clearObjectList()
	{
		for(int i=0;i<objectList.size();i++)
		{
			destroyObject(i);
		}
		objectList.clear();
	}
	
	
	/**
	 * Destroys an object at the specified location in the Games object
	 * list. Calls the objects dispose() method before fully removing it
	 * to avoid memory leaks.
	 * @param index
	 */
	public static void destroyObject(int index)
	{
		try
		{
			objectList.remove(index).dispose();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Destroys the object in the Games object list.
	 * Calls the objects dispose() method before fully removing it
	 * to avoid memory leaks.
	 * @param o
	 */
	public static void destroyObject(parentGameObject o)
	{
		try
		{
			objectList.remove( objectList.indexOf(o) ).dispose();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * determines if an object of the specified class exists in
	 * the Games object list.
	 * @param o
	 * @return
	 */
	public static boolean objectExists(Class o)
	{
		String name = o.toString();
		for(int i=0;i<objectList.size();i++)
		{
			if ( objectList.get(i).getClass().toString().equals(name) )
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Find and return a object of the specified class.
	 * Finds the object specified by objectNumber. Others before are
	 * skipped. If the object at that position does not exist, null will be
	 * returned.
	 * 
	 * Returns a parentGameObject, but should be cast to the object you searched for.
	 * 
	 * objectNumber starts at 0.
	 * 
	 * Example: to find the second version of an object named Particle, use
	 * 			Particle p = (Particle) findObject(Particle.class, 1);
	 * 
	 * Example2: to find the first version of an object named OtherThingObject, use
	 * 			OtherThingObject o = (OtherThingObject) findObject( OtherThingObject.class, 0 );
	 * @param o
	 * @param objectNumber
	 * @return
	 */
	public static parentGameObject findObject(Class o, int objectNumber)
	{
		int num=0;
		Class c;
		parentGameObject object;
		boolean objectFound = false;
		for(int i=0;i<objectList.size();i++)
		{
			object = objectList.get(i);
			c = object.getClass();
			
			do
			{
				if( o.toString().equals( c.toString() ) == true )
				{
					if(num==objectNumber)
					{
						objectFound = true;
						break;
					}
					else
					{
						num++;
						break;
					}
				}
				else
				{
					c = c.getSuperclass();
				}
			}
			while ( !c.toString().equals( parentGameObject.class.toString() ) );
			
			if(objectFound==true)
				return object;
		}
		
		//System.err.println("OBJECT DOES NOT EXISTS. POSSIBLE OUT OF BOUNDS ERROR.");
		return null;
	}
	
	/**
	 * Find and return a object of the specified class. The object returned
	 * will be the first one found. If the object does not exist, null will be
	 * returned.
	 * 
	 * Returns a parentGameObject, but should be cast to the object you searched for.
	 * 
	 * Example: Particle p = (Particle) findObject( Particle.class );
	 * Example2: OtherThingObject o = (OtherThingObject) findObject( OtherThingObject.class );
	 * @param o
	 * @return
	 */
	public static parentGameObject findObject(Class o)
	{
		int num=0;
		Class c;
		parentGameObject object;
		boolean objectFound = false;
		for(int i=0;i<objectList.size();i++)
		{
			object = objectList.get(i);
			c = object.getClass();
			
			do
			{
				
				if( o.toString().equals( c.toString() ) == true )
				{
					objectFound = true;
					break;
				}
				else
				{
					c = c.getSuperclass();
				}
			}
			while ( !c.toString().equals( parentGameObject.class.toString() ) );
			
			if(objectFound==true)
				return object;
		}
		
		//System.err.println("OBJECT DOES NOT EXISTS. POSSIBLE OUT OF BOUNDS ERROR.");
		return null;
	}
	
	/**
	 * Find an object in the Game's object List by the objects id value.
	 * The id value is a long and can be specified in the level editor.
	 * @param id
	 * @return
	 */
	public static parentGameObject findById(long id)
	{
		for(int i=0;i<objectList.size();i++)
		{
			if ( objectList.get(i).getId() == id )
			{
				return objectList.get(i);
			}
		}
		
		//System.err.println("OBJECT DOES NOT EXISTS. POSSIBLE OUT OF BOUNDS ERROR.");
		return null;
	}
	
	/**
	 * Deletes an object in the Game's object List by the objects id value.
	 * The id value is a long and can be specified in the level editor.
	 * @param id
	 * @return
	 */
	public static void destroyObjectById(long id)
	{
		for(int i=0;i<objectList.size();i++)
		{
			if( objectList.get(i).getId() == id)
			{
				destroyObject(objectList.get(i));
			}
		}
	}
	
	/**
	 * Sorts all of the Game objects by their depth to prepare for rendering.
	 * This is not exactly needed in 3D rendering, however when rendering
	 * transparency, it is needed.
	 * This is needed for 2D rendering. Keeps objects in front of objects they
	 * are supposed to be in front of.
	 */
	public static void sortByDepth()
	{
		objectListSorted.clear();
		
		boolean sorted=false;
		for(int i=0;i<objectList.size();i++)
		{
			parentGameObject currObj = objectList.get(i);
			
			sorted=false;
			
			for(int i2=0;i2<objectListSorted.size();i2++)
			{
				if ( objectListSorted.get(i2).depth <= currObj.depth)
				{
					objectListSorted.add(i2,currObj);
					sorted=true;
					break;
				}
			}
			if(sorted==false)
			{
				objectListSorted.add(currObj);
			}

		}
		
	}
}
