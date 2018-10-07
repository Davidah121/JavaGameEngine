package openGLEngine;

public abstract class InputWindow extends parentGameObject {

	protected Surface windowSurface;
	protected boolean active = false;
	protected int x = 0;
	protected int y = 0;
	protected int width = 0;
	protected int height = 0;
	private boolean first = false;
	
	private int prevX = 0;
	private int prevY = 0;
	
	private int tempX = 0;
	private int tempY = 0;
	private int tempWidth = 0;
	private int tempHeight = 0;
	
	private int scaleMode = 0; //0=none, 1=left, 2=right, 3=top, 4=bottom, 5=topLeft, 6=topRight, 7=bottomLeft, 8=bottomRight 
	
	protected void init()
	{
		windowSurface = new Surface(width, height, Surface.COLOR);
	}
	
	public void scaleWindow()
	{
		if(Input.getMouseButtonDown(Input.LEFT_MOUSE_BUTTON))
		{
			tempX = x;
			tempY = y;
			tempWidth = width;
			tempHeight = height;
		}
		
		if(Input.getMouseX() >= x-6 && Input.getMouseX() < x+2)
		{
			//System.out.println("true");
			//Left edge
			if(Input.getMouseY() >= y+2 && Input.getMouseY() < y+height-2)
			{
				//just left edge
				if(Input.getMouseButtonPressed(Input.LEFT_MOUSE_BUTTON))
				{
					prevX = Input.getMouseX();
					prevY = Input.getMouseY();
					scaleMode = 1;
				}
			}
			else if (Input.getMouseY() >= y-6 && Input.getMouseY() < y+2)
			{
				//Top left edge
				if(Input.getMouseButtonPressed(Input.LEFT_MOUSE_BUTTON))
				{
					prevX = Input.getMouseX();
					prevY = Input.getMouseY();
					scaleMode = 5;
				}
			}
			else if (Input.getMouseY() >= y+height-2 && Input.getMouseY() < y+height+6)
			{
				//Bottom Left edge
				if(Input.getMouseButtonPressed(Input.LEFT_MOUSE_BUTTON))
				{
					prevX = Input.getMouseX();
					prevY = Input.getMouseY();
					scaleMode = 7;
				}
			}
			
			System.out.println(scaleMode);
		}
		else if(Input.getMouseX() >= x+width-2 && Input.getMouseX() < x+width+6)
		{
			//Right edge
			if(Input.getMouseY() >= y+2 && Input.getMouseY() < y+height-2)
			{
				//just right edge
				if(Input.getMouseButtonPressed(Input.LEFT_MOUSE_BUTTON))
				{
					prevX = Input.getMouseX();
					prevY = Input.getMouseY();
					scaleMode = 2;
				}
			}
			else if (Input.getMouseY() >= y-6 && Input.getMouseY() < y+2)
			{
				//Top right edge
				if(Input.getMouseButtonPressed(Input.LEFT_MOUSE_BUTTON))
				{
					prevX = Input.getMouseX();
					prevY = Input.getMouseY();
					scaleMode = 6;
				}
			}
			else if (Input.getMouseY() >= y+height-2 && Input.getMouseY() < y+height+6)
			{
				//Bottom right edge
				if(Input.getMouseButtonPressed(Input.LEFT_MOUSE_BUTTON))
				{
					prevX = Input.getMouseX();
					prevY = Input.getMouseY();
					scaleMode = 8;
				}
			}
		}
		else if(Input.getMouseY() >= y-6 && Input.getMouseY() < y+2)
		{
			
			//Top edge
			if(Input.getMouseX() >= x+2 && Input.getMouseX() < x+width-2)
			{
				if(Input.getMouseButtonPressed(Input.LEFT_MOUSE_BUTTON))
				{
					prevX = Input.getMouseX();
					prevY = Input.getMouseY();
					scaleMode = 3;
				}
			}
			//Won't check the others because they would have happened
		}
		else if(Input.getMouseY() >= y+height-2 && Input.getMouseY() < y+height+6)
		{
			//bottom edge
			if(Input.getMouseX() >= x+2 && Input.getMouseX() < x+width-2)
			{
				if(Input.getMouseButtonPressed(Input.LEFT_MOUSE_BUTTON))
				{
					prevX = Input.getMouseX();
					prevY = Input.getMouseY();
					scaleMode=4;
				}
			}
			//Won't check the others because they would have happened
		}
		
		
		if(scaleMode!=0)
		{
			if(Input.getMouseButtonDown(Input.LEFT_MOUSE_BUTTON))
			{
				int xDis = prevX - Input.getMouseX();
				int yDis = prevY - Input.getMouseY();
				
				System.out.println(xDis);
				switch(scaleMode)
				{
				case 1:
					tempX = x-xDis;
					tempWidth = width+xDis;
					break;
				case 2:
					tempWidth = width-xDis;
					break;
				case 3:
					tempY = y-yDis;
					tempHeight = height+yDis;
					break;
				case 4:
					tempHeight = height-yDis;
					break;
				case 5:
					tempX = x-xDis;
					tempY = y-yDis;
					
					tempWidth = width+xDis;
					tempHeight = height+yDis;
					break;
				case 6:
					tempWidth = width-xDis;
					tempY = y-yDis;

					tempHeight = height+yDis;
					break;
				case 7:
					tempX = x-xDis;
					tempHeight = height-yDis;

					tempWidth = width+xDis;
					break;
				case 8:
					tempWidth = width-xDis;
					tempHeight = height-yDis;
					break;
				default:
					break;
				}
			}
		}
		
		if(Input.getMouseButtonUp(Input.LEFT_MOUSE_BUTTON))
		{
			x=tempX;
			y=tempY;
			width = tempWidth;
			height = tempHeight;
			scaleMode = 0;
		}
	}
	
	@Override
	public void update()
	{
		
		if(visible==true)
		{
			if(active==true)
			{
				thisUpdate();
				first=true;
			}
			else
			{
				if(first==true)
				{
					deactivate();
					first=false;
				}
			}
			
			
			if(Input.getMouseButtonPressed(Input.LEFT_MOUSE_BUTTON))
			{
				active = false;
				if(Input.getMouseX()>=x && Input.getMouseX()<=x+width)
				{
					if(Input.getMouseY()>=y && Input.getMouseY()<=y+height)
					{
						active = true;
					}
				}
			}
			
			scaleWindow();
		}
	}
	
	public abstract void thisUpdate();
	public abstract void render();
	public void deactivate(){};
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		if(visible==true)
		{
			if(active==true)
			{
				GameRender.setColor(0.0f, 0.7f, 0.7f, 1f);
				GameRender.drawRect(x-1, y-1, x+width+1, y+height+1, true);
				GameRender.setColor(0.0f, 0.3f, 0.3f, 1f);
				GameRender.drawRect(x-2, y-2, x+width+2, y+height+2, true);
				
				render();
			}
			else
			{
				GameRender.setColor(0.4f, 0.4f, 0.4f, 1f);
				GameRender.drawRect(x-1, y-1, x+width+1, y+height+1, true);
				GameRender.setColor(0.3f, 0.3f, 0.3f, 1f);
				GameRender.drawRect(x-2, y-2, x+width+2, y+height+2, true);
			}
			
			if(scaleMode!=0)
			{
				GameRender.setColor(0.0f, 1.0f, 1.0f, 1f);
				GameRender.drawRect(tempX, tempY, tempX+tempWidth, tempY+tempHeight, true);
			}
		}
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public boolean getActive()
	{
		return active;
	}
	
	public Surface getSurface()
	{
		return windowSurface;
	}
	
	public void setX(int value)
	{
		x = value;
	}
	
	public void setY(int value)
	{
		y = value;
	}
	
	public void setWidth(int value)
	{
		width = value;
	}
	
	public void setHeight(int value)
	{
		height = value;
	}
	
	public void setActive(boolean value)
	{
		active = value;
	}
	
	public void setSurface(Surface value)
	{
		windowSurface = value;
	}
}
