package glLevelEditor;

import java.util.*;

import openGLEngine.*;

public class BasicEditor extends parentGameObject {
	
	public static BasicEditor controlObject = null;
	
	private parentGameObject currentSelectedObject = null;
	
	public testOBJ obj = new testOBJ();
	
	private Level myLevel = new Level();
	
	private AddDeleteWindow myBaseWindow = new AddDeleteWindow(8,6,952,538); //944 x 532
	private EditWindow myEditWindow = new EditWindow(972, 6, 1274-972, 538); //302 x 532
	private InstanceWindow myListWindow = new InstanceWindow(972, 6, 1274-972, 538); //302 x 532
	private ObjectWindow myObjectWindow = new ObjectWindow(972, 6, 1274-972, 538); //302 x 532
	private TabWindow myTabWindow = new TabWindow(972, 550, 1274-972, 716-548); //302 x 164
	private int activeWindow = 0;
	
	public BasicEditor()
	{
		//position.x = Display.getWidth()/2;
		//position.y = Display.getHeight()/2;
		
		Game.disableCulling();
		
		BasicEditor.controlObject = this;
		
		myBaseWindow.setLevel(myLevel);
		myListWindow.setLevel(myLevel);
		
		myBaseWindow.setAlwaysRender(true);
		myEditWindow.setAlwaysRender(true);
		myListWindow.setAlwaysRender(true);
		myObjectWindow.setAlwaysRender(true);
		myTabWindow.setAlwaysRender(true);
		
		myEditWindow.setAlwaysActive(true);
		myListWindow.setAlwaysActive(true);
		myObjectWindow.setAlwaysActive(true);
		
		myBaseWindow.setResizable(false);
		myEditWindow.setResizable(false);
		myListWindow.setResizable(false);
		myObjectWindow.setResizable(false);
		myTabWindow.setResizable(false);
	}
	
	public AddDeleteWindow getAddDeleteWindow()
	{
		return myBaseWindow;
	}
	
	public EditWindow getEditWindow()
	{
		return myEditWindow;
	}
	
	public void addObject(parentGameObject t)
	{
		myLevel.addObject( t );
	}
	
	public parentGameObject getObject()
	{
		return currentSelectedObject;
	}
	
	public void setObject(parentGameObject obj)
	{
		this.currentSelectedObject = obj;
	}
	
	public Level getLevel()
	{
		return myLevel;
	}
	
	public void setActiveWindow(int i)
	{
		activeWindow = i;
		
		switch(activeWindow)
		{
		case 0:
			myEditWindow.setActive(true);
			myEditWindow.update();
			myEditWindow.setActive(false);
			break;
		case 1:
			myListWindow.setActive(true);
			myListWindow.update();
			myListWindow.setActive(false);
			break;
		case 2:
			myObjectWindow.setActive(true);
			myObjectWindow.update();
			myObjectWindow.setActive(false);
			break;
		default:
			break;
		}
		
	}
	
	public int getActiveWindow()
	{
		return activeWindow;
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
		//myEditWindow.setEntity(obj);
		
		//if(myLevel.getObjectListSize()==0)
		//	myLevel.addObject(obj);
		
		if(Input.getMouseButtonPressed(Input.LEFT_MOUSE_BUTTON))
		{
			if(Input.getMouseX()>=320 && Input.getMouseX()<=320+64)
			{
				if(Input.getMouseY()>=640 && Input.getMouseY()<640+28)
				{
					//save
					save();
				}
				else if(Input.getMouseY()>=668 && Input.getMouseY()<668+28)
				{
					//load
					load();
				}
			}
		}
	}
	
	public void save()
	{
		String levelName = Game.createInputPopUp("Save File Name");
		if(!levelName.isEmpty())
		{
			myLevel.exportLevel(levelName);
			myBaseWindow.getSurface().saveImage(levelName+"_Image.png");
			Game.createAlertPopUp("Created A Level Called: "+levelName);
		}
	}
	
	public void load()
	{
		String levelName = Game.createInputPopUp("Load File Name");
		if(!levelName.isEmpty())
		{
			myLevel.loadExternalLevel(levelName);
			Game.createAlertPopUp("Loaded A Level Called: "+levelName);
		}
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
		Game.createOrthoProjectionMatrix(0, 0, 1280, 720);
		Game.setCamera( new Camera(Camera.MODE_2D));
		Game.set2DBegin();
		
		//GameWindow.drawGrid(position, gridXSize, gridYSize, zoom);
		
		/*
		obj.reloadGuiField();
		EntityProcessor.processEntity(obj);
		EntityProcessor.drawEntityGui(obj);
		obj.updateItems();
		*/
		
		myBaseWindow.update();
		myBaseWindow.draw();
		
		switch(activeWindow)
		{
		case 0:
			myEditWindow.update();
			myEditWindow.draw();
			break;
		case 1:
			myListWindow.update();
			myListWindow.draw();
			break;
		case 2:
			myObjectWindow.update();
			myObjectWindow.draw();
			break;
		default:
			break;
		}
		
		myTabWindow.update();
		myTabWindow.draw();
		
		//GameRender.drawRect(4, 4, 32, 32, true);
		
		GameRender.drawText("MOUSE x,y: "+Input.getMouseX()+","+Input.getMouseY(), 0, 640);
		GameRender.drawText(""+this.myLevel.getObjectListSize(), 0, 668);
		GameRender.drawText("Save", 320, 640);
		GameRender.drawText("Load", 320, 668);
		
		/*
		EntityProcessor.offX = 640;
		EntityProcessor.offY = 0;
		double xScaleFactor = (double)EntityProcessor.guiSurface.getWidth() / Display.getWidth();
		double yScaleFactor = (double)EntityProcessor.guiSurface.getHeight() / Display.getHeight();
		
		GameRender.setColor(1f, 1f, 1f, 1f);
		GameRender.drawSurface(EntityProcessor.guiSurface, EntityProcessor.offX*xScaleFactor, EntityProcessor.offY*yScaleFactor, xScaleFactor, yScaleFactor);
		*/
		
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
