package openGLEngine;

import java.util.ArrayList;

public class SelectWindow extends InputWindow {

	private float sliderLocation = 0;
	private int selectedElement = -1;
	private float sliderY = 0;
	private boolean sliderActivated = false;
	
	private int listStartLocation = 0;
	private int maxDisplay = 8;
	private int selectedLocation = -1;
	
	private int selectedTab = 0;
	private ArrayList<Integer> tabs = new ArrayList<Integer>();
	private ArrayList<Box> tabButton = new ArrayList<Box>();
	
	private int yLocation = 0;
	public SelectWindow()
	{
		
		width = (int)((double)Display.getWidth()*.25);
		height = (int)((double)Display.getHeight()*.75);
		x = Display.getWidth() - width;
		y = Display.getHeight() - height;
		
		//windowSurface = new Surface(width, height, Surface.COLOR);
		
		//initGui();
	}
	
	private void initGui()
	{
		
		editParts.add( GuiElement.createTextBox((int)(width*0.23), (int)(height*.1), (int)(width*.9), (int)(height*.18)) ); //X
		editParts.get(0).setDescription("X ");
		editParts.get(0).setDescriptionOffset((int)(-width*0.2) ,0);
		editParts.get(0).setFontColor(new Vec4f(.6f, .2f, .2f, 1f));
		editParts.get(0).setDisplayLimit(8);
		editParts.get(0).setVisible(true);
		tabs.add(0);
		
		editParts.add( GuiElement.createTextBox((int)(width*0.23), (int)(height*.2), (int)(width*.9), (int)(height*.28)) ); //Y
		editParts.get(1).setDescription("Y ");
		editParts.get(1).setDescriptionOffset((int)(-width*0.2) ,0);
		editParts.get(1).setFontColor(new Vec4f(.6f, .2f, .2f, 1f));
		editParts.get(1).setDisplayLimit(8);
		tabs.add(0);
		
		editParts.add(GuiElement.createTextBox((int)(width*0.23), (int)(height*.3), (int)(width*.9), (int)(height*.38)) ); //Z
		editParts.get(2).setDescription("Z ");
		editParts.get(2).setDescriptionOffset((int)(-width*0.2) ,0);
		editParts.get(2).setFontColor(new Vec4f(.6f, .2f, .2f, 1f));
		editParts.get(2).setDisplayLimit(8);
		tabs.add(0);
		
		editParts.add( GuiElement.createTextBox((int)(width*0.23), (int)(height*.4), (int)(width*.9), (int)(height*.48)) ); //XROT
		editParts.get(3).setDescription("XROT ");
		editParts.get(3).setDescriptionOffset((int)(-width*0.2) ,0);
		editParts.get(3).setFontColor(new Vec4f(.6f, .2f, .2f, 1f));
		editParts.get(3).setDisplayLimit(8);
		tabs.add(0);
		
		editParts.add( GuiElement.createTextBox((int)(width*0.23), (int)(height*.5), (int)(width*.9), (int)(height*.58)) ); //YROT
		editParts.get(4).setDescription("YROT ");
		editParts.get(4).setDescriptionOffset((int)(-width*0.2) ,0);
		editParts.get(4).setFontColor(new Vec4f(.6f, .2f, .2f, 1f));
		editParts.get(4).setDisplayLimit(8);
		tabs.add(0);
		
		editParts.add( GuiElement.createTextBox((int)(width*0.23), (int)(height*.6), (int)(width*.9), (int)(height*.68)) ); //ZROT
		editParts.get(5).setDescription("ZROT ");
		editParts.get(5).setDescriptionOffset((int)(-width*0.2) ,0);
		editParts.get(5).setFontColor(new Vec4f(.6f, .2f, .2f, 1f));
		editParts.get(5).setDisplayLimit(8);
		tabs.add(0);
		
		editParts.add( GuiElement.createTextBox((int)(width*0.23), (int)(height*.7), (int)(width*.9), (int)(height*.78)) ); //XSCALE
		editParts.get(6).setDescription("XSCAL ");
		editParts.get(6).setDescriptionOffset((int)(-width*0.2) ,0);
		editParts.get(6).setFontColor(new Vec4f(.6f, .2f, .2f, 1f));
		editParts.get(6).setDisplayLimit(8);
		tabs.add(0);
		
		editParts.add( GuiElement.createTextBox((int)(width*0.23), (int)(height*.8), (int)(width*.9), (int)(height*.88)) ); //YSCALE
		editParts.get(7).setDescription("YSCAL ");
		editParts.get(7).setDescriptionOffset((int)(-width*0.2) ,0);
		editParts.get(7).setFontColor(new Vec4f(.6f, .2f, .2f, 1f));
		editParts.get(7).setDisplayLimit(8);
		tabs.add(0);
		
		editParts.add( GuiElement.createTextBox((int)(width*0.23), (int)(height*.9), (int)(width*.9), (int)(height*.98)) ); //ZSCALE
		editParts.get(8).setDescription("ZSCAL ");
		editParts.get(8).setDescriptionOffset((int)(-width*0.2) ,0);
		editParts.get(8).setFontColor(new Vec4f(.6f, .2f, .2f, 1f));
		editParts.get(8).setDisplayLimit(8);
		tabs.add(0);
		
		editParts.add( GuiElement.createTextBox((int)(width*0.23), (int)(height*1.0), (int)(width*.9), (int)(height*1.08)) ); //Y
		editParts.get(9).setDescription("DEPTH ");
		editParts.get(9).setDescriptionOffset((int)(-width*0.2) ,0);
		editParts.get(9).setFontColor(new Vec4f(.6f, .2f, .2f, 1f));
		editParts.get(9).setDisplayLimit(8);
		tabs.add(0);
		
		editParts.add( GuiElement.createTextBox((int)(width*0.23), (int)(height*1.1), (int)(width*.9), (int)(height*1.18)) ); //Y
		editParts.get(10).setDescription("VISIB ");
		editParts.get(10).setDescriptionOffset((int)(-width*0.2) ,0);
		editParts.get(10).setFontColor(new Vec4f(.6f, .2f, .2f, 1f));
		editParts.get(10).setDisplayLimit(8);
		tabs.add(0);
		
		editParts.add( GuiElement.createTextBox((int)(width*0.23), (int)(height*1.2), (int)(width*.9), (int)(height*1.28)) ); //Y
		editParts.get(11).setDescription("PERSI ");
		editParts.get(11).setDescriptionOffset((int)(-width*0.2) ,0);
		editParts.get(11).setFontColor(new Vec4f(.6f, .2f, .2f, 1f));
		editParts.get(11).setDisplayLimit(8);
		tabs.add(0);
	}

	public void addGuiElement(parentGameObject o)
	{
		for(int i=0; i<o.editParts.size(); i++)
		{
			editParts.add( o.editParts.get(i) );
			tabs.add( o.editTabs.get(i) );
		}
	}
	
	public void removeGuiElements()
	{
		for(int i=12; i<editParts.size(); i++)
		{
			editParts.remove( i );
			tabs.remove( i );
		}
	}
	
	public int getSelectedElement()
	{
		return selectedElement;
	}
	
	private void setVisibleTabs()
	{
		for(int i=0;i<editParts.size();i++)
		{
			if(tabs.get(i)==selectedTab)
			{
				editParts.get(i).setVisible(true);
			}
			else
			{
				editParts.get(i).setVisible(false);
			}
		}
	}
	
	@Override
	public void render()
	{
		// TODO Auto-generated method stub
		
		/*
		DefaultResources.defaultShader.start();
		
		//Setup a surface to hold our UI. This will allow us to easily toggle it
		//on and off. It also allows us to scale it if we need to.
		windowSurface.bind();
		windowSurface.clear();
		
		Mat4f m = GameMath.createOrthographicMatrix(0, 0, width, height);
		
		DefaultResources.defaultShader.setProjectionMatrix(m, false);
		
		Game.setOrthoMatrix(m);
		
		//Draw Background
		GameRender.setColor(0.42f, 0.42f, 0.42f, 1f);
		GameRender.drawRect(0, 0, width, height, false);
		
		//Draw Slider
		GameRender.setColor(0f, 0f, 0f, .2f);
		//GameRender.drawRect((float)(width-(width*.05)), (height*.20f) + sliderY, width, (float)((height*.20f)+(sliderY+(height*.10))), false);
		GameRender.drawRect((float)(width-(width*.075)), (height*0.1f) + sliderY, width, (float)((sliderY+(height*.20))), false);
		
		//Draw UI parts
		for(int i=0; i<editParts.size();i++)
		{
			editParts.get(i).render();
		}
		
		windowSurface.unBind();
		
		//Next we draw it to our screen scaling as needed.
		GameRender.setColor(1f, 1f, 1f, 1f);
		
		m = GameMath.createOrthographicMatrix(0, 0, Display.getWidth(), Display.getHeight());
		DefaultResources.defaultShader.setProjectionMatrix(m, false);
		Game.setOrthoMatrix(m);
		
		GameRender.drawSurface(windowSurface, x, y);
		*/
	}

	@Override
	public void thisUpdate()
	{
		// TODO Auto-generated method stub
		
		setVisibleTabs();
		
		if(Input.getMouseButtonPressed(Input.LEFT_MOUSE_BUTTON))
		{
			if(Input.getMouseX() > (Display.getWidth()-(width*0.075)) )
			{
				if(Input.getMouseY()>y)
				{
					sliderActivated=true;
				}
			}
		}
		if(Input.getMouseButtonDown(Input.LEFT_MOUSE_BUTTON) && sliderActivated==true)
		{
			
			double sliderPercentage = (Input.getMouseY()-y - height*.1)/(height*.8);
			
			yLocation = (int) GameMath.round(sliderPercentage*(GameMath.round(editParts.size()/2)+1));
			
			if(yLocation<=0)
				yLocation = 0;
			else if(yLocation>GameMath.round(editParts.size()/2)+1)
				yLocation = (int) (GameMath.round(editParts.size()/2)+1);
				
			sliderY = (float) ((yLocation*height*0.77) / (GameMath.round(editParts.size()/2)+1));
		}
		else
		{
			sliderActivated = false;
		}
		
		if(Input.getMouseWheelUp())
		{
			if(yLocation > 0)
			{
				yLocation-=1;
				
				sliderY = (float) ((yLocation*height*0.77) / (GameMath.round(editParts.size()/2)+1));				
			}
		}
		else if(Input.getMouseWheelDown())
		{
			if(yLocation < (GameMath.round(editParts.size()/2)+1))
			{
				yLocation+=1;

				sliderY = (float) ((yLocation*height*0.77) / (GameMath.round(editParts.size()/2)+1));
			}
		}
		
		for(int i=0; i<editParts.size();i++)
		{
			GuiElement e = editParts.get(i);
			Box t = e.box;
			t.positionVec.y = -yLocation * 16;
			
			t.updateCollisionHull();
		}
		
		for(int i=0;i<editParts.size();i++)
		{
			editParts.get(i).update( new Point(Input.getMouseX()-x, Input.getMouseY()-y) );
		}
		
		if(sliderY<=0)
			sliderY=0;
		else if(sliderY >= (height*0.77))
			sliderY = (float)(height*0.77);
		
	}

}
