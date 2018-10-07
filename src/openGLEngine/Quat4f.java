package openGLEngine;
import java.lang.Math;

public class Quat4f {

	public double w = 1;
	public double x = 0;
	public double y = 0;
	public double z = 0;
	
	public Quat4f(double w, double x, double y, double z)
	{
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double getLength()
	{
		return Math.sqrt(w*w + x*x + y*y + z*z);
	}
}
