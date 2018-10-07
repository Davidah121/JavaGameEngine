package openGLEngine;
import java.lang.Math;

public class Vec4f {
	
	public double x;
	public double y;
	public double z;
	public double w;
	
	public Vec4f( float x, float y, float z, float w)
	{
		this.x=x;
		this.y=y;
		this.z=z;
		this.w=w;
	}
	
	public Vec4f( double x, double y, double z, double w)
	{
		this.x=x;
		this.y=y;
		this.z=z;
		this.w=w;
	}
	
	public float[] toFloatArray()
	{
		return new float[]{getXFloat(), getYFloat(), getZFloat(), getWFloat()};
	}

	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public double getZ()
	{
		return z;
	}
	
	public double getW()
	{
		return w;
	}
	
	public void setX(double input)
	{
		x = input;
	}
	
	public void setY(double input)
	{
		y = input;
	}
	
	public void setZ(double input)
	{
		z = input;
	}
	
	public void setW(double input)
	{
		w = input;
	}
	
	public float getXFloat()
	{
		return (float)x;
	}
	
	public float getYFloat()
	{
		return (float)y;
	}
	
	public float getZFloat()
	{
		return (float)z;
	}
	
	public float getWFloat()
	{
		return (float)w;
	}
	
	public double getLength()
	{
		return Math.sqrt( (x*x) + (y*y) + (z*z) + (w*w) );
	}
	
	public String toString()
	{
		return x+", "+y+", "+z+", "+w;
	}
	
	public String colorToHexString()
	{
		byte[] color = new byte[]{
				(byte)GameMath.clamp(GameMath.round(255*x), 0, 255),
				(byte)GameMath.clamp(GameMath.round(255*y), 0, 255),
				(byte)GameMath.clamp(GameMath.round(255*z), 0, 255),
				(byte)GameMath.clamp(GameMath.round(255*w), 0, 255)
		};
		
		return GameMath.toHexString(color);
	}
}