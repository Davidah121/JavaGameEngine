package glLevelEditor;
import java.util.*;
import openGLEngine.*;

public class TabWindow extends InputWindow {

	/*
	 * EditWindow
	 * InstanceList
	 * ObjectList
	 * TileWindow
	 * ResourceWindow
	 */
	
	private ArrayList<String> types = new ArrayList<String>();
	private int selectLoc = -1;
	private int hoverLocPressed = -1;
	private Mat4f orthoMat;
	private Mat4f scaleMat;
	
	public TabWindow(int x, int y, int width, int height)
	{
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		
		types.add("EditWindow");
		//types.add("AddDeleteWindow");
		types.add("InstanceWindow");
		types.add("ObjectWindow");
		types.add("TileWindow");
		types.add("ResourceWindow");
		
		init();
		
		orthoMat = GameMath.createOrthographicMatrix(0, 0, 1280, 720);
		//orthoMat = Game.getViewProjectionMatrix();
		scaleMat = new Mat4f((double)renderWidth/width, 0, 0, 0,
							0, (double)renderHeight/height, 0, 0,
							0, 0, 1, 0,
							0, 0, 0, 1);
		
		orthoMat = GameMath.matrixMult(scaleMat, orthoMat);
	}
	
	@Override
	public void thisUpdate() {
		// TODO Auto-generated method stub
		int mouseX = Input.getMouseX()-x;
		int mouseY = Input.getMouseY()-y;
		
		selectLoc = -1;
		
		for(int i=0; i<types.size(); i++)
		{
			if(mouseX > 0 && mouseX < width)
			{
				if(mouseY > i*28 && mouseY < (i+1)*28)
				{
					selectLoc = i;
				}
			}
		}
		
		if(Input.getMouseButtonPressed(Input.LEFT_MOUSE_BUTTON))
		{
			if(selectLoc!=-1)
			{
				hoverLocPressed = selectLoc;
				BasicEditor.controlObject.setActiveWindow(selectLoc);
			}
		}
		
		if(Input.getMouseButtonUp(Input.LEFT_MOUSE_BUTTON))
		{
			hoverLocPressed = -1;
		}
	}

	/**
	 * Can't draw outlined Stuff
	 * Check Later
	 */
	@Override
	public void render() {
		// TODO Auto-generated method stub
		
		this.windowSurface.clear();
		
		GameRender.setColor(0f, 0f, 0f, 1f);
		
		Game.setOrthoMatrix(orthoMat);
		Game.set2DBegin();
		
		for(int i=0; i<types.size(); i++)
		{
			if(hoverLocPressed==i)
			{
				GameRender.setColor(0.2f, 0f, 0.8f, 1f);
				GameRender.drawRect(0, i*28, width, (i+1)*28, false);
				GameRender.setColor(0f, 0f, 0f, 1f);
			}
			else if(selectLoc==i)
			{
				GameRender.setColor(0.8f, 0f, 0.2f, 1f);
				GameRender.drawRect(0, i*28, width, (i+1)*28, false);
				GameRender.setColor(0f, 0f, 0f, 1f);
			}
			
			if(BasicEditor.controlObject.getActiveWindow()==i)
			{
				GameRender.setColor(0.0f, 0.1f, 0.3f, 1f);
				GameRender.drawArc(width-48, (i+0.5)*28, 32, -20, 20, false);
				GameRender.setColor(0f, 0f, 0f, 1f);
			}
			GameRender.drawText(types.get(i), 0, i*28);
		}
		
		//GameRender.drawTriangle(0, 0, 32, 0, 0, 32, false);
		GameRender.drawRect(0f, 0f, 32f, 32f, true);
		GameRender.setColor(1f, 1f, 1f, 1f);
		
		Game.createOrthoProjectionMatrix(0, 0, 1280, 720);
		Game.set2DBegin();
	}
}
