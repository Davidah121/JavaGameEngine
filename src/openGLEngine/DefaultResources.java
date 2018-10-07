package openGLEngine;

public class DefaultResources {

	public static Shader defaultShader = new Shader("DefaultStuff\\defaultShader.vs", "DefaultStuff\\defaultShader.fs");
	public static GLFont defaultFont = new GLFont("DefaultStuff\\NewTestForText.ft", Texture.NEAR_FILTERING);
	public static Texture defaultTexture = new Texture("DefaultStuff\\defaultTexture.png", Texture.NEAR_FILTERING, false, 0);
	public static Shader fontShader = new Shader("DefaultStuff\\TextShader.vs", "DefaultStuff\\TextShader.fs");
	
	public static void dispose()
	{
		
		defaultTexture.unBind();
		
		defaultFont.dispose();
		defaultTexture.dispose();
		
		defaultShader.end();
		defaultShader.dispose();
		
		fontShader.end();
		fontShader.dispose();
	}
}
