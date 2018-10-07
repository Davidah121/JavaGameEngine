import openGLEngine.*;

public class VecTest extends parentGameObject {

	public Triangle t1 = new Triangle(0,0, 128, 0, 128, 128);
	public Point p1 = new Point(64,32);
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void draw() {
		
		GameRender.setColor(1f, 0f, 0f, 1f);
		//GameRender.drawRect(0, 0, 32, 32, true);
		
		GameRender.drawTriangle(t1.getX1(), t1.getY1(), t1.getX3(), t1.getY3(), t1.getX2(), t1.getY2(), true);
		GameRender.drawCircle(p1.x, p1.y, 3, false);
		
		GameRender.setColor(0f, 1f, 1f, 1f);
		GameRender.drawLine(t1.getX1(), t1.getY1(), p1.x, p1.y);
		
		Vec2f l1 = GameMath.normalize( new Vec2f(t1.getX2()-t1.getX1(), t1.getY2()-t1.getY1()));
		Vec2f l2 = GameMath.normalize( new Vec2f(t1.getX3()-t1.getX1(), t1.getY3()-t1.getY1()));
		
		Vec2f l3 = GameMath.normalize( new Vec2f(p1.x-t1.getX1(), p1.y-t1.getY1()));
		
		double dot1 = GameMath.arccos(GameMath.dot(l1, l2));
		double dot2 = GameMath.arccos(GameMath.dot(l3, l1));
		double dot3 = GameMath.arccos(GameMath.dot(l3, l2));
		
		System.out.println("D1: "+dot1);
		System.out.println("D2: "+dot2);
		System.out.println("D3: "+dot3);
		
	}

}
