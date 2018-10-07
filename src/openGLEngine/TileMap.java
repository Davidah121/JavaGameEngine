package openGLEngine;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

public class TileMap extends parentGameObject {

	private ArrayList<Sprite> imageId = new ArrayList<Sprite>();
	private ArrayList<Integer> imageIndex = new ArrayList<Integer>();
	
	private ArrayList<Integer> tileX = new ArrayList<Integer>();
	private ArrayList<Integer> tileY = new ArrayList<Integer>();
	private ArrayList<Float> tileXScale = new ArrayList<Float>();
	private ArrayList<Float> tileYScale = new ArrayList<Float>();
	
	private Model tileModel = new Model(Model.TYPE_DYNAMIC);
	
	private int TexturePageIndex = 0;
	
	private Vec4f tileMapColor = new Vec4f(1f,1f,1f,1f);
	
	public TileMap(double depth)
	{
		this.depth=depth;
	}
	
	@Override
	public void update()
	{
		//Nothing, this is a static TileMap
		createTileMapModel();
	}
	
	public void addTile(Sprite img, int x, int y)
	{
		imageId.add(img);
		imageIndex.add(0);
		tileX.add(x);
		tileY.add(y);
		tileXScale.add(1f);
		tileYScale.add(1f);
	}
	
	public void addTile(Sprite img, int x, int y, float xScale, float yScale)
	{
		imageId.add(img);
		imageIndex.add(0);
		tileX.add(x);
		tileY.add(y);
		tileXScale.add(xScale);
		tileYScale.add(yScale);
	}
	
	public int size()
	{
		return tileX.size();
	}
	
	public void setSprite(int index, Sprite element)
	{
		imageId.set(index, element);
	}
	
	public void setTileX(int index, int element)
	{
		tileX.set(index, element);
	}
	
	public void setTileY(int index, int element)
	{
		tileY.set(index, element);
	}
	
	public void setTileXScale(int index, float element)
	{
		tileXScale.set(index, element);
	}
	
	public void setTileYScale(int index, float element)
	{
		tileYScale.set(index, element);
	}
	
	public Sprite getSprite(int index)
	{
		if(size() > index && index >= 0)
			return imageId.get(index);
		else
			return null;
	}
	
	public double getWidth(int index)
	{
		if(size() > index && index >= 0)
			return imageId.get(index).getWidth() * tileXScale.get(index);
		else
			return 0;
	}
	
	public double getHeight(int index)
	{
		if(size() > index && index >= 0)
			return imageId.get(index).getHeight() * tileYScale.get(index);
		else
			return 0;
	}
	
	public double getTileX(int index)
	{
		if(size() > index && index >= 0)
			return tileX.get(index);
		else
			return 0;
	}
	
	public double getTileY(int index)
	{
		if(size() > index && index >= 0)
			return tileY.get(index);
		else
			return 0;
	}
	
	public double getTileXScale(int index)
	{
		if(size() > index && index >= 0)
			return tileXScale.get(index);
		else
			return 0;
	}
	
	public double getTileYScale(int index)
	{
		if(size() > index && index >= 0)
			return tileYScale.get(index);
		else
			return 0;
	}
	
	public void removeTile(int index)
	{
		if(size() > index)
		{
			imageId.remove(index);
			imageIndex.remove(index);
			tileX.remove(index);
			tileY.remove(index);
			tileXScale.remove(index);
			tileYScale.remove(index);
		}
	}
	
	private void createTileMapModel()
	{
		int index = 0;
		ArrayList<Float> modP = new ArrayList<Float>();
		ArrayList<Float> modT = new ArrayList<Float>();
		float xScale, yScale;
		float tempX, tempY;
		
		for(int i=0; i<imageId.size(); i++)
		{
			tempX=tileX.get(i);
			tempY=tileY.get(i);
			xScale=tileXScale.get(i);
			yScale=tileYScale.get(i);
			
			float width=imageId.get(i).getWidth() * xScale;
			float height=imageId.get(i).getHeight() * yScale;
			
			index = imageId.get(i).getImageId( imageIndex.get(i) );
			
			float xTex = TexturePage.getImageX(index);
			float xTexWidth = TexturePage.getImageWidth(index);
			float yTex = TexturePage.texturePageHeight - TexturePage.getImageY(index);
			float yTexHeight = -TexturePage.getImageHeight(index);
			
			xTex /=TexturePage.texturePageWidth;
			xTexWidth /= TexturePage.texturePageWidth;
			
			yTex /= TexturePage.texturePageHeight;
			yTexHeight /= TexturePage.texturePageHeight;
			
			modP.add(tempX); modP.add(tempY);
			modT.add(xTex); modT.add(yTex);
			
			modP.add(tempX); modP.add(tempY+height);
			modT.add(xTex); modT.add(yTex+yTexHeight);
			
			modP.add(tempX+width); modP.add(tempY+height);
			modT.add(xTex+xTexWidth); modT.add(yTex+yTexHeight);
			
			modP.add(tempX+width); modP.add(tempY);
			modT.add(xTex+xTexWidth); modT.add(yTex);
		}
		
		tileModel.resetModel();
		
		tileModel.setFillType(GL11.GL_FILL);
		tileModel.setDrawType(GL11.GL_QUADS);
		
		tileModel.storeDataFloat(0, modP, 2);
		tileModel.storeDataFloat(1, modT, 2);
		
		tileModel.unBind();
		
		modP.clear();
		modT.clear();
	}
	
	@Override
	public void draw()
	{
		GameRender.setColor(tileMapColor);
		
		if(TexturePage.getTexturePage(TexturePageIndex) != null)
		{
			Surface texPage = TexturePage.getTexturePage(TexturePageIndex);
			texPage.bindTexture();
			tileModel.draw();
			texPage.unBindTexture();
		}
		
	}

	@Override
	public void destroy()
	{
		tileModel.destroyModel();
		imageId.clear();
		imageIndex.clear();
		tileX.clear();
		tileY.clear();
		tileXScale.clear();
		tileYScale.clear();
	}
}
