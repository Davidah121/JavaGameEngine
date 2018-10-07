package openGLEngine;

import java.util.ArrayList;
import java.util.Scanner;

import org.lwjgl.opengl.GL11;

public class LevelEditObject extends parentGameObject {

	private static Level editLevel = new Level();
	private double xRot = 0;
	private double yRot = 0;
	private double zRot = 0;
	
	private Vec3f mouseCoords = new Vec3f(0,0,0);
	
	private boolean mouseLock = false;
	
	private Camera Cam2D = new Camera(Camera.MODE_2D);
	public Camera Cam3D = new Camera(Camera.MODE_3D);
	
	public static boolean MODE_2D = false;
	public static boolean MODE_3D = true;
	
	public static final int ADD_DELETE_MODE = 0;
	public static final int SELECT_MODE = 1;
	public static final int BACKGROUND_MODE = 2;
	public static final int OBJECT_SPECIFIC_MODE = 3;
	public static final int SELECT_IMAGE_MODE = 4;
	
	private boolean mode = MODE_2D;
	private int editMode = ADD_DELETE_MODE;
			
	private Vec2f gridSize = new Vec2f(32, 32);
	private Model gridModel = new Model(Model.TYPE_DYNAMIC);
	
	private Surface guiSurface = new Surface(Display.getWidth(), Display.getHeight(), Surface.COLOR, Surface.LINEAR_FILTERING);
	private Surface newAppSurface = new Surface(Display.getWidth(), Display.getHeight(), Surface.COLOR_AND_DEPTH, Surface.NEAR_FILTERING);
	
	private float zoom = 1;
	private int zoomSliderValue = 5;
	private double startX = 0;
	private double startY = 0;
	
	public long selectedId = -1;
	private int selectedObject = 0;
	private boolean canEdit = true;
	
	private parentGameObject tempObject = null;
	
	private Sprite nullIcon = new Sprite();
	
	private ArrayList<TileMap> tiles = new ArrayList<TileMap>();
	private ArrayList<String> tileDepths = new ArrayList<String>();
	
	private int selectedLayer = 0;
	private TileMap selectedTileMap = null;
	private int selectedTile = -1;
	private Sprite selectedImage = GameResources.getSprite(0);
	
	public LevelEditObject()
	{
		createGridModel();
		
		Cam3D.setPosition(position);
		
		Input.setMousePos(Display.getWidth()/2, Display.getHeight()/2);
		
		Game.setAutoRender(false);
		Game.setControlObject(this);
		
		guiInit();
		
	}
	
	private void guiInit()
	{
		editParts.add( GuiElement.createList(Display.getWidth()-192, 32, Display.getWidth()-32, 64, Level.typeString));
		//editParts.get(0).setDescription("Selected Object");
		
		
		///Background Edit
		//Layer List
		//new Layer button
		//delete Layer button
		//change Depth button
		
		editParts.add( GuiElement.createList(Display.getWidth()-192, 32, Display.getWidth()-32, 64, tileDepths)); //List of depths where tiles are used
		editParts.get(13).setVisible(false);
		
		editParts.add( GuiElement.createButton(Display.getWidth()-192, 76, Display.getWidth()-160, 92)); //Add Tile Button
		editParts.get(14).setDescription("Add TileMap");
		editParts.get(14).setDescriptionOffset(36, -4);
		editParts.get(14).setFontColor(new Vec4f(.6f, .2f, .2f, 1f));
		editParts.get(14).setVisible(false);
		
		editParts.add( GuiElement.createButton(Display.getWidth()-192, 96, Display.getWidth()-160, 112)); //Delete Tile Button
		editParts.get(15).setDescription("Delete TileMap: ");
		editParts.get(15).setDescriptionOffset(36, -4);
		editParts.get(15).setFontColor(new Vec4f(.6f, .2f, .2f, 1f));
		editParts.get(15).setVisible(false);
		
		editParts.add( GuiElement.createButton(Display.getWidth()-192, 116, Display.getWidth()-160, 132)); //Change Tile Depth Button
		editParts.get(16).setDescription("Change TileMap Depth: ");
		editParts.get(16).setDescriptionOffset(36, -4);
		editParts.get(16).setFontColor(new Vec4f(.6f, .2f, .2f, 1f));
		editParts.get(16).setVisible(false);
		
		
		///Tile Map Information for that layer
		//x value of a particular tile
		//y value of a particular tile
		//x scale of a particular tile
		//y scale of a particular tile
		//visible value of a particular tile
		
		editParts.add( GuiElement.createButton(Display.getWidth()-192, 156, Display.getWidth()-160, 172)); //Tile X
		editParts.get(17).setDescription("Tile X: ");
		editParts.get(17).setDescriptionOffset(36, -4);
		editParts.get(17).setFontColor(new Vec4f(.6f, .2f, .2f, 1f));
		editParts.get(17).setVisible(false);
		
		editParts.add( GuiElement.createButton(Display.getWidth()-192, 176, Display.getWidth()-160, 192)); //Tile Y
		editParts.get(18).setDescription("Tile Y: ");
		editParts.get(18).setDescriptionOffset(36, -4);
		editParts.get(18).setFontColor(new Vec4f(.6f, .2f, .2f, 1f));
		editParts.get(18).setVisible(false);
		
		editParts.add( GuiElement.createButton(Display.getWidth()-192, 196, Display.getWidth()-160, 212)); //Tile XScale
		editParts.get(19).setDescription("Tile XScale: ");
		editParts.get(19).setDescriptionOffset(36, -4);
		editParts.get(19).setFontColor(new Vec4f(.6f, .2f, .2f, 1f));
		editParts.get(19).setVisible(false);
		
		editParts.add( GuiElement.createButton(Display.getWidth()-192, 216, Display.getWidth()-160, 232)); //Tile YScale
		editParts.get(20).setDescription("Tile YScale: ");
		editParts.get(20).setDescriptionOffset(36, -4);
		editParts.get(20).setFontColor(new Vec4f(.6f, .2f, .2f, 1f));
		editParts.get(20).setVisible(false);
		
	}
	
	private void createGridModel()
	{
		//gridSize of 1 is visible at min zoom of 8
		//gridSize of 2 is visible at min zoom of 4
		//gridSize of 4 is visible at min zoom of 2
		//gridSize of 8 is visible at min zoom of 1
		
		int newGridSizeX = (int)GameMath.round(gridSize.x*zoom);
		int newGridSizeY = (int)GameMath.round(gridSize.y*zoom);
		
		//System.out.println(newGridSizeX);
		
		gridModel.resetModel();
		
		if(newGridSizeX >= 4 && newGridSizeY >= 4)
		{
			
			ArrayList<Float> position = new ArrayList<Float>();
			
			gridModel.setDrawType(GL11.GL_LINES);
			gridModel.setFillType(GL11.GL_LINE);
			
			for(int i=0;i<=editLevel.width*zoom;i+=newGridSizeX)
			{
				position.add((float)i); position.add(0f); position.add(0f);
				
				position.add((float)i); position.add(editLevel.height*zoom); position.add(0f);
				
			}
			
			for(int i2=0;i2<=editLevel.height*zoom;i2+=newGridSizeY)
			{
				position.add(0f); position.add((float)i2); position.add(0f);
				
				position.add(editLevel.width*zoom); position.add((float)i2); position.add(0f);
				
			}
			
			gridModel.storeDataFloat(0,position,3);
			position.clear();
			
		}
		else
		{
			
			ArrayList<Float> position = new ArrayList<Float>();
			
			gridModel.setDrawType(GL11.GL_LINES);
			gridModel.setFillType(GL11.GL_LINE);
			
			position.add(0f); position.add(0f); position.add(0f);
			
			gridModel.storeDataFloat(0,position,3);
			position.clear();
			
		}
	}
	
	private void set3DMouseCoords()
	{
		//Get the x and y mouse coordinates in clipspace / between -1.0 and 1.0
		double tempX = ( (Input.getMouseX() - Display.getWidth() )/(double)Display.getWidth())*2.0;
		tempX = -tempX-1.0;
		double tempY = ( (Input.getMouseY() - Display.getHeight() )/(double)Display.getHeight())*2.0;
		tempY = -tempY-1.0;
		
		//First we setup some coordinates to rotate
		//Assume we are facing north/foward which would mean that the x position should not
		//change. The z value would be the screen's y-axis and the y value would be the screen's
		//x-axis.
		//Write up a better explanation for this later
		double mouseX = 1;
		double mouseY = tempX;
		double mouseZ = tempY;
		
		//View Matrix is a little different so we use a rotation matrix with the rotations same rotations
		//so that we can rotate the mouse coords around and get a 3d feel. x,y,z coordinates can be added as
		//a part of a 4x4 matrix to translate as well. translation happens last.
		Mat4f m = GameMath.createModelMatrix(0, 0, 0, xRot, yRot, zRot, 1, 1, 1);
		
		Vec4f tempVec = GameMath.matrixMultVec(m, new Vec4f(mouseX, mouseY, mouseZ, 1.0));
		
		mouseCoords.x=tempVec.x;
		mouseCoords.y=tempVec.y;
		mouseCoords.z=tempVec.z;
		
	}
	
	private void set2DMouseCoords()
	{
		
		double val1 = Input.getMouseX()-position.x;
		double floorValue = GameMath.floor(val1 / gridSize.x);
		
		mouseCoords.x = (float) (gridSize.x*floorValue);
		
		val1 = Input.getMouseY()-position.y;
		floorValue = GameMath.floor(val1 / gridSize.y);
		
		mouseCoords.y = (float) (gridSize.y*floorValue);
		
	}
	
	private void camControls3D()
	{
		if(mouseLock==true)
		{
			rotation.z += (double) ( (Display.getWidth()/2.0 - Input.getMouseX()) / 4.0);
			rotation.y += (double) ( (Display.getHeight()/2.0 - Input.getMouseY()) / 8.0);
			Input.setMousePos(Display.getWidth()/2, Display.getHeight()/2);
			
		}
		
		if(Input.getKeyPressed(Input.VK_LEFT_CONTROL))
		{
			mouseLock=!mouseLock;
		}
		
		if(Input.getKeyDown('W'))
		{
			position.x+=GameMath.lengthDirX(zRot, 1);
			position.y+=GameMath.lengthDirY(zRot, 1);
		}
		else if(Input.getKeyDown('S'))
		{
			position.x-=GameMath.lengthDirX(zRot, 1);
			position.y-=GameMath.lengthDirY(zRot, 1);
		}
		if(Input.getKeyDown('A'))
		{
			position.x+=GameMath.lengthDirX(zRot+90, 1);
			position.y+=GameMath.lengthDirY(zRot+90, 1);
		}
		else if(Input.getKeyDown('D'))
		{
			position.x+=GameMath.lengthDirX(zRot-90, 1);
			position.y+=GameMath.lengthDirY(zRot-90, 1);
		}
		
		if(Input.getKeyDown('Q'))
		{
			position.z-=1;
		}
		else if(Input.getKeyDown('E'))
		{
			position.z+=1;
		}
		
		Cam3D.setPosition(position);
		Cam3D.setRotation(rotation);
	}
	
	public Vec3f getMouseCoords()
	{
		return mouseCoords;
	}
	
	public boolean getMode()
	{
		return mode;
	}
	
	private void camControls2D()
	{
		if (Input.getKeyDown('W'))
		{
			position.y+=zoom*2;
		}
		else if (Input.getKeyDown('S'))
		{
			position.y-=zoom*2;
		}
		
		if (Input.getKeyDown('A'))
		{
			position.x+=zoom*2;
		}
		else if (Input.getKeyDown('D'))
		{
			position.x-=zoom*2;
		}

		if(Input.getMouseWheelUp())
		{
			if(Input.getMouseX()-startX>=0 && Input.getMouseY()-startY>=0)
			{
				if(zoomSliderValue<10)
				{
					zoomSliderValue+=1;
				}
			}
		}
		else if(Input.getMouseWheelDown())
		{
			if(Input.getMouseX()-startX>=0 && Input.getMouseY()-startY>=0)
			{
				if(zoomSliderValue>0)
				{
					zoomSliderValue-=1;
				}
			}
		}
		
		Cam2D.setPosition(position);
	}
	
	@Override
	public void update()
	{
		
		GuiElement tempGuiElement = null;
		
		for(int i=0; i<Game.getGuiListSize(); i++)
		{
			tempGuiElement = Game.getGuiElement(i);
			
			if(tempGuiElement.getVisible() == true)
			{
				String tempValue = tempGuiElement.value;
				boolean tempValue2 = tempGuiElement.switchValue;
				double tempValue3 = tempGuiElement.numValue;
				boolean tempValue4 = tempGuiElement.getActive();
				
				tempGuiElement.update( new Point(Input.getMouseX(), Input.getMouseY()) );
				
				if(tempValue4!=tempGuiElement.getActive())
				{
					canEdit=false;
				}
				
				if(tempValue != tempGuiElement.value
					|| tempValue2 != tempGuiElement.switchValue
					|| tempValue3 != tempGuiElement.numValue)
				{
					canEdit=false;
					break;
				}
				
				
			}
			
			//The temp values store what may be changed after the object updates.
			//If anything changed, we should quit updating the objects so that we have
			//a sense of depth. That means that a particular object will have priority
			//over others.
			
		}
		
		//Game.sortGuiElements();
		
		if(mode==MODE_2D)
		{
			camControls2D();
			set2DMouseCoords();
			
			getObjectCollisions();
		}
		else
		{
			camControls3D();
			set3DMouseCoords();
		}
		
		if(canEdit==true)
		{
			if(editMode==ADD_DELETE_MODE)
			{
				try
				{
					addObject();
					deleteObject();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			else if(editMode==SELECT_MODE || editMode==OBJECT_SPECIFIC_MODE)
			{
				selectObject();
			}
			else if(editMode==BACKGROUND_MODE)
			{
				selectTile();
				addTile();
				deleteTile();
				swapImage();
			}
			
			if(Input.getKeyDown(Input.VK_CONTROL))
			{
				if(Input.getKeyPressed('1'))
				{
					editMode=ADD_DELETE_MODE;
					tempObject=null;
				}
				if(Input.getKeyPressed('2'))
				{
					editMode=SELECT_MODE;
					tempObject=null;
				}
				if(Input.getKeyPressed('3'))
				{
					editMode=BACKGROUND_MODE;
					tempObject=null;
				}
				if(Input.getKeyPressed('4'))
				{
					editMode=OBJECT_SPECIFIC_MODE;
					tempObject=null;
				}
				if(Input.getKeyPressed('S'))
				{
					//Save
					save();
				}
				if(Input.getKeyPressed('L'))
				{
					//Load
					load();
				}
				if(Input.getKeyPressed('R'))
				{
					//Restart
					//debug purposes
					restart();
				}
			}
		}
		
		if(Input.getMouseButtonUp(Input.LEFT_MOUSE_BUTTON))
			canEdit = true;
		
		guiUpdate();
		/*
		 * lets separate the different aspects into parts
		 * First part: adding objects
		 * Second part: deleting objects
		 * Thrid part: selecting objects
		 * Fourth part: background objects
		 * Fifth part: saving
		 * Six part: loading
		 */
	}
	
	private void getObjectCollisions()
	{
		boolean col = false;
		parentGameObject o = null;
		
		double rawMouseX = Input.getMouseX()-position.x;
		double rawMouseY = Input.getMouseY()-position.y;
		
		if(Input.getMouseButtonPressed(Input.LEFT_MOUSE_BUTTON))
		{
			tempObject = null;
		}
		
		if(tempObject!=null)
		{
			col=true;
			selectedId = tempObject.getId();
			o=tempObject;
		}
		else
		{
			for(int i=1; i<Game.getObjectListSize();i++)
			{
				o = Game.findObject(i);
				Box objCol = (Box)o.levelEditCol;
				
				if(rawMouseX>=o.position.x+objCol.getLeftBound() && rawMouseX<=o.position.x+objCol.getRightBound()
					&& rawMouseY>=o.position.y+objCol.getTopBound() && rawMouseY<=o.position.y+objCol.getBottomBound())
				{
					col=true;
					selectedId = o.getId();
					break;
				}
				
				if(mouseCoords.x>=o.position.x+objCol.getLeftBound() && mouseCoords.x<=o.position.x+objCol.getRightBound()
				&& mouseCoords.y>=o.position.y+objCol.getTopBound() && mouseCoords.y<=o.position.y+objCol.getBottomBound())
				{
					col=true;
					selectedId = o.getId();
					break;
				}
			}
			
			if(col==false)
				selectedId=-1;
		}
		
	}
	
	private void swapImage()
	{
		
	}
	
	private void selectObject()
	{
		if(selectedId!=-1)
		{
			if(Input.getMouseButtonPressed(Input.LEFT_MOUSE_BUTTON))
			{
				tempObject = Game.findById(selectedId);
			}
		}
		else
		{
			if(Input.getMouseButtonPressed(Input.LEFT_MOUSE_BUTTON))
			{
				tempObject = null;
			}
		}
	}
	
	private void addObject()
	{
		if(Input.getMouseX()>-startX && Input.getMouseY()>-startY
			&& Input.getMouseX()<Display.getWidth() && Input.getMouseY()<Display.getHeight())
		{
			if(Input.getMouseButtonDown(Input.LEFT_MOUSE_BUTTON))
			{
				
				if(selectedId==-1)
				{
					Class c = Level.getObjectClass( selectedObject );
					try
					{
						tempObject = (parentGameObject)c.newInstance();
						
						tempObject.position.x=mouseCoords.x;
						tempObject.position.y=mouseCoords.y;
						tempObject.position.z=mouseCoords.z;
						
						Game.addObject( tempObject );
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					
					if(Input.getKeyDown(Input.VK_SHIFT))
					{
						tempObject=null;
					}
				}
				else
				{
					if(Input.getKeyDown(Input.VK_SHIFT))
					{
						tempObject=null;
					}
					else
					{
						tempObject = Game.findById(selectedId);
					}
				}
				
			}
			
		}
	
		if(tempObject!=null)
		{
			tempObject.position.x=mouseCoords.x;
			tempObject.position.y=mouseCoords.y;
			tempObject.position.z=mouseCoords.z;
		}
		
		if(Input.getMouseButtonUp(Input.LEFT_MOUSE_BUTTON))
		{
			tempObject = null;
		}
	
	}
	
	private void deleteObject()
	{
		if(!Input.getKeyDown(Input.VK_SHIFT))
		{
			if(Input.getMouseButtonPressed(Input.RIGHT_MOUSE_BUTTON))
			{
				Game.destroyObjectById(selectedId);
			}
		}
		else
		{
			if(Input.getMouseButtonDown(Input.RIGHT_MOUSE_BUTTON))
			{
				Game.destroyObjectById(selectedId);
			}
		}
		
	}
	
	private void selectTile()
	{
		if(Input.getMouseButtonPressed(Input.LEFT_MOUSE_BUTTON))
		{
			selectedTile = -1;
			if(selectedTileMap != null)
			{
				for(int i=0; i<selectedTileMap.size(); i++)
				{
					if(Input.getMouseX() >= selectedTileMap.getTileX(i) 
						&& Input.getMouseX() <= selectedTileMap.getTileX(i)+selectedTileMap.getWidth(i))
					{
						if(Input.getMouseY() >= selectedTileMap.getTileY(i) 
							&& Input.getMouseY() <= selectedTileMap.getTileY(i)+selectedTileMap.getHeight(i))
						{
							selectedTile = i;
							break;
						}
					}
				}
			}
		}
	}
	
	private void addTile()
	{
		if(Input.getMouseButtonPressed(Input.LEFT_MOUSE_BUTTON))
		{
			if(selectedTileMap != null && selectedTile == -1)
			{
				if(selectedImage != null)
				{
					selectedTileMap.addTile(selectedImage, Input.getMouseX(), Input.getMouseY());
					selectedTile = selectedTileMap.size()-1;
					
				}
			}
		}
		
		if(Input.getMouseButtonDown(Input.LEFT_MOUSE_BUTTON))
		{
			if(selectedTile!=-1 && selectedTileMap != null)
			{
				selectedTileMap.setTileX(selectedTile,(int) mouseCoords.x);
				selectedTileMap.setTileY(selectedTile,(int) mouseCoords.y);
				
				selectedTileMap.update();
			}
		}
	}
	
	private void deleteTile()
	{
		if(Input.getMouseButtonPressed(Input.RIGHT_MOUSE_BUTTON))
		{
			if(selectedTileMap != null && selectedTile != -1)
			{
				selectedTileMap.removeTile(selectedTile);
				selectedTileMap.update();
			}
		}
	}
	
	public void restart()
	{
		Game.reset();
		Game.addObject( new LevelEditObject());
	}
	
	public void save()
	{
		///save objectID, instanceID, x, y, z, xRot, yRot, zRot,
		//xScale, yScale, zScale, depth
		//save specifics to object on the next line. end with ;
		
		String temp = Game.createInputPopUp("Please input the file name.");
		
		if(!temp.equals("") && temp!=null)
		{
			editLevel.clearLevel();
			for(int i=1; i<Game.getObjectListSize(); i++)
			{
				editLevel.addObject( Game.findObject(i) );
			}
			
			editLevel.exportLevel( temp );
		}
	}
	
	public void load()
	{
		editLevel.clearLevel();
		String temp = Game.createInputPopUp("Please input the file name.");
		if(!temp.equals("") && temp!=null)
		{
			editLevel.loadExternalLevel( temp );
			
			while(Game.getObjectListSize()!=1)
			{
				Game.destroyObject( Game.getObjectListSize()-1);
			}
			
			for(int i=0; i<editLevel.getObjectListSize(); i++)
			{
				Game.addObject(editLevel.getObject(i));
			}
		}
	}

	public void guiUpdate()
	{
		String tempString = "";
		double newValue = 0;
		
		if(editMode==ADD_DELETE_MODE)
		{
			selectedObject = (int)editParts.get(0).numValue;
		}
		else if(editMode==SELECT_MODE)
		{
			if(editParts.get(1).switchValue==true)
			{
				//xPos
				tempString = Game.createInputPopUp("Enter an X Value");
				try
				{
					newValue = Double.parseDouble(tempString);
					tempObject.position.x = newValue;
				}
				catch(Exception e)
				{
					//do nothing.
				}
				
				editParts.get(1).switchValue=false;
			}
			else if(editParts.get(2).switchValue==true)
			{
				//yPos
				tempString = Game.createInputPopUp("Enter a Y Value");
				try
				{
					newValue = Double.parseDouble(tempString);
					tempObject.position.y = newValue;
				}
				catch(Exception e)
				{
					//do nothing.
				}
				
				editParts.get(2).switchValue=false;
			}
			else if(editParts.get(3).switchValue==true)
			{
				//zPos
				tempString = Game.createInputPopUp("Enter a Z Value");
				try
				{
					newValue = Double.parseDouble(tempString);
					tempObject.position.z = newValue;
				}
				catch(Exception e)
				{
					//do nothing.
				}
				
				editParts.get(3).switchValue=false;
			}
			else if(editParts.get(4).switchValue==true)
			{
				//xRot
				tempString = Game.createInputPopUp("Enter an X Rotation Value");
				try
				{
					newValue = Double.parseDouble(tempString);
					tempObject.rotation.x = newValue;
				}
				catch(Exception e)
				{
					//do nothing.
				}
				
				editParts.get(4).switchValue=false;
			}
			else if(editParts.get(5).switchValue==true)
			{
				//yRot
				tempString = Game.createInputPopUp("Enter a Y Rotation Value");
				try
				{
					newValue = Double.parseDouble(tempString);
					tempObject.rotation.y = newValue;
				}
				catch(Exception e)
				{
					//do nothing.
				}
				
				editParts.get(5).switchValue=false;
			}
			else if(editParts.get(6).switchValue==true)
			{
				//zRot
				tempString = Game.createInputPopUp("Enter a Z Rotation Value");
				try
				{
					newValue = Double.parseDouble(tempString);
					tempObject.rotation.z = newValue;
				}
				catch(Exception e)
				{
					//do nothing.
				}
				
				editParts.get(6).switchValue=false;
			}
			else if(editParts.get(7).switchValue==true)
			{
				//xScale
				tempString = Game.createInputPopUp("Enter an X Scale Value");
				try
				{
					newValue = Double.parseDouble(tempString);
					tempObject.scale.x = newValue;
				}
				catch(Exception e)
				{
					//do nothing.
				}
				
				editParts.get(7).switchValue=false;
			}
			else if(editParts.get(8).switchValue==true)
			{
				//yScale
				tempString = Game.createInputPopUp("Enter a Y Scale Value");
				try
				{
					newValue = Double.parseDouble(tempString);
					tempObject.scale.y = newValue;
				}
				catch(Exception e)
				{
					//do nothing.
				}
				
				editParts.get(8).switchValue=false;
			}
			else if(editParts.get(9).switchValue==true)
			{
				//zScale
				tempString = Game.createInputPopUp("Enter a Z Scale Value");
				try
				{
					newValue = Double.parseDouble(tempString);
					tempObject.scale.z = newValue;
				}
				catch(Exception e)
				{
					//do nothing.
				}
				
				editParts.get(9).switchValue=false;
			}
			else if(editParts.get(10).switchValue==true)
			{
				//Depth
				tempString = Game.createInputPopUp("Enter a depth value");
				try
				{
					newValue = Double.parseDouble(tempString);
					tempObject.depth = newValue;
				}
				catch(Exception e)
				{
					//do nothing.
				}
				
				editParts.get(10).switchValue=false;
			}
			else if(editParts.get(11).switchValue==true)
			{
				//Persistent
				tempString = Game.createInputPopUp("Set whether this object is Persistent. (0=false, 1=true)");
				try
				{
					newValue = Double.parseDouble(tempString);
					if(newValue==0)
					{
						tempObject.setPersistent(false);
					}
					else
					{
						tempObject.setPersistent(true);
					}
				}
				catch(Exception e)
				{
					//do nothing.
				}
				
				editParts.get(11).switchValue=false;
			}
			else if(editParts.get(12).switchValue==true)
			{
				//Visible
				tempString = Game.createInputPopUp("Set whether this object is Visible. (0=false, 1=true)");
				try
				{
					newValue = Double.parseDouble(tempString);
					if(newValue==0)
					{
						tempObject.setVisible(false);
					}
					else
					{
						tempObject.setVisible(true);
					}
				}
				catch(Exception e)
				{
					//do nothing.
				}
				
				editParts.get(12).switchValue=false;
			}
		}
		else if(editMode == BACKGROUND_MODE)
		{
			//Add tiles
			selectedLayer = (int)GameMath.parseDouble(editParts.get(13).textField);
			selectedTileMap = null;
			
			for(int i=0; i<tiles.size(); i++)
			{
				if(tiles.get(i).depth == selectedLayer)
				{
					selectedTileMap = tiles.get(i);
					break;
				}
			}
			
			if(editParts.get(14).switchValue==true)
			{
				//Add tilemap
				tempString = Game.createInputPopUp("Create a new Tilemap at the specified layer. (Does nothing if a tile exists at that depth)");
				try
				{
					newValue = Double.parseDouble(tempString);
					boolean same = false;
					for(int i=0; i<tiles.size();i++)
					{
						if(newValue == tiles.get(i).depth)
						{
							same=true;
							break;
						}
					}
					
					if(same==false)
					{
						TileMap p = new TileMap(newValue);
						
						tiles.add( p );
						tileDepths.add( GameMath.toString(p.depth) );
						Game.addObject( p );
						
						editParts.get(13).textField = GameMath.toString(p.depth);
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
				editParts.get(14).switchValue=false;
			}
			else if(editParts.get(15).switchValue==true)
			{
				//Delete tile map
				try
				{
					if(selectedTileMap!=null)
					{
						tiles.remove(selectedTileMap);
						tileDepths.remove(editParts.get(13).textField);
						Game.destroyObject(selectedTileMap);
						editParts.get(13).textField = "";
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
				editParts.get(15).switchValue=false;
			}
			else if(editParts.get(16).switchValue==true)
			{
				//Change tile map depth
				try
				{
					double newDepth = Double.parseDouble( Game.createInputPopUp("Enter new depth value"));
					
					boolean canDo = true;
					
					for(int i=0;i<tiles.size();i++)
					{
						if(newDepth == tiles.get(i).depth)
						{
							canDo=false;
							break;
						}
					}
					
					if(canDo==true)
					{
						int oldLocation = tileDepths.indexOf( GameMath.toString(selectedTileMap.depth) );
						
						selectedTileMap.depth = newDepth;
						tileDepths.set( oldLocation, GameMath.toString(newDepth));
						editParts.get(13).textField = GameMath.toString(newDepth);
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
				editParts.get(16).switchValue=false;
			}
		}
		else if(editMode == OBJECT_SPECIFIC_MODE)
		{
			//edit stuff from the object specifically
			if(tempObject!=null)
				tempObject.guiUpdate();
		}
	}
	
	private void drawGuiElements()
	{
		for(int i=0; i<Game.getGuiListSize(); i++)
		{
			Game.getGuiElement(i).setVisible(false);
		}
		
		switch(editMode)
		{
			case ADD_DELETE_MODE:
				for(int i=0;i<1;i++)
				{
					editParts.get(i).setVisible(true);
				}
				for(int i=1;i<editParts.size();i++)
				{
					editParts.get(i).setVisible(false);
				}
				break;
			case SELECT_MODE:
				for(int i=0;i<1;i++)
				{
					editParts.get(i).setVisible(false);
				}
				
				if(tempObject!=null)
				{
					for(int i=1;i<13;i++)
					{
						editParts.get(i).setVisible(true);
					}
				}
				else
				{
					for(int i=1;i<13;i++)
					{
						editParts.get(i).setVisible(false);
					}
				}
				
				for(int i=13;i<editParts.size();i++)
				{
					editParts.get(i).setVisible(false);
				}
				break;
			case BACKGROUND_MODE:
				for(int i=0;i<13;i++)
				{
					editParts.get(i).setVisible(false);
				}
				for(int i=13;i<editParts.size();i++)
				{
					editParts.get(i).setVisible(true);
				}
				break;
			case OBJECT_SPECIFIC_MODE:
				if(tempObject!=null)
				{
					for(int i=0;i<tempObject.editParts.size();i++)
					{
						tempObject.editParts.get(i).setVisible(true);
					}
				}
				break;
			default:
				break;
		}
		
		for(int i=0; i<Game.getGuiListSize(); i++)
		{
			if(Game.getGuiElement(i).getVisible())
			{
				Game.getGuiElement(i).render();
			}
		}
	}
	
	private void renderGUI()
	{
		
		guiSurface.bind();
		guiSurface.clear();
		
		//System.out.println(Game.currentShader.getVertexShaderFile());
		
		DefaultResources.defaultShader.start();
		
		Game.setCamera(new Camera(Camera.MODE_2D));
		Game.set2DBegin();
		
		Mat4f GuiMatrix = GameMath.createOrthographicMatrix(0, 0, guiSurface.getWidth(), guiSurface.getHeight());
		DefaultResources.defaultShader.setProjectionMatrix(GuiMatrix, false);
		
		GameRender.setColor(0f, 0f, 0f, 1f);
		
		GameRender.drawText("FPS: "+Display.getFPS(), 0, 0);
		if(tempObject!=null)
			GameRender.drawText(tempObject.toString(), 0, 32);
		else
			GameRender.drawText("null", 0, 32);
		
		
		GameRender.drawText("Amount of Objects: "+GameMath.toString(Game.getObjectListSize()-1), 0, 64);
		
		GameRender.setFontSize(18);
		GameRender.setColor(.6f, .2f, .2f, 1f);
		
		if(editMode==ADD_DELETE_MODE)
			GameRender.drawText("Add and Delete Mode", 640-176, 0);
		else if(editMode==SELECT_MODE)
			GameRender.drawText("Select Mode", 640-160, 0);
		else if(editMode==BACKGROUND_MODE)
			GameRender.drawText("Background Edit Mode", 640-176, 0);
		else if(editMode==OBJECT_SPECIFIC_MODE)
			GameRender.drawText("Object Edit Mode", 640-176, 0);
		
			
		if(editMode==SELECT_MODE)
		{
			if(tempObject!=null)
			{
				editParts.get(1).setDescription("X: "+tempObject.position.x);
				editParts.get(2).setDescription("Y: "+tempObject.position.y);
				editParts.get(3).setDescription("Z: "+tempObject.position.z);
				editParts.get(4).setDescription("XROT: "+tempObject.rotation.x);
				editParts.get(5).setDescription("YROT: "+tempObject.rotation.y);
				editParts.get(6).setDescription("ZROT: "+tempObject.rotation.z);
				editParts.get(7).setDescription("XSCALE: "+tempObject.scale.x);
				editParts.get(8).setDescription("YSCALE: "+tempObject.scale.y);
				editParts.get(9).setDescription("ZSCALE: "+tempObject.scale.z);
				editParts.get(10).setDescription("DEPTH: "+tempObject.depth);
				editParts.get(11).setDescription("VISIBLE: "+tempObject.getVisible());
				editParts.get(12).setDescription("PERSISTENT: "+tempObject.getPersistent());
			}
		}
		else if(editMode==BACKGROUND_MODE)
		{
			if(selectedTile!=-1 && selectedTileMap!=null)
			{
				editParts.get(17).setDescription("Tile X: "+selectedTileMap.getTileX(selectedTile));
				editParts.get(18).setDescription("Tile Y: "+selectedTileMap.getTileY(selectedTile));
				editParts.get(19).setDescription("Tile XScale: "+selectedTileMap.getTileXScale(selectedTile));
				editParts.get(20).setDescription("Tile YScale: "+selectedTileMap.getTileYScale(selectedTile));
				
				
			}
			
			GameRender.setColor(1f, 1f, 1f, 1f);
			if(selectedImage != null)
				GameRender.drawSpriteExt(selectedImage, 0, Display.getWidth()-160, 256, Display.getWidth()-32, 384);
		}
		
		drawGuiElements();
		
		guiSurface.unBind();
		GameRender.setColor(1f, 1f, 1f, 1f);
		GameRender.drawSurface(guiSurface, 0, 0);
		
	}
	
	public void renderGameStuff()
	{
		Display.clearWindow();
		
		Game.getApplicationSurface().bind();
		Game.getApplicationSurface().clear();
		
		GameRender.setColor(1f, 1f, 1f, 1f);
		parentGameObject tempGameObject;
		
		Game.sortByDepth();
		
		ArrayList<parentGameObject> sortedList = Game.getRawSortedObjectList();
		
		for(int i=0;i<sortedList.size();i++)
		{
			tempGameObject = sortedList.get(i);
			
			if(!tempGameObject.equals(this))
			{
				tempGameObject.guiUpdate();
				
				tempGameObject.levelEditDraw();
			}
		}
		
		Game.getApplicationSurface().unBind();
		
		newAppSurface.bind();
		newAppSurface.clear();
		
		GameRender.setColor(1f,1f,1f,1f);
		GameRender.drawSurface(Game.getApplicationSurface(), 0, 0, 1, 1);
		
		for(int i=0; i<sortedList.size(); i++)
		{
			tempGameObject = sortedList.get(i);
			
			if(!tempGameObject.equals(this))
			{
				float x1 = (float)(tempGameObject.position.x+((Box)tempGameObject.levelEditCol).getLeftBound());
				float y1 = (float)(tempGameObject.position.y+((Box)tempGameObject.levelEditCol).getTopBound());
				float x2 = (float)(tempGameObject.position.x+((Box)tempGameObject.levelEditCol).getRightBound());
				float y2 = (float)(tempGameObject.position.y+((Box)tempGameObject.levelEditCol).getBottomBound());
				
				if(!tempGameObject.equals(tempObject))
				{
					//Not Selected
					GameRender.setColor(0f, 1f, 1f, 1f);
				}
				else
				{
					//Selected Object
					GameRender.setColor(1f, 0f, 0f, 1f);
				}
				GameRender.drawRect(x1, y1, x2, y2, true);
			}
		}
		
		newAppSurface.unBind();
		
		GameRender.setColor(1f, 1f, 1f, 1f);
		GameRender.drawSurface(newAppSurface, (int)position.x, (int)position.y, 1, 1);
		
	}
	
	@Override
	public void draw()
	{
		renderGameStuff();
		
		if(mode==MODE_2D)
		{
			Game.setCamera(Cam2D);
			Game.set2DBegin();
			
			GameRender.setColor(0, 0, 0, 1);
			gridModel.draw();
		}
		else
		{
			Game.setCamera(Cam3D);
			Game.set3DBegin();
			gridModel.draw();
		}
		
		renderGUI();
		
		//Display.setShouldPrintSystemData(true);
	}
	
	@Override
	public void destroy()
	{
		System.out.println("IS BEING DESTROIED");
		nullIcon.dispose();
		editLevel.dispose();
		gridModel.destroyModel();
		guiSurface.dispose();
	}
}
