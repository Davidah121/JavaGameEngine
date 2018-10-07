package openGLEngine;

import java.util.ArrayList;

public class Sprite {
	
	private ArrayList<Integer> imageId = new ArrayList<Integer>();
	
	private int leftBound;
	private int topBound;
	private int rightBound;
	private int bottomBound;
	private int xOrigin;
	private int yOrigin;
	
	private int width;
	private int height;
	
	public Sprite()
	{
		
	}
	
	public Sprite(String fileName)
	{
		addSprite( fileName );
	}
	
	public Sprite(String[] fileName)
	{
		for(int i=0; i<fileName.length; i++)
			addSprite( fileName[i] );
	}
	
	public Sprite(ArrayList<String> fileName)
	{
		for(int i=0; i<fileName.size(); i++)
			addSprite( fileName.get(i) );
	}
	
	public void addSprite(String fileName)
	{
		if (TexturePage.createImage(fileName))
		{
			imageId.add(TexturePage.getAmountImages()-1);
			
			width = GameMath.max(width, TexturePage.getImageWidth(TexturePage.getAmountImages()-1));
			height = GameMath.max(height, TexturePage.getImageHeight(TexturePage.getAmountImages()-1));
			
		}
	}
	
	public int getImageId(int i)
	{
		if(i<0)
		{
			System.out.println("I IS < 0");
		}
		if(i>=imageId.size() || i<0)
		{
			return imageId.get(imageId.size()-1);
		}
		else
		{
			return imageId.get(i);
		}
	}
	
	public int getSize()
	{
		return imageId.size();
	}
	
	public void setOrigin(int x, int y)
	{
		xOrigin = x;
		yOrigin = y;
	}
	
	public void setOrigin()
	{
		xOrigin = TexturePage.getImageWidth(imageId.get(0)) / 2;
		yOrigin = TexturePage.getImageHeight(imageId.get(0)) / 2;
	}
	
	public int getXOrigin()
	{
		return xOrigin;
	}
	
	public int getYOrigin()
	{
		return yOrigin;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void dispose()
	{
		imageId.clear();
	}
}
