package glLevelEditor;

import openGLEngine.*;

public class AddDeleteWindow extends InputWindow {

	private parentGameObject currentSelectedObject = null;
	private int currentInstanceID = -1;
	private int currentObjectID = -1;
	
	private int addObjectID = -1;
	
	private int mode = -1; //1: add/delete mode, 2: edit mode, 3: tile mode
	
	//A unit is arbitrary
	
	private int gridXSize = 32; // every 32 units
	private int gridYSize = 32; // every 32 units
	private int zoom = 1; //by a whole unit
	
	private int gridMaxSize = 128; //128 x 128 grid
	
	//Must be 8 units between each grid to be visible
	//Please note that this does not prevent gridXSize from being 1.
	private int gridMinSizeDis = 8;
	
	private Vec2f tempMousePos = new Vec2f(0,0);
	private Vec3f tempCamPos = new Vec3f(0,0,0);
	
	private int moveSpeed = 1;
	
	private Level myLevel;
		
	public AddDeleteWindow(int x, int y, int width, int height)
	{
		position.x = Display.getWidth()/2;
		position.y = Display.getHeight()/2;
		
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		
		init();
		setActive(true);
		setVisible(true);
		
		GameWindow.createGridModel(gridXSize, gridYSize, zoom);
		
	}
	
	public void setLevel(Level t)
	{
		myLevel = t;
	}
	
	private void moveMode()
	{
		if(Input.getMouseButtonPressed(Input.MIDDLE_MOUSE_BUTTON))
		{
			tempMousePos.x = getMouseXRelative();
			tempMousePos.y = getMouseYRelative();
			tempCamPos.x = position.x;
			tempCamPos.y = position.y;
			tempCamPos.z = position.z;
		}
		
		if(Input.getMouseButtonDown(Input.MIDDLE_MOUSE_BUTTON))
		{
			position.x = tempCamPos.x + (getMouseXRelative() - tempMousePos.x);
			position.y = tempCamPos.y + (getMouseYRelative() - tempMousePos.y);
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
	public void thisUpdate() {
		// TODO Auto-generated method stub
		moveMode();
		selectMode();
		
		if(mode==0)
			addDeleteMode();
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
		windowSurface.clear();
		GameWindow.drawGrid(position, gridXSize, gridYSize, zoom);
		
	}

}
