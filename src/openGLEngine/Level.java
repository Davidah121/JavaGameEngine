package openGLEngine;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;


public class Level implements Serializable{

	/*
	 * File System
	 * ends with .lvl
	 * format
	 * Width
	 * Height
	 * 
	 * Object, proceeding info for that object
	 * 
	 */
	//Every object needs a specific loader for it.
	
	ArrayList<parentGameObject> objectData = new ArrayList<parentGameObject>();
	
	private int width = 640;
	private int height = 480;
	
	private String name = "";
	
	private static ArrayList<Class> types = new ArrayList<Class>();
	private static ArrayList<String> typeString = new ArrayList<String>();
	
	private static long newID = 0;
	private static long setToID=0;
	
	/**
	 * Creates a new Level. A Level is a object that holds an array of
	 * parentGameObjects that can be used during the game.
	 */
	public Level()
	{
		
	}
	
	/**
	 * Returns the width of the room. The width of the room is just a value
	 * that can be used by the camera to prevent it from moving past a certain
	 * point.
	 * @return
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * Sets the width of the room. The width of the room is just a value
	 * that can be used by the camera to prevent it from moving past a certain
	 * point.
	 * @return
	 */
	public void setWidth(int w)
	{
		width = w;
	}
	
	/**
	 * Returns the height of the room. The width of the room is just a value
	 * that can be used by the camera to prevent it from moving past a certain
	 * point.
	 * @return
	 */
	public int getHeight()
	{
		return height;
	}
	
	/**
	 * Sets the height of the room. The width of the room is just a value
	 * that can be used by the camera to prevent it from moving past a certain
	 * point.
	 * @return
	 */
	public void setHeight(int h)
	{
		height = h;
	}
	
	/**
	 * Returns the name of the room. The name is just an identifier you can use when
	 * searching for the room. The file name will use this name by default and will
	 * store it in its file.
	 * @return
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Sets the name of the room. The name is just an identifier you can use when
	 * searching for the room. The file name will use this name by default and will
	 * store it in its file.
	 * @return
	 */
	public void setName(String n)
	{
		name = n;
	}
	
	public static void verifyClass(Class c)
	{
		Constructor[] cc = c.getConstructors();
		boolean hasEmptyConstructor = false;
		boolean extendsParentGameObject = false;
		Class parentClass = c.getSuperclass();
		
		for(int i=0; i<cc.length; i++)
		{
			if(cc[i].getParameterTypes().length == 0)
			{
				hasEmptyConstructor=true;
				break;
			}
		}
		
		while(!parentClass.equals(Object.class))
		{
			if(parentClass.equals(parentGameObject.class))
			{
				extendsParentGameObject=true;
				break;
			}
			parentClass = parentClass.getSuperclass();
		}
		
		if(hasEmptyConstructor==false)
		{
			//can not be used.
			System.err.println(c.getName()+" can not be used in level creation.");
			System.err.println(c.getName()+" does not have an empty constructor available.");
			System.err.println("Program will now terminate.");
			System.exit(0);
		}
		
		if(extendsParentGameObject==false)
		{
			//can not be used.
			System.err.println(c.getName()+" can not be used in level creation.");
			System.err.println(c.getName()+" does not extend from the parentGameObject class.");
			System.err.println("Program will now terminate.");
			System.exit(0);
		}
	}
	
	/**
	 * In order to make saving and loading easier, this method was
	 * created. Every parentGameObject you intend to use should be added 
	 * to the available types using this method. This makes it so that the object
	 * can be saved and loading if needed.
	 * @return void
	 */
	public static void addObjectType(Class c)
	{
		
		verifyClass(c);
		if(types.indexOf(c)==-1)
		{
			setToID++;
			types.add(c);
			typeString.add(c.getName());
		}
		else
			System.out.println("Exists");
	}
	
	/**
	 * Gets the object type in the static object type list at the location
	 * specified. That location is also the static objectID.
	 * @param id
	 * @return
	 */
	public static Class getObjectClass(int id)
	{
		if(id>=0 && id<types.size())
		{
			return types.get(id);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Returns the location of the class in the list of classes that can be added
	 * in any particular level. If the class is not in the list, it returns -1
	 * @param c
	 * @return
	 */
	public static int getObjectClassID(Class c)
	{
		return types.indexOf(c);
	}
	
	/**
	 * Gets a list of the names of the available objects.
	 * @return
	 */
	public static ArrayList<String> getTypeStringList()
	{
		return typeString;
	}
	
	/**
	 * Gets the size of the static object type list.
	 * @return
	 */
	public static int getObjectTypeSize()
	{
		return types.size();
	}
	
	/**
	 * Clears out data from this particular level object.
	 */
	public void dispose()
	{
		objectData.clear();
	}
	
	/**
	 * Clears out the data from the static object type list.
	 * Use at the end of your program or when it is absolutely
	 * necessary.
	 */
	public static void disposeTypes()
	{
		types.clear();
	}
	
	/**
	 * Adds an object to this level.
	 * @param o
	 */
	public void addObject(parentGameObject o)
	{
		objectData.add(o);
		o.setID((int)newID);
		newID+=1;
	}
	
	/**
	 * Gets an object by it's instance id number if it exists.
	 * @param id
	 * @return
	 */
	public parentGameObject getObjectByObjectID(int id)
	{
		
		for(int i=0; i<objectData.size(); i++)
		{
			if(objectData.get(i).getId() == id)
				return objectData.get(i);
		}

		System.err.println("Could not find an instance with this id");
		return null;
	}
	
	/**
	 * Gets an object in this level by it's location in the list.
	 * @param index
	 * @return
	 */
	public parentGameObject getObject(int index)
	{
		return objectData.get(index);
	}
	
	/**
	 * Gets the size of this level's object list. Not the static
	 * object type list.
	 * @return
	 */
	public int getObjectListSize()
	{
		return objectData.size();
	}
	
	/**
	 * deletes an object by it's instance id number.
	 * @param id
	 */
	public void deleteObject(long id)
	{
		for(int i=0; i<objectData.size(); i++)
		{
			if(objectData.get(i).getId() == id)
				objectData.remove(i).destroy();
		}
	}
	
	public void deleteObject(parentGameObject o)
	{
		objectData.remove(o);
		o.destroy();
	}
	/**
	 * deletes all objects in this level's object list.
	 */
	public void clearLevel()
	{
		for(int i=0; i<objectData.size(); i++)
		{
			objectData.get(i).destroy();
		}
		objectData.clear();
		
		newID = 0;
	}
	
	/**
	 * Copies all of the objects currently in the game's object list
	 * to this level's object list.
	 */
	public void copyLevel()
	{
		objectData.clear();
		
		for(int i=0;i<Game.getObjectListSize();i++)
		{
			objectData.add( Game.findObject(i).copy());
		}
	}
	
	/**
	 * Exports the level to a file with the specified name.
	 * @param name
	 */
	public void exportLevel(String name)
	{
		
		Game.getApplicationSurface().saveImage(name+"_Image.png");
		quickIO file = new quickIO(name+".lvl", quickIO.TYPE_WRITE);
		
		file.write("<START, "+name+", "+width+", "+height+", "+newID+">");
		file.writeln();
		
		for(int i=0; i<objectData.size(); i++)
		{
			String tempData = objectData.get(i).saveObject();
			
			int objectID = typeString.indexOf( objectData.get(i).getObjectName() );
			char c = 9;
			
			file.write( c+"<"+objectID+">" );
			file.writeln();
			
			file.write( c+""+c+"<"+tempData+">" );
			file.writeln();
			
		}
		file.write("<END>");
		file.close();
	}
	
	/**
	 * loads a level from the specified file. must be .lvl format
	 * in order to work.
	 * @param fileName
	 */
	public void loadExternalLevel(String fileName)
	{
		try
		{
			quickIO file = new quickIO(fileName+".lvl", quickIO.TYPE_READ);

			String[] tempString = file.readNextLn().split(",");
			String otherString = "";
			
			name = tempString[1];
			width = GameMath.parseInt(tempString[2]);
			height = GameMath.parseInt(tempString[3]);
			
			newID = GameMath.parseInt( tempString[4].substring(0, tempString[4].indexOf('>')) );
			
			int objID = -1;
			while(!file.endOfFile())
			{
				String text = file.readNextLn(); //ObjectID
				
				if(text.equals("<END>"))
				{
					System.out.println("ENDING");
					break;
				}
				else
				{
					try
					{
						
						objID = GameMath.parseInt(text.substring(1, text.length()-1));
						
						text = file.readNextLn();
						
						tempString = text.substring(1, text.length()-1).split(",");
						
						Class c = getObjectClass(objID);
						
						parentGameObject b = (parentGameObject)c.newInstance();
						
						System.out.println("ADDED OBJECT");
						b.loadObject(tempString);
						objectData.add(b);
						
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
			
			file.close();
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println(objectData.size());
	}
	
	/**
	 * Clears all of the objects in the Game's object list, and copies
	 * this list's objects into the game's object list. Objects that are
	 * specified as persistent will not be deleted from the Game's object list.
	 */
	public void useLevel()
	{
		for(int i=0;i<objectData.size();i++)
		{
			if( Game.findObject(i).getPersistent() == false )
				Game.destroyObject(i);
		}
		
		for(int i=0;i<objectData.size();i++)
		{
			Game.addObject( objectData.get(i).copy() );
		}
		
		Game.resetID();
		Game.currentLevel=this;
	}
	
}
