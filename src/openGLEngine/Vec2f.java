package openGLEngine;
import java.awt.Point;
import java.lang.Math;

public class Vec2f {
	
	public double x;
	public double y;
	
	public Vec2f( float x, float y)
	{
		this.x=x;
		this.y=y;
	}
	
	public Vec2f( double x, double y)
	{
		this.x=x;
		this.y=y;
	}
	
	public Vec2f(Point p)
	{
		x = p.getX();
		y = p.getY();
	}
	
	public float[] toFloatArray()
	{
		return new float[]{getXFloat(), getYFloat()};
	}

	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public void setX(double input)
	{
		x = input;
	}
	
	public void setY(double input)
	{
		y = input;
	}
	
	public float getXFloat()
	{
		return (float)x;
	}
	
	public float getYFloat()
	{
		return (float)y;
	}
	
	public double getLength()
	{
		return Math.sqrt( (x*x) + (y*y) );
	}
	
	public String toString()
	{
		return x+", "+y;
	}
}