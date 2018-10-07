package openGLEngine;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.LineMetrics;
import java.awt.image.BufferedImage;

public class testFont {

	private static int fontSize = 24;
	private static BufferedImage im = new BufferedImage(Display.getWidth(), Display.getHeight(), BufferedImage.TYPE_INT_ARGB);
	private static Graphics2D imGraphics = im.createGraphics();
	private static Texture fontTexture;
	
	public static void setFont(Font f)
	{
		imGraphics.setFont(f);
		fontSize=f.getSize();
	}
	
	public static void test(String s, int x, int y)
	{
		imGraphics.clearRect(0, 0, im.getWidth(), im.getHeight());
		imGraphics.drawString(s, x, y);
		
		fontTexture = new Texture(im, Texture.NEAR_FILTERING, false, 0);
		
		GameRender.drawTexture(fontTexture, 0, 0);
		
		fontTexture.dispose();
		
	}
	
}
