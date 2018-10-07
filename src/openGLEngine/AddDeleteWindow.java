package openGLEngine;

import java.util.ArrayList;

public class AddDeleteWindow extends InputWindow {

	private ArrayList<GuiElement> guiList = new ArrayList<GuiElement>();
	
	private float sliderLocation = 0;
	private int selectedElement = -1;
	private float sliderY = 0;
	
	private int listStartLocation = 0;
	private int maxDisplay = 8;
	private int selectedLocation = -1;
	
	public AddDeleteWindow()
	{
		
		width = (int)((double)Display.getWidth()*.25);
		height = (int)((double)Display.getHeight()*.75);
		x = Display.getWidth() - width;
		y = Display.getHeight() - height;
		
		windowSurface = new Surface(width, height, Surface.COLOR);
		
	}
	

	public int getSelectedElement()
	{
		return selectedElement;
	}
	
	public void deactivate()
	{
		selectedElement=-1;
	}
	
	@Override
	public void render()
	{
		// TODO Auto-generated method stub
		
		DefaultResources.defaultShader.start();
		
		windowSurface.bind();
		windowSurface.clear();
		Mat4f m = GameMath.createOrthographicMatrix(0, 0, width, height);
		
		DefaultResources.defaultShader.setProjectionMatrix(m, false);
		
		Game.setOrthoMatrix(m);
		
		GameRender.setColor(0.42f, 0.42f, 0.42f, 1f);
		GameRender.drawRect(0, 0, width, height, false);
		
		GameRender.setColor(0f, 0f, 0f, .2f);
		GameRender.drawRect((width-(width*.05)), (height*.20f) + sliderY, width, ((height*.20f)+(sliderY+(height*.10))), false);
		
		if(selectedElement>=0 && selectedElement<Level.typeString.size())
		{
			GameRender.setColor(.0f, .3f, .3f, 0.5f);
			GameRender.drawRect( width*0.02f, height*0.01f, width*0.88f, height*0.11f, false);
			
			GameRender.setColor(1f, 1f, 1f, 1f);
			GameRender.drawTextStretched(Level.typeString.get(selectedElement), width*0.05f, height*0.01f, width*0.8f, height*0.1f);
		}
		
		int minLength = GameMath.min(listStartLocation+maxDisplay, Level.typeString.size());
		
		float yLoc = 0.2f;
		GameRender.setColor(0f, 0f, 0f, 1f);
		
		for(int i=listStartLocation; i<minLength; i++)
		{
			if(selectedLocation == i)
			{
				GameRender.setColor(0.9607f, 0.9607f, 0.8627f, 1f);
				GameRender.drawRect(width*0.04f, height*yLoc, width*0.06f + width*0.5f, height*yLoc + height*0.08f, true);
				GameRender.setColor(0f, 0f, 0f, 1f);
			}
			
			GameRender.drawTextStretched(Level.typeString.get(i), width*0.05f, height*yLoc, width*0.5f, height*0.08f);
			yLoc += 0.1f;
		}
		
		windowSurface.unBind();
		
		GameRender.setColor(1f, 1f, 1f, 1f);
		
		m = GameMath.createOrthographicMatrix(0, 0, Display.getWidth(), Display.getHeight());
		DefaultResources.defaultShader.setProjectionMatrix(m, false);
		Game.setOrthoMatrix(m);
		
		GameRender.drawSurface(windowSurface, x, y);
	}

	@Override
	public void thisUpdate()
	{
		// TODO Auto-generated method stub
		
		if(Input.getMouseWheelUp() && sliderLocation>0)
		{
			sliderLocation -= 1f / Level.typeString.size();
		}
		else if(Input.getMouseWheelDown()  && listStartLocation+maxDisplay < Level.typeString.size())
		{
			sliderLocation += 1f / Level.typeString.size();
		}
		
		sliderLocation = GameMath.clamp(sliderLocation, 0f, 1f);
		
		sliderY = (float) (sliderLocation*(height*.70));
		
		
		if(sliderLocation*Level.typeString.size() > maxDisplay+listStartLocation)
		{
			listStartLocation+=1;
		}
		else if(sliderLocation*Level.typeString.size() < listStartLocation)
		{
			listStartLocation-=1;
		}
		
		selectedLocation = -1;
		
		if(Input.getMouseX()>=x + width*0.05f && Input.getMouseX()<=x + width*0.5f)
		{
			float yLoc = 0.2f;
			int minLength = GameMath.min(maxDisplay, Level.typeString.size());
			
			for(int i=0; i<minLength; i++)
			{
				if(Input.getMouseY()>=y + height*yLoc && Input.getMouseY()<=y + height*yLoc + height*0.08f)
				{
					selectedLocation = i+listStartLocation;
					if(Input.getMouseButtonPressed(Input.LEFT_MOUSE_BUTTON))
					{
						selectedElement = i+listStartLocation;
					}
				}
				yLoc += 0.1f;
			}
		}
		
	}

}
