package openGLEngine;

import java.io.File;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class Shader {

	private int programID;
	private int vertexID;
	private int fragmentID;
	
	private boolean bound = false;
	
	private String vertexShaderFile;
	private String fragmentShaderFile;
	
	private ArrayList<String> uniformString = new ArrayList<String>();
	private ArrayList<Integer> uniformLocation = new ArrayList<Integer>();
		
	/**
	 * Creates a Shader to be used for rendering. A Shader is code that will
	 * be ran on the GPU. It is split into parts. A Shader requires a Vertex
	 * Shader and a Fragment Shader. Other parts can be added such as a
	 * Geometry Shader. In order to use additional parts, it would be best to
	 * make a subclass of this.
	 * Must be disposed of before the program ends.
	 * @param vertexFile
	 * @param fragmentFile
	 */
	public Shader(String vertexFile, String fragmentFile)
	{
		vertexShaderFile = vertexFile;
		fragmentShaderFile = fragmentFile;
		
		vertexID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		fragmentID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		programID = GL20.glCreateProgram();
		
		GL20.glAttachShader(programID, vertexID);
		GL20.glAttachShader(programID, fragmentID);
		
		bindAttributes();
		
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
	}
	
	public void reloadShader()
	{
		end();
		
		int tempVertexID = loadShader(vertexShaderFile, GL20.GL_VERTEX_SHADER);
		int tempFragmentID = loadShader(fragmentShaderFile, GL20.GL_FRAGMENT_SHADER);
		
		if(tempVertexID!=0 && tempFragmentID!=0)
		{
			dispose();
			programID = GL20.glCreateProgram();
			
			GL20.glAttachShader(programID, vertexID);
			GL20.glAttachShader(programID, fragmentID);
			
			bindAttributes();
			
			GL20.glLinkProgram(programID);
			GL20.glValidateProgram(programID);
		}
		else
		{
			System.err.println("Could not re-compile this shader. No changes have been made");
		}
		
		if(tempVertexID!=0)
			GL20.glDeleteShader(tempVertexID);
		
		if(tempFragmentID!=0)
			GL20.glDeleteShader(tempFragmentID);
	}
	/**
	 * Bind the attributes of a shader by name all at once
	 * This is not needed in most cases, but is there if you desire.
	 * Should be Overridden in any subclass. This method is called
	 * when the Shader is first created.
	 * @return void
	 */
	public void bindAttributes()
	{
		bindAttribute(0, "in_position");
		bindAttribute(1, "in_texCoords");
		bindAttribute(2, "in_normals");
	}
	
	/**
	 * Bind the attributes of a shader by name.
	 * This is not needed in most cases, but is there if you desire.
	 * @param attribute
	 * @param variableName
	 * Can not be Overridden.
	 */
	protected void bindAttribute(int attribute, String variableName)
	{
		GL20.glBindAttribLocation(programID, attribute, variableName);
	}
	
	/**
	 * Returns whether this Shader is currently bound/ whether the shader is
	 * currently being used.
	 * @return boolean
	 */
	public boolean getBound()
	{
		return bound;
	}
	
	/**
	 * Returns the Program ID for the Shader.
	 * @return int
	 * Can not be Overridden.
	 */
	protected int getProgram()
	{
		return programID;
	}
	
	protected String getVertexShaderFile()
	{
		return vertexShaderFile;
	}
	
	protected String getFragmentShaderFile()
	{
		return fragmentShaderFile;
	}
	
	/**
	 * Binds the shader to be used for future rendering.
	 * @return void
	 * Can not be Overridden.
	 */
	public void start()
	{
		if(DefaultResources.defaultShader.getBound()==true)
		{
			DefaultResources.defaultShader.end();
		}
		GL20.glUseProgram(programID);
		Game.currentShader=this;
		bound=true;
	}
	
	/**
	 * Sets the Projection Matrix Uniform Variable in the Shader.
	 * The Projection Matrix uniform name by default is set to - projectionMatrix
	 * This method should be called if the Projection Matrix or View Matrix has
	 * changed. This method just calls the setUniform method.
	 * @param Matrix
	 * @param transpose
	 * @return void
	 * Can be Overridden.
	 */
	public void setProjectionMatrix(Mat4f matrix, boolean transpose)
	{
		setUniform("projectionMatrix", transpose, matrix);
	}
	
	/**
	 * Sets a Uniform Variable in the Shader if it exists.
	 * @param uniform
	 * @param values
	 * @return void
	 * Can not be Overridden.
	 */
	public void setUniform(String uniform, int[] values)
	{
		if(uniformString.indexOf(uniform)==-1)
		{
			int location = GL20.glGetUniformLocation(programID, uniform);
			if(location!=-1)
			{
				uniformString.add(uniform);
				uniformLocation.add(location);
				
				if(values.length==1)
					GL20.glUniform1iv(location, values);
				else if(values.length==2)
					GL20.glUniform2iv(location, values);
				else if(values.length==3)
					GL20.glUniform3iv(location, values);
				else if(values.length==4)
					GL20.glUniform4iv(location, values);
			}
		}
		else
		{
			int location = uniformLocation.get( uniformString.indexOf(uniform) );
			
			if(values.length==1)
				GL20.glUniform1iv(location, values);
			else if(values.length==2)
				GL20.glUniform2iv(location, values);
			else if(values.length==3)
				GL20.glUniform3iv(location, values);
			else if(values.length==4)
				GL20.glUniform4iv(location, values);
		}
	}
	
	/**
	 * Sets a Uniform Variable in the Shader if it exists.
	 * @param uniform
	 * @param values
	 * @return void
	 * Can not be Overridden.
	 */
	public void setUniform(String uniform, float[] values)
	{
		if(uniformString.indexOf(uniform)==-1)
		{
			int location = GL20.glGetUniformLocation(programID, uniform);
			if(location!=-1)
			{
				uniformString.add(uniform);
				uniformLocation.add(location);

				if(values.length==1)
					GL20.glUniform1fv(location, values);
				else if(values.length==2)
					GL20.glUniform2fv(location, values);
				else if(values.length==3)
					GL20.glUniform3fv(location, values);
				else if(values.length==4)
					GL20.glUniform4fv(location, values);
			}
		}
		else
		{
			int location = uniformLocation.get( uniformString.indexOf(uniform) );

			if(values.length==1)
				GL20.glUniform1fv(location, values);
			else if(values.length==2)
				GL20.glUniform2fv(location, values);
			else if(values.length==3)
				GL20.glUniform3fv(location, values);
			else if(values.length==4)
				GL20.glUniform4fv(location, values);
		}
	}
	
	/**
	 * Sets a Uniform Variable in the Shader if it exists.
	 * @param uniform
	 * @param transpose
	 * @param mat
	 * @return void
	 * Can not be Overridden.
	 */
	public void setUniform(String uniform, boolean transpose, Mat2f mat)
	{
		if(uniformString.indexOf(uniform)==-1)
		{
			int location = GL20.glGetUniformLocation(programID, uniform);
			if(location!=-1)
			{
				uniformString.add(uniform);
				uniformLocation.add(location);

				GL20.glUniformMatrix2fv(location, transpose, mat.getRawArrayFloat());
			}
		}
		else
		{
			int location = uniformLocation.get( uniformString.indexOf(uniform) );

			GL20.glUniformMatrix2fv(location, transpose, mat.getRawArrayFloat());
		}
	}
	
	/**
	 * Sets a Uniform Variable in the Shader if it exists.
	 * @param uniform
	 * @param transpose
	 * @param mat
	 * @return void
	 * Can not be Overridden.
	 */
	public void setUniform(String uniform, boolean transpose, Mat3f mat)
	{
		if(uniformString.indexOf(uniform)==-1)
		{
			int location = GL20.glGetUniformLocation(programID, uniform);
			if(location!=-1)
			{
				uniformString.add(uniform);
				uniformLocation.add(location);

				GL20.glUniformMatrix3fv(location, transpose, mat.getRawArrayFloat());
			}
		}
		else
		{
			int location = uniformLocation.get( uniformString.indexOf(uniform) );

			GL20.glUniformMatrix3fv(location, transpose, mat.getRawArrayFloat());
		}
	}
	
	/**
	 * Sets a Uniform Variable in the Shader if it exists.
	 * @param uniform
	 * @param transpose
	 * @param mat
	 * @return void
	 * Can not be Overridden.
	 */
	public void setUniform(String uniform, boolean transpose, Mat4f mat)
	{
		if(uniformString.indexOf(uniform)==-1)
		{
			int location = GL20.glGetUniformLocation(programID, uniform);
			if(location!=-1)
			{
				uniformString.add(uniform);
				uniformLocation.add(location);

				GL20.glUniformMatrix4fv(location, transpose, mat.getRawArrayFloat());
			}
		}
		else
		{
			int location = uniformLocation.get( uniformString.indexOf(uniform) );

			GL20.glUniformMatrix4fv(location, transpose, mat.getRawArrayFloat());
		}
	}
	
	/**
	 * Unbinds the Shader and uses the Default Shader to render.
	 * @return void
	 * Can not be Overridden.
	 */
	public void end()
	{
		GL20.glUseProgram(0);
		if(!this.toString().equals( DefaultResources.defaultShader.toString()))
			DefaultResources.defaultShader.start();
	}
	
	/**
	 * Disposes the Shader and cleans all of the memory it used.
	 * Must be called before the program ends.
	 * @return void
	 * Can be Overridden.
	 */
	public void dispose()
	{
		if(bound==true)
			end();
		
		uniformLocation.clear();
		uniformString.clear();
		
		GL20.glDetachShader(programID,vertexID);
		GL20.glDetachShader(programID,fragmentID);
		
		GL20.glDeleteShader(vertexID);
		GL20.glDeleteShader(fragmentID);
		GL20.glDeleteProgram(programID);
	}
	
	/**
	 * Finds the location of a Uniform Variable in the Shader by the variable Name
	 * @return int
	 * Can not be Overridden.
	 */
	public int getUniform(String variableName)
	{
		return GL20.glGetUniformLocation(programID, variableName);
	}
	
	/**
	 * Loads a file that contains the code for a Shader.
	 * type refers to whether this will be a Vertex, Fragment, or different
	 * Shader.
	 * @param fileName
	 * @param type
	 * @return int
	 * Can not be Overridden.
	 */
	protected int loadShader(String filename, int type)
	{
		StringBuilder code = new StringBuilder();
		
		quickIO file = new quickIO(filename,quickIO.TYPE_READ);
		String text=file.readNextLn();
		code.append(text).append("\n");
		
		while(!file.endOfFile())
		{
			text=file.readNextLn();
			code.append(text).append("\n");
		}
		file.close();
		
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID,code);
		GL20.glCompileShader(shaderID);
		
		if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS)==GL11.GL_FALSE)
		{
			System.out.println(GL20.glGetShaderInfoLog(shaderID));
			System.out.println("ERROR: SHADER DID NOT COMPILE");
			System.out.println(filename);
			return -1;
			//System.exit(-1);
		}
		
		
		return shaderID;
	}
}
