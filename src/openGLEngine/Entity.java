package openGLEngine;

import java.util.*;
public abstract class Entity {

	public class EntityElement
	{
		private char type = 0;
		private boolean expand = false;
		
		private ArrayList<String> elements = new ArrayList<String>(); //The element to edit
		private ArrayList<String> elementName = new ArrayList<String>(); //Will appear next to the element
		private String name; //Name of the entire field.
		
		public static final char SINGLE_ELEMENT = 0; //Single element
		public static final char LIST = 1; //A static List of elements
		public static final char ARRAY_LIST = 2; //A dynamic List of elements
		
		public EntityElement(String name, char type)
		{
			this.name = name;
			this.type = type;
		}
		
		public String getName()
		{
			return name;
		}
		
		public ArrayList<String> getElements()
		{
			return elements;
		}
		
		public ArrayList<String> getElementName()
		{
			return elementName;
		}
		
		public void addElement(String n)
		{
			elements.add("");
			elementName.add(n);
		}
		
		public int getSizeOfList()
		{
			return elements.size();
		}
		
		public char getType()
		{
			return type;
		}
		
		public boolean getExpand()
		{
			return expand;
		}
		
		public void setExpand(boolean v)
		{
			expand = v;
		}
		
		public void clear()
		{
			elements.clear();
			elementName.clear();
		}
	}
	
	public ArrayList<EntityElement> guiField = new ArrayList<EntityElement>();
	
	public abstract void prepareGuiField();
	public abstract void reloadGuiField();
	public abstract void updateItems();
}
