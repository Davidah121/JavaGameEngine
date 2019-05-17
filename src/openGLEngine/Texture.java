package openGLEngine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.sun.prism.paint.Color;

public class Texture implements Serializable{

	private int id;
	private int width;
	private int height;
	private String file;
	
	public static final int NEAR_FILTERING = 0;
	public static final int LINEAR_FILTERING = 1;
	
	public static final int MIPMAP_2_FILTERING = 2;
	public static final int MIPMAP_4_FILTERING = 3;
	public static final int MIPMAP_8_FILTERING = 4;
	public static final int MIPMAP_16_FILTERING = 5;
	
	public static final int MIPMAP_LINEAR_2_FILTERING = 6;
	public static final int MIPMAP_LINEAR_4_FILTERING = 7;
	public static final int MIPMAP_LINEAR_8_FILTERING = 8;
	public static final int MIPMAP_LINEAR_16_FILTERING = 9;
	
	public Texture(String fileName, int filtering, boolean repeat, float lod)
	{
		file = fileName;
		
		ByteBuffer buff = loadImage(fileName);
		
		setupTexture(buff, filtering, repeat, lod);

		buff.clear();
	}
	
	public Texture(BufferedImage img, int filtering, boolean repeat, float lod)
	{
		file = "";
		
		ByteBuffer buff = loadImage(img);
		
		setupTexture(buff, filtering, repeat, lod);

		buff.clear();
	}
	
	private void setupTexture(ByteBuffer buff, int filtering, boolean repeat, float lod)
	{
		id = GL11.glGenTextures();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		bind();
		
		
		//Setup wrap mode
		
		int repeatParam = (repeat==true)? GL12.GL_CLAMP_TO_EDGE : GL11.GL_REPEAT;
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, repeatParam);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, repeatParam);
	 
		//Setup texture scaling filtering
		//Setup mipMapping as well if necessary
		switch(filtering)
		{
		case NEAR_FILTERING:
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buff);
			break;
			
		case LINEAR_FILTERING:
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buff);
			break;
			
		case MIPMAP_2_FILTERING:
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR_MIPMAP_NEAREST);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buff);
			
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LOD, 2f);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MIN_LOD, 2f);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, lod);
			break;
		
		case MIPMAP_4_FILTERING:
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR_MIPMAP_NEAREST);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buff);
			
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LOD, 4f);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MIN_LOD, 2f);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, lod);
			break;
			
		case MIPMAP_8_FILTERING:
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR_MIPMAP_NEAREST);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buff);
			
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LOD, 8f);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MIN_LOD, 2f);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, lod);
			break;
			
		case MIPMAP_16_FILTERING:
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR_MIPMAP_NEAREST);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buff);
			
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LOD, 16f);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MIN_LOD, 2f);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, lod);
			break;
			
		case MIPMAP_LINEAR_2_FILTERING:
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buff);
			
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LOD, 2f);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MIN_LOD, 2f);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, lod);
			break;
		
		case MIPMAP_LINEAR_4_FILTERING:
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buff);
			
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LOD, 4f);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MIN_LOD, 2f);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, lod);
			break;
		
		case MIPMAP_LINEAR_8_FILTERING:
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buff);
			
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LOD, 8f);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MIN_LOD, 2f);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, lod);
			break;
			
		case MIPMAP_LINEAR_16_FILTERING:
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buff);
			
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LOD, 16f);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MIN_LOD, 2f);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, lod);
			break;
		}

	}
	
	private ByteBuffer loadImage(String fileName)
	{
		file = fileName;
		try
		{
			File tempFile = new File(fileName);
			BufferedImage image = null;
			
			if(tempFile.exists())
			{
				image = ImageIO.read( tempFile );
			}
			else
			{
				try
				{
					image = ImageIO.read( ClassLoader.getSystemClassLoader().getResourceAsStream(fileName) );
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			
			width = image.getWidth();
			height = image.getHeight();
			
			int[] pixels = new int[width*height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
			
			ByteBuffer buff = BufferUtils.createByteBuffer(width*height*4);
			
			for(int y=0; y<height; y++)
			{
				for(int x=0; x<width; x++)
				{
					int pixel = pixels[y*width + x];
					
					buff.put( (byte) ((pixel >> 16) & 0xFF));    // Red
					buff.put( (byte) ((pixel >> 8) & 0xFF));     // Green
					buff.put( (byte) ((pixel >> 0) & 0xFF));     // Blue
					buff.put( (byte) ((pixel >> 24) & 0xFF));    // Alpha
				}
			}
			
			image.flush();
			buff.flip();
			return buff;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	private ByteBuffer loadImage(BufferedImage image)
	{
		width = image.getWidth();
		height = image.getHeight();
		
		int[] pixels = new int[width*height];
		image.getRGB(0, 0, width, height, pixels, 0, width);
		
		ByteBuffer buff = BufferUtils.createByteBuffer(width*height*4);
		
		for(int y=0; y<height; y++)
		{
			for(int x=0; x<width; x++)
			{
				int pixel = pixels[y*width + x];
				
				buff.put( (byte) ((pixel >> 16) & 0xFF));    // Red
				buff.put( (byte) ((pixel >> 8) & 0xFF));     // Green
				buff.put( (byte) ((pixel >> 0) & 0xFF));     // Blue
				buff.put( (byte) ((pixel >> 24) & 0xFF));    // Alpha
			}
		}
		
		image.flush();
		buff.flip();
		return buff;
	}
	
	public void bind()
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}
	
	public void bind(int location)
	{
		switch(location)
		{
		case 0:
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			break;
		case 1:
			GL13.glActiveTexture(GL13.GL_TEXTURE1);
			break;
		case 2:
			GL13.glActiveTexture(GL13.GL_TEXTURE2);
			break;
		case 3:
			GL13.glActiveTexture(GL13.GL_TEXTURE3);
			break;
		case 4:
			GL13.glActiveTexture(GL13.GL_TEXTURE4);
			break;
		case 5:
			GL13.glActiveTexture(GL13.GL_TEXTURE5);
			break;
		case 6:
			GL13.glActiveTexture(GL13.GL_TEXTURE6);
			break;
		default:
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			break;
		}
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}
	
	public void unBind()
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		
		if(!this.toString().equals( DefaultResources.defaultTexture.toString()))
			DefaultResources.defaultTexture.bind();
	}
	
	public int getID()
	{
		return id;
	}
	
	public void dispose()
	{
		GL11.glDeleteTextures(id);
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void saveImage(String fileName)
	{
		int[] pixels = new int[width*height];
		
		bind();
		GL11.glGetTexImage(GL11.GL_TEXTURE_2D, 0, GL12.GL_BGRA, GL11.GL_UNSIGNED_BYTE, pixels);
		unBind();
		//GL11.GL_RGBA returns format like this ABGR
		//GL12.GL_BGRA returns format like this ARGB
		
		BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		img.setRGB(0, 0, width, height, pixels, 0, width);
		
		try
		{
			ImageIO.write(img, "png", new File(fileName));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		img.flush();
	}
}
