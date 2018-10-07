import openGLEngine.*;

public class Resources {

	public static Texture testTexture1;
	public static Texture testTexture2;
	public static Sprite sp1;
	public static Sprite sp2;
	public static Sprite sp3;
	public static Sprite sp4;
	public static Sprite sp5;
	public static Sprite sp6;
	
	public static void init()
	{
		initObjects();
		initResources();
	}
	
	private static void initObjects()
	{
		Level.addObjectType( testObject.class );
		Level.addObjectType( testObject2.class );
		Level.addObjectType( testObject.class );
	}
	
	private static void initResources()
	{
		testTexture1 = new Texture("Textures\\Dirt_tex.png", Texture.LINEAR_FILTERING, false, 0);
		testTexture2 = new Texture("Textures\\Grass_tex.png", Texture.LINEAR_FILTERING, false, 0);
		
		sp1 = new Sprite("Sprites\\Grass.png");
		sp2 = new Sprite("Sprites\\Water.png");
		sp3 = new Sprite("Sprites\\rock1.png");
		sp4 = new Sprite("Sprites\\rock2.png");
		sp5 = new Sprite("Sprites\\rock3.png");
		sp6 = new Sprite("Sprites\\rock4.png");
		
		GameResources.addResource(sp1, "sp1");
		GameResources.addResource(sp2, "sp2");
		GameResources.addResource(sp3, "sp3");
		GameResources.addResource(sp4, "sp4");
		GameResources.addResource(sp5, "sp5");
		GameResources.addResource(sp6, "sp6");
		
		GameResources.addResource(testTexture1, "1");
		GameResources.addResource(testTexture2, "2");
		
	}
	
}
