import openGLEngine.*;

public class randomObject extends parentGameObject {

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		Mat4f t = GameMath.createViewMatrix(position.x, position.y, 0, 0, 0, 0, GameMath.TYPE_2D);
		Game.setOrthoMatrix(m);
		
		GameRender.drawTextStretched("This is whatever this is", 320, 240, 400, 200);
		GameRender.drawCircle(0, 0, 32, true);
	}

}
