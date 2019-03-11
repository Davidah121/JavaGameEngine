package glLevelEditor;

import java.util.ArrayList;

import openGLEngine.*;

public class GameWindow {

	
	private static Surface gameSurface = null;
	private static Level currentLevel = new Level();
	private static Camera editorCamera = new Camera(Camera.MODE_2D);
	
	private static boolean playMode = false; //tells whether you are play testing
	
	private static int xPos = 0;
	private static int yPos = 0;
	
	private static int width = 0;
	private static int height = 0;
	
	public static  final int gridMaxSize = 128; //128 x 128 grid
	
	//Must be 8 units between each grid to be visible
	//Please note that this does not prevent gridXSize from being 1.
	private static int gridMinSizeDis = 8;
	
	private static Model gridModel = new Model(Model.TYPE_DYNAMIC);
	
	public static void init(int x, int y, int width, int height)
	{
		xPos = x;
		yPos = y;
		
		GameWindow.width = width;
		GameWindow.height = height;
		gameSurface = new Surface(Game.getRenderWidth(), Game.getRenderHeight(), Surface.COLOR_AND_DEPTH);
		
		GameResources.addResource(gameSurface, "GameSurface");
		
		createGridModel(32,32,0);
	}
	
	public static void setPlaying()
	{
		playMode = true;
	}
	
	public static boolean getPlaying()
	{
		return playMode;
	}
	
	public static Camera getCamera()
	{
		return editorCamera;
	}
	
	public static Level getLevel()
	{
		return currentLevel;
	}
	
	public static Surface getSurface()
	{
		return gameSurface;
	}
	
	public static void createGridModel(int gridXSize, int gridYSize, int zoom)
	{
		
		ArrayList<Double> position = new ArrayList<Double>();
		
		if(gridXSize+zoom>gridMinSizeDis
			&& gridYSize+zoom>gridMinSizeDis)
		{
			for(int i=-gridMaxSize/2; i<gridMaxSize/2; i++)
			{
				position.add( (double)(i*(gridXSize+zoom)) );
				position.add( (double)(-gridMaxSize/2)*(gridYSize+zoom) );
				position.add( 0.0 );
				
				position.add( (double)(i*(gridXSize+zoom)) );
				position.add( (double)(gridYSize+zoom)*(gridMaxSize/2) );
				position.add( 0.0 );
				
				position.add( (double)(gridXSize+zoom)*(-gridMaxSize/2) );
				position.add( (double)(i*(gridYSize+zoom)) );
				position.add( 0.0 );
				
				position.add( (double)(gridMaxSize*(gridXSize+zoom)) );
				position.add( (double)(i*(gridYSize+zoom)) );
				position.add( 0.0 );
			}
		}
		
		gridModel.resetModel();
		
		gridModel.storeDataDouble(0, position, 3);
		gridModel.setDrawType(Model.DRAW_TYPE_LINES);
		gridModel.setFillType(Model.FILL_TYPE_LINE);
	}
	
	public static void drawGrid(Vec3f position, int gridXSize, int gridYSize, int zoom)
	{
		//int xVal = ((int) ((position.x)/(gridXSize+zoom)))*(gridXSize+zoom);
		//int yVal = ((int) ((position.y)/(gridYSize+zoom)))*(gridYSize+zoom);
		
		//Mat4f modelMatrix = GameMath.createModelMatrix(-xVal, -yVal, 0, 0, 0, 0, 1, 1, 1);
		
		//Game.currentShader.setUniform("modelMatrix", false, modelMatrix);
		
		Game.currentCamera.setPosition(position.x, position.y, position.z);
		Game.set2DBegin();
		
		
		GameRender.setColor(0, 0, 0, 1);
		
		//Game.currentShader.setUniform("modelMatrix", false, Mat4f.getIdentityMatrix());
		
		gridModel.draw();
		
		//X-Axis
		GameRender.setColor(1f, 0f, 0f, 1f);
		GameRender.drawLine(-10000, 0, 10000, 0);
		//GameRender.drawCircle(0, 0, 32, false);
		
		//Y-Axis
		GameRender.setColor(0f, 1f, 0f, 1f);
		GameRender.drawLine(0, -10000, 0, 10000);
		
		Game.currentCamera.setPosition(0,0,0);
		Game.set2DBegin();
		
	}
	
	public static void update()
	{
		/*
		for(int i=0; i<currentLevel.getObjectListSize(); i++)
		{
			//currentLevel.getObject(i).guiUpdate();
		}
		*/
	}
	
	public static void render(Vec3f position, int gridXSize, int gridYSize, int zoom)
	{
		gameSurface.bind();
		gameSurface.clear();
		
		GameRender.setColor(1, 1, 1, 1);
		
		drawGrid(position, gridXSize, gridYSize, zoom);
		for(int i=0; i<currentLevel.getObjectListSize(); i++)
		{
			currentLevel.getObject(i).levelEditDraw();
		}
		
		gameSurface.unBind();
		
		GameRender.setColor(1f, 1f, 1f, 1f);
		Game.currentShader.setProjectionMatrix(Game.getDisplayOrthoMatrix(), false);
		GameRender.drawSurfaceExt(gameSurface, 0, 0, width, height);
	}
}
