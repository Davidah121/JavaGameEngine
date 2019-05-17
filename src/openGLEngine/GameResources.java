 package openGLEngine;

import java.util.ArrayList;

public class GameResources {

	private static ArrayList<String> spriteId = new ArrayList<String>();
	private static ArrayList<String> soundId = new ArrayList<String>();
	private static ArrayList<String> modelId = new ArrayList<String>();
	private static ArrayList<String> shaderId = new ArrayList<String>();
	private static ArrayList<String> textureId = new ArrayList<String>();
	private static ArrayList<String> fontId = new ArrayList<String>();
	private static ArrayList<String> surfaceId = new ArrayList<String>();
	private static ArrayList<String> levelId = new ArrayList<String>();
	private static ArrayList<String> tileMapsId = new ArrayList<String>();
	
	private static ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	private static ArrayList<Sound> sounds = new ArrayList<Sound>();
	private static ArrayList<Model> models = new ArrayList<Model>();
	private static ArrayList<Shader> shaders = new ArrayList<Shader>();
	private static ArrayList<Texture> textures = new ArrayList<Texture>();
	private static ArrayList<GLFont> fonts = new ArrayList<GLFont>();
	private static ArrayList<Surface> surfaces = new ArrayList<Surface>();
	private static ArrayList<Level> levels= new ArrayList<Level>();
	private static ArrayList<TileMap> tileMaps = new ArrayList<TileMap>();
	
	
	public static void reloadAllResources()
	{
		//Later
	}
	
	/**
	 * loads a .obj model file into a usable model.
	 * Automatically loads it into it's list of models to be accessed.
	 * resourceName allows you to access it from this list.
	 * 
	 * This method also returns the model itself so there is no need to
	 * access it through this class.
	 * @param fileName
	 * @param resourceName
	 * @return
	 */
	public static Model loadModel(String fileName, String resourceName)
	{
		ArrayList<Float> tempPositionData = new ArrayList<Float>();
		ArrayList<Float> tempTextureCoords = new ArrayList<Float>();
		ArrayList<Float> tempNormalData = new ArrayList<Float>();
		
		ArrayList<Float> positionData = new ArrayList<Float>();
		ArrayList<Float> textureCoords = new ArrayList<Float>();
		ArrayList<Float> normalData = new ArrayList<Float>();
		
		boolean hasTexture=false;
		boolean hasNormals = false;
		
		//assuming extension is .obj
		quickIO file = new quickIO(fileName,quickIO.TYPE_READ);
		while(!file.endOfFile())
		{
			String[] splitText = file.readNextLn().split(" ");
			
			if (splitText[0].equals("v"))
			{
				//position
				tempPositionData.add(GameMath.parseFloat(splitText[1]));
				tempPositionData.add(GameMath.parseFloat(splitText[2]));
				tempPositionData.add(GameMath.parseFloat(splitText[3]));
				
			}
			else if(splitText[0].equals("vt"))
			{
				//textureCoords
				hasTexture=true;
				tempTextureCoords.add(GameMath.parseFloat(splitText[1]));
				tempTextureCoords.add(GameMath.parseFloat(splitText[2]));
				
			}
			else if (splitText[0].equals("vn"))
			{
				//normals
				hasNormals=true;
				tempNormalData.add(GameMath.parseFloat(splitText[1]));
				tempNormalData.add(GameMath.parseFloat(splitText[2]));
				tempNormalData.add(GameMath.parseFloat(splitText[3]));
				
			}
			else if (splitText[0].equals("f"))
			{
				//face data or triangle data
				//splitText[0] == id
				//everyThing else after is numbers, hopefully
				
				if(splitText.length==4)
				{
					//Triangle
					for(int i=1;i<4;i++)
					{
						String[] faceStuff = splitText[i].split("/");
						int vert = (GameMath.parseInt(faceStuff[0])-1)*3;
						int tex = (GameMath.parseInt(faceStuff[1])-1)*2;
						int norm = (GameMath.parseInt(faceStuff[2])-1)*3;
						
						positionData.add( tempPositionData.get(vert));
						positionData.add( tempPositionData.get(vert+1));
						positionData.add( tempPositionData.get(vert+2));
						
						if(hasTexture)
						{
						textureCoords.add( tempTextureCoords.get(tex));
						textureCoords.add( tempTextureCoords.get(tex+1));
						}
						
						if(hasNormals)
						{
						normalData.add( tempNormalData.get(norm));
						normalData.add( tempNormalData.get(norm+1));
						normalData.add( tempNormalData.get(norm+2));
						}
					}
				}
				else if(splitText.length==5)
				{
					String[] faceStuff;
					int vert, tex, norm;
					
					for(int i=1;i<3;i++)
					{
						faceStuff = splitText[i].split("/");
						
						vert = (GameMath.parseInt(faceStuff[0])-1)*3;
						tex = (GameMath.parseInt(faceStuff[1])-1)*2;
						norm = (GameMath.parseInt(faceStuff[2])-1)*3;
						
						positionData.add( tempPositionData.get(vert));
						positionData.add( tempPositionData.get(vert+1));
						positionData.add( tempPositionData.get(vert+2));
						
						if(hasTexture)
						{
						textureCoords.add( tempTextureCoords.get(tex));
						textureCoords.add( tempTextureCoords.get(tex+1));
						}
						
						if(hasNormals)
						{
						normalData.add( tempNormalData.get(norm));
						normalData.add( tempNormalData.get(norm+1));
						normalData.add( tempNormalData.get(norm+2));
						}
					}
					
					faceStuff = splitText[1].split("/");
					
					vert = (GameMath.parseInt(faceStuff[0])-1)*3;
					tex = (GameMath.parseInt(faceStuff[1])-1)*2;
					norm = (GameMath.parseInt(faceStuff[2])-1)*3;
					
					positionData.add( tempPositionData.get(vert));
					positionData.add( tempPositionData.get(vert+1));
					positionData.add( tempPositionData.get(vert+2));
					
					if(hasTexture)
					{
					textureCoords.add( tempTextureCoords.get(tex));
					textureCoords.add( tempTextureCoords.get(tex+1));
					}
					
					if(hasNormals)
					{
					normalData.add( tempNormalData.get(norm));
					normalData.add( tempNormalData.get(norm+1));
					normalData.add( tempNormalData.get(norm+2));
					}
					
					for(int i=3;i<4;i++)
					{
						faceStuff = splitText[i].split("/");
						
						vert = (GameMath.parseInt(faceStuff[0])-1)*3;
						tex = (GameMath.parseInt(faceStuff[1])-1)*2;
						norm = (GameMath.parseInt(faceStuff[2])-1)*3;
						
						positionData.add( tempPositionData.get(vert));
						positionData.add( tempPositionData.get(vert+1));
						positionData.add( tempPositionData.get(vert+2));
						
						if(hasTexture)
						{
						textureCoords.add( tempTextureCoords.get(tex));
						textureCoords.add( tempTextureCoords.get(tex+1));
						}
						
						if(hasNormals)
						{
						normalData.add( tempNormalData.get(norm));
						normalData.add( tempNormalData.get(norm+1));
						normalData.add( tempNormalData.get(norm+2));
						}
					}
				}
				else if(splitText.length>5)
				{
					//Find middle and create model from that.
					//to be done later
				}
			}
		}
		
		file.close();
		
		Model tempModel = new Model();
		tempModel.storeDataFloat(0, positionData, 3);
		
		if(hasTexture) {
			tempModel.storeDataFloat(1, textureCoords, 2);
		}
		
		if(hasNormals) {
			tempModel.storeDataFloat(2, normalData, 3);
		}
		
		tempModel.unBind();
		
		positionData.clear();
		textureCoords.clear();
		normalData.clear();
		tempPositionData.clear();
		tempTextureCoords.clear();
		tempNormalData.clear();
		
		return tempModel;
	}
	
	/**
	 * Gets a sound object with the specified name if it exists in the 
	 * list of sounds this class holds.
	 * @param id
	 * @return
	 */
	public static Sound getSound(String id)
	{
		try
		{
			return sounds.get( soundId.indexOf(id) );
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	/**
	 * Gets a sound object at the specified location if it exists in the 
	 * list of sounds this class holds.
	 * @param id
	 * @return
	 */
	public static Sound getSound(int id)
	{
		try
		{
			return sounds.get( id );
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	/**
	 * Gets the name of the sound if it has been stored in the list of sounds
	 * this class holds.
	 * @param k
	 * @return
	 */
	public static String getSoundName(Sound k)
	{
		int index = sounds.indexOf(k);
		if(index>=0)
		{
			return soundId.get(index);
		}
		return "";
	}
	
	/**
	 * Gets a Sprite object at the specified location if it exists in the 
	 * list of Sprites this class holds.
	 * @param id
	 * @return
	 */
	public static Sprite getSprite(int id)
	{
		try
		{
			return sprites.get(id);
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	/**
	 * Gets a Sprite object with the name if it exists in the 
	 * list of Sprites this class holds.
	 * @param id
	 * @return
	 */
	public static Sprite getSprite(String id)
	{
		try
		{
			return sprites.get( spriteId.indexOf(id) );
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	/**
	 * Gets the name of the sprite if it has been stored in the list of sprite
	 * this class holds.
	 * @param k
	 * @return
	 */
	public static String getSpriteName(Sprite k)
	{
		int index = sprites.indexOf(k);
		if(index>=0)
		{
			return spriteId.get(index);
		}
		return "";
	}
	
	/**
	 * Gets a model object with the specified name if it exists in 
	 * the list of models this class holds.
	 * @param id
	 * @return
	 */
	public static Model getModel(String id)
	{
		try
		{
			return models.get( modelId.indexOf(id) );
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	/**
	 * Gets a model object at the specified location in the list of models
	 * this class holds.
	 * @param id
	 * @return
	 */
	public static Model getModel(int id)
	{
		try
		{
			return models.get( id );
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	/**
	 * Gets the name of the model if it has been stored in the list of models
	 * this class holds.
	 * @param k
	 * @return
	 */
	public static String getModelName(Model k)
	{
		int index = models.indexOf(k);
		if(index>=0)
		{
			return modelId.get(index);
		}
		return "";
	}
	
	/**
	 * Gets a texture with the specified name if it exists in the 
	 * list of textures this class holds.
	 * @param id
	 * @return
	 */
	public static Texture getTexture(String id)
	{
		try
		{
			return textures.get( textureId.indexOf(id) );
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	/**
	 * Gets a texture at the specified location if it exists in the 
	 * list of textures this class holds.
	 * @param id
	 * @return
	 */
	public static Texture getTexture(int id)
	{
		try
		{
			return textures.get( id );
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	/**
	 * Gets the name of the texture if it has been stored in the list of textures
	 * this class holds.
	 * @param k
	 * @return
	 */
	public static String getTextureName(Texture k)
	{
		int index = textures.indexOf(k);
		if(index>=0)
		{
			return textureId.get(index);
		}
		return "";
	}
	
	/**
	 * Gets a shader with the specified name if it exists in the 
	 * list of shader this class holds.
	 * @param id
	 * @return
	 */
	public static Shader getShader(String id)
	{
		try
		{
			return shaders.get( shaderId.indexOf(id) );
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	/**
	 * Gets a shader at the specified location if it exists in the 
	 * list of shaders this class holds.
	 * @param id
	 * @return
	 */
	public static Shader getShader(int id)
	{
		try
		{
			return shaders.get( id );
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	/**
	 * Gets the name of the shader if it has been stored in the list of shaders
	 * this class holds.
	 * @param k
	 * @return
	 */
	public static String getShaderName(Shader k)
	{
		int index = shaders.indexOf(k);
		if(index>=0)
		{
			return shaderId.get(index);
		}
		return "";
	}
	
	/**
	 * Gets a font with the specified name if it exists in the 
	 * list of fonts this class holds.
	 * @param id
	 * @return
	 */
	public static GLFont getFont(String id)
	{
		try
		{
			return fonts.get( fontId.indexOf(id) );
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	/**
	 * Gets a font at the specified location if it exists in the 
	 * list of fonts this class holds.
	 * @param id
	 * @return
	 */
	public static GLFont getFont(int id)
	{
		try
		{
			return fonts.get( id );
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	/**
	 * Gets the name of the font if it has been stored in the list of fonts
	 * this class holds.
	 * @param k
	 * @return
	 */
	public static String getFontName(GLFont k)
	{
		int index = fonts.indexOf(k);
		if(index>=0)
		{
			return fontId.get(index);
		}
		return "";
	}
	
	/**
	 * Gets a surface with the specified name if it exists in the 
	 * list of surfaces this class holds.
	 * @param id
	 * @return
	 */
	public static Surface getSurface(String id)
	{
		try
		{
			return surfaces.get( surfaceId.indexOf(id));
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	/**
	 * Gets a surface at the specified location if it exists in the 
	 * list of surface this class holds.
	 * @param id
	 * @return
	 */
	public static Surface getSurface(int id)
	{
		try
		{
			return surfaces.get( id );
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	/**
	 * Gets the name of the surface if it has been stored in the list of surfaces
	 * this class holds.
	 * @param k
	 * @return
	 */
	public static String getSurfaceName(Surface k)
	{
		int index = surfaces.indexOf(k);
		if(index>=0)
		{
			return surfaceId.get(index);
		}
		return "";
	}
	
	/**
	 * Gets a level with the specified name if it exists in the 
	 * list of levels this class holds.
	 * @param id
	 * @return
	 */
	public static Level getLevel(String id)
	{
		try
		{
			return levels.get( levelId.indexOf(id) );
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	/**
	 * Gets a level at the specified location if it exists in the 
	 * list of levels this class holds.
	 * @param id
	 * @return
	 */
	public static Level getLevel(int id)
	{
		try
		{
			return levels.get( id );
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	/**
	 * Gets the name of the level if it has been stored in the list of levels
	 * this class holds.
	 * @param k
	 * @return
	 */
	public static String getLevelName(Level k)
	{
		int index = levels.indexOf(k);
		if(index>=0)
		{
			return levelId.get(index);
		}
		return "";
	}
	
	/**
	 * Gets a tileMap with the specified name if it exists in the 
	 * list of tileMaps this class holds.
	 * @param id
	 * @return
	 */
	public static TileMap getTileMap(String id)
	{
		try
		{
			return tileMaps.get( tileMapsId.indexOf(id) );
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	/**
	 * Gets a tileMap at the specified location if it exists in the 
	 * list of tileMaps this class holds.
	 * @param id
	 * @return
	 */
	public static TileMap getTileMap(int id)
	{
		try
		{
			return tileMaps.get( id );
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	/**
	 * Gets the name of the tileMap if it has been stored in the list of tileMaps
	 * this class holds.
	 * @param k
	 * @return
	 */
	public static String getTileMapName(TileMap k)
	{
		int index = tileMaps.indexOf(k);
		if(index>=0)
		{
			return tileMapsId.get(index);
		}
		return "";
	}
	
	/**
	 * Adds a sound resource to the list of sounds with the specified
	 * id.
	 * @param s
	 * @param id
	 */
	public static void addResource(Sound s, String id)
	{
		if(soundId.indexOf(id)==-1)
		{
			sounds.add(s);
			soundId.add(id);
		}
		else
		{
			System.err.println("Resource Id already exists");
		}
	}
	
	/**
	 * Adds a Sprite resource to the list of Sprites with the specified
	 * id.
	 * @param s
	 * @param id
	 */
	public static void addResource(Sprite t, String id)
	{
		if(spriteId.indexOf(id)==-1)
		{
			sprites.add(t);
			spriteId.add(id);
		}
		else
		{
			System.err.println("Resource Id already exists");
		}
	}
	
	/**
	 * Adds a model resource to the list of models with the specified
	 * id.
	 * @param s
	 * @param id
	 */
	public static void addResource(Model m, String id)
	{
		if(modelId.indexOf(id)==-1)
		{
			models.add(m);
			modelId.add(id);
		}
		else
		{
			System.err.println("Resource Id already exists");
		}
	}
	
	/**
	 * Adds a texture resource to the list of textures with the specified
	 * id.
	 * @param s
	 * @param id
	 */
	public static void addResource(Texture t, String id)
	{
		if(textureId.indexOf(id)==-1)
		{
			textures.add(t);
			textureId.add(id);
		}
		else
		{
			System.err.println("Resource Id already exists");
		}
	}
	
	/**
	 * Adds a shader resource to the list of shaders with the specified
	 * id.
	 * @param s
	 * @param id
	 */
	public static void addResource(Shader s, String id)
	{
		if(shaderId.indexOf(id)==-1)
		{
			shaders.add(s);
			shaderId.add(id);
		}
		else
		{
			System.err.println("Resource Id already exists");
		}
	}
	
	/**
	 * Adds a font resource to the list of fonts with the specified
	 * id.
	 * @param s
	 * @param id
	 */
	public static void addResource(GLFont f, String id)
	{
		if(fontId.indexOf(id)==-1)
		{
			fonts.add(f);
			fontId.add(id);
		}
		else
		{
			System.err.println("Resource Id already exists");
		}
	}
	
	/**
	 * Adds a surface resource to the list of surfaces with the specified
	 * id.
	 * @param s
	 * @param id
	 */
	public static void addResource(Surface s, String id)
	{
		if(surfaceId.indexOf(id)==-1)
		{
			surfaces.add(s);
			surfaceId.add(id);
		}
		else
		{
			System.err.println("Resource Id already exists");
		}
	}
	
	/**
	 * Adds a level resource to the list of levels with the specified
	 * id.
	 * @param s
	 * @param id
	 */
	public static void addResource(Level l, String id)
	{
		if(levelId.indexOf(id)==-1)
		{
			levels.add(l);
			levelId.add(id);
		}
		else
		{
			System.err.println("Resource Id already exists");
		}
	}
	
	/**
	 * Adds a tileMap resource to the list of tileMaps with the specified
	 * id.
	 * @param s
	 * @param id
	 */
	public static void addResource(TileMap t, String id)
	{
		if(tileMapsId.indexOf(id)==-1)
		{
			tileMaps.add(t);
			tileMapsId.add(id);
		}
		else
		{
			System.err.println("Resource Id already exists");
		}
	}
	
	/**
	 * Deletes a sound with the specified name.
	 * @param id
	 */
	public static void deleteSound(String id)
	{
		try
		{
			sounds.remove(soundId.indexOf(id)).dispose();
			soundId.remove(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Resource id does not exists");
		}
	}
	
	/**
	 * Deletes a sprite with the specified name.
	 * @param id
	 */
	public static void deleteSprite(String id)
	{
		try
		{
			sprites.remove(spriteId.indexOf(id)).dispose();
			spriteId.remove(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Resource id does not exists");
		}
	}
	
	/**
	 * Deletes a model with the specified name.
	 * @param id
	 */
	public static void deleteModel(String id)
	{
		try
		{
			models.remove( modelId.indexOf(id) ).destroyModel();
			modelId.remove(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Resource id does not exists");
		}
	}
	
	/**
	 * Deletes a texture with the specified name.
	 * @param id
	 */
	public static void deleteTexture(String id)
	{
		try
		{
			textures.remove( textureId.indexOf(id) ).dispose();
			textureId.remove(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Resource id does not exists");
		}
	}
	
	/**
	 * Deletes a shader with the specified name.
	 * @param id
	 */
	public static void deleteShader(String id)
	{
		try
		{
			shaders.remove( shaderId.indexOf(id) ).dispose();
			shaderId.remove(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Resource id does not exists");
		}
	}
	
	/**
	 * Deletes a font with the specified name.
	 * @param id
	 */
	public static void deleteFont(String id)
	{
		try
		{
			fonts.remove( fontId.indexOf(id) ).dispose();
			fontId.remove(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Resource id does not exists");
		}
	}
	
	/**
	 * Deletes a surface with the specified name.
	 * @param id
	 */
	public static void deleteSurface(String id)
	{
		try
		{
			surfaces.remove( surfaceId.indexOf(id)).dispose();
			surfaceId.remove(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Resource id does not exists");
		}
	}
	
	/**
	 * Deletes a level with the specified name.
	 * @param id
	 */
	public static void deleteLevel(String id)
	{
		try
		{
			levels.remove( levelId.indexOf(id) ).dispose();
			levelId.remove(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Resource id does not exists");
		}
	}
	
	/**
	 * Deletes a tileMap with the specified name.
	 * @param id
	 */
	public static void deleteTileMap(String id)
	{
		try
		{
			tileMaps.remove( tileMapsId.indexOf(id) ).destroy();
			tileMapsId.remove(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Resource id does not exists");
		}
	}
	
	public static int getSoundSize()
	{
		return sounds.size();
	}
	
	public static int getSpriteSize()
	{
		return sprites.size();
	}
	
	public static int getModelSize()
	{
		return models.size();
	}
	
	public static int getTextureSize()
	{
		return textures.size();
	}
	
	public static int getShaderSize()
	{
		return shaders.size();
	}
	
	public static int getFontSize()
	{
		return fonts.size();
	}
	
	public static int getSurfaceSize()
	{
		return surfaces.size();
	}
	
	public static int getLevelSize()
	{
		return levels.size();
	}
	
	public static int getTileMapSize()
	{
		return tileMaps.size();
	}
	
	/**
	 * Disposes of all resources stored in this class's list of 
	 * resources.
	 */
	public static void dispose()
	{
		for(int i=0;i<sounds.size();i++)
		{
			sounds.get(i).dispose();
		}
		for(int i=0;i<sprites.size();i++)
		{
			sprites.get(i).dispose();
		}
		for(int i=0;i<models.size();i++)
		{
			models.get(i).destroyModel();
		}
		for(int i=0;i<textures.size();i++)
		{
			textures.get(i).dispose();
		}
		for(int i=0;i<shaders.size();i++)
		{
			shaders.get(i).dispose();
		}
		for(int i=0;i<fonts.size();i++)
		{
			fonts.get(i).dispose();
		}
		for(int i=0;i<surfaces.size();i++)
		{
			surfaces.get(i).dispose();
		}
		for(int i=0;i<levels.size();i++)
		{
			levels.get(i).dispose();
		}
		for(int i=0;i<tileMaps.size();i++)
		{
			tileMaps.get(i).destroy();
		}
		
		sounds.clear();
		sprites.clear();
		models.clear();
		textures.clear();
		shaders.clear();
		fonts.clear();
		surfaces.clear();
		levels.clear();
		tileMaps.clear();
		
		soundId.clear();
		spriteId.clear();
		modelId.clear();
		textureId.clear();
		shaderId.clear();
		fontId.clear();
		surfaceId.clear();
		levelId.clear();
		tileMapsId.clear();
	}
}
