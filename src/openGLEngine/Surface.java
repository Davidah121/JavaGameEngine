package openGLEngine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL33;
import org.lwjgl.opengl.GL40;

public class Surface implements Serializable{

	private int fbo;
	private int textureID;
	private int textureID2;
	private int depthAttachment;
	
	private int width;
	private int height;
	
	public final static int COLOR = 0;
	public final static int DEPTH = 1;
	public final static int COLOR_AND_DEPTH = 2;
	
	public final static int NEAR_FILTERING = GL11.GL_NEAREST;
	public final static int LINEAR_FILTERING = GL11.GL_LINEAR;
	
	private int style = COLOR;
	private int Interpolation = GL11.GL_NEAREST;
	
	/**
	 * Creates a surface that can be drawn upon.
	 * The surface will have the specified with and height.
	 * 
	 * A surface, in this case, is a wrapper around opengl's FBOs.
	 * Many effects use FBOs, so keep that in mind.
	 * 
	 * The style determines the type of information rendered to this
	 * surface. 
	 * Possible values are: Surface.COLOR, Surface.DEPTH, Surface.COLOR_AND_DEPTH
	 * 
	 * Color and Depth style is for when you want a depth buffer, but do not require
	 * depth information.
	 *  
	 * @param width
	 * @param height
	 * @param style
	 */
	public Surface(int width, int height, int style)
	{
		this.width=width;
		this.height=height;
		this.style=style;
		
		init();
	}
	
	/**
	 * Creates a surface that can be drawn upon.
	 * The surface will have the specified with and height.
	 * 
	 * A surface, in this case, is a wrapper around opengl's FBOs.
	 * Many effects use FBOs, so keep that in mind.
	 * 
	 * The style determines the type of information rendered to this
	 * surface. 
	 * Possible values are: Surface.COLOR, Surface.DEPTH, Surface.COLOR_AND_DEPTH
	 * 
	 * Color and Depth style is for when you want a depth buffer, but do not require
	 * depth information.
	 * 
	 * Interpolation determines the type of filtering used for drawing the surface.
	 * 
	 * Possible values are currently Surface.NEAR_FILTERING and Surface.LINEAR_FILTERING.
	 * 
	 * Other texture values are possible, but this class lacks the methods to use mipmapping.
	 * 
	 * @param width
	 * @param height
	 * @param style
	 * @param Interpolation
	 */
	public Surface(int width, int height, int style, int Interpolation)
	{
		this.width=width;
		this.height=height;
		this.style=style;
		
		if(Interpolation!=NEAR_FILTERING && Interpolation!=LINEAR_FILTERING)
			this.Interpolation = LINEAR_FILTERING;
		else
			this.Interpolation = Interpolation;
		
		init();
	}
	
	private void init()
	{
		//createFrameBuffer
		fbo = GL30.glGenFramebuffers();
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, fbo);
		
		//createTexture
		if(style==COLOR)
		{
			createColor();
			GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0);
		}
		else if(style==DEPTH)
		{
			createDepth();
			GL11.glDrawBuffer(GL11.GL_NONE);
		}
		else if(style==COLOR_AND_DEPTH)
		{
			createColor();
			createDepthAttachment();
			
			GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0);
		}
		
		clear();
		
		switch(GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER)) 
		{
		case GL30.GL_FRAMEBUFFER_COMPLETE:
			System.out.println("Complete");
			break;
		case GL30.GL_FRAMEBUFFER_BINDING:
			System.out.println("Binding");
			break;
		case GL30.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT:
			System.out.println("incomplete attachment");
			break;
		case GL30.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT:
			System.out.println("incomplete missing attachment");
			break;
		case GL30.GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER:
			System.out.println("incomplete draw buffer");
			break;
		case GL30.GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER:
			System.out.println("incomplete read buffer");
			break;
		case GL30.GL_FRAMEBUFFER_UNDEFINED:
			System.out.println("undefined");
			break;
		case GL30.GL_FRAMEBUFFER_UNSUPPORTED:
			System.out.println("unsupported");
			break;
		default:
			System.out.println("something else: "+GL30.glCheckFramebufferStatus(fbo));
			break;
		}
		
		unBind();
	}
	
	private void createColor()
	{
		
		textureID = GL11.glGenTextures();
		bindTexture();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		
		//Setup wrap mode
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		
	    //Setup texture scaling filtering
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, Interpolation);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, Interpolation);
		
		//Send texel data to OpenGL
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_FLOAT, 0);
		
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL11.GL_TEXTURE_2D, textureID, 0);
		//GL32.glFramebufferTexture(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, textureID, 0);
		
		unBindTexture();
	}
	
	private void createDepth()
	{
		textureID = GL11.glGenTextures();
		bindTexture();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		
		//Setup wrap mode
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
	 
	    //Setup texture scaling filtering
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		
		//Send texel data to OpenGL
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL14.GL_DEPTH_COMPONENT24, width, height, 0, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, 0);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL11.GL_TEXTURE_2D, textureID, 0);
		unBindTexture();
	}
	
	private void createDepthAttachment()
	{
		depthAttachment = GL30.glGenRenderbuffers();
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, depthAttachment);
		GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL11.GL_DEPTH_COMPONENT, width, height);
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, 0);
		
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER, depthAttachment);
		
	}
	
	/**
	 * Clears the Surface to a color. The color is 0f,0f,0f,0f. This color
	 * can not be changed. Does not bind the surface before clearing, so it
	 * will clear any bound surface or the default FBO.
	 */
	public void clear()
	{
		GL11.glClearColor(0f, 0f, 0f, 0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	/**
	 * Cleans up the memory used by this surface. Must be called when this surface
	 * will no longer be needed.
	 */
	public void dispose()
	{
		GL30.glDeleteFramebuffers(fbo);
		
		if(textureID!=0)
			GL11.glDeleteTextures(textureID);
		
		if(textureID2!=0)
			GL11.glDeleteTextures(textureID2);
		
		GL30.glDeleteRenderbuffers(depthAttachment);
	}
	
	/**
	 * Binds the surface so that all further draw calls will render
	 * to this surface.
	 */
	public void bind()
	{
		Game.currentSurface = this;
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, fbo);
		GL11.glViewport( 0, 0, width, height );
	}
	
	/**
	 * Unbinds the surface. All further draw calls will render to the
	 * default FBO. The default FBO is not recommended to be used for
	 * anything other than drawing a surface upon.
	 */
	public void unBind()
	{
		Game.currentSurface = null;
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		GL11.glViewport( 0, 0, Display.getWidth(), Display.getHeight() );
	}
	
	/**
	 * Binds the Surface as a texture.
	 */
	public void bindTexture()
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
	}
	
	/**
	 * Unbinds the Surface as a texture.
	 */
	public void unBindTexture()
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	/**
	 * Unbinds the Surface as a texture at a particular location.
	 * @param location
	 */
	public void unBindTexture(int location)
	{
		GL13.glActiveTexture(location);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	/**
	 * Binds the Surface as a texture at a particular location.
	 * @param location
	 */
	public void bindTexture(int location)
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
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
	}
	
	/**
	 * Returns the ID for the FBO this surface uses.
	 * @return int
	 */
	public int getID()
	{
		return fbo;
	}
	
	/**
	 * Returns the ID for the texture this surface uses.
	 * @return int
	 */
	public int getTextureID()
	{
		return textureID;
	}
	
	/**
	 * Returns the width of this surface.
	 * @return int
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * Returns the height of this surface.
	 * @return int
	 */
	public int getHeight()
	{
		return height;
	}
	
	/**
	 * Saves the texture of this surface as a PNG image with the 
	 * specified file name.
	 * @param fileName
	 */
	public void saveImage(String fileName)
	{
		
		int[] pixels = new int[width*height];
		
		bind();
		GL11.glReadPixels(0, 0, width, height, GL12.GL_BGRA, GL11.GL_UNSIGNED_BYTE, pixels);
		unBind();
		
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
