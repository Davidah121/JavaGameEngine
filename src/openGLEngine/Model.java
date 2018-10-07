package openGLEngine;

import java.nio.*;
import java.util.ArrayList;
import java.util.Arrays;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import org.lwjgl.system.MemoryUtil;

public class Model {
	
	private int vaoID;
	private int vboID;
	private int size;
	private int attribs;
	
	private ArrayList<Integer> vbos = new ArrayList<Integer>();
	private ArrayList< ArrayList > attributeData = new ArrayList< ArrayList >();
	private ArrayList<Integer> indicies = new ArrayList<Integer>();
	private ArrayList<Integer> vertAmount = new ArrayList<Integer>();
	
	private int location=0;
	
	private int type = GL11.GL_TRIANGLES;
	private int fillType = GL11.GL_FILL;
	
	private int modelType = GL15.GL_STATIC_DRAW;
	
	public static final int TYPE_INT = 0;
	public static final int TYPE_FLOAT = 1;
	public static final int TYPE_DOUBLE = 2;
	
	public static final int TYPE_STATIC = GL15.GL_STATIC_DRAW;
	public static final int TYPE_STREAM = GL15.GL_STREAM_DRAW;
	public static final int TYPE_DYNAMIC = GL15.GL_DYNAMIC_DRAW;
	
	public static final int FILL_TYPE_FILL = GL11.GL_FILL;
	public static final int FILL_TYPE_LINE = GL11.GL_LINE;
	
	public static final int DRAW_TYPE_POINT = GL11.GL_POINT;
	
	public static final int DRAW_TYPE_LINES = GL11.GL_LINES;
	public static final int DRAW_TYPE_QUADS = GL11.GL_QUADS;
	public static final int DRAW_TYPE_TRIANGLES = GL11.GL_TRIANGLES;
	
	public static final int DRAW_TYPE_TRINAGLE_STRIP = GL11.GL_TRIANGLE_STRIP;
	public static final int DRAW_TYPE_LINE_STRIP = GL11.GL_LINE_STRIP;
	public static final int DRAW_TYPE_QUAD_STRIP = GL11.GL_QUAD_STRIP;
	
	public static final int DRAW_TYPE_TRINAGLE_FAN = GL11.GL_TRIANGLE_FAN;
	
	private ArrayList<Boolean> attributeUsed = new ArrayList<Boolean>();
	private ArrayList<Boolean> attributeEnable = new ArrayList<Boolean>();
	private ArrayList<Integer> attributeSize = new ArrayList<Integer>();
	
	/**
	 * Creates a model that will store vertex data. A model does not
	 * have to be strictly 2D or 3D. A model can store different attributes
	 * for a vertex.
	 * 
	 * Example: a single vertex could store: 
	 * position, normal vector, texture coordinates, color
	 * 
	 */
	public Model()
	{
		vaoID = GL30.glGenVertexArrays();
	}
	
	/**
	 * Creates a model that will store vertex data. A model does not
	 * have to be strictly 2D or 3D. A model can store different attributes
	 * for a vertex.
	 * 
	 * Example: a single vertex could store: 
	 * position, normal vector, texture coordinates, color
	 * 
	 * Model type refers to how the GPU will store and treat the data.
	 * Possible values are Model.TYPE_STATIC, Model.TYPE_STREAM, MODEL.TYPE_DYNAMIC
	 * 
	 * For more specific information, google opengl dynamic models.
	 * @param modelType
	 */
	public Model(int modelType)
	{
		this.modelType = modelType;
		vaoID = GL30.glGenVertexArrays();
	}
	
	/**
	 * Enable all of this model's attributes to be used when drawing 
	 * this model.
	 */
	public void enableAllAttributes()
	{
		for(int i=0; i<attributeEnable.size(); i++)
		{
			attributeEnable.set(i, true);
		}
	}
	
	/**
	 * disable all of this model's attributes to be used when drawing 
	 * this model.
	 */
	public void disableAllAttributes()
	{
		for(int i=0; i<attributeEnable.size(); i++)
		{
			attributeEnable.set(i, false);
		}
	}
	
	/**
	 * Enable the attribute at this location in the model to be used when
	 * drawing this model.
	 * @param i
	 */
	public void enableAttribute(int i)
	{
		if(i>=0 && i<attributeEnable.size())
			attributeEnable.set(i, true);
	}
	
	/**
	 * Disable the attribute at this location in the model to be used when
	 * drawing this model.
	 * @param i
	 */
	public void disableAttribute(int i)
	{
		if(i>=0 && i<attributeEnable.size())
			attributeEnable.set(i, false);
	}
	
	/**
	 * Determines whether the attribute at the specified location in the
	 * model will be used for drawing.
	 * @param i
	 * @return boolean
	 */
	public boolean getAttributeEnabled(int i)
	{
		try
		{
			return attributeEnable.get(i);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Resets parts of the model to default to enable rewriting data.
	 * Is faster than creating a new model each time.
	 */
	public void resetModel()
	{
		vertAmount.clear();
		for(int i=0; i<attributeData.size(); i++)
		{
			attributeData.get(i).clear();
			//attributeUsed.set(i,false);
			attributeSize.set(i, 0);
		}
		attributeData.clear();
		indicies.clear();
		disableAllAttributes();
		
	}
	
	/**
	 * Returns the current attribute being edited.
	 * @return int
	 */
	public int getCurrentAttributeLocation()
	{
		return location;
	}
	
	/**
	 * Sets the Drawing method for this model. Defines how the data is
	 * arranged. Possible values are any of the DRAW_TYPE constants
	 * belonging to the Model class.
	 * @param value
	 */
	public void setDrawType(int value)
	{
		type=value;
	}
	
	/**
	 * Sets Whether this model will be filled or just an outline. Possible values
	 * are Model.FILL_TYPE_FILL or Model.FILL_TYPE_LINE
	 * @param value
	 */
	public void setFillType(int value)
	{
		fillType=value;
	}
	
	/**
	 * Draws the model. It particularly just sends the data to a shader
	 * to be processed. Any additional processing such as rotation, translation,
	 * and scaling should be done in a shader.
	 */
	public void draw()
	{
		GL30.glBindVertexArray(vaoID);
		
		for(int i=0;i<attribs;i++)
		{
			if(attributeEnable.get(i)==true)
				GL20.glEnableVertexAttribArray(i);
		}
	    
	    GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, fillType);
	    
	    GL11.glDrawArrays(type, 0, size);
	    
	    for(int i=attribs-1;i>=0;i--)
	    {
	    	GL20.glDisableVertexAttribArray(i);
	    }
		
		GL30.glBindVertexArray(0);
	}
	
	private float[] toFloatArray(ArrayList<Float> arg)
	{
		float[] temp = new float[arg.size()];
		for(int i=0;i<arg.size();i++)
		{
			temp[i]=(float)arg.get(i);
		}
		return temp;
	}
	
	private int[] toIntArray(ArrayList<Integer> arg)
	{
		int[] temp = new int[arg.size()];
		for(int i=0;i<arg.size();i++)
		{
			temp[i]=(int)arg.get(i);
		}
		return temp;
	}
	
	private double[] toDoubleArray(ArrayList<Double> arg)
	{
		double[] temp = new double[arg.size()];
		for(int i=0;i<arg.size();i++)
		{
			temp[i]=(double)arg.get(i);
		}
		return temp;
	}
	
	private ArrayList<Integer> toIntegerArrayList(int[] data)
	{
		ArrayList<Integer> p =  new ArrayList<Integer>();
		for(int i=0; i<data.length; i++)
		{
			p.add( data[i] );
		}
		return p;
	}
	
	private ArrayList<Double> toDoubleArrayList(double[] data)
	{
		ArrayList<Double> p =  new ArrayList<Double>();
		for(int i=0; i<data.length; i++)
		{
			p.add( data[i] );
		}
		return p;
	}
	
	private ArrayList<Float> toFloatArrayList(float[] data)
	{
		ArrayList<Float> p =  new ArrayList<Float>();
		for(int i=0; i<data.length; i++)
		{
			p.add( data[i] );
		}
		return p;
	}
	
	public void storeDataInt(int attributeNum, int[] data, int amountData)
	{
		ArrayList<Integer> p =  toIntegerArrayList(data);
		storeDataInt(attributeNum, p, amountData);
	}
	/**
	 * A method that creates a vbo at the specified attribute number with
	 * the specified data. amountData specifies the size of the vector. A
	 * vertex can have up to 4 values in a single attribute.
	 * Stores Integers
	 * @param attributeNum
	 * @param data
	 * @param amountData
	 */
	public void storeDataInt(int attributeNum, ArrayList<Integer> data, int amountData)
	{
		bind();
		
		if(attributeUsed.size()<=attributeNum)
		{
			attributeUsed.add(false);
			attributeSize.add(0);
			attribs++;
			attributeEnable.add(true);
		}
		
		if(attributeUsed.get(attributeNum)==false)
		{
			vboID = GL15.glGenBuffers();
			vbos.add(vboID);
		}
		else
		{
			vboID = vbos.get(attributeNum);
		}
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		
		size=data.size()/amountData;
		
		int[] nData = toIntArray(data);
		
		if(size<=attributeSize.get(attributeNum) && attributeUsed.get(attributeNum)==true)
		{
			//No need to reallocate, just change the values in the model and only draw a few of the verts
			GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, nData );
		}
		else
		{
			//Need to Reallocate memory so that the size can change.
			
			IntBuffer vertBuff = MemoryUtil.memAllocInt(data.size());
			
			vertBuff.put( nData ).flip();
			
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertBuff, modelType);
			
			vertBuff.clear();
			MemoryUtil.memFree(vertBuff);
			
		}
		
		attributeUsed.set(attributeNum, true);
		attributeEnable.set(attributeNum, true);
		attributeSize.set(attributeNum, size);
		
		GL20.glVertexAttribPointer(attributeNum, amountData, GL11.GL_INT, false, 0, 0 );
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		//Add the data to attributeData list so that it can be accessed later
		attributeData.add( data );
		unBind();
	}
	
	public void storeDataDouble(int attributeNum, double[] data, int amountData)
	{
		ArrayList<Double> p =  toDoubleArrayList(data);
		
		storeDataDouble(attributeNum, p, amountData);
	}
	/**
	 * A method that creates a vbo at the specified attribute number with
	 * the specified data. amountData specifies the size of the vector. A
	 * vertex can have up to 4 values in a single attribute.
	 * Stores Doubles
	 * @param attributeNum
	 * @param data
	 * @param amountData
	 */
	public void storeDataDouble(int attributeNum, ArrayList<Double> data, int amountData)
	{
		bind();
		
		if(attributeUsed.size()<=attributeNum)
		{
			attributeUsed.add(false);
			attributeSize.add(0);
			attribs++;
			attributeEnable.add(true);
		}
		
		if(attributeUsed.get(attributeNum)==false)
		{
			vboID = GL15.glGenBuffers();
			vbos.add(vboID);
		}
		else
		{
			vboID = vbos.get(attributeNum);
		}
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		
		size=data.size()/amountData;
		double[] nData = toDoubleArray(data);
		
		if( size<=attributeSize.get(attributeNum) && attributeUsed.get(attributeNum)==true )
		{
			//No need to reallocate, just change the values in the model and only draw a few of the verts
			GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, nData );
		}
		else
		{
			//Need to Reallocate memory so that the size can change.
			
			DoubleBuffer vertBuff = MemoryUtil.memAllocDouble(data.size());
			
			vertBuff.put( nData ).flip();
			
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertBuff, modelType);
			
			vertBuff.clear();
			MemoryUtil.memFree(vertBuff);
			
		}
		
		attributeUsed.set(attributeNum, true);
		attributeEnable.set(attributeNum, true);
		attributeSize.set(attributeNum, size);
		
		GL20.glVertexAttribPointer(attributeNum, amountData, GL11.GL_DOUBLE, false, 0, 0 );
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		//Add the data to attributeData list so that it can be accessed later
		attributeData.add( data );
		unBind();
	}
	
	/*
	public void storeIndices(ArrayList<Integer> data)
	{
		size = data.size();
		
		IntBuffer vertBuff = MemoryUtil.memAllocInt(data.size());
		vertBuff.put( toIntArray(data) ).flip();
		
		vboID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, vertBuff, GL15.GL_STATIC_DRAW);
		
		MemoryUtil.memFree(vertBuff);
		vertBuff.clear();
		
	}
	*/
	
	public void storeDataFloat(int attributeNum, float[] data, int amountData)
	{
		ArrayList<Float> p =  toFloatArrayList(data);
		
		storeDataFloat(attributeNum, p, amountData);
	}
	/**
	 * A method that creates a vbo at the specified attribute number with
	 * the specified data. amountData specifies the size of the vector. A
	 * vertex can have up to 4 values in a single attribute.
	 * Stores Floats
	 * @param attributeNum
	 * @param data
	 * @param amountData
	 */
	public void storeDataFloat(int attributeNum, ArrayList<Float> data, int amountData)
	{
		bind();
		
		if(attributeUsed.size()<=attributeNum)
		{
			attributeUsed.add(false);
			attributeSize.add(0);
			attribs++;
			attributeEnable.add(true);
		}
		
		if(attributeUsed.get(attributeNum)==false)
		{
			vboID = GL15.glGenBuffers();
			vbos.add(vboID);
		}
		else
		{
			vboID = vbos.get(attributeNum);
		}
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		
		size=data.size()/amountData;
		float[] nData = toFloatArray(data);
		
		if(size <= attributeSize.get(attributeNum) && attributeUsed.get(attributeNum)==true )
		{
			//No need to reallocate, just change the values in the model and only draw a few of the verts
			GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, nData );
		}
		else
		{
			//Need to Reallocate memory so that the size can change.
			
			FloatBuffer vertBuff = MemoryUtil.memAllocFloat(data.size());
			
			vertBuff.put( nData ).flip();
			
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertBuff, modelType);
			
			vertBuff.clear();
			MemoryUtil.memFree(vertBuff);
			
		}
		
		attributeUsed.set(attributeNum, true);
		attributeEnable.set(attributeNum, true);
		attributeSize.set(attributeNum, size);
		
		
		GL20.glVertexAttribPointer(attributeNum, amountData, GL11.GL_FLOAT, false, 0, 0 );
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		//Add the data to attributeData list so that it can be accessed later
		attributeData.add( data );
		unBind();
	}
	
	/**
	 * Returns the id of the vbo at a particular position.
	 * This implementation of attributes separates them by vbos. 
	 * @param num
	 * @return int
	 */
	public int getVbo(int num)
	{
		try
		{
			return vbos.get(num);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * Returns the id of the VAO. A VAO stores all of the VBOs used
	 * for this model.
	 * @return int
	 */
	public int getVao()
	{
		return vaoID;
	}
	
	/**
	 * Returns the amount of vertices this model has. Not the amount
	 * of attributes or the total amount of values for this model.
	 * @return int
	 */
	public int getVertexCount()
	{
		return size;
	}
	
	/**
	 * Returns the size of a particular attribute.
	 * -1 is returned if the attribute does not exist.
	 * @param attributeNumber
	 * @return int
	 */
	public int getAttributeSize(int attributeNumber)
	{
		if(attributeNumber >=0 && attributeNumber < attributeSize.size())
			return attributeSize.get(attributeNumber);
		else
		{
			System.err.println("That attribute number does not exist");
			return -1;
		}
	}
	
	/**
	 * Binds the model for drawing or editing. Particularly binds the vao.
	 */
	public void bind()
	{
		GL30.glBindVertexArray(vaoID);
	}
	
	/**
	 * Unbinds the model for drawing or editing. Particularly unbinds the vao.
	 */
	public void unBind()
	{
		GL30.glBindVertexArray(0);
	}
	
	/**
	 * Clears all of the memory that the VBOs used and deletes them.
	 */
	public void destroyBuffers()
	{
		for(int i=0;i<vbos.size();i++)
		{
			GL15.glDeleteBuffers( vbos.get(i) );
		}
		vertAmount.clear();
		attributeUsed.clear();
		attributeEnable.clear();
		attributeSize.clear();
		attributeData.clear();
		indicies.clear();
	}
	
	/**
	 * Clears all of the memory that this model uses.
	 */
	public void destroyModel()
	{
		attribs=0;
		size=0;
		
		destroyBuffers();
		
		GL30.glDeleteVertexArrays(vaoID);
	}

}
