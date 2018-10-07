import openGLEngine.*;

public class Obj3 extends parentGameObject {

	public SelectWindow p = new SelectWindow();
	
	public Obj3()
	{
		Game.addObject(p);
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
		if(Input.getKeyPressed(Input.VK_SPACE))
		{
			Game.end();
		}
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		GameRender.drawText("FPS: "+Display.getFPS(), 0, 0);
		
	}

}
