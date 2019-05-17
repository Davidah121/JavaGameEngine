package openGLEngine;

import java.io.Serializable;

public class Camera implements Serializable{

	private Vec3f position = new Vec3f(0,0,0);
	
	private Vec3f lookVec = new Vec3f(1,0,0);
	private Vec3f leftVec = new Vec3f(0,1,0);
	
	private Vec3f rotation = new Vec3f(0,0,0);
	
	private double zoomValue = 0;
	
	private boolean mode = false;
	
	public static final boolean MODE_2D = false;
	public static final boolean MODE_3D = true;
	
	private Mat4f viewMat = GameMath.createViewMatrix(0, 0, 0, 0, 0, 0, mode);
	private Mat4f scaleMat = Mat4f.getIdentityMatrix();
	
	/**
	 * Creates a Camera to be used by the game. 
	 * The camera needs a mode.
	 * The mode is either MODE_2D or MODE_3D.
	 * Each are constants of the class Camera.
	 * @param mode
	 */
	public Camera(boolean mode)
	{
		this.mode = mode;
		viewMat = GameMath.createViewMatrix(0, 0, 0, 0, 0, 0, mode);
	}
	
	/**
	 * Creates a Camera to be used by the game at the specified position
	 * and rotation
	 * The camera needs a mode.
	 * The mode is either MODE_2D or MODE_3D.
	 * Each are constants of the class Camera.
	 * @param position
	 * @param rotation
	 * @param mode
	 */
	public Camera(Vec3f position, Vec3f rotation, boolean mode)
	{
		this.position = position;
		this.rotation = rotation;
		this.mode = mode;
		
		viewMat = GameMath.createViewMatrix(position.x, position.y, position.z, 
					rotation.x, rotation.y, rotation.z, mode);
		
	}
	
	/**
	 * Returns the cameras View Matrix to be used in shaders.
	 * @return
	 */
	public Mat4f getViewMat()
	{
		return viewMat;
	}
	
	/**
	 * Returns the cameras View Matrix combined with the Projection Matrix
	 * to be used in shaders.
	 * @return
	 */
	public Mat4f getViewProjectionMat()
	{
		Mat4f temp = GameMath.matrixMult(scaleMat, viewMat );
		
		return GameMath.matrixMult(temp, Game.get3DProjectionMatrix() );
	}
	
	/**
	 * Sets the mode of the camera.
	 * The Mode is either MODE_2D or MODE_3D.
	 * Each are constants of the class Camera.
	 * @param value
	 */
	public void setMode(boolean value)
	{
		mode = value;
	}
	
	/**
	 * Sets the position of the camera.
	 * Updates the view matrix.
	 * @param value
	 */
	public void setPosition(Vec3f value)
	{
		position=value;
		
		viewMat = GameMath.createViewMatrix(position.x, position.y, position.z, 
					rotation.x, rotation.y, rotation.z, mode);
	}
	
	/**
	 * Gets the position of the camera.
	 * @return
	 */
	public Vec3f getPosition()
	{
		return position;
	}
	
	/**
	 * Gets the rotation of the camera.
	 * @return
	 */
	public Vec3f getRotation()
	{
		return rotation;
	}
	
	/**
	 * Gets a vector pointing left of the direction
	 * the camera is facing.
	 * @return
	 */
	public Vec3f getLeftVec()
	{
		return leftVec;
	}
	
	/**
	 * Gets a vector representing the direction the
	 * camera is facing.
	 * @return
	 */
	public Vec3f getLookVec()
	{
		return lookVec;
	}
	
	/**
	 * Sets the position of the camera.
	 * Updates the view matrix.
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setPosition(double x, double y, double z)
	{
		position.x=(float)x;
		position.y=(float)y;
		position.z=(float)z;
		
		viewMat = GameMath.createViewMatrix(position.x, position.y, position.z, 
					rotation.x, rotation.y, rotation.z, mode);
		
	}
	
	/**
	 * Sets the rotation of the camera.
	 * Updates the view matrix.
	 * @param value
	 */
	public void setRotation(Vec3f value)
	{
		rotation=value;
		
		double zto2 = GameMath.dcos(rotation.y);
		
		lookVec.x = GameMath.dcos(rotation.z)*zto2;
		lookVec.y = GameMath.dsin(rotation.z)*zto2;
		lookVec.z = GameMath.dsin(rotation.y);
		
		
		leftVec.x = GameMath.dcos(rotation.z+90)*zto2;
		leftVec.y = GameMath.dsin(rotation.z+90)*zto2;
		leftVec.z = GameMath.dsin(rotation.y);
		
		
		viewMat = GameMath.createViewMatrix(position.x, position.y, position.z, 
					rotation.x, rotation.y, rotation.z, mode);
		
	}
	
	/**
	 * Sets the rotation of the camera.
	 * Updates the view matrix.
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setRotation(double x, double y, double z)
	{
		rotation.x=(float)x;
		rotation.y=(float)y;
		rotation.z=(float)z;
		
		double zto2 = GameMath.dcos(rotation.y);
		
		lookVec.x = GameMath.dcos(rotation.z)*zto2;
		lookVec.y = GameMath.dsin(rotation.z)*zto2;
		lookVec.z = GameMath.dsin(rotation.y);
		
		
		leftVec.x = GameMath.dcos(rotation.z+90)*zto2;
		leftVec.y = GameMath.dsin(rotation.z+90)*zto2;
		leftVec.z = GameMath.dsin(rotation.y);
		
		viewMat = GameMath.createViewMatrix(position.x, position.y, position.z, 
					rotation.x, rotation.y, rotation.z, mode);
		
	}
	
	/**
	 * Sets a value that can be used to create a zoom in effect for the camera.
	 * Essentially scales the projection
	 * Work on later
	 * @param v
	 */
	public void setZoom(double v)
	{
		zoomValue = v;
		
		scaleMat = new Mat4f(v, 0, 0, 0,
							 0, v, 0, 0,
							 0, 0, v, 0,
							 0, 0, 0, 1);
	}
}
