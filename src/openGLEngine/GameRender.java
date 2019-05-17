package openGLEngine;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class GameRender extends Game {
	
	private static float fontSize = 24;
	private static GLFont renderFont = DefaultResources.defaultFont;
	private static int percision = 24;
	
	private static int smoothStrength = 3;
	
	private static Vec4f color = new Vec4f(1f,1f,1f,1f);
	
	private static Model tempModel = new Model(Model.TYPE_DYNAMIC);
	
	private static Shader fontShader = DefaultResources.fontShader;
	
	/**
	 * Disposes of any resources that this class uses. This class in
	 * particular uses a singular model for all drawing methods in this
	 * class.
	 */
	public static void dispose()
	{
		tempModel.destroyModel();
	}
	
	/**
	 * Initializes any resources or variables needed. This method is empty.
	 */
	public static void init()
	{
		//put init stuff here
		//GameResources.addResource(new GLFont(".\\Fonts\\testFont.ft"),"font");
	}
	
	/**
	 * Set the color to be used by the default shader for rendering.
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public static void setColor(float r, float g, float b, float a)
	{
		color.x=r;
		color.y=g;
		color.z=b;
		color.w=a;
		
		float[] stuff = color.toFloatArray();
		
		DefaultResources.defaultShader.setUniform("color", stuff);
		
		if(Game.currentShader!=DefaultResources.defaultShader)
			Game.currentShader.setUniform("color", stuff);
		
		fontShader.setUniform("color", stuff);
		
	}
	
	/**
	 * Sets the color to be used by the default shader for rendering.
	 * @param color
	 */
	public static void setColor(Vec4f color)
	{
		GameRender.color.x = color.x;
		GameRender.color.y = color.y;
		GameRender.color.z = color.z;
		GameRender.color.w = color.w;
		
		float[] stuff = color.toFloatArray();
		
		DefaultResources.defaultShader.setUniform("color", stuff);
		
		if(Game.currentShader!=DefaultResources.defaultShader)
			Game.currentShader.setUniform("color", stuff);
		
		fontShader.setUniform("color", stuff);
		
	}
	
	/**
	 * Gets the color all drawing in this class will use.
	 * if the uniform "color" in the default shader was changed outside this
	 * class, this will not return the color currently being used.
	 * @return
	 */
	public static Vec4f getColor()
	{
		return color;
	}
	
	/**
	 * Sets the font to be used for drawing text.
	 * Must be a GLFont object.
	 * @param f
	 */
	public static void setFont(GLFont f)
	{
		renderFont = f;
	}
	
	/**
	 * Gets the font used for drawing text
	 * @return
	 */
	public static GLFont getFont()
	{
		return renderFont;
	}
	
	/**
	 * Sets the font size to render the text at.
	 * @param value
	 */
	public static void setFontSize(float value)
	{
		fontSize = value;
	}
	
	/**
	 * Gets the font size that text will be rendered at.
	 * @return
	 */
	public static float getFontSize()
	{
		return fontSize;
	}
	
	public static Shader getFontShader()
	{
		return fontShader;
	}
	
	public static void setFontShader(Shader p)
	{
		fontShader = p;
	}
	
	public static void setFontSmoothFactor(int value)
	{
		smoothStrength = GameMath.max(1, value);
	}
	
	public static int getFontSmoothFactor()
	{
		return smoothStrength;
	}
	
	/**
	 * Draws text at the x and y location on the current rendering surface.
	 * This method uses the font set by the user in the setFont() method.
	 * This method also scales the font by the font size specified in the
	 * setFontSize() method.
	 * @param text
	 * @param x
	 * @param y
	 */
	public static void drawText(String text, float x, float y)
	{
		
		float scaleSize = fontSize / renderFont.getFontSize();
		
		float renderX=0;
		float renderY=0;
		int index = -1;
		Texture fontTexture = renderFont.getFontTexture();
		
		float u1=0;
		float u2=0;
		float v1=0;
		float v2=0;
		
		float x1=0;
		float x2=0;
		float y1=0;
		float y2=0;
		
		ArrayList<Float> position = new ArrayList<Float>();
		ArrayList<Float> texcoord = new ArrayList<Float>();
		
		int fontTextureWidth = fontTexture.getWidth();
		int fontTextureHeight = fontTexture.getHeight();
		float charXPos, charYPos, charWidth, charHeight;
		
		
		for(int i=0;i<text.length();i++)
		{
			index = renderFont.getId(text.charAt(i));
			//if(text.charAt(i)>=32)
			//{
			
			if(text.charAt(i)==32)
			{
				renderX += renderFont.getFontSize()*0.25;
			}
			else
			{
				if (index>=0)
				{
					charXPos = renderFont.getXPos(index);
					charYPos = renderFont.getYPos(index);
					charWidth = renderFont.getWidth(index);
					charHeight = renderFont.getHeight(index);
					
					u1=(charXPos / fontTextureWidth);
					v1=(charYPos / fontTextureHeight);
					u2=((charXPos + charWidth) / fontTextureWidth);
					v2=((charYPos + charHeight) / fontTextureHeight);
					
					x1=renderX*scaleSize;
					y1=renderY*scaleSize;
					x2=x1+(charWidth*scaleSize);
					y2=y1+(charHeight*scaleSize);
					
					renderX+=charWidth;
					
					///
					position.add(x+x1);
					position.add(y+y1);
					
					position.add(x+x1);
					position.add(y+y2);
					
					position.add(x+x2);
					position.add(y+y2);
					
					position.add(x+x2);
					position.add(y+y1);
					
					///
					texcoord.add(u1);
					texcoord.add(v1);
					
					texcoord.add(u1);
					texcoord.add(v2);
					
					texcoord.add(u2);
					texcoord.add(v2);
					
					texcoord.add(u2);
					texcoord.add(v1);
					
					///
				}
			}
				
			//}
			//else
			//{
			//	renderX=0;
			//	renderY+=renderFont.getFontSize();
			//}
		}
		
		
		tempModel.setDrawType(GL11.GL_QUADS);
		
		tempModel.storeDataFloat(0, position, 2);
		tempModel.storeDataFloat(1, texcoord, 2);
		
		///
		Shader temp = Game.currentShader;
		fontShader.start();
		fontShader.setUniform("size", new int[]{smoothStrength});
		fontShader.setUniform("pixel", new float[]{1f/GameRender.getFont().getFontTexture().getWidth(), 1f/GameRender.getFont().getFontTexture().getHeight()});
		fontShader.setProjectionMatrix(Game.getViewProjectionMatrix(), false);
		fontShader.setUniform("color", color.toFloatArray());
		///
		
		fontTexture.bind();
		
		tempModel.draw();
		
		position.clear();
		texcoord.clear();
		
		fontTexture.unBind();
		
		///
		fontShader.end();
		temp.start();
		///
		
		tempModel.resetModel();
	}
	
	public static void drawText(String text, int x, int y)
	{
		drawText(text, (float)x, (float)y);
	}
	
	public static void drawText(String text, double x, double y)
	{
		drawText(text, (float)x, (float)y);
	}
	
	/**
	 * Draws text at the x and y location on the current rendering surface.
	 * This method uses the font set by the user in the setFont() method.
	 * This method also scales the font by the font size specified in the
	 * setFontSize() method.
	 * @param text
	 * @param x
	 * @param y
	 */
	public static void drawText(String text, float x, float y, float xScale, float yScale)
	{
		
		float scaleSize = fontSize / renderFont.getFontSize();
		
		float renderX=0;
		float renderY=0;
		int index = -1;
		Texture fontTexture = renderFont.getFontTexture();
		
		float u1=0;
		float u2=0;
		float v1=0;
		float v2=0;
		
		float x1=0;
		float x2=0;
		float y1=0;
		float y2=0;
		
		ArrayList<Float> position = new ArrayList<Float>();
		ArrayList<Float> texcoord = new ArrayList<Float>();
		
		int fontTextureWidth = fontTexture.getWidth();
		int fontTextureHeight = fontTexture.getHeight();
		float charXPos, charYPos, charWidth, charHeight;
		
		
		for(int i=0;i<text.length();i++)
		{
			index = renderFont.getId(text.charAt(i));
			//if(text.charAt(i)>=32)
			//{
			
			if(text.charAt(i)==32)
			{
				renderX += renderFont.getFontSize()*0.25*xScale;
			}
			else
			{
				if (index>=0)
				{
					charXPos = renderFont.getXPos(index);
					charYPos = renderFont.getYPos(index);
					charWidth = renderFont.getWidth(index);
					charHeight = renderFont.getHeight(index);
					
					u1=(charXPos / fontTextureWidth);
					v1=(charYPos / fontTextureHeight);
					u2=((charXPos + charWidth) / fontTextureWidth);
					v2=((charYPos + charHeight) / fontTextureHeight);
					
					x1=renderX*scaleSize;
					y1=renderY*scaleSize;
					x2=x1+(charWidth*scaleSize)*xScale;
					y2=y1+(charHeight*scaleSize)*yScale;
					
					renderX+=charWidth*xScale;
					
					///
					position.add(x+x1);
					position.add(y+y1);
					
					position.add(x+x1);
					position.add(y+y2);
					
					position.add(x+x2);
					position.add(y+y2);
					
					position.add(x+x2);
					position.add(y+y1);
					
					///
					texcoord.add(u1);
					texcoord.add(v1);
					
					texcoord.add(u1);
					texcoord.add(v2);
					
					texcoord.add(u2);
					texcoord.add(v2);
					
					texcoord.add(u2);
					texcoord.add(v1);
					
					///
				}
			}
				
			//}
			//else
			//{
			//	renderX=0;
			//	renderY+=renderFont.getFontSize();
			//}
		}
		
		
		tempModel.setDrawType(GL11.GL_QUADS);
		
		tempModel.storeDataFloat(0, position, 2);
		tempModel.storeDataFloat(1, texcoord, 2);
		
		///
		Shader temp = Game.currentShader;
		fontShader.start();
		fontShader.setUniform("size", new int[]{smoothStrength});
		fontShader.setUniform("pixel", new float[]{1f/GameRender.getFont().getFontTexture().getWidth(), 1f/GameRender.getFont().getFontTexture().getHeight()});
		fontShader.setProjectionMatrix(Game.getViewProjectionMatrix(), false);
		fontShader.setUniform("color", color.toFloatArray());
		///
		
		fontTexture.bind();
		
		tempModel.draw();
		
		position.clear();
		texcoord.clear();
		
		fontTexture.unBind();
		
		///
		fontShader.end();
		temp.start();
		///
		
		tempModel.resetModel();
	}
	
	public static void drawText(String text, int x, int y, int xScale, int yScale)
	{
		drawText(text, (float)x, (float)y, (float)xScale, (float)yScale);
	}
	
	public static void drawText(String text, double x, double y, double xScale, double yScale)
	{
		drawText(text, (float)x, (float)y, (float)xScale, (float)yScale);
	}
	
	public static void drawTextStretched(String text, float x, float y, float width, float height)
	{
		float globalWidth = 0;
		float globalHeight = 0;
		
		float renderX=0;
		float renderY=0;
		int index = -1;
		Texture fontTexture = renderFont.getFontTexture();
		
		int heightOffset = (int) (renderFont.getHeight(0)-renderFont.getFontSize());
		
		float u1=0;
		float u2=0;
		float v1=0;
		float v2=0;
		
		float x1=0;
		float x2=0;
		float y1=0;
		float y2=0;
		
		ArrayList<Float> position = new ArrayList<Float>();
		ArrayList<Float> texcoord = new ArrayList<Float>();
		
		int fontTextureWidth = fontTexture.getWidth();
		int fontTextureHeight = fontTexture.getHeight();
		float charXPos, charYPos, charWidth, charHeight;
		
		
		for(int i=0;i<text.length();i++)
		{
			index = renderFont.getId(text.charAt(i));
			if(text.charAt(i)==32)
			{
				//renderX += renderFont.getFontSize()*0.25;
				globalWidth += renderFont.getFontSize()*0.25;
			}
			else
			{
				if (index>=0)
				{
					charXPos = renderFont.getXPos(index);
					charYPos = renderFont.getYPos(index);
					charWidth = renderFont.getWidth(index);
					charHeight = renderFont.getHeight(index);
					
					u1=(charXPos / fontTextureWidth);
					v1=(charYPos / fontTextureHeight);
					u2=((charXPos + charWidth) / fontTextureWidth);
					v2=((charYPos + charHeight) / fontTextureHeight);
					
					///
					texcoord.add(u1);
					texcoord.add(v1);
					
					texcoord.add(u1);
					texcoord.add(v2);
					
					texcoord.add(u2);
					texcoord.add(v2);
					
					texcoord.add(u2);
					texcoord.add(v1);
					///
					
					globalWidth += charWidth;
					globalHeight = charHeight;
				}
			}
		}
		
		float scaleXValue = (float)(width / globalWidth);
		float scaleYValue = (float)(height / (globalHeight-heightOffset));
		
		for(int i=0;i<text.length();i++)
		{
			index = renderFont.getId(text.charAt(i));
			if(text.charAt(i)==32)
			{
				renderX += (renderFont.getFontSize()*0.25)*scaleXValue;
			}
			else
			{
				if (index>=0)
				{
					charWidth = renderFont.getWidth(index)*scaleXValue;
					charHeight = renderFont.getHeight(index)*scaleYValue;
					
					x1=renderX;
					y1=renderY;
					x2=x1+charWidth;
					y2=y1+charHeight;
					
					renderX+=charWidth;
					
					position.add(x+x1);
					position.add(y+y1);
					
					position.add(x+x1);
					position.add(y+y2);
					
					position.add(x+x2);
					position.add(y+y2);
					
					position.add(x+x2);
					position.add(y+y1);
				}
			}
		}
		
		tempModel.setDrawType(GL11.GL_QUADS);
		
		tempModel.storeDataFloat(0, position, 2);
		tempModel.storeDataFloat(1, texcoord, 2);
		
		///
		Shader temp = Game.currentShader;
		fontShader.start();
		fontShader.setUniform("size", new int[]{smoothStrength});
		fontShader.setUniform("pixel", new float[]{1f/GameRender.getFont().getFontTexture().getWidth(), 1f/GameRender.getFont().getFontTexture().getHeight()});
		fontShader.setProjectionMatrix(Game.getViewProjectionMatrix(), false);
		fontShader.setUniform("color", color.toFloatArray());
		///
		
		fontTexture.bind();
		
		tempModel.draw();
		
		position.clear();
		texcoord.clear();
		
		fontTexture.unBind();
		
		///
		fontShader.end();
		temp.start();
		///
		
		tempModel.resetModel();
	}
	
	public static void drawTextStretched(String text, int x, int y, int width, int height)
	{
		drawTextStretched(text, (float)x, (float)y, (float)width, (float)height);
	}
	
	public static void drawTextStretched(String text, double x, double y, double width, double height)
	{
		drawTextStretched(text, (float)x, (float)y, (float)width, (float)height);
	}
	
	/**
	 * Draws the matrix on the screen.
	 * 
	 * Old method. Used for testing mainly.
	 * @param matrix
	 * @param x
	 * @param y
	 */
	public static void drawMatrix(Mat4f matrix, float x, float y)
	{
		String text = "{";
		for(int i=0;i<16;i++)
		{
			text+=GameMath.toString(matrix.getValue(i))+",";
		}
		text+="}";
		
		drawText(text,x,y);
	}
	
	/**
	 * draws a rectangle starting a x1 and y1 and ending at x2 and y2.
	 * outline determines whether the rectangle will be filled with pixels
	 * or just be an outline of the shape.
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param outline
	 */
	public static void drawRect(float x1, float y1, float x2, float y2, boolean outline)
	{
		
		ArrayList<Float> position = new ArrayList<Float>();
		ArrayList<Float> texcoords = new ArrayList<Float>();
		
		///
		position.add(x1);
		position.add(y1);
		
		texcoords.add(0f);
		texcoords.add(0f);
		
		position.add(x2);
		position.add(y1);
		
		texcoords.add(1f);
		texcoords.add(0f);
		
		position.add(x2);
		position.add(y2);
		
		texcoords.add(1f);
		texcoords.add(1f);
		
		position.add(x1);
		position.add(y2);
		
		texcoords.add(0f);
		texcoords.add(1f);
		///
		
		tempModel.storeDataFloat(0, position, 2);
		tempModel.storeDataFloat(1, texcoords, 2);
		
		position.clear();
		texcoords.clear();
		
		tempModel.setDrawType(GL11.GL_QUADS);
		if (outline==true)
		{
			tempModel.setFillType(GL11.GL_LINE);
		}
		
		
		DefaultResources.defaultTexture.bind();
		tempModel.draw();
		DefaultResources.defaultTexture.unBind();
		tempModel.setFillType(GL11.GL_FILL);
		
		tempModel.resetModel();
	}
	
	public static void drawRect(double x1, double y1, double x2, double y2, boolean outline)
	{
		drawRect((float)x1,(float)y1,(float)x2,(float)y2,outline);
	}
	
	public static void drawRect(int x1, int y1, int x2, int y2, boolean outline)
	{
		drawRect((float)x1,(float)y1,(float)x2,(float)y2,outline);
	}
	
	/**
	 * draws the texture at the specified x and y position.
	 * @param img
	 * @param x
	 * @param y
	 */
	public static void drawTexture(Texture img, float x, float y)
	{
		ArrayList<Float> position = new ArrayList<Float>();
		ArrayList<Float> texcoords = new ArrayList<Float>();
		
		///
		position.add(x);
		position.add(y);
		position.add(0f);
		
		position.add(x);
		position.add(y+img.getHeight());
		position.add(0f);
		
		position.add(x+img.getWidth());
		position.add(y+img.getHeight());
		position.add(0f);
		
		position.add(x+img.getWidth());
		position.add(y);
		position.add(0f);
		
		texcoords.add(0f);
		texcoords.add(0f);
		
		texcoords.add(0f);
		texcoords.add(1f);
		
		texcoords.add(1f);
		texcoords.add(1f);
		
		texcoords.add(1f);
		texcoords.add(0f);
		
		///
		
		tempModel.storeDataFloat(0, position, 3);
		tempModel.storeDataFloat(1, texcoords, 2);
		
		position.clear();
		texcoords.clear();
		
		tempModel.setDrawType(GL11.GL_QUADS);
		
		img.bind();
		tempModel.draw();
		img.unBind();
		
		tempModel.resetModel();
	}
	
	public static void drawTexture(Texture img, int x, int y)
	{
		drawTexture(img, (float)x, (float)y);
	}
	
	public static void drawTexture(Texture img, double x, double y)
	{
		drawTexture(img, (float)x, (float)y);
	}
	
	/**
	 * Draws the texture at the specified x and y position.
	 * Scales the image by the xScale and yScale values.
	 * @param img
	 * @param x
	 * @param y
	 * @param xScale
	 * @param yScale
	 */
	public static void drawTexture(Texture img, float x, float y, float xScale, float yScale)
	{
		ArrayList<Float> position = new ArrayList<Float>();
		ArrayList<Float> texcoords = new ArrayList<Float>();
		
		///
		position.add(x);
		position.add(y);
		position.add(0f);
		
		position.add(x);
		position.add(y+(img.getHeight()*yScale));
		position.add(0f);
		
		position.add(x+(img.getWidth()*xScale));
		position.add(y+(img.getHeight()*yScale));
		position.add(0f);
		
		position.add(x+(img.getWidth()*xScale));
		position.add(y);
		position.add(0f);
		
		texcoords.add(0f);
		texcoords.add(0f);
		
		texcoords.add(0f);
		texcoords.add(1f);
		
		texcoords.add(1f);
		texcoords.add(1f);
		
		texcoords.add(1f);
		texcoords.add(0f);
		
		///
		
		tempModel.storeDataFloat(0, position, 3);
		tempModel.storeDataFloat(1, texcoords, 2);
		
		position.clear();
		texcoords.clear();
		tempModel.setDrawType(GL11.GL_QUADS);
		
		img.bind();
		tempModel.draw();
		img.unBind();
		
		tempModel.resetModel();
	}
	
	public static void drawTexture(Texture img, int x, int y, int xScale, int yScale)
	{
		drawTexture(img, (float)x, (float)y, (float)xScale, (float)yScale);
	}
	
	public static void drawTexture(Texture img, double x, double y, double xScale, double yScale)
	{
		drawTexture(img, (float)x, (float)y, (float)xScale, (float)yScale);
	}
	
	/**
	 * Draws a texture starting at the x and y position specified
	 * with the width and height specified.
	 * @param img
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public static void drawTextureExt(Texture img, float x, float y, float width, float height)
	{
		ArrayList<Float> position = new ArrayList<Float>();
		ArrayList<Float> texcoords = new ArrayList<Float>();
		
		///
		position.add(x);
		position.add(y);
		position.add(0f);
		
		position.add(x);
		position.add(y+height);
		position.add(0f);
		
		position.add(x+width);
		position.add(y+height);
		position.add(0f);
		
		position.add(x+width);
		position.add(y);
		position.add(0f);
		
		texcoords.add(0f);
		texcoords.add(0f);
		
		texcoords.add(0f);
		texcoords.add(1f);
		
		texcoords.add(1f);
		texcoords.add(1f);
		
		texcoords.add(1f);
		texcoords.add(0f);
		
		///
		
		tempModel.storeDataFloat(0, position, 3);
		tempModel.storeDataFloat(1, texcoords, 2);
		
		position.clear();
		texcoords.clear();
		tempModel.setDrawType(GL11.GL_QUADS);
		
		img.bind();
		tempModel.draw();
		img.unBind();
		
		tempModel.resetModel();
	}

	public static void drawTextureExt(Texture img, int x, int y, int width, int height)
	{
		drawTextureExt(img, (float)x, (float)y, (float)width, (float)height);
	}
	
	public static void drawTextureExt(Texture img, double x, double y, double width, double height)
	{
		drawTextureExt(img, (float)x, (float)y, (float)width, (float)height);
	}
	
	/**
	 * Draws a surface at the x and y position specified.
	 * @param s
	 * @param x
	 * @param y
	 */
	public static void drawSurface(Surface s, float x, float y)
	{
		
		tempModel.setDrawType(GL11.GL_QUADS);
		
		ArrayList<Float> position = new ArrayList<Float>();
		ArrayList<Float> texcoords = new ArrayList<Float>();
		
		position.add(x); position.add(y);
		texcoords.add(0f); texcoords.add(1f);
		
		position.add(x); position.add(y+s.getHeight());
		texcoords.add(0f); texcoords.add(0f);
		
		position.add(x+s.getWidth()); position.add(y+s.getHeight());
		texcoords.add(1f); texcoords.add(0f);
		
		position.add(x+s.getWidth()); position.add(y);
		texcoords.add(1f); texcoords.add(1f);
		
		tempModel.storeDataFloat(0, position, 2);
		tempModel.storeDataFloat(1, texcoords, 2);
		
		s.bindTexture();
		int preBlendMode = Game.getBlendMode();
		Game.setBlending(Game.SURFACE_DRAW);
		tempModel.draw();
		Game.setBlending(preBlendMode);
		s.unBindTexture();
		
		
		tempModel.resetModel();
		position.clear();
		texcoords.clear();
	}
	
	public static void drawSurface(Surface s, int x, int y)
	{
		drawSurface(s, (float)x,(float)y);
	}
	
	public static void drawSurface(Surface s, double x, double y)
	{
		drawSurface(s, (float)x,(float)y);
	}
	
	/**
	 * Draws a surface at the x and y position specified.
	 * Scales the image by the xScale and yScale values.
	 * @param s
	 * @param x
	 * @param y
	 * @param xScale
	 * @param yScale
	 */
	public static void drawSurface(Surface s, float x, float y, float xScale, float yScale)
	{
		
		tempModel.setDrawType(GL11.GL_QUADS);
		
		ArrayList<Float> position = new ArrayList<Float>();
		ArrayList<Float> texcoords = new ArrayList<Float>();
		
		position.add(x); position.add(y);
		texcoords.add(0f); texcoords.add(1f);
		
		position.add(x); position.add(y+(s.getHeight()*yScale));
		texcoords.add(0f); texcoords.add(0f);
		
		position.add(x+(s.getWidth()*xScale)); position.add(y+(s.getHeight()*yScale));
		texcoords.add(1f); texcoords.add(0f);
		
		position.add(x+(s.getWidth()*xScale)); position.add(y);
		texcoords.add(1f); texcoords.add(1f);
		
		tempModel.storeDataFloat(0, position, 2);
		tempModel.storeDataFloat(1, texcoords, 2);
		
		float[] stuff = new float[]{1f, 1f, 1f, color.getWFloat()};
		DefaultResources.defaultShader.setUniform("color", stuff);
		
		s.bindTexture();
		int preBlendMode = Game.getBlendMode();
		Game.setBlending(Game.SURFACE_DRAW);
		tempModel.draw();
		Game.setBlending(preBlendMode);
		s.unBindTexture();
		
		tempModel.resetModel();
		position.clear();
		texcoords.clear();
		
		stuff = color.toFloatArray();
		DefaultResources.defaultShader.setUniform("color", stuff);
	}
	
	public static void drawSurface(Surface s, int x, int y, int xScale, int yScale)
	{
		drawSurface(s,(float)x,(float)y,(float)xScale,(float)yScale);
	}
	
	public static void drawSurface(Surface s, double x, double y, double xScale, double yScale)
	{
		drawSurface(s,(float)x,(float)y,(float)xScale,(float)yScale);
	}
	
	/**
	 * Draws a surface at the x and y position specified
	 * with the width and height specified.
	 * @param s
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public static void drawSurfaceExt(Surface s, float x, float y, float width, float height)
	{
		
		tempModel.setDrawType(GL11.GL_QUADS);
		ArrayList<Float> position = new ArrayList<Float>();
		ArrayList<Float> texcoords = new ArrayList<Float>();
		
		position.add(x); position.add(y);
		texcoords.add(0f); texcoords.add(1f);
		
		position.add(x); position.add(y+height);
		texcoords.add(0f); texcoords.add(0f);
		
		position.add(x+width); position.add(y+height);
		texcoords.add(1f); texcoords.add(0f);
		
		position.add(x+width); position.add(y);
		texcoords.add(1f); texcoords.add(1f);
		
		
		tempModel.storeDataFloat(0, position, 2);
		tempModel.storeDataFloat(1, texcoords, 2);
		
		float[] stuff = new float[]{1f, 1f, 1f, color.getWFloat()};
		DefaultResources.defaultShader.setUniform("color", stuff);
		
		s.bindTexture();
		int preBlendMode = Game.getBlendMode();
		Game.setBlending(Game.SURFACE_DRAW);
		tempModel.draw();
		Game.setBlending(preBlendMode);
		s.unBindTexture();
		
		tempModel.resetModel();
		position.clear();
		texcoords.clear();
		
		stuff = color.toFloatArray();
		DefaultResources.defaultShader.setUniform("color", stuff);
	}
	
	public static void drawSurfaceExt(Surface s, int x, int y, int width, int height)
	{
		drawSurfaceExt(s, (float)x, (float)y, (float)width, (float)height);
	}
	
	public static void drawSurfaceExt(Surface s, double x, double y, double width, int height)
	{
		drawSurfaceExt(s, (float)x, (float)y, (float)width, (float)height);
	}
	
	/**
	 * Draws a line starting from x1 and y1 to x2 and y2
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public static void drawLine(float x1, float y1, float x2, float y2)
	{
		ArrayList<Float> position = new ArrayList<Float>();
		
		tempModel.setDrawType(GL11.GL_LINES);
		
		position.add(x1); position.add(y1);
		position.add(x2); position.add(y2);
		
		tempModel.storeDataFloat(0, position, 2);
		
		DefaultResources.defaultTexture.bind();
		tempModel.draw();
		
		tempModel.resetModel();
		position.clear();
	}
	
	public static void drawLine(int x1, int y1, int x2, int y2)
	{
		drawLine((float)x1,(float)y1,(float)x2,(float)y2);
	}
	
	public static void drawLine(double x1, double y1, double x2, double y2)
	{
		drawLine((float)x1,(float)y1,(float)x2,(float)y2);
	}
	
	/**
	 * Draws a line starting from x1, y1, and z1 to x2, y2, and z2
	 * @param x1
	 * @param y1
	 * @param z1
	 * @param x2
	 * @param y2
	 * @param z2
	 */
	public static void drawLine(float x1, float y1, float z1, float x2, float y2, float z2)
	{
		
		ArrayList<Float> position = new ArrayList<Float>();
		
		tempModel.setDrawType(GL11.GL_LINES);
		
		position.add(x1); position.add(y1); position.add(z1);
		position.add(x2); position.add(y2); position.add(z2);
		
		tempModel.storeDataFloat(0, position, 3);
		
		DefaultResources.defaultTexture.bind();
		tempModel.draw();
		
		tempModel.resetModel();
		position.clear();
	}
	
	public static void drawLine(int x1, int y1, int z1, int x2, int y2, int z2)
	{
		drawLine((float)x1,(float)y1,(float)z1,(float)x2,(float)y2,(float)z2);
	}
	
	public static void drawLine(double x1, double y1, double z1, double x2, double y2, double z2)
	{
		drawLine((float)x1,(float)y1,(float)z1,(float)x2,(float)y2,(float)z2);
	}
	
	/**
	 * Draws a point a the position x and y
	 * @param x
	 * @param y
	 */
	public static void drawPoint(float x, float y)
	{
		tempModel.setDrawType(Model.DRAW_TYPE_POINT);
		
		tempModel.storeDataFloat(0, new float[] { x , y }, 2);
		
		DefaultResources.defaultTexture.bind();
		tempModel.draw();
		
		tempModel.resetModel();
	}
	
	public static void drawPoint(int x, int y)
	{
		drawPoint((float)x, (float)y);
	}
	
	public static void drawPoint(double x, double y)
	{
		drawPoint((float)x, (float)y);
	}
	
	/**
	 * Draws a point a the position x, y, and z
	 * @param x
	 * @param y
	 * @param z
	 */
	public static void drawPoint(float x, float y, float z)
	{
		tempModel.setDrawType(Model.DRAW_TYPE_POINT);
		
		tempModel.storeDataFloat(0, new float[] { x , y , z }, 3);
		
		DefaultResources.defaultTexture.bind();
		tempModel.draw();
		
		tempModel.resetModel();
	}
	
	public static void drawPoint(int x, int y, int z)
	{
		drawPoint((float)x, (float)y, (float)z);
	}

	public static void drawPoint(double x, double y, double z)
	{
		drawPoint((float)x, (float)y, (float)z);
	}
	
	/**
	 * Draws a circle at the specified x and y coordinates with the specified
	 * radius.
	 * outline determines whether the circle will be filled or not.
	 * @param x
	 * @param y
	 * @param radius
	 * @param outline
	 */
	public static void drawCircle(float x, float y, float radius, boolean outline)
	{
		
		ArrayList<Float> position = new ArrayList<Float>();
		
		double divValue = (2*Math.PI)/percision;
		
		int amount=0;
		if(outline==false)
		{
			tempModel.setDrawType(GL11.GL_TRIANGLES);
			
			for(int i=0; i<percision;i++)
			{
				position.add((float) (x+Math.cos( divValue*i )*radius)); position.add((float) (y+Math.sin( divValue*i )*radius));
				position.add((float) x); position.add((float) y); 
				position.add((float) (x+Math.cos( divValue*(i+1) )*radius)); position.add((float) (y+Math.sin( divValue*(i+1) )*radius)); 
				
			}
		}
		else
		{
			tempModel.setDrawType(GL11.GL_LINES);
			
			for(int i=0; i<percision;i++)
			{
				position.add((float) (x+Math.cos( divValue*i )*radius)); position.add((float) (y+Math.sin( divValue*i )*radius));
				position.add((float) (x+Math.cos( divValue*(i+1) )*radius)); position.add((float) (y+Math.sin( divValue*(i+1) )*radius)); 
			}
		}
		
		tempModel.storeDataFloat(0, position, 2);
		DefaultResources.defaultTexture.bind();
		tempModel.draw();
		
		tempModel.resetModel();
		position.clear();
	}
	
	public static void drawCircle(int x, int y, int radius, boolean outline)
	{
		drawCircle((float)x, (float)y, (float)radius, outline);
	}
	
	public static void drawCircle(double x, double y, double radius, boolean outline)
	{
		drawCircle((float)x, (float)y, (float)radius, outline);
	}
	
	/**
	 * Draws an arc at the specified x and y coordinates with the specified
	 * radius. 
	 * arcInDegs specifies angle of the arc. It is expected to be in degrees.
	 * outline determines whether the arc will be filled or not.
	 * @param x
	 * @param y
	 * @param radius
	 * @param arcInDegs
	 * @param outline
	 */
	public static void drawArc(float x, float y, float radius, float arcInDegs, boolean outline)
	{
		ArrayList<Float> position = new ArrayList<Float>();
		
		double divValue = (GameMath.degToRad(arcInDegs))/percision;
		
		if(outline==false)
		{
			tempModel.setDrawType(GL11.GL_TRIANGLES);
			
			for(int i=0; i<percision;i++)
			{
				position.add((float) (x+Math.cos( divValue*i )*radius)); position.add((float) (y+Math.sin( divValue*i )*radius));
				position.add((float)x); position.add((float)y);
				position.add((float) (x+Math.cos( divValue*(i+1) )*radius)); position.add((float) (y+Math.sin( divValue*(i+1) )*radius)); 
				
			}
		}
		else
		{
			tempModel.setDrawType(GL11.GL_LINES);
			
			for(int i=0; i<percision;i++)
			{
				position.add((float) (x+Math.cos( divValue*i )*radius)); position.add((float) (y+Math.sin( divValue*i )*radius));
				position.add((float) (x+Math.cos( divValue*(i+1) )*radius)); position.add((float) (y+Math.sin( divValue*(i+1) )*radius)); 
			}
		}
		
		tempModel.storeDataFloat(0, position, 2);
		DefaultResources.defaultTexture.bind();
		tempModel.draw();
		
		tempModel.resetModel();
		position.clear();
	}
	
	public static void drawArc(int x, int y, int radius, int arcInDegs, boolean outline)
	{
		drawArc((float)x,(float)y,(float)radius,(float)arcInDegs, outline);
	}
	
	public static void drawArc(double x, double y, double radius, double arcInDegs, boolean outline)
	{
		drawArc((float)x,(float)y,(float)radius,(float)arcInDegs, outline);
	}
	
	/**
	 * Draws an arc at the specified x and y coordinates with the specified
	 * radius. 
	 * startArcInDegs specifies the starting angle of the arc. It is expected to be in degrees.
	 * endArcInDegs specifies the ending angle of the arc. It is expected to be in degrees.
	 * outline determines whether the arc will be filled or not.
	 * @param x
	 * @param y
	 * @param radius
	 * @param startArcInDegs
	 * @param endArcInDegs
	 * @param outline
	 */
	public static void drawArc(float x, float y, float radius, float startArcInDegs, float endArcInDegs, boolean outline)
	{
		ArrayList<Float> position = new ArrayList<Float>();
		
		double divValue = (GameMath.degToRad(endArcInDegs)-GameMath.degToRad(startArcInDegs))/percision;
		double startValue = GameMath.degToRad(startArcInDegs);
		
		if(outline==false)
		{
			tempModel.setDrawType(GL11.GL_TRIANGLES);
			
			for(int i=0; i<percision;i++)
			{
				position.add((float) (x+Math.cos( startValue+divValue*i )*radius)); position.add((float) (y+Math.sin( startValue+divValue*i )*radius));
				position.add((float) x); position.add((float) y);
				position.add((float) (x+Math.cos( startValue+divValue*(i+1) )*radius)); position.add((float) (y+Math.sin( startValue+divValue*(i+1) )*radius)); 
				
			}
		}
		else
		{
			tempModel.setDrawType(GL11.GL_LINES);
			
			for(int i=0; i<percision;i++)
			{
				position.add((float) (x+Math.cos( startValue+divValue*i )*radius)); position.add((float) (y+Math.sin( startValue+divValue*i )*radius));
				position.add((float) (x+Math.cos( startValue+divValue*(i+1) )*radius)); position.add((float) (y+Math.sin( startValue+divValue*(i+1) )*radius)); 
			}
		}
		
		tempModel.storeDataFloat(0, position, 2);
		DefaultResources.defaultTexture.bind();
		tempModel.draw();
		
		tempModel.resetModel();
		position.clear();
	}
	
	public static void drawArc(int x, int y, int radius, int startArcInDegs, int endArcInDegs, boolean outline)
	{
		drawArc((float)x,(float)y,(float)radius,(float)startArcInDegs,(float)endArcInDegs,outline);
	}
	
	public static void drawArc(double x, double y, double radius, double startArcInDegs, double endArcInDegs, boolean outline)
	{
		drawArc((float)x,(float)y,(float)radius,(float)startArcInDegs,(float)endArcInDegs,outline);
	}
	
	/**
	 * Draws a triangle with the specified coordinates.
	 * outline determines whether or not the triangle will be filled or not.
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param x3
	 * @param y3
	 * @param outline
	 */
	public static void drawTriangle(float x1, float y1, float x2, float y2, float x3, float y3, boolean outline)
	{
		ArrayList<Float> position = new ArrayList<Float>();
		
		tempModel.setDrawType(Model.DRAW_TYPE_TRIANGLES);
		
		if(outline==true)
		{
			tempModel.setFillType(Model.FILL_TYPE_LINE);
		}
		else
		{
			tempModel.setFillType(Model.FILL_TYPE_FILL);
		}
		
		position.add(x1); position.add(y1);
		position.add(x2); position.add(y2);
		position.add(x3); position.add(y3);
		
		tempModel.storeDataFloat(0, position, 2);
		
		DefaultResources.defaultTexture.bind();
		tempModel.draw();
		
		tempModel.resetModel();
		position.clear();
	}
	
	public static void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3, boolean outline)
	{
		drawTriangle((float)x1,(float)y1,(float)x2,(float)y2,(float)x3,(float)y3,outline);
	}
	
	public static void drawTriangle(double x1, double y1, double x2, double y2, double x3, double y3, boolean outline)
	{
		drawTriangle((float)x1,(float)y1,(float)x2,(float)y2,(float)x3,(float)y3,outline);
	}
	
	/**
	 * Draws a triangle with the specified coordinates.
	 * outline determines whether or not the triangle will be filled.
	 * @param x1
	 * @param y1
	 * @param z1
	 * @param x2
	 * @param y2
	 * @param z2
	 * @param x3
	 * @param y3
	 * @param z3
	 * @param outline
	 */
	public static void drawTriangle(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, boolean outline)
	{
		ArrayList<Float> position = new ArrayList<Float>();
		
		tempModel.setDrawType(Model.DRAW_TYPE_TRIANGLES);
		if(outline==true)
			tempModel.setFillType(Model.FILL_TYPE_LINE);
		
		position.add(x1); position.add(y1); position.add(z1);
		position.add(x2); position.add(y2); position.add(z2);
		position.add(x3); position.add(y3); position.add(z3);

		tempModel.storeDataFloat(0, position, 3);
		
		DefaultResources.defaultTexture.bind();
		tempModel.draw();
		
		tempModel.resetModel();
		position.clear();
		
	}
	
	public static void drawTriangle(int x1, int y1, int z1, int x2, int y2, int z2, int x3, int y3, int z3, boolean outline)
	{
		drawTriangle((float)x1,(float)y1,(float)z1,(float)x2,(float)y2,(float)z2,(float)x3,(float)y3,(float)z3,outline);
	}
	
	public static void drawTriangle(double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3, boolean outline)
	{
		drawTriangle((float)x1,(float)y1,(float)z1,(float)x2,(float)y2,(float)z2,(float)x3,(float)y3,(float)z3,outline);
	}
	
	/**
	 * draws a sprite at the specified x and y coordinates.
	 * imageNumber determines the image used from the sprite if it has
	 * more than one.
	 * @param img
	 * @param imageNumber
	 * @param x
	 * @param y
	 */
	public static void drawSprite(Sprite img, int imageNumber, float x, float y)
	{
		if (img.getSize()>0)
		{
			int index = img.getImageId(imageNumber);
			
			float imageWidth = (float)TexturePage.getImageWidth(index);
			float imageHeight = (float)TexturePage.getImageHeight(index);
			float imageX = (float)TexturePage.getImageX(index);
			float imageY = (float)TexturePage.getImageY(index);
			int texturePageNumber = TexturePage.getTexturePageLocation(index);
			
			float texX = imageX / TexturePage.texturePageWidth;
			float texWidth = imageWidth / TexturePage.texturePageWidth;
			float texY = 1f - (imageY / TexturePage.texturePageHeight);
			float texHeight = -(imageHeight / TexturePage.texturePageHeight);
			
			
			ArrayList<Float> position = new ArrayList<Float>();
			ArrayList<Float> texcoords = new ArrayList<Float>();
			
			position.add(x-img.getXOrigin()); position.add(y-img.getYOrigin());
			texcoords.add(texX); texcoords.add(texY);
			
			position.add(x-img.getXOrigin()); position.add(y+imageHeight-img.getYOrigin());
			texcoords.add(texX); texcoords.add(texY+texHeight);
			
			position.add(x+imageWidth-img.getXOrigin()); position.add(y+imageHeight-img.getYOrigin());
			texcoords.add(texX+texWidth); texcoords.add(texY+texHeight);
			
			position.add(x+imageWidth-img.getXOrigin()); position.add(y-img.getYOrigin());
			texcoords.add(texX+texWidth); texcoords.add(texY);
			
			tempModel.storeDataFloat(0, position, 2);
			tempModel.storeDataFloat(1, texcoords, 2);
			
			tempModel.setFillType(GL11.GL_FILL);
			tempModel.setDrawType(GL11.GL_QUADS);
			
			TexturePage.getTexturePage(texturePageNumber).bindTexture();
			
			tempModel.draw();
			
			TexturePage.getTexturePage(texturePageNumber).unBindTexture();
			
			tempModel.resetModel();
			position.clear();
			texcoords.clear();
		}
	}
	
	public static void drawSprite(Sprite img, int imageNumber, int x, int y)
	{
		drawSprite(img, imageNumber, (float)x, (float)y);
	}
	
	public static void drawSprite(Sprite img, int imageNumber, double x, double y)
	{
		drawSprite(img, imageNumber, (float)x, (float)y);
	}
	
	/**
	 * draws a sprite at the specified x and y coordinates.
	 * imageNumber determines the image used from the sprite if it has
	 * more than one.
	 * Scales the sprite by the xScale and yScale values.
	 * @param img
	 * @param imageNumber
	 * @param x
	 * @param y
	 * @param xScale
	 * @param yScale
	 */
	public static void drawSprite(Sprite img, int imageNumber, float x, float y, float xScale, float yScale)
	{
		if (img.getSize()>0)
		{
			int index = img.getImageId(imageNumber);
			
			float imageWidth = (float)TexturePage.getImageWidth(index);
			float imageHeight = (float)TexturePage.getImageHeight(index);
			float imageX = (float)TexturePage.getImageX(index);
			float imageY = (float)TexturePage.getImageY(index);
			int texturePageNumber = TexturePage.getTexturePageLocation(index);
			
			float texX = imageX / TexturePage.texturePageWidth;
			float texWidth = imageWidth / TexturePage.texturePageWidth;
			float texY = 1f - (imageY / TexturePage.texturePageHeight);
			float texHeight = -(imageHeight / TexturePage.texturePageHeight);
			
			
			ArrayList<Float> position = new ArrayList<Float>();
			ArrayList<Float> texcoords = new ArrayList<Float>();
			
			position.add(x-(img.getXOrigin()*xScale)); position.add(y-(img.getYOrigin()*yScale));
			texcoords.add(texX); texcoords.add(texY);
			
			position.add(x-(img.getXOrigin()*xScale)); position.add(y+( (imageHeight-img.getYOrigin() )*yScale) );
			texcoords.add(texX); texcoords.add(texY+texHeight);
			
			position.add(x+( (imageWidth-img.getXOrigin())*xScale)); position.add(y+( (imageHeight-img.getYOrigin() )*yScale) );
			texcoords.add(texX+texWidth); texcoords.add(texY+texHeight);
			
			
			position.add(x+( (imageWidth-img.getXOrigin())*xScale)); position.add(y-(img.getYOrigin()*yScale) );
			texcoords.add(texX+texWidth); texcoords.add(texY);
			
			
			tempModel.storeDataFloat(0,position,2);
			tempModel.storeDataFloat(1,texcoords,2);
			
			tempModel.setFillType(GL11.GL_FILL);
			tempModel.setDrawType(GL11.GL_QUADS);
			
			TexturePage.getTexturePage(texturePageNumber).bindTexture();
			
			tempModel.draw();
			
			TexturePage.getTexturePage(texturePageNumber).unBindTexture();
			
			tempModel.resetModel();
			position.clear();
			texcoords.clear();
			//currentSurface.drawImage(TexturePage.getTexturePage(texturePageNumber), x-img.getXOrigin(), y-img.getYOrigin(), x+imageWidth-img.getXOrigin(), y+imageHeight-img.getYOrigin(), imageX, imageY, imageX+imageWidth, imageY+imageHeight, null);
		}
	}
	
	public static void drawSprite(Sprite img, int imageNumber, int x, int y, int xScale, int yScale)
	{
		drawSprite(img, imageNumber, (float)x, (float)y, (float)xScale, (float)yScale);
	}
	
	public static void drawSprite(Sprite img, int imageNumber, double x, double y, double xScale, double yScale)
	{
		drawSprite(img, imageNumber, (float)x, (float)y, (float)xScale, (float)yScale);
	}
	
	/**
	 * draws a sprite at the specified x and y coordinates.
	 * imageNumber determines the image used from the sprite if it has
	 * more than one.
	 * @param img
	 * @param imageNumber
	 * @param x
	 * @param y
	 */
	public static void drawSpriteExt(Sprite img, int imageNumber, float x, float y, float width, float height)
	{
		if (img.getSize()>0)
		{
			int index = img.getImageId(imageNumber);
			
			float imageWidth = (float)TexturePage.getImageWidth(index);
			float imageHeight = (float)TexturePage.getImageHeight(index);
			float imageX = (float)TexturePage.getImageX(index);
			float imageY = (float)TexturePage.getImageY(index);
			int texturePageNumber = TexturePage.getTexturePageLocation(index);
			
			float texX = imageX / TexturePage.texturePageWidth;
			float texWidth = imageWidth / TexturePage.texturePageWidth;
			float texY = 1f - (imageY / TexturePage.texturePageHeight);
			float texHeight = -(imageHeight / TexturePage.texturePageHeight);
			
			
			ArrayList<Float> position = new ArrayList<Float>();
			ArrayList<Float> texcoords = new ArrayList<Float>();
			
			position.add(x); position.add(y);
			texcoords.add(texX); texcoords.add(texY);
			
			position.add(x); position.add(y+height);
			texcoords.add(texX); texcoords.add(texY+texHeight);
			
			position.add(x+width); position.add(y+height);
			texcoords.add(texX+texWidth); texcoords.add(texY+texHeight);
			
			position.add(x+width); position.add(y);
			texcoords.add(texX+texWidth); texcoords.add(texY);
			
			tempModel.storeDataFloat(0, position, 2);
			tempModel.storeDataFloat(1, texcoords, 2);
			
			tempModel.setFillType(GL11.GL_FILL);
			tempModel.setDrawType(GL11.GL_QUADS);
			
			TexturePage.getTexturePage(texturePageNumber).bindTexture();
			
			tempModel.draw();
			
			TexturePage.getTexturePage(texturePageNumber).unBindTexture();
			
			tempModel.resetModel();
			position.clear();
			texcoords.clear();
		}
	}
	
	public static void drawSpriteExt(Sprite img, int imageNumber, int x, int y, int width, int height)
	{
		drawSpriteExt(img,imageNumber,(float)x,(float)y,(float)width,(float)height);
	}
	
	public static void drawSpriteExt(Sprite img, int imageNumber, double x, double y, double width, double height)
	{
		drawSpriteExt(img,imageNumber,(float)x,(float)y,(float)width,(float)height);
	}
	
	/**
	 * Draws a 3 dimensional plane starting from x,y and z to
	 * x2,y2, and z2.
	 * The model consists of 3 values for position,
	 * then 2 values for texture coordinates,
	 * then 3 values for normal vectors.
	 * @param x
	 * @param y
	 * @param z
	 * @param x2
	 * @param y2
	 * @param z2
	 */
	public static void drawFloor(float x, float y, float z, float x2, float y2, float z2)
	{
		ArrayList<Float> position = new ArrayList<Float>();
		ArrayList<Float> texcoords = new ArrayList<Float>();
		ArrayList<Float> normals = new ArrayList<Float>();
		
		Vec3f normVec = GameMath.crossProduct( new Vec3f(x2-x, y-y, z-z), new Vec3f(x2-x, y2-y, z2-z));
		
		///
		position.add(x);
		position.add(y);
		position.add(z);
		
		position.add(x);
		position.add(y2);
		position.add(z2);
		
		position.add(x2);
		position.add(y2);
		position.add(z2);
		
		position.add(x2);
		position.add(y);
		position.add(z);
		
		texcoords.add(0f);
		texcoords.add(0f);
		
		texcoords.add(0f);
		texcoords.add(1f);
		
		texcoords.add(1f);
		texcoords.add(1f);
		
		texcoords.add(1f);
		texcoords.add(0f);
		
		normals.add((float) normVec.x);
		normals.add((float) normVec.y);
		normals.add((float) normVec.z);
		
		normals.add((float) normVec.x);
		normals.add((float) normVec.y);
		normals.add((float) normVec.z);
		
		normals.add((float) normVec.x);
		normals.add((float) normVec.y);
		normals.add((float) normVec.z);
		
		normals.add((float) normVec.x);
		normals.add((float) normVec.y);
		normals.add((float) normVec.z);
		///
		
		
		tempModel.storeDataFloat(0, position, 3);
		tempModel.storeDataFloat(1, texcoords, 2);
		tempModel.storeDataFloat(2, normals, 3);
		
		position.clear();
		texcoords.clear();
		normals.clear();
		tempModel.setDrawType(Model.DRAW_TYPE_QUADS);
		tempModel.setFillType(Model.FILL_TYPE_FILL);
		
		tempModel.draw();
		
		tempModel.resetModel();
	}
	
	/**
	 * draws a 3 dimensional box starting from x1,y1, and z1 to x2,y2, and z2.
	 * outline determines whether the box will be filled or not.
	 * 
	 * The model consists of 3 values for position,
	 * then 2 values for texture coordinates,
	 * then 3 values for normal vectors.
	 * @param x1
	 * @param y1
	 * @param z1
	 * @param x2
	 * @param y2
	 * @param z2
	 * @param outline
	 */
	public static void drawBox(float x1, float y1, float z1, float x2, float y2, float z2, boolean outline)
	{
		
		ArrayList<Float> position = new ArrayList<Float>();
		ArrayList<Float> texcoords = new ArrayList<Float>();
		ArrayList<Float> normals = new ArrayList<Float>();
		
		Vec3f normVec = GameMath.crossProduct( new Vec3f(x2-x1, 0, 0), new Vec3f(x2-x1, y2-y1, 0));
		
		//bottom
		position.add(x1); position.add(y1); position.add(z1);
		texcoords.add(0f); texcoords.add(0f);
		normals.add(0f); normals.add(0f); normals.add(-1f);
		
		position.add(x2); position.add(y1); position.add(z1);
		texcoords.add(1f); texcoords.add(0f);
		normals.add(0f); normals.add(0f); normals.add(-1f);
		
		position.add(x2); position.add(y2); position.add(z1);
		texcoords.add(1f); texcoords.add(1f);
		normals.add(0f); normals.add(0f); normals.add(-1f);
		
		position.add(x1); position.add(y2); position.add(z1);
		texcoords.add(0f); texcoords.add(1f);
		normals.add(0f); normals.add(0f); normals.add(-1f);
		
		
		//top
		
		position.add(x1); position.add(y1); position.add(z2);
		texcoords.add(0f); texcoords.add(0f);
		normals.add(0f); normals.add(0f); normals.add(1f);
		
		position.add(x1); position.add(y2); position.add(z2);
		texcoords.add(0f); texcoords.add(1f);
		normals.add(0f); normals.add(0f); normals.add(1f);
		
		position.add(x2); position.add(y2); position.add(z2);
		texcoords.add(1f); texcoords.add(1f);
		normals.add(0f); normals.add(0f); normals.add(1f);
		
		position.add(x2); position.add(y1); position.add(z2);
		texcoords.add(1f); texcoords.add(0f);
		normals.add(0f); normals.add(0f); normals.add(1f);
		
		normVec = GameMath.crossProduct( new Vec3f(x2-x1, 0, 0), new Vec3f(x2-x1, 0, z2-z1));
		//front
		
		position.add(x1); position.add(y1); position.add(z1);
		texcoords.add(0f); texcoords.add(0f);
		normals.add(0f); normals.add(1f); normals.add(0f);
		
		position.add(x1); position.add(y1); position.add(z2);
		texcoords.add(0f); texcoords.add(1f);
		normals.add(0f); normals.add(1f); normals.add(0f);
		
		position.add(x2); position.add(y1); position.add(z2);
		texcoords.add(1f); texcoords.add(1f);
		normals.add(0f); normals.add(1f); normals.add(0f);
		
		position.add(x2); position.add(y1); position.add(z1);
		texcoords.add(1f); texcoords.add(0f);
		normals.add(0f); normals.add(1f); normals.add(0f);
		
		
		//back
		position.add(x1); position.add(y2); position.add(z1);
		texcoords.add(0f); texcoords.add(0f);
		normals.add(0f); normals.add(-1f); normals.add(0f);
		
		position.add(x2); position.add(y2); position.add(z1);
		texcoords.add(1f); texcoords.add(0f);
		normals.add(0f); normals.add(-1f); normals.add(0f);
		
		position.add(x2); position.add(y2); position.add(z2);
		texcoords.add(1f); texcoords.add(1f);
		normals.add(0f); normals.add(-1f); normals.add(0f);
		
		position.add(x1); position.add(y2); position.add(z2);
		texcoords.add(0f); texcoords.add(1f);
		normals.add(0f); normals.add(-1f); normals.add(0f);
		
		
		normVec = GameMath.crossProduct( new Vec3f(0, y2-y1, 0), new Vec3f(0, y2-y1, z2-z1));
		//left
		
		position.add(x1); position.add(y1); position.add(z1);
		texcoords.add(0f); texcoords.add(0f);
		normals.add(1f); normals.add(0f); normals.add(0f);
		
		position.add(x1); position.add(y2); position.add(z1);
		texcoords.add(1f); texcoords.add(0f);
		normals.add(1f); normals.add(0f); normals.add(0f);
		
		position.add(x1); position.add(y2); position.add(z2);
		texcoords.add(1f); texcoords.add(1f);
		normals.add(1f); normals.add(0f); normals.add(0f);
		
		position.add(x1); position.add(y1); position.add(z2);
		texcoords.add(0f); texcoords.add(1f);
		normals.add(1f); normals.add(0f); normals.add(0f);
		
		//right
		position.add(x2); position.add(y1); position.add(z1);
		texcoords.add(0f); texcoords.add(0f);
		normals.add(-1f); normals.add(0f); normals.add(0f);
		
		position.add(x2); position.add(y1); position.add(z2);
		texcoords.add(0f); texcoords.add(1f);
		normals.add(-1f); normals.add(0f); normals.add(0f);
		
		position.add(x2); position.add(y2); position.add(z2);
		texcoords.add(1f); texcoords.add(1f);
		normals.add(-1f); normals.add(0f); normals.add(0f);
		
		position.add(x2); position.add(y2); position.add(z1);
		texcoords.add(1f); texcoords.add(0f);
		normals.add(-1f); normals.add(0f); normals.add(0f);
		
		tempModel.storeDataFloat(0, position, 3);
		tempModel.storeDataFloat(1, texcoords, 2);
		tempModel.storeDataFloat(2, normals, 3);

		tempModel.setDrawType(GL11.GL_QUADS);
		if(outline==true)
			tempModel.setFillType(GL11.GL_LINE);
		else
			tempModel.setFillType(GL11.GL_FILL);
		
		tempModel.draw();
		
		tempModel.resetModel();
		
		position.clear();
		texcoords.clear();
		normals.clear();
		
	}

	/**
	 * Draws a 3 dimensional sphere at the specified coordinates with the
	 * specified radius. 
	 * outline determines whether the sphere is filled or not.
	 * 
	 * The model consists of 3 values for position,
	 * then 2 values for texture coordinates,
	 * then 3 values for normal vectors.
	 * @param x
	 * @param y
	 * @param z
	 * @param radius
	 * @param outline
	 */
	public static void drawSphere(float x, float y, float z, float radius, boolean outline)
	{
		ArrayList<Float> position = new ArrayList<Float>();
		ArrayList<Float> texcoords = new ArrayList<Float>();
		ArrayList<Float> normals = new ArrayList<Float>();
		
		tempModel.setDrawType(GL11.GL_QUADS);
		if(outline==true)
			tempModel.setFillType(GL11.GL_LINE);
		
		Vec3f normVec;
		
		float absRad = GameMath.abs(radius);
		float rad = -absRad;
		
		double dirIncrease = 360.0/percision;
		double dirIncrease2 = 180.0/percision;
		
		for(int i=0; i<=percision; i++)
		{
			float z1 = (float) GameMath.dcos(i*dirIncrease2);
			float z2 = (float) GameMath.dcos((i+1)*dirIncrease2);
			
			float dValue1 = (float) GameMath.dsin(i*dirIncrease2);
			float dValue2 = (float) GameMath.dsin((i+1)*dirIncrease2);
			
			float yTex = (float) (GameMath.dcos(i*dirIncrease2)+1f)/2f;
			float yTex2 = (float) (GameMath.dcos((i+1)*dirIncrease2)+1f)/2f;
			
			for(int i2=0; i2<=percision; i2++)
			{
				
				float xTex = (float) (GameMath.dcos(i2*(dirIncrease/2f))+1f)/2f;
				float xTex2 = (float) (GameMath.dcos((i2+1)*(dirIncrease/2f))+1f)/2f;
				
				float x1 =(float) GameMath.dcos(i2*dirIncrease) * dValue1;
				float y1 =(float) GameMath.dsin(i2*dirIncrease) * dValue1;
				
				float x2 =(float) GameMath.dcos((i2+1)*dirIncrease) * dValue1;
				float y2 =(float) GameMath.dsin((i2+1)*dirIncrease) * dValue1;
				
				float x3 =(float) GameMath.dcos((i2+1)*dirIncrease) * dValue2;
				float y3 =(float) GameMath.dsin((i2+1)*dirIncrease) * dValue2;
				
				float x4 =(float) GameMath.dcos(i2*dirIncrease) * dValue2;
				float y4 =(float) GameMath.dsin(i2*dirIncrease) * dValue2;
				
				normVec = GameMath.crossProduct(new Vec3f(x2-x1,y2-y1,z1-z1), new Vec3f(x3-x1,y3-y1,z2-z1));
				
				normVec = GameMath.normalize(normVec);
				
				position.add(x+(x1*rad)); position.add(y+(y1*rad)); position.add(z+(z1*rad));
				texcoords.add(xTex); texcoords.add(yTex);
				normals.add((float)normVec.x); normals.add((float)normVec.y); normals.add((float)normVec.z);
				
				position.add(x+(x4*rad)); position.add(y+(y4*rad)); position.add(z+(z2*rad));
				texcoords.add(xTex); texcoords.add(yTex2);
				normals.add((float)normVec.x); normals.add((float)normVec.y); normals.add((float)normVec.z);
				
				position.add(x+(x3*rad)); position.add(y+(y3*rad)); position.add(z+(z2*rad));
				texcoords.add(xTex2); texcoords.add(yTex2);
				normals.add((float)normVec.x); normals.add((float)normVec.y); normals.add((float)normVec.z);
				
				position.add(x+(x2*rad)); position.add(y+(y2*rad)); position.add(z+(z1*rad));
				texcoords.add(xTex2); texcoords.add(yTex);
				normals.add((float)normVec.x); normals.add((float)normVec.y); normals.add((float)normVec.z);
				
			}
			
		}
		
		tempModel.storeDataFloat(0, position, 3);
		tempModel.storeDataFloat(1, texcoords, 2);
		tempModel.storeDataFloat(2, normals, 3);
		
		tempModel.draw();
		
		tempModel.resetModel();
		position.clear();
		texcoords.clear();
		normals.clear();
		
		tempModel.setFillType(GL11.GL_FILL);
	}
}
