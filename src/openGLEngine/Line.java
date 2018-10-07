package openGLEngine;

public class Line extends collisionHull {

	private double x1 = 0;
	private double y1 = 0;
	private double x2 = 0;
	private double y2 = 0;
	
	private Vec2f v = new Vec2f(0,0);
	
	private double minX = 0;
	private double minY = 0;
	private double maxX = 0;
	private double maxY = 0;
	
	private double baseX1 = 0;
	private double baseY1 = 0;
	private double baseX2 = 0;
	private double baseY2 = 0;
	
	private double slope = 0;
	private double yInt = 0;
	
	private boolean isVertical = false;
	
	/**
	 * Creates a Line to be used for collision.
	 * @return void
	 */
	public Line(double x1, double y1, double x2, double y2)
	{
		this.x1=x1;
		this.x2=x2;
		this.y1=y1;
		this.y2=y2;
		
		baseX1=x1;
		baseY1=y1;
		baseX2=x2;
		baseY2=y2;
		
		updateCollisionHull();
	}
	
	/**
	 * An update function that can be called to change anything about the Line.
	 * This is an empty function inherited from the Parent Class collisionHull.
	 * Can be Overridden in subclasses.
	 * @return void
	 */
	@Override
	public void updateCollisionHull() {
		// TODO Auto-generated method stub
		
		rotate();
		translate();
		
		setMinMax();
		setVertical();
		setSlope();
		setYInt();
		
		setVector();
	}
	
	public void rotate()
	{
		
	}
	
	public void translate()
	{
		x1 = baseX1+positionVec.x;
		y1 = baseY1+positionVec.y;
		x2 = baseX2+positionVec.x;
		y2 = baseY2+positionVec.y;
	}

	/**
	 * Draws a representation of this Line at its x, y position.
	 * Can be Overridden in subclasses.
	 * @return void
	 */
	@Override
	public void drawCollisionHull() {
		GameRender.drawLine((float)x1, (float)y1, (float)x2, (float)y2);
	}

	/**
	 * Returns the start x position of this line.
	 * @return double
	 */
	public double getStartX()
	{
		return x1;
	}
	
	/**
	 * Returns the start y position of this line.
	 * @return double
	 */
	public double getStartY()
	{
		return y1;
	}
	
	/**
	 * Returns the end x position of this line.
	 * @return double
	 */
	public double getEndX()
	{
		return x2;
	}
	
	/**
	 * Returns the end y position of this line.
	 * @return double
	 */
	public double getEndY()
	{
		return y2;
	}
	
	/**
	 * Gets the minimum between the start x and end x.
	 * @return
	 */
	public double getMinX()
	{
		return minX;
	}
	
	/**
	 * Gets the maximum between the start x and end x.
	 * @return
	 */
	public double getMaxX()
	{
		return maxX;
	}
	
	/**
	 * Gets the minimum between the start y and end y.
	 * @return
	 */
	public double getMinY()
	{
		return minY;
	}
	
	/**
	 * Gets the maximum between the start y and end y.
	 * @return
	 */
	public double getMaxY()
	{
		return maxY;
	}
	
	/**
	 * sets the maximum and minimum x and y values.
	 */
	private void setMinMax()
	{
		minX = GameMath.min(x1,x2);
		minY = GameMath.min(y1,y2);
		maxX = GameMath.max(x1,x2);
		maxY = GameMath.max(y1,y2);
	}
	
	/**
	 * Sets the start x position of this line.
	 * @param value
	 * @return void
	 */
	public void setStartX(double value)
	{
		baseX1=value;
	}
	
	/**
	 * Sets the start y position of this line.
	 * @param value
	 * @return void
	 */
	public void setStartY(double value)
	{
		baseY1=value;
	}
	
	/**
	 * Sets the end x position of this line.
	 * @param value
	 * @return void
	 */
	public void setEndX(double value)
	{
		baseX2=value;
	}
	
	/**
	 * Sets the end y position of this line.
	 * @param value
	 * @return void
	 */
	public void setEndY(double value)
	{
		baseY2=value;
	}
	
	/**
	 * gets if this line is a vertical line.
	 * @return
	 */
	public boolean getVertical()
	{
		return isVertical;
	}
	
	/**
	 * gets the slope of this line.
	 * If this line is a vertical line, the slope will be set
	 * to zero.
	 * @return
	 */
	public double getSlope()
	{
		return slope;
	}
	
	/**
	 * Gets the Y Intercept of this line. The Y Intercept
	 * is not accurate if this line is a vertical line.
	 * @return
	 */
	public double getYInt()
	{
		return yInt;
	}
	
	/**
	 * Gets a vector representing this line.
	 * @return
	 */
	public Vec2f getVector()
	{
		return v;
	}
	
	/**
	 * Sets whether this line is vertical.
	 */
	private void setVertical()
	{
		if( x1 == x2 )
			isVertical=true;
		else
			isVertical=false;
	}
	
	/**
	 * Sets the slope of this line.
	 * Slope is zero if it is a vertical line
	 */
	private void setSlope()
	{
		if(isVertical)
		{
			slope=0;
		}
		else
		{
			double yV = y2-y1;
			double xV = x2-x1;
			
			slope = yV/xV;
		}
	}
	
	/**
	 * Sets the Y Intercept of this line.
	 */
	private void setYInt()
	{
		//y = mx + b
		//b = -( mx - y )
		
		yInt = -( (slope*x1) - y1);
	}
	
	/**
	 * Sets a vector representing this line.
	 */
	private void setVector()
	{
		v.x = x2-x1;
		v.y = y2-y1;
	}
}
