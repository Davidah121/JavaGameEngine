import openGLEngine.*;

public class testObject extends parentGameObject {

	public testObject(int x, int y, int depth)
	{
		this();
		this.position.x=x;
		this.position.y=y;
		this.depth=depth;
		
	}
	
	public testObject()
	{
		colHull = new Circle(0, 0, 16);
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		GameRender.setColor(1f, 1f, 1f, 1f);
		GameRender.drawCircle((int)position.x, (int)position.y, 16, false);
		colHull.drawCollisionHull();
	}

}
