package glLevelEditor;

import java.util.*;

import openGLEngine.*;

public class BasicEditor extends parentGameObject {
	
	private parentGameObject currentSelectedObject = null;
	private int currentInstanceID = -1;
	private int currentObjectID = -1;
	
	private int addObjectID = -1;
	
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
	
	private Level myLevel = new Level();
	
	private AddDeleteWindow myV = new AddDeleteWindow(8,8,952,540);
	private GuiWindow myG = new GuiWindow(972, 8, 1274-972, 540);
	
	public BasicEditor()
	{
		//position.x = Display.getWidth()/2;
		//position.y = Display.getHeight()/2;
		myV.setLevel(myLevel);
		
		myV.setAlwaysRender(true);
		myG.setAlwaysRender(true);
	}
	
	public void addObject(parentGameObject t)
	{
		myLevel.addObject( t );
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
	
	private void selectMode()
	{
		double tempX = Input.getMouseX()-position.x;
		double tempY = Input.getMouseY()-position.y;
		
		currentSelectedObject = null;
		
		for(int i=0; i<myLevel.getObjectListSize(); i++)
		{
			parentGameObject tempObject = myLevel.getObject(i);
			
			double sqrX = GameMath.sqr(tempObject.getPosition().x - tempX);
			double sqrY = GameMath.sqr(tempObject.getPosition().y - tempY);
			
			if(sqrX + sqrY <= tempObject.approxArea)
			{
				currentSelectedObject = tempObject;
				currentInstanceID = tempObject.getId();
				break;
			}
		}
	}
	
	private void addDeleteMode()
	{
		Class whatToAdd = Level.getObjectClass(addObjectID);
		parentGameObject newObject = null;
		
		if(whatToAdd!=null)
		{
			if(Input.getMouseButtonPressed(Input.LEFT_MOUSE_BUTTON))
			{
				try
				{
					newObject = (parentGameObject)whatToAdd.newInstance();
					newObject.setPosition( new Vec3f(position) );
					Game.addObject(newObject);
				}
				catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		if(Input.getMouseButtonPressed(Input.RIGHT_MOUSE_BUTTON))
		{
			Game.destroyObject( currentSelectedObject );
			currentInstanceID = -1;
			currentSelectedObject = null;
		}
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
		//moveMode();
		//
		
		if(mode == 1)
		{
			//addDeleteMode();
		}
		else if(mode == 2)
		{
			//selectMode();
		}
			
		/*
		for(int i=0; i<objectList.size(); i++)
		{
			objectList.get(i).guiUpdate();
		}
		*/
	}
	
	public void drawLevelObjects()
	{
		for(int i=0; i<myLevel.getObjectListSize(); i++)
		{
			parentGameObject tempObject = myLevel.getObject(i);
			tempObject.levelEditDraw();
			
			GameRender.setColor(1.0f, 0.0f, 0.0f, 1.0f);
			GameRender.drawCircle(tempObject.getPosition().x, tempObject.getPosition().y
					, GameMath.sqrt(tempObject.approxArea), false);
		}
	}
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
		
		Game.getApplicationSurface().clear();
		Display.clearWindow();
		
		//GameWindow.drawGrid(position, gridXSize, gridYSize, zoom);
		
		myV.update();
		myV.draw();
		
		myG.update();
		myG.draw();
		
		/*
		for(int i=0; i<objectList.size(); i++)
		{
			objectList.get(i).levelEditDraw();
		}
		*/
	}

	public void destroy()
	{
		
	}
}
