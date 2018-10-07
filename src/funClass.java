
import openGLEngine.*;

public class funClass extends parentGameObject {

	private Shader s = new Shader("Shaders\\test.vs", "Shaders\\test.fs");
	private Texture t = new Texture("Textures\\floorTex.jpg", Texture.LINEAR_FILTERING, true, 0);
	private float val = 1f;
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
		val = 4f;
		s.start();
		s.setProjectionMatrix(Game.getOrthoProjectionMatrix(), false);
		s.setUniform("color", GameRender.getColor().toFloatArray());
		s.setUniform("powValue", new float[]{val});
		
		GameRender.drawTexture(t, 0, 0, 0.5, 0.5);
		s.end();
		
	}
	
	@Override
	public void destroy()
	{
		s.dispose();
		t.dispose();
	}

}
