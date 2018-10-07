package openGLEngine;

public abstract class collisionHull {

	/**
	 * This class is an abstract class used to define a
	 * shape for collision. It consist of 2 methods.
	 * public abstract void updateCollisionHull();
	 * public abstract void drawCollisionHull();
	 */
	protected Vec3f rotationVec = new Vec3f(0,0,0);
	protected Vec3f scaleVec = new Vec3f(1,1,1);
	protected Vec3f positionVec = new Vec3f(0,0,0);
	
	public abstract void updateCollisionHull();
	public abstract void drawCollisionHull();
	
	/**
	 * A method that should be overridden in subclasses. This method
	 * does not contain anything
	 * @param rotationVec
	 */
	public void rotate(){};
	
	/**
	 * A method that should be overridden in subclasses. This method
	 * does not contain anything
	 * @param scaleVec
	 */
	public void scale(){};
	
	/**
	 * A method that should be overridden in subclasses. This method
	 * does not contain anything.
	 * @param positionVec
	 */
	public void translate(){};
	
	/**
	 * returns a vector representing the rotation of this collision hull.
	 * The x value is the x rotation.
	 * The y value is the y rotation.
	 * The z value is the z rotation.
	 * @return
	 */
	public Vec3f getRotationVector()
	{
		return rotationVec;
	}
	
	/**
	 * returns a vector representing the position of this collision hull relative 
	 * to the global origin. Otherwise known as world space.
	 * The x value is the x position.
	 * The y value is the y position.
	 * The z value is the z position.
	 * @return
	 */
	public Vec3f getPositionVector()
	{
		return positionVec;
	}
	
	/**
	 * returns a vector representing the scale of this collision hull.
	 * The x value is the x scale.
	 * The y value is the y scale.
	 * The z value is the z scale.
	 * @return
	 */
	public Vec3f getScaleVector()
	{
		return scaleVec;
	}
	
	/**
	 * sets the value of the rotation vector.
	 * @param value
	 */
	public void setRotationVector(Vec3f value)
	{
		rotationVec.x=value.x;
		rotationVec.y=value.y;
		rotationVec.z=value.z;
	}
	
	/**
	 * sets the value of the position vector.
	 * @param value
	 */
	public void setPositionVector(Vec3f value)
	{
		positionVec.x=value.x;
		positionVec.y=value.y;
		positionVec.z=value.z;
	}
	
	/**
	 * sets the value of the scale vector.
	 * @param value
	 */
	public void setScaleVector(Vec3f value)
	{
		scaleVec.x=value.x;
		scaleVec.y=value.y;
		scaleVec.z=value.z;
	}
}
