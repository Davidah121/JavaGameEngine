package openGLEngine;

public class Box extends collisionHull {

	private double y1 = 0;
	private double x1 = 0;
	private double y2 = 0;
	private double x2 = 0;
	
	private double baseY1 = 0;
	private double baseY2 = 0;
	private double baseX1 = 0;
	private double baseX2 = 0;
	
	/**
	 * Creates a Axis-Aligned Bounding Box to be used for collision.
	 * @return void
	 */
	public Box(double leftBound, double topBound, double rightBound, double bottomBound)
	{
		this.y1=topBound;
		this.x1=leftBound;
		this.y2=bottomBound;
		this.x2=rightBound;
		
		baseY1 = y1;
		baseY2 = y2;
		baseX1 = x1;
		baseX2 = x2;
	}
	
	public Box(Box other)
	{
		super(other);
		this.y1=other.y1;
		this.x1=other.x1;
		this.y2=other.y2;
		this.x2=other.x2;
		
		baseY1 = y1;
		baseY2 = y2;
		baseX1 = x1;
		baseX2 = x2;
	}
	
	/**
	 * An update function that can be called to change anything about the Axis-Aligned Box.
	 * This is an empty function inherited from the Parent Class collisionHull.
	 * Can be Overridden in subclasses.
	 * @return void
	 */
	@Override
	public void updateCollisionHull() {
		// TODO Auto-generated method stub
		scale();
		translate();
	}

	/**
	 * Draws a representation of this Axis-Aligned Bounding Box at its x, y position.
	 * Can be Overridden in subclasses.
	 * @return void
	 */
	@Override
	public void drawCollisionHull() {
		GameRender.drawRect((float)getLeftBound(), (float)getTopBound(), (float)getRightBound(), (float)getBottomBound(), true);
	}
	
	/**
	 * Translate the collision hull. Does not affect the base position values.
	 */
	@Override
	public void translate()
	{
		x1=baseX1+positionVec.x;
		y1=baseY1+positionVec.y;
		
		x2=baseX2+positionVec.x;
		y2=baseY2+positionVec.y;

	}
	
	/**
	 * Scale the collision hull. Does not affect the base position values.
	 */
	@Override
	public void scale()
	{
		x1=baseX1*scaleVec.x;
		y1=baseY1*scaleVec.y;

		x2=baseX2*scaleVec.x;
		y2=baseY2*scaleVec.y;
	}
	
	/**
	 * Returns the top most part of the Axis-Aligned Bounding Box.
	 * @return double
	 */
	public double getTopBound()
	{
		return y1;
	}
	
	/**
	 * Returns the left most part of the Axis-Aligned Bounding Box.
	 * @return double
	 */
	public double getLeftBound()
	{
		return x1;
	}
	
	/**
	 * Returns the bottom most part of the Axis-Aligned Bounding Box.
	 * @return double
	 */
	public double getBottomBound()
	{
		return y2;
	}
	
	/**
	 * Returns the right most part of the Axis-Aligned Bounding Box.
	 * @return double
	 */
	public double getRightBound()
	{
		return x2;
	}
	
	/**
	 * Sets the top most part of the Axis-Aligned Bounding Box.
	 * @param value
	 * @return void
	 */
	public void setTopBound(double value)
	{
		baseY1=value;
	}
	
	/**
	 * Sets the left most part of the Axis-Aligned Bounding Box.
	 * @param value
	 * @return void
	 */
	public void setLeftBound(double value)
	{
		baseX1=value;
	}
	
	/**
	 * Sets the bottom most part of the Axis-Aligned Bounding Box.
	 * @param value
	 * @return void
	 */
	public void setBottomBound(double value)
	{
		baseY2=value;
	}
	
	/**
	 * Sets the right most part of the Axis-Aligned Bounding Box.
	 * @param value
	 * @return void
	 */
	public void setRightBound(double value)
	{
		baseX2=value;
	}
	
	public double getCenterX()
	{
		return (x1+x2)/2;
	}
	
	public double getCenterY()
	{
		return (y1+y2)/2;
	}
	
	public double getMinX()
	{
		return GameMath.min(x1,x2);
	}
	
	public double getMaxX()
	{
		return GameMath.max(x1,x2);
	}
	
	public double getMinY()
	{
		return GameMath.min(y1,y2);
	}
	
	public double getMaxY()
	{
		return GameMath.max(y1,y2);
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
	
}
