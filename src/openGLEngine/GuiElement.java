package openGLEngine;

import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;

public class GuiElement implements Serializable{
	
	private String description = "";
	public String textField = "";
	public String value = "";
	public double numValue = 0;
	public boolean switchValue = false;
	
	public Box box = new Box(0,0,0,0);
	private boolean active = false;
	private int cursorLocation = 0;
	private boolean visible = true;
	
	private int type = 0;
	
	private static final int TYPE_TEXT = 0;
	private static final int TYPE_SLIDER = 1;
	private static final int TYPE_BUTTON = 2;
	private static final int TYPE_LIST = 3;
	
	private Vec2f descriptionOffset = new Vec2f(0,0);
	
	private Vec2f range = null;
	private Vec2f bounds = null;
	private boolean isVertical = false;
	
	private ArrayList<String> listItem = new ArrayList<String>();
	
	private int startPos = 0;				//Start location of rendered text
	private int limit = 9;					//Amount of characters that can be rendered in a box at a time
	private int listLimit = 9;				//Amout of items shown in a list at a time
	private int startList = 0;				//Start location of the items in a list.
	private Rectangle listBoxSize = new Rectangle();
	
	private boolean blink = false;
	private double lastTime = Display.getTime();
	private int fontSize = 0;
	
	private Vec4f color = new Vec4f(0f,0f,0f,1f);
	
	private boolean hover = false;
	
	private GuiElement(int type)
	{
		switch(type)
		{
		case TYPE_TEXT:
			this.type=TYPE_TEXT;
			break;
		case TYPE_SLIDER:
			this.type=TYPE_SLIDER;
			break;
		case TYPE_BUTTON:
			this.type=TYPE_BUTTON;
			break;
		case TYPE_LIST:
			this.type=TYPE_LIST;
			break;
		default:
			this.type=TYPE_TEXT;
			break;
		}
		
		Game.addGuiElement(this);
	}
	
	public static GuiElement createTextBox(int x1, int y1, int x2, int y2)
	{
		GuiElement textBox = new GuiElement(TYPE_TEXT);
		textBox.setBoxSize(x1, y1, x2, y2);
		
		return textBox;
	}
	
	public static GuiElement createTextBox(Box r)
	{
		GuiElement textBox = new GuiElement(TYPE_TEXT);
		textBox.setBoxSize(r);
		
		return textBox;
	}
	
	public static GuiElement createButton(int x1, int y1, int x2, int y2)
	{
		GuiElement button = new GuiElement(TYPE_BUTTON);
		
		button.setBoxSize(x1, y1, x2, y2);
		return button;
	}
	
	public static GuiElement createButton(Box r)
	{
		GuiElement button = new GuiElement(TYPE_BUTTON);
		
		button.setBoxSize(r);
		return button;
		
	}
	
	public static GuiElement createSlider(int x1, int y1, int x2, int y2, Vec2f bounds, Vec2f range, boolean isVertical)
	{
		GuiElement slider = new GuiElement(TYPE_SLIDER);
		
		slider.setBoxSize(x1,y1,x2,y2);
		slider.setBounds(bounds);
		slider.setRange(range);
		
		return slider;
	}
	
	public static GuiElement createSlider(Box r, Vec2f bounds, Vec2f range, boolean isVertical)
	{
		GuiElement slider = new GuiElement(TYPE_SLIDER);
		
		slider.setBoxSize(r);
		slider.setBounds(bounds);
		slider.setRange(range);
		
		return slider;
	}
	
	public static GuiElement createList(int x1, int y1, int x2, int y2, ArrayList<String> list)
	{
		GuiElement listElement = new GuiElement(TYPE_LIST);
		
		listElement.setBoxSize(x1, y1, x2, y2);
		listElement.setList(list);
		
		if(list.size()>0)
		{
			listElement.value = list.get(0);
			listElement.textField = list.get(0);
		}
		
		return listElement;
	}
	
	public static GuiElement createList(Box r, ArrayList<String> list)
	{
		GuiElement listElement = new GuiElement(TYPE_LIST);
		
		listElement.setBoxSize(r);
		listElement.setList(list);
		
		listElement.value = list.get(0);
		listElement.textField = list.get(0);
		
		return listElement;
	}
	
	public void setDescription(String info)
	{
		description = info;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescriptionOffset(Vec2f offset)
	{
		descriptionOffset = offset;
	}
	
	public void setDescriptionOffset(int xOffset, int yOffset)
	{
		descriptionOffset.x = xOffset;
		descriptionOffset.y = yOffset;
	}
	
	public Vec2f getDescriptionOffset()
	{
		return descriptionOffset;
	}
	
	public void setFontColor(Vec4f color)
	{
		this.color = color;
	}
	
	public Vec4f getFontColor()
	{
		return color;
	}
	
	public void setFontSize(int size)
	{
		fontSize=size;
	}
	
	public void setDisplayLimit(int limit)
	{
		this.limit = limit;
	}
	
	public void setBounds(int min, int max)
	{
		bounds.x=min;
		bounds.y=max;
	}
	
	public void setRange(float min, float max)
	{
		range.x=min;
		range.y=max;
	}
	
	public void setBounds(Vec2f b)
	{
		bounds=b;
	}
	
	public void setRange(Vec2f r)
	{
		range=r;
	}
	
	public void setVertical(boolean isVertical)
	{
		this.isVertical = isVertical;
	}
	
	public void setBoxSize(Box r)
	{
		box = r;
	}
	
	public void setBoxSize(int x1, int y1, int x2, int y2)
	{
		box = new Box(x1,y1,x2,y2);
	}
	
	public void setList(ArrayList<String> list)
	{
		listItem.clear();
		listItem = list;
	}
	
	public void setListBoxSize(int x1, int y1, int x2, int y2)
	{
		listBoxSize = new Rectangle(x1,y1,x2-x1,y2-y1);
	}
	
	public void setListBoxSize(Rectangle r)
	{
		listBoxSize = r;
	}
	
	public boolean getVisible()
	{
		return visible;
	}
	
	public void setVisible(boolean thisValue)
	{
		visible = thisValue;
	}
	
	public boolean getActive()
	{
		return active;
	}
	
	private void setActive(boolean value)
	{
		active=value;
	}
	
	public void updateTextField(Point p)
	{
		if(Input.getMouseButtonPressed(Input.LEFT_MOUSE_BUTTON))
		{
			
			if(GameLogic.getCollisionBoxToPoint(box, p))
			{
				active=true;
			}
			else
			{
				active=false;
				textField = value;
				cursorLocation = 0;
			}
		}
		
		if(active==true)
		{
			double currentTime = Display.getTime();
			if(currentTime-lastTime>=0.5)
			{
				lastTime=currentTime;
				blink=!blink;
			}
			
			if(Input.getKeyPressed(Input.VK_LEFT))
			{
				cursorLocation-=1;
				if(cursorLocation<=0)
				{
					cursorLocation=0;
				}
				if(cursorLocation-1<=startPos)
				{
					startPos--;
					
					if(startPos<=0)
						startPos=0;
				}
			}
			else if(Input.getKeyPressed(Input.VK_RIGHT))
			{
				cursorLocation+=1;
				if(cursorLocation>=textField.length())
				{
					cursorLocation=textField.length();
				}
				
				if(cursorLocation+1>=startPos+limit)
				{
					if(startPos+limit < textField.length())
						startPos++;
				}
			}
			
			if(Input.getKeyboardHasBeenUsed()==true)
			{
				if(Input.getLastKey()==Input.VK_BACKSPACE)
				{
					try
					{
						String String1 = textField.substring(0, cursorLocation-1);
						String String2 = textField.substring(cursorLocation, textField.length());
						
						textField=String1+String2;
						
						cursorLocation--;
						
						if(cursorLocation-1<=startPos)
						{
							startPos--;
							
							if(startPos<=0)
								startPos=0;
						}
					} 
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				else if(Input.getLastKey()==Input.VK_DELETE)
				{
					try
					{
						String String1 = textField.substring(0, cursorLocation);
						String String2 = textField.substring(cursorLocation+1, textField.length());
						
						textField=String1+String2;
					} 
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				else if(Input.getKeyPressed(Input.VK_ENTER))
				{
					value = textField;
				}
				else
				{
				
					if(Input.getLastKey()>=32 && Input.getLastKey()<=126 )
					{
						if(Input.getKeyDown(Input.VK_SHIFT))
						{
							String st1 = textField.substring(0, cursorLocation);
							String st2 = textField.substring(cursorLocation, textField.length());
							
							st1+=Character.toUpperCase( Input.getLastKey() );
							
							textField = st1+st2;
						}
						else
						{
							String st1 = textField.substring(0, cursorLocation);
							String st2 = textField.substring(cursorLocation, textField.length());
							
							st1+=Character.toLowerCase( Input.getLastKey() );
							
							textField = st1+st2;
						}
						
						cursorLocation++;
						
						if(cursorLocation+1>=startPos+limit)
						{
							if(startPos+limit < textField.length())
								startPos++;
						}
					}
				}
			
			}
			
		}
		else
		{
			textField=value;
		}
	}
	
	public void updateSlider(Point p)
	{
		if(Input.getMouseButtonDown(Input.LEFT_MOUSE_BUTTON))
		{
			if(GameLogic.getCollisionBoxToPoint(box, p))
			{
				active=true;
			}
		}
		else
		{
			active=false;
		}
		
		if(active==true)
		{
			Vec3f pos = box.getPositionVector();
			
			if(isVertical==false)
			{
				
				pos.x = (int) GameMath.clamp(p.x, bounds.x, bounds.y);
				
				double multValue = (pos.x-bounds.x)/ (bounds.y-bounds.x);
				numValue = ( (range.y-range.x)*multValue ) + range.x;
			}
			else
			{
				pos.y = (int) GameMath.clamp(p.y, bounds.x, bounds.y);
				
				double multValue = (pos.y-bounds.x)/ (bounds.y-bounds.x);
				numValue =  ( (range.y-range.x)*multValue ) + range.x;
			}
			
			box.setPositionVector(pos);
			box.updateCollisionHull();
		}
		else
		{
			Vec3f pos = box.getPositionVector();
			
			numValue = GameMath.clamp(numValue, GameMath.min(range.x, range.y), GameMath.max(range.x, range.y));
			
			float newValue = (float) (numValue-range.getXFloat()) / (range.getYFloat()-range.getXFloat());
			float moveValue = (bounds.getYFloat()-bounds.getXFloat())*newValue;
			
			if(isVertical==false)
			{
				pos.x = (int) (bounds.x+moveValue);
			}
			else
			{
				pos.y = (int) (bounds.x+moveValue);
			}
			
			box.setPositionVector(pos);
			box.updateCollisionHull();
		}
	}
	
	public void updateButton(Point p)
	{
		if(Input.getMouseButtonPressed(Input.LEFT_MOUSE_BUTTON))
		{
			if(GameLogic.getCollisionBoxToPoint(box, p))
			{
				active=true;
				
				switchValue=!switchValue;
			}
		}
		
		
		if(Input.getMouseButtonUp(Input.LEFT_MOUSE_BUTTON))
		{
			active=false;
		}
	}
	
	
	public void updateList(Point p)
	{
		
		if(active==true)
		{
			if(Input.getMouseWheelDown())
			{
				startList+=1;
				if(startList+listLimit >= listItem.size())
				{
					startList=listItem.size()-listLimit;
				}
			}
			else if(Input.getMouseWheelUp())
			{
				startList-=1;
				if(startList<=0)
				{
					startList=0;
				}
			}
			if(Input.getMouseButtonPressed(Input.LEFT_MOUSE_BUTTON))
			{
				//More Collision Stuff
				if(p.x >= box.getMinX() && p.x <= box.getMaxX())
				{
					
					int i2=0;
					for(int i=startList; i<GameMath.min(startList+listLimit, listItem.size()); i++)
					{
						if(p.y>=box.getBottomBound()*(i2+1) && p.y<=box.getBottomBound()*(i2+2) )
						{
							value = listItem.get(i);
						}
						i2++;
					}
				}
			}
		}
		
		if(Input.getMouseButtonPressed(Input.LEFT_MOUSE_BUTTON))
		{
			
			if(GameLogic.getCollisionBoxToPoint(box, p))
			{
				active = !active;
			}
			else
			{
				active = false;
			}
		}
		
	}
	
	
	public void update(Point p)
	{
		if(type==TYPE_TEXT)
		{
			updateTextField(p);
		}
		else if(type==TYPE_SLIDER)
		{
			updateSlider(p);
		}
		else if(type==TYPE_BUTTON)
		{
			updateButton(p);
		}
		else if(type==TYPE_LIST)
		{
			updateList(p);
		}
		
		if(active==true)
		{
			
			for(int i=0; i<Game.getGuiListSize(); i++)
			{
				Game.getGuiElement(i).setActive(false);
			}
			active=true;
		}
	}
	
	public void renderDescription()
	{
		if(description!=null && !description.equals(""))
		{
			GameRender.setColor(color);
			//GameRender.drawText(description, (box.getLeftBound()+descriptionOffset.x), (box.getTopBound()+descriptionOffset.y));
			GameRender.drawTextStretched(description, (box.getLeftBound()+descriptionOffset.x), (box.getTopBound()+descriptionOffset.y), GameMath.abs(descriptionOffset.x), (box.getMaxY()-box.getMinY()));
			
		}
	}
	
	public void render()
	{
		if(visible==true)
		{
			renderDescription();
			if(type == TYPE_TEXT)
			{
				String finalString = textField.substring(startPos, GameMath.min(textField.length(), startPos+limit));
				
				if(active==false)
				{
					GameRender.setColor(0.45f, 0.45f, 0.45f, 1f);
					GameRender.drawRect(box.getLeftBound(), box.getTopBound(), box.getRightBound(), box.getBottomBound(), false);
					
					GameRender.setColor(0.6f, 0.6f, 0.6f, 1f);
					GameRender.drawRect(box.getLeftBound()+1, box.getTopBound()+1, box.getRightBound()-1, box.getBottomBound()-1, true);
					
					GameRender.setColor(0.7f, 0.7f, 0.7f, 1f);
					GameRender.drawRect(box.getLeftBound(), box.getTopBound(), box.getRightBound(), box.getBottomBound(), true);
				}
				else
				{
					GameRender.setColor(0.45f, 0.45f, 0.45f, 1f);
					GameRender.drawRect(box.getLeftBound(), box.getTopBound(), box.getRightBound(), box.getBottomBound(), false);
					
					GameRender.setColor(0.7f, 0.7f, 0.7f, 1f);
					GameRender.drawRect(box.getLeftBound()-1, box.getTopBound()-1, box.getRightBound()+1, box.getBottomBound()+1, true);
					
					GameRender.setColor(0.8f, 0.8f, 0.8f, 1f);
					GameRender.drawRect(box.getLeftBound(), box.getTopBound(), box.getRightBound(), box.getBottomBound(), true);
					
					if(blink==true)
					{
						String st1 = textField.substring(startPos, cursorLocation);
						String st2 = textField.substring(cursorLocation, GameMath.min(textField.length(), startPos+limit));
						finalString = st1+"|"+st2;
					}
					//GameRender.drawLine(box.x+((GameRender.fontSize/2)*cursorLocation)+1, box.y+1, box.x+((GameRender.fontSize/2)*cursorLocation)+1, box.y+box.height-1);
				}
				
				GameRender.setColor(0f, 0f, 0f, 1f);
				//GameRender.drawTextStretched(finalString, box.getLeftBound(), box.getTopBound(), box.getRightBound(), box.getBottomBound());
				//GameRender.drawTextStretched(finalString, box.getLeftBound(), box.getTopBound()-6, 2, box.getBottomBound()-28);
				
				GameRender.setFontSize( ((float)(box.getMaxY() - box.getMinY()))/1.25f );
				
				GameRender.drawText(finalString, box.getLeftBound()+4, box.getTopBound());
				
				//GameRender.drawTextStretched(finalString, box.getLeftBound()+4, box.getTopBound()-4, xSize*(finalString.length()+1), box.getMaxY() - box.getMinY());
				
			}
			else if(type == TYPE_SLIDER)
			{
				
				GameRender.setColor(0.25f, 0.25f, 0.25f, 1f);
				GameRender.drawRect(bounds.getXFloat(), box.getCenterY()-2, bounds.getYFloat(), box.getCenterY()+2, false);
				
				if(active==false)
				{
					GameRender.setColor(0.45f, 0.45f, 0.45f, 1f);
					GameRender.drawRect(box.getLeftBound(), box.getTopBound(), box.getRightBound(), box.getBottomBound(), false);
					
					GameRender.setColor(0.6f, 0.6f, 0.6f, 1f);
					GameRender.drawRect(box.getLeftBound()+1, box.getTopBound()+1, box.getRightBound()-1, box.getBottomBound()-1, true);
					
					GameRender.setColor(0.7f, 0.7f, 0.7f, 1f);
					GameRender.drawRect(box.getLeftBound(), box.getTopBound(), box.getRightBound(), box.getBottomBound(), true);
				}
				else
				{
					GameRender.setColor(0.45f, 0.45f, 0.45f, 1f);
					GameRender.drawRect(box.getLeftBound(), box.getTopBound(), box.getRightBound(), box.getBottomBound(), false);
					
					GameRender.setColor(0.7f, 0.7f, 0.7f, 1f);
					GameRender.drawRect(box.getLeftBound()-1, box.getTopBound()-1, box.getRightBound()+1, box.getBottomBound()+1, true);
					
					GameRender.setColor(0.8f, 0.8f, 0.8f, 1f);
					GameRender.drawRect(box.getLeftBound(), box.getTopBound(), box.getRightBound(), box.getBottomBound(), true);
				}
			}
			else if(type == TYPE_BUTTON)
			{
				if(active==false)
				{
					GameRender.setColor(0.45f, 0.45f, 0.45f, 1f);
					GameRender.drawRect(box.getLeftBound(), box.getTopBound(), box.getRightBound(), box.getBottomBound(), false);
					
					GameRender.setColor(0.6f, 0.6f, 0.6f, 1f);
					GameRender.drawRect(box.getLeftBound()+1, box.getTopBound()+1, box.getRightBound()-1, box.getBottomBound()-1, true);
					
					GameRender.setColor(0.7f, 0.7f, 0.7f, 1f);
					GameRender.drawRect(box.getLeftBound(), box.getTopBound(), box.getRightBound(), box.getBottomBound(), true);
				}
				else
				{
					GameRender.setColor(0.45f, 0.45f, 0.45f, 1f);
					GameRender.drawRect(box.getLeftBound(), box.getTopBound(), box.getRightBound(), box.getBottomBound(), false);
					
					GameRender.setColor(0.7f, 0.7f, 0.7f, 1f);
					GameRender.drawRect(box.getLeftBound()-1, box.getTopBound()-1, box.getRightBound()+1, box.getBottomBound()+1, true);
					
					GameRender.setColor(0.8f, 0.8f, 0.8f, 1f);
					GameRender.drawRect(box.getLeftBound(), box.getTopBound(), box.getRightBound(), box.getBottomBound(), true);
				}
				
			}
			else if(type == TYPE_LIST)
			{
				if(active==false)
				{
					GameRender.setColor(0.45f, 0.45f, 0.45f, 1f);
					GameRender.drawRect(box.getLeftBound(), box.getTopBound(), box.getRightBound(), box.getBottomBound(), false);
					
					GameRender.setColor(0.6f, 0.6f, 0.6f, 1f);
					GameRender.drawRect(box.getLeftBound()+1, box.getTopBound()+1, box.getRightBound()-1, box.getBottomBound()-1, true);
					
					GameRender.setColor(0.7f, 0.7f, 0.7f, 1f);
					GameRender.drawRect(box.getLeftBound(), box.getTopBound(), box.getRightBound(), box.getBottomBound(), true);
					
					GameRender.setColor(0f, 0f, 0f, 1f);
					GameRender.drawCircle(box.getRightBound()-8, box.getCenterY(), 4, false);
					
				}
				else
				{
					GameRender.setColor(0.45f, 0.45f, 0.45f, 1f);
					GameRender.drawRect(box.getLeftBound(), box.getTopBound(), box.getRightBound(), box.getBottomBound(), false);
					GameRender.setColor(0.7f, 0.7f, 0.7f, 1f);
					GameRender.drawRect(box.getLeftBound()-1, box.getTopBound()-1, box.getRightBound()+1, box.getBottomBound()+1, true);
					
					GameRender.setColor(0.8f, 0.8f, 0.8f, 1f);
					GameRender.drawRect(box.getLeftBound(), box.getTopBound(), box.getRightBound(), box.getBottomBound(), true);
					GameRender.setColor(0f, 0f, 0f, 1f);
					GameRender.drawCircle(box.getRightBound()-8, box.getCenterY(), 4, true);
					
					int i2=0;
					for(int i=startList; i<GameMath.min(startList+listLimit, listItem.size()); i++)
					{
						GameRender.setColor(0.52f, 0.52f, 0.52f, 1f);
						GameRender.drawRect(box.getLeftBound(), box.getBottomBound()*(i2+1), box.getRightBound(), box.getBottomBound()*(i2+2), false);
						GameRender.setColor(0.7f, 0.7f, 0.7f, 1f);
						GameRender.drawRect(box.getLeftBound(), box.getBottomBound()*(i2+1), box.getRightBound(), box.getBottomBound()*(i2+2), true);
						
						GameRender.setColor(0f,0f,0f,1f);
						GameRender.drawText(listItem.get(i), box.getLeftBound()+4, box.getBottomBound()*(i2+1));
						i2++;
					}
				}
				
				GameRender.setColor(0f, 0f, 0f, 1f);
				GameRender.drawText(value, box.getLeftBound()+4, box.getTopBound());
				/*
				if(fontSize==0)
					GameRender.setFontSize(box.height);
				else
					GameRender.setFontSize(fontSize);
				
				fontSize=16;
				
				if(active==false)
				{
					GameRender.setColor(0.45f, 0.45f, 0.45f, 1f);
					GameRender.drawRect(box.x, box.y, box.x+box.width, box.y+box.height, false);
					
					GameRender.setColor(0.6f, 0.6f, 0.6f, 1f);
					GameRender.drawRect(box.x+1, box.y+1, box.x+box.width-1, box.y+box.height-1, true);
					
					GameRender.setColor(0.7f, 0.7f, 0.7f, 1f);
					GameRender.drawRect(box.x, box.y, box.x+box.width, box.y+box.height, true);
					
					GameRender.setColor(0f, 0f, 0f, 1f);
					GameRender.drawTriangle(box.x+box.width+2, box.y+4, box.x+box.width+10, box.y+box.height-4, box.x+box.width+18, box.y+4, false);
				}
				else
				{
					GameRender.setColor(0.45f, 0.45f, 0.45f, 1f);
					GameRender.drawRect(box.x, box.y, box.x+box.width, box.y+box.height, false);
					
					GameRender.setColor(0.7f, 0.7f, 0.7f, 1f);
					GameRender.drawRect(box.x-1, box.y-1, box.x+box.width+1, box.y+box.height+1, true);
					
					GameRender.setColor(0.8f, 0.8f, 0.8f, 1f);
					GameRender.drawRect(box.x, box.y, box.x+box.width, box.y+box.height, true);
					
					GameRender.setColor(0f, 0f, 0f, 1f);
					GameRender.drawTriangle(box.x+box.width+2, box.y+box.height-4, box.x+box.width+10, box.y+4, box.x+box.width+18, box.y+box.height-4, false);
					
					int i2=0;
					for(int i=startList; i<GameMath.min(startList+listLimit, listItem.size()); i++)
					{
						int tempHeight = box.height*(i2+1);
						GameRender.setColor(0.35f, 0.35f, 0.35f, 1f);
						GameRender.drawRect(box.x, box.y +tempHeight, box.x+box.width, box.y+box.height+tempHeight, false);
						
						GameRender.setColor(0.6f, 0.6f, 0.6f, 1f);
						GameRender.drawRect(box.x+1, box.y+1+tempHeight, box.x+box.width-1, box.y+box.height-1+tempHeight, true);
						
						GameRender.setColor(0.7f, 0.7f, 0.7f, 1f);
						GameRender.drawRect(box.x, box.y+tempHeight, box.x+box.width, box.y+box.height+tempHeight, true);
						
						GameRender.setColor(0f, 0f, 0f, 1f);
						GameRender.drawText(listItem.get(i), box.x+4, box.y+tempHeight );
						
						i2++;
					}
					
					int val1 = listItem.size() - listLimit;
					// val1 = 9 - 6; // val1 = 3; // maxSize 
					float adjustHeight = ((float)startList/val1) * (box.height* (listLimit-1) );
					
					GameRender.drawRect(box.x+box.width, box.y+box.height+adjustHeight, box.x+box.width+4, box.y+box.height+16+adjustHeight, false);
				}
				
				GameRender.setColor(0f, 0f, 0f, 1f);
				GameRender.drawText(textField, box.x+4, box.y);
				*/
				
			}
		}
	}
	
}
