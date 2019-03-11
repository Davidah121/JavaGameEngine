package openGLEngine;

public class Triangle extends collisionHull {

	private double x1 = 0;
	private double y1 = 0;
	private double x2 = 0;
	private double y2 = 0;
	private double x3 = 0;
	private double y3 = 0;
	
	private double baseX1 = 0;
	private double baseY1 = 0;
	private double baseX2 = 0;
	private double baseY2 = 0;
	private double baseX3 = 0;
	private double baseY3 = 0;
	
	/**
	 * Creates a 2D Triangle to be used for collision.
	 * @return void
	 */
	public Triangle(double x1, double y1, double x2, double y2, double x3, double y3)
	{
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.x3 = x3;
		this.y3 = y3;
		
		baseX1 = x1;
		baseY1 = y1;
		baseX2 = x2;
		baseY2 = y2;
		baseX3 = x3;
		baseY3 = y3;
	}
	
	public Triangle(Triangle other)
	{
		super(other);
		x1 = other.x1;
		y1 = other.y1;
		x2 = other.x2;
		y2 = other.y2;
		x3 = other.x3;
		y3 = other.y3;
		
		baseX1 = x1;
		baseY1 = y1;
		baseX2 = x2;
		baseY2 = y2;
		baseX3 = x3;
		baseY3 = y3;
	}
	/**
	 * An update function that can be called to change anything about the Triangle.
	 * This is an empty function inherited from the Parent Class collisionHull.
	 * Can be Overridden in subclasses.
	 * @return void
	 */
	@Override
	public void updateCollisionHull() {
		// TODO Auto-generated method stub
		
		//Unlike other classes, this involves all three methods.
		//Instead of making three methods, and attempting to work around the math,
		//do the stuff in this method.
		
		Mat4f tempMat = GameMath.createModelMatrix(positionVec.x, positionVec.y, positionVec.z,
												rotationVec.x, rotationVec.y, rotationVec.z,
												scaleVec.x, scaleVec.y, scaleVec.z);
		
		Vec4f tempVec = GameMath.matrixMultVec(tempMat, new Vec4f(baseX1, baseY1, 0, 1));
		x1 = tempVec.x;
		y1 = tempVec.y;
		
		tempVec = GameMath.matrixMultVec(tempMat, new Vec4f(baseX2, baseY2, 0, 1));
		x2 = tempVec.x;
		y2 = tempVec.y;
		
		tempVec = GameMath.matrixMultVec(tempMat, new Vec4f(baseX3, baseY3, 0, 1));
		x3 = tempVec.x;
		y3 = tempVec.y;
	}

	/**
	 * Draws a representation of this Triangle at its x, y position.
	 * Can be Overridden in subclasses.
	 * @return void
	 */
	@Override
	public void drawCollisionHull() {
		GameRender.drawTriangle( (float) getX1(), (float) getY1(),
								(float) getX2(), (float) getY2(),
								(float) getX3(), (float) getY3(), true );
	}

	
	/**
	 * Returns the first x value of this triangle.
	 * @return double
	 */
	public double getX1()
	{
		return x1;
	}
	
	/**
	 * Returns the second x value of this triangle.
	 * @return double
	 */
	public double getX2()
	{
		return x2;
	}
	
	/**
	 * Returns the third x value of this triangle.
	 * @return double
	 */
	public double getX3()
	{
		return x3;
	}
	
	/**
	 * Returns the first y value of this triangle.
	 * @return double
	 */
	public double getY1()
	{
		return y1;
	}
	
	/**
	 * Returns the second y value of this triangle.
	 * @return double
	 */
	public double getY2()
	{
		return y2;
	}
	
	/**
	 * Returns the third y value of this triangle.
	 * @return double
	 */
	public double getY3()
	{
		return y3;
	}
	
	/**
	 * Sets the first x value of this triangle.
	 * @param value 
	 * 			the x value to set
	 */
	public void setX1(double value)
	{
		baseX1 = value;
	}

	/**
	 * Sets the first y value of this triangle.
	 * @param value 
	 * 			the y value to set
	 */
	public void setY1(double value)
	{
		baseY1 = value;
	}

	/**
	 * Sets the second x value of this triangle.
	 * @param value 
	 * 			the x value to set
	 */
	public void setX2(double value)
	{
		baseX2 = value;
	}

	/**
	 * Sets the second y value of this triangle.
	 * @param value 
	 * 			the y value to set
	 */
	public void setY2(double value) 
	{
		baseY2 = value;
	}

	/**
	 * Sets the third x value of this triangle.
	 * @param value 
	 * 			the x value to set
	 */
	public void setX3(double value)
	{
		baseX3 = value;
	}

	/**
	 * Sets the third y value of this triangle.
	 * @param value 
	 * 			the y value to set
	 */
	public void setY3(double value)
	{
		baseY3 = value;
	}

	public double getBaseX1()
	{
		return baseX1;
	}
	public double getBaseY1()
	{
		return baseY1;
	}
	
	public double getBaseX2()
	{
		return baseX2;
	}
	public double getBaseY2()
	{
		return baseY2;
	}
	
	public double getBaseX3()
	{
		return baseX3;
	}
	public double getBaseY3()
	{
		return baseY3;
	}
}
