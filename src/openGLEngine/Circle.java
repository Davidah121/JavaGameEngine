package openGLEngine;

public class Circle extends collisionHull {

	private double radius = 0;
	
	private double x=0;
	private double y=0;
	
	private double baseX=0;
	private double baseY=0;
	
	/**
	 * Creates a Circle to be used for collision.
	 * @return void
	 */
	public Circle(double x, double y, double radius)
	{
		this.x=x;
		this.y=y;
		baseX=x;
		baseY=y;
		
		this.radius = radius;
	}
	
	public Circle(Circle other)
	{
		super(other);
		this.x=other.x;
		this.y=other.y;
		baseX=x;
		baseY=y;
		
		this.radius = other.radius;
	}
	
	/**
	 * An update function that can be called to change anything about the Circle.
	 * This is an empty function inherited from the Parent Class collisionHull.
	 * Can be Overridden in subclasses.
	 * @return void
	 */
	@Override
	public void updateCollisionHull() {
		// TODO Auto-generated method stub
		
		translate();
	}

	public void translate()
	{
		x = baseX+positionVec.x;
		y = baseY+positionVec.y;
	}
	/**
	 * Draws a representation of this circle at its x, y position.
	 * Can be Overridden in subclasses.
	 * @return void
	 */
	@Override
	public void drawCollisionHull() {
		GameRender.drawCircle((int)x, (int)y, (float)radius, true);
	}

	/**
	 * returns the x value of this circle
	 * @return
	 */
	public double getX()
	{
		return x;
	}
	
	/**
	 * returns the y value of this circle
	 * @return
	 */
	public double getY()
	{
		return y;
	}
	
	/**
	 * sets the x value of this circle
	 * @return
	 */
	public void setX(double value)
	{
		baseX = value;
	}
	
	/**
	 * sets the y value of this circle
	 * @return
	 */
	public void setY(double value)
	{
		baseY = value;
	}
	/**
	 * Returns radius of this circle.
	 * @return double
	 */
	public double getRadius()
	{
		return radius;
	}
	
	/**
	 * Sets the radius of this circle.
	 * @param value
	 * @return void
	 */
	public void setRadius(double value)
	{
		radius=value;
	}
}
