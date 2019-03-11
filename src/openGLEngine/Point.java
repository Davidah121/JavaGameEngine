package openGLEngine;

public class Point extends collisionHull {

	public double x=0;
	public double y=0;
	
	/**
	 * Creates a Point to be used for collision.
	 * @return void
	 */
	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Point(Point other)
	{
		super(other);
		this.x = other.x;
		this.y = other.y;
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
		
	}

	/**
	 * Draws a representation of this circle at its x, y position.
	 * Can be Overridden in subclasses.
	 * @return void
	 */
	@Override
	public void drawCollisionHull() {
		GameRender.drawPoint((int)x, (int)y);
	}

	/**
	 * Sets the position of this circle. Does not affect the collision
	 * algorithm. This just affects where it is drawn.
	 * @param x, y
	 * @return void
	 */
	public void setPosition(double x, double y)
	{
		this.x=x;
		this.y=y;
	}
}
