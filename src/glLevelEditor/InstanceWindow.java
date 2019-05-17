package glLevelEditor;
import java.util.*;
import openGLEngine.*;

public class InstanceWindow extends InputWindow {

	private Level refLevel;
	private parentGameObject obj;
	private int hoverLoc = -1;
	private int hoverLocPressed = -1;
	private int selectLoc = -1;
	
	private int startY = 0;
	private int maxY = 0;
	
	public InstanceWindow(int x, int y, int width, int height)
	{
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		
		init();
	}
	
	public void setLevel(Level v)
	{
		refLevel = v;
	}
	
	public void processScrolling()
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
	
	@Override
	public void thisUpdate() {
		// TODO Auto-generated method stub
		
		//int mouseX = Input.getMouseX()-x;
		//int mouseY = Input.getMouseY()-y;
		processScrolling();
		
		int mouseX = this.getMouseXRelative();
		int mouseY = this.getMouseYRelative();
		
		hoverLoc = -1;
		if(refLevel!=null)
		{
			for(int i=0; i<refLevel.getObjectListSize(); i++)
			{
				if(mouseX > 0 && mouseX < width)
				{
					if(mouseY > startY + i*28 && mouseY < startY + (i+1)*28)
					{
						hoverLoc = i;
					}
				}
				
				maxY = -(i*28) + 28;
			}
			
			maxY = -(refLevel.getObjectListSize()*28) + height - 28 + startY;
			
			if(Input.getMouseButtonPressed(Input.LEFT_MOUSE_BUTTON))
			{
				if(hoverLoc!=-1)
				{
					hoverLocPressed = hoverLoc;
					selectLoc = hoverLoc;
					obj = refLevel.getObject(hoverLoc);
					BasicEditor.controlObject.setObject(obj);
				}
			}
			
			if(Input.getMouseButtonUp(Input.LEFT_MOUSE_BUTTON))
			{
				hoverLocPressed = -1;
			}
			
			if(Input.getKeyPressed(Input.VK_DELETE))
			{
				refLevel.deleteObject(selectLoc);
			}
		}
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
		this.windowSurface.clear();
		
		Mat4f temo = Game.getOrthoProjectionMatrix();
		
		Game.createOrthoProjectionMatrix(0,0,width,height);
		Game.set2DBegin();
		
		GameRender.setColor(0f, 0f, 0f, 1f);
		for(int i=0; i<refLevel.getObjectListSize(); i++)
		{
			int id = refLevel.getObject(i).getId();
			String name = refLevel.getObject(i).getClass().getName();
			
			String finalString = id+": "+name;
			
			if(i==hoverLocPressed)
			{
				GameRender.setColor(0.2f, 0f, 0.8f, 1f);
				GameRender.drawRect(0, startY + i*28, width, startY + (i+1)*28, false);
				GameRender.setColor(0f, 0f, 0f, 1f);
			}
			else if(i==hoverLoc)
			{
				GameRender.setColor(0.8f, 0f, 0.2f, 1f);
				GameRender.drawRect(0, startY + i*28, width, startY + (i+1)*28, false);
				GameRender.setColor(0f, 0f, 0f, 1f);
			}
			
			if(selectLoc==i)
			{
				GameRender.setColor(0.0f, 0.2f, 0.1f, 1f);
				GameRender.drawArc(width-48, (i+0.5)*28, 32, -20, 20, false);
				GameRender.setColor(0f, 0f, 0f, 1f);
			}
			GameRender.drawText(finalString, 0, startY + i*28);
		}
		
		Game.createOrthoProjectionMatrix(0, 0, 1280, 720);
		Game.set2DBegin();
	}
}
