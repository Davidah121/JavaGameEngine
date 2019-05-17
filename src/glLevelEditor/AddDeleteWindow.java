package glLevelEditor;

import java.util.ArrayList;

import openGLEngine.*;

public class AddDeleteWindow extends InputWindow {

	private parentGameObject currentSelectedObject = null;
	private int currentInstanceID = -1;
	private int currentObjectID = -1;
	private parentGameObject currentHoverObj = null;
	
	private int addObjectID = -1;
	
	private parentGameObject justAddedObject = null;
	
	private int mode = -1; //1: add/delete mode, 2: edit mode, 3: tile mode
	
	//A unit is arbitrary
	
	private int gridXSize = 32; // every 32 units
	private int gridYSize = 32; // every 32 units
	private int zoom = 0; //by a whole unit
	
	private int gridMaxSize = 128; //128 x 128 grid
	
	//Must be 8 units between each grid to be visible
	//Please note that this does not prevent gridXSize from being 1.
	private int gridMinSizeDis = 8;
	
	private boolean gridAdd = true;
	
	private Vec2f tempMousePos = new Vec2f(0,0);
	private Vec3f tempCamPos = new Vec3f(0,0,0);
	
	private int moveSpeed = 1;
	
	private Level myLevel;
	
	private Model gridModel = new Model();
	
	private Mat4f proMat = Mat4f.getIdentityMatrix();
	private Mat4f scaleMat = Mat4f.getIdentityMatrix();
	
	private int addX, addY;
	private int preX, preY;
	
	//How many objects to skip over when you select objects at the same place
	private int skipObjects = 0; 
	private boolean moveMode = false;
	
	public AddDeleteWindow(int x, int y, int width, int height)
	{
		position.x = width/2;
		position.y = height/2;
		
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		
		init();
		setActive(true);
		setVisible(true);
		
		scaleMat = new Mat4f( (double)renderWidth/width, 0, 0, 0,
									0, (double)renderHeight/height, 0, 0,
									0, 0, 1, 0,
									0, 0, 0, 1
									);
		proMat = GameMath.createOrthographicMatrix(0, 0, renderWidth, renderHeight);
		
		proMat = GameMath.matrixMult(scaleMat, proMat);
		
		createGridModel(gridXSize, gridYSize);
		
	}
	
	public void setObjectID(int oid)
	{
		addObjectID = oid;
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
			zoom+=1;
			
			if(zoom>=3)
			{
				zoom = 3;
			}
			createGridModel(gridXSize, gridYSize);
			//GameWindow.createGridModel(gridXSize, gridYSize, zoom);
		}
		else if(Input.getMouseWheelDown())
		{
			zoom-=1;
			
			if(zoom<=-3)
			{
				zoom = -3;
			}
			createGridModel(gridXSize, gridYSize);
			//GameWindow.createGridModel(gridXSize, gridYSize, zoom);
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
		
		double tempX = ((getMouseXRelative()-position.x)/GameMath.pow(1.5, zoom));
		double tempY = ((getMouseYRelative()-position.y)/GameMath.pow(1.5, zoom));
		
		skipObjects = 1;
		int objsPassed = 0;
		parentGameObject preObject = null;
		
		for(int i=0; i<myLevel.getObjectListSize(); i++)
		{
			parentGameObject tempObject = myLevel.getObject(i);
			
			tempObject.getCollisionHull().setPositionVector(tempObject.getPosition());
			tempObject.getCollisionHull().updateCollisionHull();
			
			boolean col = GameLogic.getCollision(tempObject.getCollisionHull(), new Point(tempX, tempY));
			
			if(col)
			{
				preObject = tempObject;
				
				if(skipObjects==objsPassed)
				{
					currentHoverObj = tempObject;
					break;
				}
				else
				{
					objsPassed++;
				}
			}
			
			//Hit the end of the list
			if(i==myLevel.getObjectListSize()-1)
			{
				currentHoverObj = preObject;
				break;
			}
		}
		
		if(Input.getMouseButtonPressed(Input.LEFT_MOUSE_BUTTON) && currentHoverObj!=null)
		{
			currentSelectedObject = currentHoverObj;
			currentInstanceID = currentHoverObj.getId();
			moveMode = true;
			
			preX = addX;
			preY = addY;
		}
		
		if(currentSelectedObject!=null && moveMode==true)
		{
			currentSelectedObject.getPosition().x = preX+(addX-preX);
			currentSelectedObject.getPosition().y = preY+(addY-preY);
			currentSelectedObject.getPosition().z = 0;
		}
		
		if(Input.getMouseButtonUp(Input.LEFT_MOUSE_BUTTON))
		{
			moveMode = false;
		}
	}
	
	private void addDeleteMode()
	{
		Class whatToAdd = Level.getObjectClass(addObjectID);
		parentGameObject newObject = null;
		
		if(whatToAdd!=null)
		{
			if(Input.getMouseButtonPressed(Input.LEFT_MOUSE_BUTTON) && BasicEditor.controlObject.getActiveWindow()==2)
			{
				try
				{
					newObject = (parentGameObject)whatToAdd.newInstance();
					newObject.setPosition( new Vec3f(addX, addY, 0) );
					myLevel.addObject(newObject);
					
					currentSelectedObject = newObject;
					justAddedObject = newObject;
					
					if(guiField.size()>0)
					{
						newObject.guiField.get(0).setExpand(true);
					}
				}
				catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		if(justAddedObject!=null)
		{
			currentSelectedObject = justAddedObject;
			
			justAddedObject.getPosition().x = addX;
			justAddedObject.getPosition().y = addY;
			justAddedObject.getPosition().z = 0;
			
			if(Input.getMouseButtonUp(Input.LEFT_MOUSE_BUTTON))
			{
				justAddedObject = null;
			}
		}
		else if(Input.getMouseButtonPressed(Input.RIGHT_MOUSE_BUTTON))
		{
			if(currentHoverObj!=null)
			{
				myLevel.deleteObject( currentHoverObj );
				//myLevel.destroyObject( currentSelectedObject );
			}
		}
	}
	
	@Override
	public void thisUpdate() {
		// TODO Auto-generated method stub
		
		addX = (int) ((getMouseXRelative()-position.x)/GameMath.pow(1.5, zoom));
		addY = (int) ((getMouseYRelative()-position.y)/GameMath.pow(1.5, zoom));
		
		if(gridAdd == true)
		{
			addX = (int)GameMath.round((double)addX/gridXSize)*gridXSize;
			addY = (int)GameMath.round((double)addY/gridYSize)*gridYSize;
		}
		
		moveMode();
		selectMode();
		
		addDeleteMode();
		
		BasicEditor.controlObject.setObject(currentSelectedObject);
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
	public void render() {
		// TODO Auto-generated method stub
		
		windowSurface.clear();
		
		//Game.currentCamera.setPosition(position.x+(Display.getWidth()/2), position.y+(Display.getHeight()/2), position.z);
		//Game.set2DBegin();
		
		Camera c = new Camera(Camera.MODE_2D);
		c.setPosition(position.x, position.y, position.z);
		
		Mat4f finalMat = GameMath.matrixMult(c.getViewMat(), proMat);
		
		double powOfZoom = GameMath.pow(1.5, zoom);
		//System.out.println(powOfZoom);
		
		finalMat.setValue( finalMat.getValue(0, 0)*powOfZoom, 0, 0);
		finalMat.setValue( finalMat.getValue(1, 1)*powOfZoom, 1, 1);
		
		Game.setViewProjectionMatrix( finalMat );
		
		Vec3f offset = new Vec3f(0,0,0);
		
		offset.x = gridXSize * (int)(-position.x/(gridXSize*powOfZoom));
		offset.y = gridYSize * (int)(-position.y/(gridYSize*powOfZoom));
		
		drawGrid(offset);
		
		//GameRender.drawCircle(64, 64, 5, false);
		GameRender.drawCircle(addX, addY, 4, false);
		drawLevelObjects();
		
		Game.set2DBegin();
		
		//GameRender.drawText("TEMPX: "+(addX), 0, 0);
		//GameRender.drawText("TEMPY: "+(addY), 0, 24);
		//GameRender.drawText("ZOOM: "+zoom, 0, 48);
	}

	public void createGridModel(int gridXSize, int gridYSize)
	{
		
		ArrayList<Double> position = new ArrayList<Double>();
		
		if(gridXSize>gridMinSizeDis
			&& gridYSize>gridMinSizeDis)
		{
			for(int i=0; i<gridMaxSize; i++)
			{
				position.add( (double)(i*(gridXSize)) );
				position.add( (double)(-gridMaxSize*(gridYSize)));
				position.add( 0.0 );
				
				position.add( (double)(i*(gridXSize)) );
				position.add( (double)(gridYSize*(gridMaxSize)) );
				position.add( 0.0 );
				
				position.add( (double)(gridXSize*(-gridMaxSize)) );
				position.add( (double)(i*(gridYSize)) );
				position.add( 0.0 );
				
				position.add( (double)(gridXSize*(gridMaxSize)) );
				position.add( (double)(i*(gridYSize)) );
				position.add( 0.0 );
			}
		}
		
		gridModel.resetModel();
		
		gridModel.storeDataDouble(0, position, 3);
		gridModel.setDrawType(Model.DRAW_TYPE_LINES);
		gridModel.setFillType(Model.FILL_TYPE_LINE);
	}
	
	public void drawGrid(Vec3f offset)
	{
		
		GameRender.setColor(0, 0, 0, 1);
		
		//System.out.println(offset.x+","+offset.y);
		Mat4f tempModelMat = GameMath.createModelMatrix(offset.x, offset.y, offset.z, 0, 0, 0, 1, 1, 1);
		
		Game.currentShader.setUniform("modelMatrix", true, tempModelMat);
		gridModel.draw();
		Game.currentShader.setUniform("modelMatrix", true, Mat4f.getIdentityMatrix());
		//X-Axis
		GameRender.setColor(1f, 0f, 0f, 1f);
		GameRender.drawLine(-10000, 0, 10000, 0);
		
		//Y-Axis
		GameRender.setColor(0f, 1f, 0f, 1f);
		GameRender.drawLine(0, -10000, 0, 10000);
		
	}
}
