package glLevelEditor;
import java.util.*;

import openGLEngine.*;

public class EntityProcessor
{

	public static int width = 302;
	public static int height = 532;
	
	public static Surface guiSurface;
	
	public static int offX = 0;
	public static int offY = 0;
	
	private static int startY = 0;
	private static int maxY = 0;
	
	public static String text = "";
	
	private static ArrayList<String> valueRef;
	private static int refLocation = 0;
	
	private static boolean editing = false;
	
	public static void init()
	{
		guiSurface = new Surface(width, height, Surface.COLOR, Surface.LINEAR_FILTERING);
	}
	
	public static void processScrolling()
	{
		if(Input.getMouseWheelDown())
		{
			if(startY>maxY)
			{
				if(Input.getKeyDown(Input.VK_SHIFT))
					startY-=16;
				else
					startY-=8;
			}
			
			if(startY<maxY)
				startY = maxY;
		}
		else if(Input.getMouseWheelUp())
		{
			if(startY<0)
			{
				if(Input.getKeyDown(Input.VK_SHIFT))
					startY+=16;
				else
					startY+=8;
			}
			
			if(startY>0)
				startY=0;
		}
	}
	
	private static void typingStuff()
	{
		if(Input.getKeyPressed(Input.VK_BACKSPACE))
		{
			if(text.length()>0)
				text = text.substring(0, text.length()-1);
		}
		else if(Input.getKeyPressed(Input.VK_ENTER))
		{
			valueRef.set(refLocation, text);
		}
		else if(Input.getKeyboardHasBeenUsed())
		{
			if(Input.getKeyDown(Input.VK_SHIFT))
			{
				text+=Input.getLastKey();
			}
			else
			{
				text+=Character.toLowerCase(Input.getLastKey());
			}
		}
	}
	
	public static void processEntity(Entity obj)
	{
		if(editing==true)
		{
			//KeyStuff
			typingStuff();
		}
		
		int amountOfElements = obj.guiField.size();
		int yVal = startY;
		
		boolean mouseClicked = Input.getMouseButtonPressed(Input.LEFT_MOUSE_BUTTON);
		int mouseX = Input.getMouseX()-offX;
		int mouseY = Input.getMouseY()-offY;
		
		yVal+=28;
		yVal+=28;
		yVal+=28;
		
		if(mouseClicked==true)
		{
			editing = false;
			text = "";
			valueRef = null;
			refLocation = -1;
		}
		
		for(int i=0;i<amountOfElements; i++)
		{
			Entity.EntityElement temp = obj.guiField.get(i);
			
			if(temp.getExpand()==false)
			{
				if(mouseClicked)
				{
					if(mouseX>width-20 && mouseX<width)
					{
						if(mouseY>yVal+6 && mouseY<yVal+26)
						{
							temp.setExpand(true);
						}
					}
				}
			}
			else
			{
				if(mouseClicked)
				{
					if(mouseX>width-20 && mouseX<width)
					{
						if(mouseY>yVal+6 && mouseY<yVal+26)
						{
							temp.setExpand(false);
						}
					}
					
					//GameRender.drawRect(64, yVal+2, width-96, yVal+26, false);
					
				}
			}
			
			if(temp.getExpand()==true)
			{
				char type = temp.getType();
				if(type == Entity.EntityElement.SINGLE_ELEMENT)
				{
					yVal+=28;
					yVal+=28;
					if(mouseClicked)
					{
						if(mouseX>64 && mouseX<width-96)
						{
							if(mouseY>yVal+2 && mouseY<yVal+26)
							{
								//in an editable field
								editing=true;
								valueRef = temp.getElements();
								refLocation = 0;
								text = valueRef.get(0);
							}
						}
					}
				}
				else if(type == Entity.EntityElement.LIST)
				{
					
					for(int i2=0; i2<temp.getSizeOfList(); i2++)
					{
						yVal+=28;
						yVal+=28;
						if(mouseClicked)
						{
							if(mouseX>64 && mouseX<width-96)
							{
								if(mouseY>yVal+2 && mouseY<yVal+26)
								{
									//in an editable field
									editing=true;
									valueRef = temp.getElements();
									refLocation = i2;
									text = valueRef.get(i2);
								}
							}
						}
					}
				}
				else
				{
					for(int i2=0; i2<temp.getSizeOfList(); i2++)
					{
						yVal+=28;
						yVal+=28;
						if(mouseClicked)
						{
							if(mouseX>64 && mouseX<width-96)
							{
								if(mouseY>yVal+2 && mouseY<yVal+26)
								{
									//in an editable field
									editing=true;
									valueRef = temp.getElements();
									refLocation = i2;
									text = valueRef.get(i2);
								}
							}
						}
					}
					yVal+=28;
					
					if(mouseClicked)
					{
						if(mouseX>64 && mouseX<width-96)
						{
							if(mouseY>yVal+2 && mouseY<yVal+26)
							{
								//Button
							}
						}
					}
					//yVal+=28;
				}
			}
			
			yVal+=28;
		}
		
		maxY = -yVal + height + startY - 28;
		
		if(maxY >= 0)
			maxY = 0;
		
		if(startY<maxY)
			startY = maxY;
	}
	
	public static void drawEntityGui(Entity obj)
	{
		guiSurface.bind();
		guiSurface.clear();
		
		Mat4f tMat = GameMath.createOrthographicMatrix(0, 0, width, height);
		Game.setOrthoMatrix(tMat);
		
		Game.set2DBegin();
		int amountOfElements = obj.guiField.size();
		int yVal = startY;
		
		GameRender.setColor(0f, 0f, 0f, 1f);
		//DrawName, InstanceID, ObjectID
		parentGameObject o = (parentGameObject)obj;
		
		GameRender.setColor(0.2f, 0f, 0f, 1f);
		GameRender.drawText("->  "+o.getClass().getName(), 0, yVal);
		yVal+=28;
		GameRender.drawText("->  "+"InstanceID: "+o.getId(), 0, yVal);
		yVal+=28;
		GameRender.drawText("->  "+"ObjectID: "+Level.getObjectClassID(o.getClass()), 0, yVal);
		yVal+=28;
		
		GameRender.setColor(0f, 0f, 0f, 1f);
		
		for(int i=0;i<amountOfElements; i++)
		{
			Entity.EntityElement temp = obj.guiField.get(i);
			
			GameRender.drawText(temp.getName(), 0, yVal);
			if(temp.getExpand()==false)
			{
				GameRender.drawRect(width-20, yVal+14, width, yVal+18, false);
				GameRender.drawRect(width-12, yVal+6, width-8, yVal+26, false);
				//System.out.println("i:"+yVal);
			}
			else
			{
				GameRender.drawRect(width-20, yVal+14, width, yVal+18, false);
			}
			
			yVal+=28;
			
			if(temp.getExpand()==true)
			{
				char type = temp.getType();
				if(type == Entity.EntityElement.SINGLE_ELEMENT)
				{
					if(temp.getElements() == valueRef && editing)
					{
						GameRender.setColor(1f,0f,0.3f,1f);
						GameRender.drawText(temp.getElementName().get(0), 0, yVal);
						yVal+=28;
						GameRender.setColor(1f,1f,1f,1f);
						GameRender.drawRect(64, yVal+2, width-96, yVal+26, false);
						
						GameRender.setColor(1f,0f,0.3f,1f);
						GameRender.drawRect(64, yVal+2, width-96, yVal+26, true);
					
						GameRender.setColor(0f,0f,0f,1f);
						GameRender.drawText(text, 68, yVal);
					}
					else
					{
						GameRender.setColor(0f,0f,0f,1f);
						GameRender.drawText(temp.getElementName().get(0), 0, yVal);
						yVal+=28;
						GameRender.setColor(1f,1f,1f,1f);
						GameRender.drawRect(64, yVal+2, width-96, yVal+26, false);
						
						GameRender.setColor(0f,0f,0f,1f);
						GameRender.drawRect(64, yVal+2, width-96, yVal+26, true);
						
						GameRender.setColor(0f,0f,0f,1f);
						GameRender.drawText(temp.getElements().get(0), 68, yVal);
					}
					
					yVal+=28;
				}
				else if(type == Entity.EntityElement.LIST)
				{
					for(int i2=0; i2<temp.getSizeOfList(); i2++)
					{
						if(temp.getElements() == valueRef && i2 == refLocation && editing)
						{
							GameRender.setColor(1f,0f,0.3f,1f);
							GameRender.drawText(temp.getElementName().get(i2), 0, yVal);
							yVal+=28;
							GameRender.setColor(1f, 1f, 1f, 1f);
							GameRender.drawRect(64, yVal+2, width-96, yVal+26, false);
							
							GameRender.setColor(1f,0f,0.3f,1f);
							GameRender.drawRect(64, yVal+2, width-96, yVal+26, true);
							
							GameRender.setColor(0f,0f,0f,1f);
							GameRender.drawText(text, 68, yVal);
						}
						else
						{
							GameRender.setColor(0f,0f,0f,1f);
							GameRender.drawText(temp.getElementName().get(i2), 0, yVal);
							yVal+=28;
							GameRender.setColor(1f,1f,1f,1f);
							GameRender.drawRect(64, yVal+2, width-96, yVal+26, false);
							
							GameRender.setColor(0f,0f,0f,1f);
							GameRender.drawRect(64, yVal+2, width-96, yVal+26, true);
							
							GameRender.setColor(0f,0f,0f,1f);
							GameRender.drawText(temp.getElements().get(i2), 68, yVal);
						}
						yVal+=28;
					}
				}
				else
				{
					for(int i2=0; i2<temp.getSizeOfList(); i2++)
					{
						if(temp.getElements() == valueRef && i2 == refLocation && editing)
						{
							GameRender.setColor(1f,0f,0.3f,1f);
							GameRender.drawText(temp.getElementName().get(i2), 0, yVal);
							yVal+=28;
							GameRender.setColor(1f,1f,1f,1f);
							GameRender.drawRect(64, yVal+2, width-96, yVal+26, false);
							
							GameRender.setColor(1f,0f,0.3f,1f);
							GameRender.drawRect(64, yVal+2, width-96, yVal+26, true);
						
							GameRender.setColor(0f,0f,0f,1f);
							GameRender.drawText(text, 68, yVal);
						}
						else
						{
							GameRender.setColor(0f,0f,0f,1f);
							GameRender.drawText(temp.getElementName().get(i2), 0, yVal);
							yVal+=28;
							GameRender.setColor(1f,1f,1f,1f);
							GameRender.drawRect(64, yVal+2, width-96, yVal+26, false);
							
							GameRender.setColor(0f,0f,0f,1f);
							GameRender.drawRect(64, yVal+2, width-96, yVal+26, true);
							
							GameRender.setColor(0f,0f,0f,1f);
							GameRender.drawText(temp.getElements().get(i2), 68, yVal);
						}
						yVal+=28;
					}
					
					GameRender.setColor(1f,1f,1f,1f);
					GameRender.drawRect(0, yVal-10, width, yVal+10, false);
					GameRender.setColor(0f,0f,0f,1f);
					GameRender.drawRect(0, yVal-10, width, yVal+10, true);
					GameRender.setColor(0f,0f,0f,1f);
					GameRender.drawText("Add/Delete Value", 0, yVal);
					yVal+=28;
				}
			}
			
		}
		
		guiSurface.unBind();
		
		Game.createOrthoProjectionMatrix(0, 0, 1280, 720);
		Game.set2DBegin();
	}
}
