package openGLEngine;

import java.io.*;
import java.util.*;

import glLevelEditor.EntityProcessor;

public abstract class parentGameObject extends Entity implements Serializable {
	
	protected double depth = 0;
	
	protected Vec3f position = new Vec3f(0,0,0);
	protected Vec3f rotation = new Vec3f(0,0,0);
	protected Vec3f scale = new Vec3f(1,1,1);
	
	protected Sprite spriteIndex;
	protected int imageIndex = 0;
	protected boolean visible = true;
	protected boolean persistent = false;
	
	protected collisionHull colHull = new Box(-8, -8, 8, 8);
	
	private long updateTime = 0;
	private long renderTime = 0;
	
	private int id = -1;
	
	/*
	//Level Edit Stuff
	public ArrayList<GuiElement> editParts = new ArrayList<GuiElement>();
	public ArrayList<Integer> editTabs = new ArrayList<Integer>();
	*/
	
	public collisionHull levelEditCol = new Box(-8, -8, 8, 8);
	
	public double approxArea = 16; //Just the radius of a bounding circle squared. r=4 here
	
	public parentGameObject()
	{
		prepareGuiField();
	}
	
	public parentGameObject(parentGameObject o)
	{
		depth = o.depth;
		position = o.position;
		rotation = o.rotation;
		scale = o.scale;
		
		spriteIndex = o.spriteIndex;
		imageIndex = o.imageIndex;
		visible = o.visible;
		persistent = o.persistent;
		
		colHull = o.colHull;
		
		updateTime = o.updateTime;
		renderTime = o.renderTime;
		
	}
	/**
	 * Returns the name of this object.
	 * @return String
	 */
	protected String getObjectName()
	{
		return this.getClass().getName();
	}
	
	/**
	 * Returns the name of this object along with a specific id.
	 * This is just the toString() method.
	 * @return String
	 */
	protected String getObjectName_ext()
	{
		return this.toString();
	}
	
	/**
	 * Sets the sprite index for this object. The sprite must 
	 * have images in it.
	 * @param imageId
	 */
	protected void setSprite(Sprite imageId)
	{
		
		if (imageId.getSize() > 0)
		{
			spriteIndex=imageId;
		}
		else
		{
			System.err.println("Image Object has no sprites.");
		}
		
	}
	
	/**
	 * Returns the instance id of this object. This is set by the game,
	 * or by a level. Also set when the object is loaded.
	 * @return long
	 */
	public int getId()
	{
		return this.id;
	}
	
	/**
	 * Sets the instance id of this object. This is normally set by the game
	 * or by a level, or when this object is loaded. This can be set to
	 * whatever value you want, but should not be the same as another object
	 * that is currently in use. Does not check if it has the same id as
	 * another object in use.
	 * @param id
	 */
	public void setID(int id)
	{
		this.id = id;
	}
	
	/**
	 * Determines if this object will collide with an object from the
	 * specified class at the position x and y. Uses AABBs to determine
	 * if there is a collision. If both objects do not have AABBs as their
	 * collision hulls then no collision will happen between the two objects.
	 * @param x
	 * @param y
	 * @param o
	 * @return boolean
	 */
	public boolean getCollision(double x, double y, Class o)
	{
		int i=0;
		parentGameObject ins = Game.findObject(o,i);
		boolean col=false;
		
		while(ins!=null)
		{
			col = GameLogic.getCollision(this, ins);
			if ( col==true )
			{
				break;
			}
			i++;
			ins = Game.findObject(o,i);
		}
		
		return col;
	}
	
	/**
	 * Determines if this object will collide with an object from the
	 * specified class at the position x and y. Uses AABBs to determine
	 * if there is a collision. If both objects do not have AABBs as their
	 * collision hulls then no collision will happen between the two objects.
	 * 
	 * Returns the first object that caused a collision.
	 * @param x
	 * @param y
	 * @param o
	 * @return parentGameObject
	 */
	public parentGameObject getCollisionExt(double x, double y, Class o)
	{
		int i=0;
		parentGameObject ins = Game.findObject(o,i);
		boolean col=false;
		while(ins!=null)
		{
			col = GameLogic.getCollision(this, ins);
			//System.out.println(col);
			if ( col==true )
			{
				break;
			}
			i++;
			ins = Game.findObject(o,i);
		}
		
		if (col==true)
		{
			return ins;
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Gets the collision hull that is being used by this object.
	 * @return collisionHull
	 */
	public collisionHull getCollisionHull()
	{
		return colHull;
	}
	
	/**
	 * Sets the collision hull that will be used by this object.
	 * @param col
	 */
	public void setCollisionHull(collisionHull col)
	{
		this.colHull = col;
	}
	
	/**
	 * Gets whether this object is visible or not. If the object is not
	 * visible, the draw method for this object will not be called.
	 * @return boolean
	 */
	public boolean getVisible()
	{
		return visible;
	}
	
	/**
	 * Sets whether this object is visible or not. If the object is not
	 * visible, the draw method for this object will not be called.
	 * @param isVisible
	 */
	public void setVisible(boolean isVisible)
	{
		visible = isVisible;
	}
	
	/**
	 * Gets whether this object is persistent or not. If the object is not
	 * persistent, this object will not be deleted when a new level is used.
	 * @return boolean
	 */
	public boolean getPersistent()
	{
		return persistent;
	}
	
	/**
	 * Sets whether this object is persistent or not. If the object is not
	 * persistent, this object will not be deleted when a new level is used.
	 * @param isPersistent
	 */
	public void setPersistent(boolean isPersistent)
	{
		persistent = isPersistent;
	}
	
	/**
	 * Gets the sprite index of this object. The sprite index will be shown
	 * in the level editor.
	 * 
	 * @return Sprite
	 */
	public Sprite getSpriteIndex()
	{
		return spriteIndex;
	}
	
	/**
	 * Sets the sprite index of this object. The sprite index will be shown
	 * in the level editor.
	 * @param image
	 */
	public void setSpriteIndex(Sprite image)
	{
		spriteIndex = image;
	}
	
	public Vec3f getPosition()
	{
		return position;
	}
	
	public void setPosition(Vec3f pos)
	{
		position = pos;
	}
	
	public Vec3f getRotation()
	{
		return rotation;
	}
	
	public void setRotation(Vec3f rot)
	{
		rotation = rot;
	}

	public Vec3f getScale()
	{
		return scale;
	}
	
	public void setScale(Vec3f scal)
	{
		scale = scal;
	}
	
	/**
	 * Default save method. Is called when the object is saved and returns
	 * an ArrayList<String> that consists of the different values that need
	 * to be saved. The order of the things to be saved are as follows.
	 * 
	 * objectID, instanceID, x, y, z, xRot, yRot, zRot, xScale, yScale, zScale, depth, visible, persistent
	 * 
	 * @return ArrayList<String>
	 */
	protected ArrayList<String> defaultSave()
	{
		///save objectID, instanceID, x, y, z, xRot, yRot, zRot,
		//xScale, yScale, zScale, depth, visible,
		//save specifics to object on the next line. end with ;
				
		ArrayList<String> data = new ArrayList<String>();
		
		data.add(GameMath.toString(this.id));
		
		data.add(GameMath.toString(position.x));
		data.add(GameMath.toString(position.y));
		data.add(GameMath.toString(position.z));
		
		data.add(GameMath.toString(rotation.x));
		data.add(GameMath.toString(rotation.y));
		data.add(GameMath.toString(rotation.z));
		
		data.add(GameMath.toString(scale.x));
		data.add(GameMath.toString(scale.y));
		data.add(GameMath.toString(scale.z));
		
		data.add(GameMath.toString(depth));
		
		if(visible==true)
		{
			data.add("1");
		}
		else
		{
			data.add("0");
		}
		
		if(persistent==true)
		{
			data.add("1");
		}
		else
		{
			data.add("0");
		}
		
		return data;
	}
	
	/**
	 * Default load method. Loads data in this particular order.
	 * 
	 * objectID, instanceID, x, y, z, xRot, yRot, zRot, xScale, yScale, zScale, depth, visible, persistent
	 * 
	 * Returns the location it left off at. If this array of data contains additional data, it can be
	 * loaded using a separate method.
	 * 
	 * @param list
	 * @return int
	 */
	protected int defaultLoad(String[] list)
	{
		///save objectID, instanceID, x, y, z, xRot, yRot, zRot,
		//xScale, yScale, zScale, depth, visible, persistent, 
		//save specifics to object on the next line. end with ;
		
		this.id = GameMath.parseInt(list[0]);
		
		position.x=GameMath.parseDouble(list[1]);
		position.y=GameMath.parseDouble(list[2]);
		position.z=GameMath.parseDouble(list[3]);
		
		rotation.x = GameMath.parseDouble(list[4]);
		rotation.y = GameMath.parseDouble(list[5]);
		rotation.z = GameMath.parseDouble(list[6]);
		
		scale.x = GameMath.parseDouble(list[7]);
		scale.y = GameMath.parseDouble(list[8]);
		scale.z = GameMath.parseDouble(list[9]);
		
		this.depth=GameMath.parseInt(list[10]);
		this.visible = GameMath.parseInt(list[11])==1;
		
		this.persistent = GameMath.parseInt(list[12])==1;
		
		
		return 13;
	}
	
	/**
	 * The update method for this object. Will be called every update
	 * cycle. Must be overridden in subclasses.
	 */
	public abstract void update();
	
	/**
	 * The preDraw method will be called before the draw method. Can disable
	 * automatic calling of the preDraw methods. An empty method, but not
	 * required to be overridden.
	 */
	public void preDraw(){}
	
	/**
	 * The draw method for this object. Will be called every update cycle.
	 * Must be overridden in subclasses.
	 */
	public abstract void draw();
	
	/**
	 * This draw method is called by a levelEdit object. This allows you to specify what
	 * you desire your object to look like when it is being used in the level editor.
	 * This, by default, calls the normal draw method.
	 */
	public void levelEditDraw()
	{
		draw();
	}
	
	public void defaultUpdateItems()
	{
		/*
		protected double depth = 0;
		
		protected Vec3f position = new Vec3f(0,0,0);
		protected Vec3f rotation = new Vec3f(0,0,0);
		protected Vec3f scale = new Vec3f(1,1,1);
		
		protected Sprite spriteIndex;
		protected int imageIndex = 0;
		protected boolean visible = true;
		protected boolean persistent = false;
		*/
		
		//EntityProcessor.processEntity(this);
		
		Entity.EntityElement q = this.guiField.get(0);
		ArrayList<String> values = q.getElements();
		
		position.x = GameMath.parseDouble(values.get(0));
		position.y = GameMath.parseDouble(values.get(1));
		position.z = GameMath.parseDouble(values.get(2));
		
		q = guiField.get(1);
		values = q.getElements();
		
		rotation.x = GameMath.parseDouble(values.get(0));
		rotation.y = GameMath.parseDouble(values.get(1));
		rotation.z = GameMath.parseDouble(values.get(2));
		
		q = this.guiField.get(2);
		values = q.getElements();
		
		scale.x = GameMath.parseDouble(values.get(0));
		scale.y = GameMath.parseDouble(values.get(1));
		scale.z = GameMath.parseDouble(values.get(2));
		
		q = this.guiField.get(3);
		values = q.getElements();
		
		depth = GameMath.parseDouble(values.get(0));
		
		q = this.guiField.get(4);
		values = q.getElements();
		
		spriteIndex = GameResources.getSprite(values.get(0));
		
		q = this.guiField.get(5);
		values = q.getElements();
		
		visible = values.get(0).equalsIgnoreCase("true");
		
		q = this.guiField.get(6);
		values = q.getElements();
		
		persistent = values.get(0).equalsIgnoreCase("true");
		
	}
	/**
	 * A blank method used to change the elements of this object
	 * Used for level editing
	 */
	public void updateItems()
	{
		defaultUpdateItems();
	}
	
	public void defaultPrepareGuiField()
	{
		Entity.EntityElement Element1 = new Entity.EntityElement("Position", Entity.EntityElement.LIST);
		Element1.addElement("X");
		Element1.addElement("Y");
		Element1.addElement("Z");
		
		Entity.EntityElement Element2 = new Entity.EntityElement("Rotation", Entity.EntityElement.LIST);
		Element2.addElement("X");
		Element2.addElement("Y");
		Element2.addElement("Z");
		
		Entity.EntityElement Element3 = new Entity.EntityElement("Scale", Entity.EntityElement.LIST);
		Element3.addElement("X");
		Element3.addElement("Y");
		Element3.addElement("Z");
		
		Entity.EntityElement Element4 = new Entity.EntityElement("Depth", Entity.EntityElement.SINGLE_ELEMENT);
		Element4.addElement("");
		
		Entity.EntityElement Element5 = new Entity.EntityElement("SpriteName", Entity.EntityElement.SINGLE_ELEMENT);
		Element5.addElement("");
		
		Entity.EntityElement Element6 = new Entity.EntityElement("Visible", Entity.EntityElement.SINGLE_ELEMENT);
		Element6.addElement("");
		
		Entity.EntityElement Element7 = new Entity.EntityElement("Persistent", Entity.EntityElement.SINGLE_ELEMENT);
		Element7.addElement("");
		
		guiField.add(Element1);
		guiField.add(Element2);
		guiField.add(Element3);
		guiField.add(Element4);
		guiField.add(Element5);
		guiField.add(Element6);
		guiField.add(Element7);
		
		reloadGuiField();
	}
	
	public void prepareGuiField()
	{
		defaultPrepareGuiField();
	}
	
	public void defaultReloadGuiField()
	{
		ArrayList<String> stringValues = guiField.get(0).getElements();
		stringValues.set(0, GameMath.toString(position.x));
		stringValues.set(1, GameMath.toString(position.y));
		stringValues.set(2, GameMath.toString(position.z));
		
		stringValues = guiField.get(1).getElements();
		stringValues.set(0, GameMath.toString(rotation.x));
		stringValues.set(1, GameMath.toString(rotation.y));
		stringValues.set(2, GameMath.toString(rotation.z));
		
		stringValues = guiField.get(2).getElements();
		stringValues.set(0, GameMath.toString(scale.x));
		stringValues.set(1, GameMath.toString(scale.y));
		stringValues.set(2, GameMath.toString(scale.z));
		
		stringValues = guiField.get(3).getElements();
		stringValues.set(0, GameMath.toString(depth));
		
		stringValues = guiField.get(4).getElements();
		stringValues.set(0, GameResources.getSpriteName(spriteIndex));
		
		stringValues = guiField.get(5).getElements();
		stringValues.set(0, GameMath.toString(visible));
		
		stringValues = guiField.get(6).getElements();
		stringValues.set(0, GameMath.toString(persistent));
	}
	
	public void reloadGuiField()
	{
		defaultReloadGuiField();
	}
	
	public void clearGuiField()
	{
		for(int i=0; i<guiField.size(); i++)
		{
			guiField.get(i).clear();
		}
		guiField.clear();
	}
	
	/**
	 * The destroy method for this object. Will be called when this object
	 * is destroyed. Not required to be overridden. Empty method.
	 */
	public void destroy(){}
	
	/**
	 * Returns a string that will be used to load the object.
	 * Uses the defaultSave method.
	 * @return String
	 */
	protected String saveObject()
	{
		ArrayList<String> listInfo = defaultSave();
		String text = "";
		for(int i=0; i<listInfo.size(); i++)
		{
			text+=listInfo.get(i);
			if(i!=listInfo.size()-1)
			{
				text+=',';
			}
		}
		
		return text;
	}
	
	/**
	 * Returns a string that will be used to load the object.
	 * Requires an ArrayList<String> that contains all of the data that
	 * needs to be saved.
	 * 
	 * If the defaultLoad method is used, the ArrayList
	 * should be initialized using the defaultSave method.
	 * 
	 * By default, the loadObject method just uses the defaultLoad method.
	 * @param listInfo
	 * @return String
	 */
	protected String saveObject(ArrayList<String> listInfo)
	{
		String text = "";
		for(int i=0; i<listInfo.size(); i++)
		{
			text+=listInfo.get(i);
			if(i!=listInfo.size()-1)
			{
				text+=',';
			}
		}
		
		return text+";";
	}
	
	/**
	 * Loads the values from the input Array into this object.
	 * By default, this method just uses the defaultLoad method.
	 * 
	 * Can be overridden.
	 * @param text
	 */
	public void loadObject(String[] text)
	{
		defaultLoad(text);
	}
	
	/**
	 * Unused method. Plans to be used for a debug mode which allows
	 * time tracking of this object's update method.
	 */
	public void debugUpdate()
	{
		long startTime = System.nanoTime();
		update();
		updateTime = System.nanoTime()-startTime;
	}
	
	/**
	 * Unused method. Plans to be used for a debug mode which allows
	 * time tracking of this object's draw method.
	 */
	public void debugDraw()
	{
		long startTime = System.nanoTime();
		draw();
		renderTime = System.nanoTime()-startTime;
	}
	
	/**
	 * Unused method. Plans to be used for a debug mode which allows
	 * time tracking of this object's update method.
	 * @return long
	 */
	public long getUpdateTime()
	{
		if(debugClass.isInDebugMode()==false)
			System.out.println("Game is not in debugMode. Time will not change.");
		
		return updateTime;
	}
	
	/**
	 * Unused method. Plans to be used for a debug mode which allows
	 * time tracking of this object's draw method.
	 * @return long
	 */
	public long getRenderTime()
	{
		if(debugClass.isInDebugMode()==false)
			System.out.println("Game is not in debugMode. Time will not change.");
		
		return renderTime;
	}
	
	/**
	 * Calls the destroy method. Deprecated method.
	 */
	protected void dispose()
	{
		destroy();
		/*
		if (isAvaliable==true)
			Game.destroyObject( this );
		
		isAvaliable=false;
		*/
	}
	
	public parentGameObject copy()
	{
		return SystemUtils.copyObject(this);
		
	}
}
