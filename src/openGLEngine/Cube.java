package openGLEngine;

public class Cube extends collisionHull {

	private double x1 = 0;
	private double x2 = 0;
	private double y1 = 0;
	private double y2 = 0;
	private double z1 = 0;
	private double z2 = 0;
	
	private double baseX1 = 0;
	private double baseX2 = 0;
	private double baseY1 = 0;
	private double baseY2 = 0;
	private double baseZ1 = 0;
	private double baseZ2 = 0;
	
	/**
	 * Creates a 3D Axis-Aligned Bounding Box to be used for collision.
	 * @return void
	 */
	public Cube(double x1, double y1, double z1, double x2, double y2, double z2)
	{
		this.x1=x1;
		this.x2=x2;
		this.y1=y1;
		this.y2=y2;
		this.z1=z1;
		this.z2=z2;
		
		//Set some base values for translating and scaling
		baseX1=x1;
		baseY1=y1;
		baseZ1=z1;
		
		baseX2=x2;
		baseY2=y2;
		baseZ2=z2;
	}
	
	public Cube(Cube o)
	{
		super(o);
		this.x1=o.x1;
		this.x2=o.x2;
		this.y1=o.y1;
		this.y2=o.y2;
		this.z1=o.z1;
		this.z2=o.z2;
		
		//Set some base values for translating and scaling
		baseX1=x1;
		baseY1=y1;
		baseZ1=z1;
		
		baseX2=x2;
		baseY2=y2;
		baseZ2=z2;
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
		GameRender.drawBox((float)getLeftBound(), (float)getBackBound(), (float)getBottomBound(),
							(float)getRightBound(), (float)getFrontBound(), (float)getTopBound(), true);
	}
	
	/**
	 * Translate the collision hull. Does not affect the base position values.
	 */
	@Override
	public void translate()
	{
		x1+=positionVec.x;
		y1+=positionVec.y;
		z1+=positionVec.z;
		x2+=positionVec.x;
		y2+=positionVec.y;
		z2+=positionVec.z;
	}
	
	/**
	 * Scale the collision hull. Does not affect the base position values.
	 */
	@Override
	public void scale()
	{
		x1=baseX1*scaleVec.x;
		y1=baseY1*scaleVec.y;
		z1=baseZ1*scaleVec.z;
		x2=baseX2*scaleVec.x;
		y2=baseY2*scaleVec.y;
		z2=baseZ2*scaleVec.z;
	}
	
	/**
	 * Returns the bottom most part of the Axis-Aligned Bounding Box.
	 * (The least z value)
	 * @return double
	 */
	public double getBottomBound()
	{
		return z1;
	}
	
	/**
	 * Returns the top most part of the Axis-Aligned Bounding Box.
	 * (The greatest z value)
	 * @return double
	 */
	public double getTopBound()
	{
		return z2;
	}
	
	/**
	 * Returns the left most part of the Axis-Aligned Bounding Box.
	 * (The least x value)
	 * @return double
	 */
	public double getLeftBound()
	{
		return x1;
	}
	
	/**
	 * Returns the right most part of the Axis-Aligned Bounding Box.
	 * (The greatest x value)
	 * @return double
	 */
	public double getRightBound()
	{
		return x2;
	}
	
	/**
	 * Returns the back most part of the Axis-Aligned Bounding Box.
	 * (The least y value)
	 * @return double
	 */
	public double getBackBound()
	{
		return y1;
	}
	
	/**
	 * Returns the front most part of the Axis-Aligned Bounding Box.
	 * (The greatest y value)
	 * @return double
	 */
	public double getFrontBound()
	{
		return y2;
	}
	
	/**
	 * Sets the bottom most part of the Axis-Aligned Bounding Box.
	 * Affects the value before translating and scaling are applied.
	 * (The least z value)
	 * @param value
	 * @return void
	 */
	public void setBottomBound(int value)
	{
		baseZ1=value;
	}
	
	/**
	 * Sets the top most part of the Axis-Aligned Bounding Box.
	 * Affects the value before translating and scaling are applied.
	 * (The greatest z value)
	 * @param value
	 * @return void
	 */
	public void setTopBound(int value)
	{
		baseZ2=value;
	}
	
	/**
	 * Sets the left most part of the Axis-Aligned Bounding Box.
	 * Affects the value before translating and scaling are applied.
	 * (The least x value)
	 * @param value
	 * @return void
	 */
	public void setLeftBound(int value)
	{
		baseX1=value;
	}
	
	/**
	 * Sets the right most part of the Axis-Aligned Bounding Box.
	 * Affects the value before translating and scaling are applied.
	 * (The greatest x value)
	 * @param value
	 * @return void
	 */
	public void setRightBound(int value)
	{
		baseX2=value;
	}
	
	/**
	 * Sets the back most part of the Axis-Aligned Bounding Box.
	 * Affects the value before translating and scaling are applied.
	 * (The least y value)
	 * @param value
	 * @return void
	 */
	public void setBackBound(int value)
	{
		baseY1=value;
	}
	
	/**
	 * Sets the front most part of the Axis-Aligned Bounding Box.
	 * Affects the value before translating and scaling are applied.
	 * (The greatest y value)
	 * @param value
	 * @return void
	 */
	public void setFrontBound(int value)
	{
		baseY2=value;
	}
	
	public double getBaseX1()
	{
		return baseX1;
	}
	public double getBaseY1()
	{
		return baseY1;
	}
	public double getBaseZ1()
	{
		return baseZ1;
	}
	public double getBaseX2()
	{
		return baseX2;
	}
	public double getBaseY2()
	{
		return baseY2;
	}
	public double getBaseZ2()
	{
		return baseZ2;
	}
}
