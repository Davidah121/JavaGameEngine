package openGLEngine;

import static openGLEngine.GameRender.*;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

public class Particle extends parentGameObject{

	private ArrayList<Double> partX = new ArrayList<Double>();
	private ArrayList<Double> partY = new ArrayList<Double>();
	private ArrayList<Double> partZ = new ArrayList<Double>();
	private ArrayList<Integer> partTime = new ArrayList<Integer>();
	private ArrayList<Integer> partMaxTime = new ArrayList<Integer>();
	private ArrayList<Double> partXScale = new ArrayList<Double>();
	private ArrayList<Double> partYScale = new ArrayList<Double>();
	private ArrayList<Double> partRot = new ArrayList<Double>();
	private ArrayList<Vec4f> partColor = new ArrayList<Vec4f>();
	private ArrayList<Vec2f> moveVec = new ArrayList<Vec2f>();
	private Vec4f blendColor1;
	private Vec4f blendColor2;
	private Vec4f blendColor3;
	
	public static final boolean BURST = false;
	public static final boolean EMITTER = true;
	public static final int SQUARE = 0;
	public static final int CIRCLE = 1;
	public static final int STAR = 2;
	public static final int CUSTOM = -1;
	
	private Shader particleShader = new Shader("Shaders//particleShader.vs","Shaders//particleShader.fs");
	//private Texture partTex = new Texture(null, 0); //Come up with a particle texture later
	private int maxLife = 10;
	private int minLife = 0;
	
	private int shape=0;
	
	private double minRot = 0;
	private double maxRot = 0;
	
	private double minXScale = 1;
	private double maxXScale = 1;
	
	private double minYScale = 1;
	private double maxYScale = 1;
	
	private int minXBound = 0;
	private int maxXBound = 0;
	private int minYBound = 0;
	private int maxYBound = 0;
	
	private Vec2f minMove = new Vec2f(-2.0,2.0);
	private Vec2f maxMove = new Vec2f(2.0,4.0);
	
	private boolean start = true;
	
	private int amount = 2;
	
	private boolean type = true; //type = false - (Burst) | true - (Emitter)
	
	public Particle(double x, double y, int depth)
	{
		position = new Vec3f(x,y,0);
		this.depth=depth;
	}
	
	public Particle(double x, double y, int depth, int shape, int minLife, int maxLife, int amount, boolean type, Sprite partImage)
	{
		position = new Vec3f(x,y,0);
		setSpriteIndex(partImage);
		
		this.minLife = minLife;
		this.maxLife = maxLife;
		this.amount = amount;
		
		this.type = type;
		this.depth = depth;
		this.shape = shape;
	}
	
	public Particle(double x, double y, int depth, int shape, int minLife, int maxLife, int amount, double minXScale, double maxXScale, double minYScale, double maxYScale, double minRot, double maxRot, boolean type, Sprite partImage)
	{
		position = new Vec3f(x,y,0);
		setSpriteIndex(partImage);
		this.minLife = minLife;
		this.maxLife = maxLife;
		
		this.minXScale=minXScale;
		this.minYScale=minYScale;
		
		this.maxXScale=maxXScale;
		this.maxYScale=maxYScale;
		
		this.minRot = minRot;
		this.maxRot = maxRot;
		
		this.amount = amount;
		
		this.type = type;
		this.depth = depth;
		
		this.shape = shape;
	}
	
	public void setLives(int minLife, int maxLife)
	{
		this.minLife = minLife;
		this.maxLife = maxLife;
	}
	
	public void setScales(double minX, double minY, double maxX, double maxY)
	{
		minXScale = minX;
		minYScale = minY;
		maxXScale = maxX;
		maxYScale = maxY;
	}
	
	public void setRotations(double minRot, double maxRot)
	{
		this.minRot = minRot;
		this.maxRot = maxRot;
	}
	
	public void createParticles()
	{
		for(int i=0;i<amount;i++)
		{
			double xx=minXBound+Math.random()*(maxXBound-minXBound);
			double yy=minYBound+Math.random()*(maxYBound-minYBound);
			int tt=(int)( minLife + Math.round(Math.random()*(maxLife-minLife)));
			double xsc = minXScale+Math.random()*(maxXScale-minXScale);
			double ysc = minYScale+Math.random()*(maxYScale-minYScale);
			double rot = minRot+Math.random()*(maxRot-minRot);
			Vec4f c = new Vec4f(1.0,1.0,1.0,1.0);
			
			double moveX = minMove.x+Math.random()*(maxMove.x-minMove.x);
			double moveY = minMove.y+Math.random()*(maxMove.y-minMove.y);
			
			
			partX.add(xx);
			partY.add(yy);
			partTime.add(0);
			partMaxTime.add(tt);
			partXScale.add(xsc);
			partYScale.add(ysc);
			partRot.add(rot);
			partColor.add(c);
			moveVec.add(new Vec2f(moveX,moveY));
		}
	}
	
	public void deleteParticles(int index)
	{
		partX.remove(index);
		partY.remove(index);
		//partZ.remove(index);
		partTime.remove(index);
		partMaxTime.remove(index);
		partXScale.remove(index);
		partYScale.remove(index);
		partRot.remove(index);
		partColor.remove(index);
		moveVec.remove(index);
	}
	
	public void update()
	{
		if (start==true)
		{
			createParticles();
			start=false;
		}
		else
		{
			if(type==EMITTER)
			{
				createParticles();
			}
			
			for(int i=partX.size()-1;i>=0;i--)
			{
				partX.set(i, partX.get(i)+moveVec.get(i).x);
				partY.set(i, partY.get(i)+moveVec.get(i).y);
				partTime.set(i, partTime.get(i)+1);
				
				if(partTime.get(i)>=partMaxTime.get(i))
				{
					deleteParticles(i);
				}
			}
		}
	}
	
	private Model createParticleModel()
	{
		Model partModel = new Model();
		partModel.setDrawType(GL11.GL_QUADS);
		
		ArrayList<Float> positions = new ArrayList<Float>();
		ArrayList<Float> texcoords = new ArrayList<Float>();
		ArrayList<Float> normals = new ArrayList<Float>(); //For 3d purposes(not needed yet)
		ArrayList<Float> color = new ArrayList<Float>(); //needed
		
		if(shape==SQUARE)
		{
			for(int i=0;i<partX.size();i++)
			{
				float x1 = this.position.getXFloat() + partX.get(i).floatValue();
				float y1 = this.position.getYFloat() + partY.get(i).floatValue();
				float x2 = partXScale.get(i).floatValue();
				float y2 = partXScale.get(i).floatValue();
				
				positions.add(x1); positions.add(y1);
				texcoords.add(0f); texcoords.add(0f);
				color.add(1f); color.add(1f); color.add(1f); color.add(1f);
				
				positions.add(x1+1); positions.add(y1);
				texcoords.add(1f); texcoords.add(0f);
				color.add(1f); color.add(1f); color.add(1f); color.add(1f);
				
				positions.add(x1+1); positions.add(y1+1);
				texcoords.add(1f); texcoords.add(1f);
				color.add(1f); color.add(1f); color.add(1f); color.add(1f);
				
				positions.add(x1); positions.add(y1+1);
				texcoords.add(0f); texcoords.add(1f);
				color.add(1f); color.add(1f); color.add(1f); color.add(1f);
			}
		}
		partModel.storeDataFloat(0, positions, 2);
		partModel.storeDataFloat(1, texcoords, 2);
		partModel.storeDataFloat(2, color, 4);
		
		partModel.unBind();
		
		positions.clear();
		texcoords.clear();
		color.clear();
		return partModel;
	}
	
	public void draw()
	{
		Model tempModel = createParticleModel();

		particleShader.start();
		
		Game.set2DBegin();
		DefaultResources.defaultTexture.bind();
		tempModel.draw();
		tempModel.destroyModel();
		
		particleShader.end();
		Game.set2DBegin();
	}
	
	@Override
	public void destroy() {
		partX.clear();
		partY.clear();
		partZ.clear();
		partTime.clear();
		partMaxTime.clear();
		partXScale.clear();
		partYScale.clear();
		partRot.clear();
		partColor.clear();
		moveVec.clear();
		
		Game.destroyObject( this );
	}
}
