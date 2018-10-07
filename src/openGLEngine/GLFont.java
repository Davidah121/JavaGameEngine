package openGLEngine;

import java.util.ArrayList;

public class GLFont {

	private ArrayList<Integer> id = new ArrayList<Integer>();
	private ArrayList<Float> xPos = new ArrayList<Float>();
	private ArrayList<Float> yPos = new ArrayList<Float>();
	private ArrayList<Float> width = new ArrayList<Float>();
	private ArrayList<Float> height = new ArrayList<Float>();
	
	private Texture fontTexture;
	private String fontName;
	private int fontSize;
	private String fontFile;
	private String localDir;
	private String fontStyle;
	
	/**
	 * Creates a new GLFont to be used for drawing text.
	 * 
	 * The file it needs is a .ft file that is custom made.
	 * The .ft used will specify the file name for the font image
	 * it needs. Both should be in the same directory.
	 * 
	 * This type of font is a bitmap font.
	 * @param file
	 */
	public GLFont(String file)
	{
		int separator = file.lastIndexOf('\\');
		localDir = file.substring(0, separator);
		System.out.println(file);
		System.out.println(localDir);
		quickIO fontInfoFile = new quickIO(file, quickIO.TYPE_READ);
		readInfo(fontInfoFile, Texture.NEAR_FILTERING);
		fontInfoFile.close();
		
		//
	}
	
	/**
	 * Creates a new GLFont to be used for drawing text.
	 * 
	 * The file it needs is a .ft file that is custom made.
	 * The .ft used will specify the file name for the font image
	 * it needs. Both should be in the same directory.
	 * 
	 * This type of font is a bitmap font.
	 * 
	 * The Interpolation can be got from the Texture class. By default,
	 * it uses Texture.NEAR_FILTERING
	 * 
	 * @param file
	 * @param Interpolation
	 */
	public GLFont(String file, int Interpolation)
	{
		int separator = file.lastIndexOf('\\');
		localDir = file.substring(0, separator);
		System.out.println(file);
		System.out.println(localDir);
		quickIO fontInfoFile = new quickIO(file, quickIO.TYPE_READ);
		readInfo(fontInfoFile, Interpolation);
		fontInfoFile.close();
		
		//
	}
	
	private void readInfo(quickIO file, int Interpolation)
	{
		
		fontName = file.readNextLn();
		fontStyle = file.readNextLn();
		
		fontSize = GameMath.parseInt(file.readNextLn());
		
		fontFile = localDir+file.readNextLn();
		
		fontTexture = new Texture(fontFile, Interpolation, false, 0);
		
		file.readNextLn(); // separator
		String text = file.readNextLn();
		
		while(!file.endOfFile())
		{
			if(!text.equals("endl;"))
			{
				String[] splitText = text.split(",");
				
				id.add(GameMath.parseInt(splitText[0]));
				xPos.add(GameMath.parseFloat(splitText[1]));
				yPos.add(GameMath.parseFloat(splitText[2]));
				width.add(GameMath.parseFloat(splitText[3]));
				height.add(GameMath.parseFloat(splitText[4]));
				
			}
			text = file.readNextLn();
		}
	}
	
	/**
	 * returns the location of the specified character in the list
	 * of characters this object contains.
	 * @param data
	 * @return
	 */
	public int getId(char data)
	{
		return id.indexOf((int)data);
	}
	
	/**
	 * Gets the x position of the character at the specified index
	 * on the font image.
	 * @param index
	 * @return
	 */
	public float getXPos(int index)
	{
		return xPos.get(index);
	}
	
	/**
	 * Gets the y position of the character at the specified index
	 * on the font image.
	 * @param index
	 * @return
	 */
	public float getYPos(int index)
	{
		return yPos.get(index);
	}
	
	/**
	 * Gets the width of the character at the specified index
	 * on the font image.
	 * @param index
	 * @return
	 */
	public float getWidth(int index)
	{
		return width.get(index);
	}
	
	/**
	 * Gets the height of the character at the specified index
	 * on the font image.
	 * @param index
	 * @return
	 */
	public float getHeight(int index)
	{
		return height.get(index);
	}
	
	/**
	 * Gets the name of the font.
	 * @return
	 */
	public String getFontName()
	{
		return fontName;
	}
	
	/**
	 * Gets the font file.
	 * @return
	 */
	public String getFontFile()
	{
		return fontFile;
	}
	
	/**
	 * Gets the texture used by this font.
	 * @return
	 */
	public Texture getFontTexture()
	{
		return fontTexture;
	}
	
	/**
	 * Gets the size of the font.
	 * @return
	 */
	public int getFontSize()
	{
		return fontSize;
	}
	
	/**
	 * Cleans up the resources used by this font.
	 */
	public void dispose()
	{
		fontTexture.unBind();
		
		id.clear();
		xPos.clear();
		yPos.clear();
		width.clear();
		height.clear();
		fontTexture.dispose();
	}
}
