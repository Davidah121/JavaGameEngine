package glLevelEditor;

import openGLEngine.*;

public class EditWindow extends InputWindow {

	private Mat4f orthoMat;
	private Mat4f scaleMat;
	
	private Entity obj;
	
	public EditWindow(int x, int y, int width, int height)
	{
		
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		
		init();
		
		EntityProcessor.offX = x;
		EntityProcessor.offY = y;
		EntityProcessor.width = width;
		EntityProcessor.height = height;
		
		EntityProcessor.init();
		orthoMat = GameMath.createOrthographicMatrix(0, 0, 1280, 720);
		//orthoMat = Game.getViewProjectionMatrix();
		scaleMat = new Mat4f((double)renderWidth/width, 0, 0, 0,
							0, (double)renderHeight/height, 0, 0,
							0, 0, 1, 0,
							0, 0, 0, 1);
		
		orthoMat = GameMath.matrixMult(scaleMat, orthoMat);
	}
	
	public void setEntity(Entity obj)
	{
		this.obj = obj;
	}
	
	@Override
	public void thisUpdate() {
		// TODO Auto-generated method stub
		EntityProcessor.processScrolling();
		
		obj = BasicEditor.controlObject.getObject();
		
		if(obj!=null)
		{
			obj.reloadGuiField();
			EntityProcessor.processEntity(obj);
			EntityProcessor.drawEntityGui(obj); //On a separate surface so it doesn't matter when its drawn
			obj.updateItems();
		}
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		windowSurface.clear();
		
		Mat4f temo = Game.getOrthoProjectionMatrix();
		
		Game.setOrthoMatrix(orthoMat);
		Game.set2DBegin();
		
		GameRender.setColor(1f, 1f, 1f, 1f);
		GameRender.drawSurface(EntityProcessor.guiSurface, 0, 0);
		
		Game.setOrthoMatrix(temo);
		Game.set2DBegin();
		
		//GameRender.drawText("This is text", 0, 0, (float)renderWidth/width, (float)renderHeight/height);
	}

}
