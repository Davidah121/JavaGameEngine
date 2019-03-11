package glLevelEditor;

import openGLEngine.*;

public class GuiWindow extends InputWindow {

	private Mat4f orthoMat;
	private Mat4f scaleMat;
	
	public GuiWindow(int x, int y, int width, int height)
	{
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		
		init();
		setActive(true);
		setVisible(true);
		
		//orthoMat = GameMath.createOrthographicMatrix(0, 0, 1280, 720);
		orthoMat = Game.getViewProjectionMatrix();
		scaleMat = new Mat4f(4, 0, 0, 0,
							0, 2, 0, 0,
							0, 0, 1, 0,
							0, 0, 0, 1);
		
		orthoMat = GameMath.matrixMult(scaleMat, orthoMat);
	}
	
	@Override
	public void thisUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		windowSurface.clear();
		
		Mat4f temo = Game.getOrthoProjectionMatrix();
		
		orthoMat = Game.getViewProjectionMatrix();
		scaleMat = new Mat4f((float)renderWidth/width, 0, 0, 0,
							0, (float)renderHeight/height, 0, 0,
							0, 0, 1, 0,
							0, 0, 0, 1);
		
		orthoMat = GameMath.matrixMult(scaleMat, orthoMat);
		
		Game.setOrthoMatrix(orthoMat);
		//Game.setViewProjectionMatrix(orthoMat);
		
		GameRender.setColor(1f, 1f, 1f, 1f);
		GameRender.drawText("This is text", 0, 0);
		
		Game.setOrthoMatrix(temo);
		
		//GameRender.drawText("This is text", 0, 0, (float)renderWidth/width, (float)renderHeight/height);
	}

}
