package glLevelEditor;

import openGLEngine.*;

public class GameStats {

	private static Surface statsSurface = null;

	private static int xPos = 0;
	private static int yPos = 0;
	
	private static int width = 0;
	private static int height = 0;
	
	private static Vec2f tempMousePos = new Vec2f(0,0);
	
	public static void init(int x, int y, int width, int height)
	{
		xPos = x;
		yPos = y;
		
		GameStats.width = width;
		GameStats.height = height;
		statsSurface = new Surface(width, height, Surface.COLOR);
		
		GameResources.addResource(statsSurface, "statsSurface");
	}
	
	public static void update()
	{
		tempMousePos.x = GameMath.clamp(Input.getMouseX()-xPos, 0, width)/width;
		tempMousePos.y = GameMath.clamp(Input.getMouseY()-yPos, 0, height)/height;
	}
	
	public static void setXPos(int x)
	{
		xPos = x;
	}
	
	public static void setYPos(int y)
	{
		yPos = y;
	}
	
	public static void render()
	{
		statsSurface.bind();
		statsSurface.clear();
		
		Game.setCamera(new Camera(Camera.MODE_2D));
		Game.set2DBegin();
		
		GameRender.setColor(0.45f, 0.45f, 0.45f, 1.0f);
		GameRender.drawRect(0, 0, statsSurface.getWidth(), statsSurface.getHeight(), false);
		
		GameRender.setColor(0.6f, 0.6f, 0.6f, 1.0f);
		GameRender.drawRect(1, 0, statsSurface.getWidth(), (statsSurface.getHeight()*0.3125)-1, true);
		
		GameRender.setColor(1, 1, 1, 1);
		GameRender.drawText("Level Name: "+GameWindow.getLevel().getName(), 16, 0);
		GameRender.drawText("Level Width: "+GameWindow.getLevel().getWidth(), 16, 32);
		GameRender.drawText("Level Height: "+GameWindow.getLevel().getHeight(), 16, 64);
		
		GameRender.drawText("Current X: "+GameWindow.getCamera().getPosition().x, 16, 96);
		GameRender.drawText("Current Y: "+GameWindow.getCamera().getPosition().y, 16, 128);
		
		GameRender.drawText("Game Render Width: "+Game.getRenderWidth(), 392, 0);
		GameRender.drawText("Game Render Height: "+Game.getRenderHeight(), 392, 32);
		GameRender.drawText("Game Object List Size: "+Game.getObjectListSize(), 392, 64);
		
		GameRender.drawText("Grid X: "+GameWindow.getCamera().getPosition().x, 392, 96);
		GameRender.drawText("Grid Y: "+GameWindow.getCamera().getPosition().y, 392, 128);
		
		GameRender.drawText("Game FPS: "+Display.getFPS(), 720, 0);
		GameRender.drawText("Game Theoretical FPS: "+Display.getRawFPS(), 720, 32);
		GameRender.drawText("Game Vsync: "+Display.getVSync(), 720, 64);
		GameRender.drawText("Game FPSCap: "+Display.getFPSCap(), 720, 96);
		
		statsSurface.unBind();
		
		GameRender.setColor(1f, 1f, 1f, 1f);
		Game.currentShader.setProjectionMatrix(Game.getDisplayOrthoMatrix(), false);
		GameRender.drawSurfaceExt(statsSurface, xPos, yPos, width, height);
	}
	
}
