package openGLEngine;

import java.util.ArrayList;

public class TexturePage {

	private static ArrayList<Integer> imageX = new ArrayList<Integer>();
	private static ArrayList<Integer> imageY = new ArrayList<Integer>();
	private static ArrayList<Integer> imageWidth = new ArrayList<Integer>();
	private static ArrayList<Integer> imageHeight = new ArrayList<Integer>();
	
	private static ArrayList<Integer> TexturePageLocation = new ArrayList<Integer>();
	
	private static ArrayList<Surface> TexturePage = new ArrayList<Surface>();
	private static ArrayList<Integer> TextureXSpace = new ArrayList<Integer>();
	private static ArrayList<Integer> TextureYSpace = new ArrayList<Integer>();
	private static int texturePageMaxYSize = 0;
	
	public static final int texturePageWidth = 2048;
	public static final int texturePageHeight = 2048;
	
	public static final Mat4f orthoMat = GameMath.createOrthographicMatrix(0, 0, 2048, 2048);
	
	private static void createTexturePage()
	{
		TexturePage.add( new Surface(texturePageWidth, texturePageHeight, Surface.COLOR) );
		TextureXSpace.add(0);
		TextureYSpace.add(0);
		texturePageMaxYSize = 0;
	}
	
	public static boolean createImage(String fileName)
	{
		if (TexturePage.size()==0)
		{
			createTexturePage();
		}
		
		boolean created=false;
		
		try
		{
			Texture img = new Texture(fileName, Texture.NEAR_FILTERING, false, 0);
			img.saveImage("Test");
			
			int tempHeight = img.getHeight();
			int tempWidth = img.getWidth();
			int size = TexturePage.size()-1;
			
			int texX = TextureXSpace.get(size);
			int texY = TextureYSpace.get(size);
			
			GameRender.setColor(1f,1f,1f,1f);
			Camera oldCamera = Game.currentCamera;
			Mat4f oldOrthoMat = Game.getOrthoProjectionMatrix();
			boolean switchBack = false;
			
			Game.setOrthoMatrix(orthoMat);
			Game.setCamera( new Camera(Camera.MODE_2D) );
			
			switchBack = Game.is3DEnabled;
			
			Game.set2DBegin();
			
			if (tempWidth<texturePageWidth && tempHeight<texturePageHeight)
			{
				
				if (texX+tempWidth<texturePageWidth && texY+tempHeight<texturePageHeight)
				{
					TexturePage.get(TexturePage.size()-1).bind();
					GameRender.drawTexture(img, TextureXSpace.get(size), TextureYSpace.get(size));
					TexturePage.get(TexturePage.size()-1).unBind();
					
					imageX.add(TextureXSpace.get(size));
					imageY.add(TextureYSpace.get(size));
					imageWidth.add(tempWidth);
					imageHeight.add(tempHeight);
					TexturePageLocation.add(size);
					
					TextureXSpace.set(size, texX+tempWidth);
					if (tempHeight > texturePageMaxYSize)
					{
						texturePageMaxYSize=tempHeight;
					}
					
					created=true;
				}
				else if (texY+texturePageMaxYSize<texturePageHeight)
				{
					TextureXSpace.set(size,0);
					TextureYSpace.set(size, texY+texturePageMaxYSize);
					
					
					TexturePage.get(TexturePage.size()-1).bind();
					GameRender.drawTexture(img, TextureXSpace.get(size), TextureYSpace.get(size));
					TexturePage.get(TexturePage.size()-1).unBind();
					
					
					imageX.add(TextureXSpace.get(size));
					imageY.add(TextureYSpace.get(size));
					imageWidth.add(tempWidth);
					imageHeight.add(tempHeight);
					TexturePageLocation.add(size);
					
					if (tempHeight > texturePageMaxYSize)
					{
						texturePageMaxYSize=tempHeight;
					}
					
					created=true;
				}
				else if (texY+texturePageMaxYSize<texturePageHeight)
				{
					createTexturePage();
					
					TexturePage.get(TexturePage.size()-1).bind();
					GameRender.drawTexture(img, TextureXSpace.get(size), TextureYSpace.get(size));
					TexturePage.get(TexturePage.size()-1).unBind();
					
					imageX.add(TextureXSpace.get(size));
					imageY.add(TextureYSpace.get(size));
					imageWidth.add(tempWidth);
					imageHeight.add(tempHeight);
					TexturePageLocation.add(size);
					
					TextureXSpace.set(size, texX+tempWidth);
					if (tempHeight > texturePageMaxYSize)
					{
						texturePageMaxYSize=tempHeight;
					}
					
					created=true;
				}
			}
			else
			{
				System.err.println("Image Size To Large. Must be size of Texture Page.");
			}
			
			img.dispose();
			
			Game.setCamera(oldCamera);
			Game.setOrthoMatrix(oldOrthoMat);
			
			if(switchBack==true)
				Game.set3DBegin();
			else
				Game.set2DBegin();
			
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
		return created;
	}
	
	public static boolean imageExists(int i)
	{
		return (imageX.size()>i);
	}
	
	public static int getImageX(int index)
	{
		return imageX.get(index);
	}
	
	public static int getImageY(int index)
	{
		return imageY.get(index);
	}
	
	public static int getImageWidth(int index)
	{
		return imageWidth.get(index);
	}
	
	public static int getImageHeight(int index)
	{
		return imageHeight.get(index);
	}
	
	public static int getTexturePageLocation(int index)
	{
		return TexturePageLocation.get(index);
	}
	
	public static Surface getTexturePage(int pageNumber)
	{
		if(TexturePage.size()>pageNumber)
			return TexturePage.get(pageNumber);
		else
			return null;
	}
	
	public static int getAmountImages()
	{
		return imageX.size();
	}
	
	public static void dispose()
	{	
		for(int i=0;i<TexturePage.size();i++)
		{
			TexturePage.get(i).saveImage("TexturePage"+i);
			TexturePage.get(i).dispose();
		}
		
		TexturePage.clear();
		imageX.clear();
		imageY.clear();
		imageWidth.clear();
		imageHeight.clear();
		TexturePageLocation.clear();
	}
}
