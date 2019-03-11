package glLevelEditor;

import java.util.*;

import openGLEngine.*;

public class TempLevelEditObject extends parentGameObject {
	
	private parentGameObject currentSelectedObject = null;
	private int currentInstanceID = -1;
	private int currentObjectID = -1;
	
	private int mode = -1; //1: add/delete mode, 2: edit mode, 3: tile mode
	
	//A unit is arbitrary
	private int gridXSize = 32; // every 32 units
	private int gridYSize = 32; // every 32 units
	private int zoom = 0; //by a whole unit
	
	private int gridMaxSize = 128; //128 x 128 grid
	
	//Must be 8 units between each grid to be visible
	//Please note that this does not prevent gridXSize from being 1.
	private int gridMinSizeDis = 8;
	
	private Vec2f tempMousePos = new Vec2f(0,0);
	private Vec3f tempCamPos = new Vec3f(0,0,0);
	
	private int moveSpeed = 1;
	
	//Need four surfaces for different parts
	
	//Handled by GameWindow
	//private Surface gameSurface = new Surface(640,480, Surface.COLOR_AND_DEPTH);
	
	//Handled by GameStats
	//private Surface gameStatsSurface = new Surface(640,480,Surface.COLOR);
	
	
	//private Surface gameEditorSurface = new Surface(640,480,Surface.COLOR);
	//private Surface gameSettingsSurface = new Surface(640,480,Surface.COLOR);
	
	public TempLevelEditObject()
	{
		position.x = Display.getWidth()/2;
		position.y = Display.getHeight()/2;
		
		GameStats.init(0, (int)(Display.getHeight()*.8), (int)(Display.getWidth()*.8), (int)(Display.getHeight()*.8));
		GameWindow.init(0, 0, (int)(Display.getWidth()*.75), (int)(Display.getHeight()*.75));
	}
	
	public void getObject()
	{
		
	}
	
	private void moveMode()
	{
		if(Input.getMouseButtonPressed(Input.MIDDLE_MOUSE_BUTTON))
		{
			tempMousePos.x = Input.getMouseX();
			tempMousePos.y = Input.getMouseY();
			tempCamPos.x = position.x;
			tempCamPos.y = position.y;
			tempCamPos.z = position.z;
		}
		
		if(Input.getMouseButtonDown(Input.MIDDLE_MOUSE_BUTTON))
		{
			position.x = tempCamPos.x + (Input.getMouseX() - tempMousePos.x);
			position.y = tempCamPos.y + (Input.getMouseY() - tempMousePos.y);
		}
		
		if(Input.getMouseWheelUp())
		{
			zoom++;
			GameWindow.createGridModel(gridXSize, gridYSize, zoom);
		}
		else if(Input.getMouseWheelDown())
		{
			zoom--;
			if(zoom<0)
			{
				zoom=0;
			}
			GameWindow.createGridModel(gridXSize, gridYSize, zoom);
		}
		
		if(Input.getKeyDown(Input.VK_SHIFT))
		{
			moveSpeed = 10;
		}
		else
		{
			moveSpeed = 1;
		}
		
		if(Input.getKeyDown(Input.VK_UP))
		{
			position.y+=moveSpeed;
		}
		else if(Input.getKeyDown(Input.VK_DOWN))
		{
			position.y-=moveSpeed;
		}
		if(Input.getKeyDown(Input.VK_LEFT))
		{
			position.x+=moveSpeed;
		}
		else if(Input.getKeyDown(Input.VK_RIGHT))
		{
			position.x-=moveSpeed;
		}
	
	}
	
	private void addDeleteMode()
	{
		//Level.types.get(objectID);
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
		moveMode();
		
		if(mode == 1)
		{
			addDeleteMode();
		}
			
		/*
		for(int i=0; i<objectList.size(); i++)
		{
			objectList.get(i).guiUpdate();
		}
		*/
	}
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
		Game.getApplicationSurface().clear();
		Display.clearWindow();
		
		GameRender.setColor(1, 1, 1, 1);
		
		GameWindow.render(position, gridXSize, gridYSize, zoom);
		GameStats.render();
		
		/*
		for(int i=0; i<objectList.size(); i++)
		{
			objectList.get(i).levelEditDraw();
		}
		*/
	}

}
