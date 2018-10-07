import openGLEngine.*;

public class ColTest extends parentGameObject {

	public Line t1 = new Line(0,0,32,32);
	public Line b1 = new Line(0,0,32,32);
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(Input.getKeyDown('A'))
		{
			position.x-=1;
		}
		else if(Input.getKeyDown('D'))
		{
			position.x+=1;
		}
		
		if(Input.getKeyDown('W'))
		{
			position.y-=1;
		}
		else if(Input.getKeyDown('S'))
		{
			position.y+=1;
		}
		
		b1.setEndX(96);
		b1.setEndY(0);
		
		t1.setEndX(96);
		t1.setEndY(32);
		t1.setStartX(32);
		t1.setStartY(32);
		
		t1.updateCollisionHull();
		b1.setPositionVector( position );
		b1.updateCollisionHull();
	}

	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
		boolean value = false;
		
		long t = System.nanoTime();
		value = GameLogic.getCollisionLineToLine(t1, b1);
		System.out.println(System.nanoTime()-t);
		
		GameRender.drawText("col: "+value, 0, 0);
		
		GameRender.setColor(1f, 1f, 1f, 1f);
		GameRender.drawLine((float)t1.getStartX(), (float)t1.getStartY(), (float)t1.getEndX(), (float)t1.getEndY());
		
		GameRender.setColor(1f, 0f, 0f, 1f);
		GameRender.drawLine((float)b1.getStartX(), (float)b1.getStartY(), (float)b1.getEndX(), (float)b1.getEndY());
		
	}

}
