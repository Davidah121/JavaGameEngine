import openGLEngine.Vec2f;
import openGLEngine.parentGameObject;

public class VecObj extends parentGameObject {

	public double x = 0;
	public double y = 0;
	
	public Vec2f pos = new Vec2f(0,0);
	public VecObj(double x, double y)
	{
		this.x = x;
		this.y = y;
		
		pos.x = x;
		pos.y = y;
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
	}

}
