package openGLEngine;
import java.io.Serializable;
import java.lang.Math;

public class Vec3f implements Serializable{
	
	public double x;
	public double y;
	public double z;
	
	public Vec3f( float x, float y, float z)
	{
		this.x=x;
		this.y=y;
		this.z=z;
	}
	
	public Vec3f( double x, double y, double z)
	{
		this.x=x;
		this.y=y;
		this.z=z;
	}
	
	public Vec3f(Vec3f other)
	{
		x = other.x;
		y = other.y;
		z = other.z;
	}
	
	public float[] toFloatArray()
	{
		return new float[]{getXFloat(), getYFloat(), getZFloat()};
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
	
	public double getLength()
	{
		return Math.sqrt( (x*x) + (y*y) +(z*z) );
	}
	
	public String toString()
	{
		return x+", "+y+", "+z;
	}
	
	public String colorToHexString()
	{
		byte[] color = new byte[]{
				(byte)GameMath.clamp(GameMath.round(255*x), 0, 255),
				(byte)GameMath.clamp(GameMath.round(255*y), 0, 255),
				(byte)GameMath.clamp(GameMath.round(255*z), 0, 255)
		};
		
		return GameMath.toHexString(color);
	}
}