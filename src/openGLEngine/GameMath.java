package openGLEngine;
import java.lang.Math;

public class GameMath {
	
	public static final double PI = Math.PI;
	public static final boolean TYPE_2D = false;
	public static final boolean TYPE_3D = true;
	
	////////java Math Section
	
	/**
	 * returns the value rounded to the nearest whole number.
	 * @param value
	 * @return
	 */
	public static double round(double value)
	{
		return Math.round(value);
	}
	
	/**
	 * returns the value rounded to the nearest whole number.
	 * @param value
	 * @return
	 */
	public static float round(float value)
	{
		return Math.round(value);
	}
	
	/**
	 * returns the value rounded to the specified amount of decimal places
	 * @param value
	 * @param decimalPlaces
	 * @return
	 */
	public static double round(double value, int decimalPlaces)
	{
		String tempString = toString(value);
		int newDecimalPlaces;
		
		int index = tempString.indexOf(".");
		
		if (index+decimalPlaces <= tempString.length()-2)
		{
			newDecimalPlaces = decimalPlaces;
		}
		else
		{
			newDecimalPlaces = tempString.length()-2;
		}
		
		String addNum=".";
		for(int i=0;i<newDecimalPlaces;i++)
		{
			addNum+="0";
		}
		addNum+=tempString.charAt(index+newDecimalPlaces+1);
		
		double number = parseDouble(tempString.substring(0, index+newDecimalPlaces))
					+ parseDouble(addNum);
		
		return number;
	}
	
	/**
	 * returns the value rounded to the specified amount of decimal places.
	 * @param value
	 * @param decimalPlaces
	 * @return
	 */
	public static float round(float value, int decimalPlaces)
	{
		String tempString = toString(value);
		int newDecimalPlaces;
		
		int index = tempString.indexOf(".");
		
		if (index+decimalPlaces <= tempString.length()-2)
		{
			newDecimalPlaces = decimalPlaces;
		}
		else
		{
			newDecimalPlaces = tempString.length()-2;
		}
		
		String addNum=".";
		for(int i=0;i<newDecimalPlaces;i++)
		{
			addNum+="0";
		}
		addNum+=tempString.charAt(index+newDecimalPlaces+1);
		
		float number = parseFloat(tempString.substring(0, index+newDecimalPlaces))
					+ parseFloat(addNum);
		
		return number;
	}
	
	/**
	 * returns the value rounded down.
	 * @param value
	 * @return
	 */
	public static double floor(double value)
	{
		return Math.floor(value);
	}
	
	/**
	 * returns the value rounded down.
	 * @param value
	 * @return
	 */
	public static float floor(float value)
	{
		return (float) Math.floor(value);
	}
	
	/**
	 * returns the value rounded up.
	 * @param value
	 * @return
	 */
	public static double ceil(double value)
	{
		return Math.ceil(value);
	}
	
	/**
	 * returns the value rounded up.
	 * @param value
	 * @return
	 */
	public static float ceil(float value)
	{
		return (float)Math.ceil(value);
	}
	
	/**
	 * returns the absolute value of the specified value.
	 * @param value
	 * @return
	 */
	public static double abs(double value)
	{
		return Math.abs(value);
	}
	
	/**
	 * returns the absolute value of the specified value.
	 * @param value
	 * @return
	 */
	public static float abs(float value)
	{
		return Math.abs(value);
	}
	
	/**
	 * returns the absolute value of the specified value.
	 * @param value
	 * @return
	 */
	public static long abs(long value)
	{
		return Math.abs(value);
	}
	
	/**
	 * returns the absolute value of the specified value.
	 * @param value
	 * @return
	 */
	public static int abs(int value)
	{
		return Math.abs(value);
	}
	
	/**
	 * returns the max between the two values.
	 * @param x
	 * @param y
	 * @return
	 */
	public static double max(double x, double y)
	{
		return Math.max(x, y);
	}
	
	/**
	 * returns the max between the two values.
	 * @param x
	 * @param y
	 * @return
	 */
	public static float max(float x, float y)
	{
		return Math.max(x, y);
	}
	
	/**
	 * returns the max between the two values.
	 * @param x
	 * @param y
	 * @return
	 */
	public static long max(long x, long y)
	{
		return Math.max(x, y);
	}
	
	/**
	 * returns the max between the two values.
	 * @param x
	 * @param y
	 * @return
	 */
	public static int max(int x, int y)
	{
		return Math.max(x, y);
	}
	
	/**
	 * returns the min between the two values.
	 * @param x
	 * @param y
	 * @return
	 */
	public static double min(double x, double y)
	{
		return Math.min(x, y);
	}
	
	/**
	 * returns the min between the two values.
	 * @param x
	 * @param y
	 * @return
	 */
	public static float min(float x, float y)
	{
		return Math.min(x, y);
	}
	
	/**
	 * returns the min between the two values.
	 * @param x
	 * @param y
	 * @return
	 */
	public static long min(long x, long y)
	{
		return Math.min(x, y);
	}
	
	/**
	 * returns the min between the two values.
	 * @param x
	 * @param y
	 * @return
	 */
	public static int min(int x, int y)
	{
		return Math.min(x, y);
	}
	
	/**
	 * returns the maximum value in the array.
	 * @param values
	 * @return
	 */
	public static double max(double[] values)
	{
		double maxValue = values[0];
		
		for(int i=0;i<values.length;i++)
		{
			maxValue = values[i] > maxValue ? values[i] : maxValue;
		}
		
		return maxValue;
	}
	
	/**
	 * returns the maximum value in the array.
	 * @param values
	 * @return
	 */
	public static long max(long[] values)
	{
		long maxValue = values[0];
		
		for(int i=0;i<values.length;i++)
		{
			maxValue = values[i] > maxValue ? values[i] : maxValue;
		}
		
		return maxValue;
	}
	
	/**
	 * returns the maximum value in the array.
	 * @param values
	 * @return
	 */
	public static float max(float[] values)
	{
		float maxValue = values[0];
		
		for(int i=0;i<values.length;i++)
		{
			maxValue = values[i] > maxValue ? values[i] : maxValue;
		}
		
		return maxValue;
		
	}
	
	/**
	 * returns the maximum value in the array.
	 * @param values
	 * @return
	 */
	public static int max(int[] values)
	{
		int maxValue = values[0];
		
		for(int i=0;i<values.length;i++)
		{
			maxValue = values[i] > maxValue ? values[i] : maxValue;
		}
		
		return maxValue;
	}
	
	/**
	 * returns the minimum value in the array.
	 * @param values
	 * @return
	 */
	public static double min(double[] values)
	{
		double minValue = values[0];
		
		for(int i=0;i<values.length;i++)
		{
			minValue = values[i] < minValue ? values[i] : minValue;
		}
		
		return minValue;
	}
	
	/**
	 * returns the minimum value in the array.
	 * @param values
	 * @return
	 */
	public static long min(long[] values)
	{
		long minValue = values[0];
		
		for(int i=0;i<values.length;i++)
		{
			minValue = values[i] < minValue ? values[i] : minValue;
		}
		
		return minValue;
	}
	
	/**
	 * returns the minimum value in the array.
	 * @param values
	 * @return
	 */
	public static float min(float[] values)
	{
		float minValue = values[0];
		
		for(int i=0;i<values.length;i++)
		{
			minValue = values[i] < minValue ? values[i] : minValue;
		}
		
		return minValue;
	}
	
	/**
	 * returns the minimum value in the array.
	 * @param values
	 * @return
	 */
	public static int min(int[] values)
	{
		int minValue = values[0];
		
		for(int i=0;i<values.length;i++)
		{
			minValue = values[i] < minValue ? values[i] : minValue;
		}
		
		return minValue;
	}
	
	/**
	 * returns a random value
	 * @return
	 */
	public static double random()
	{
		return Math.random();
	}
	
	/**
	 * returns a random value between the specified max and min bounds.
	 * @param min
	 * @param max
	 * @return
	 */
	public static int randomRange(int min, int max)
	{
		int diffValue = max-min;
		
		return (int) round( ( Math.random()*diffValue )+min );
	}
	
	/**
	 * returns a random value between the specified max and min bounds.
	 * @param min
	 * @param max
	 * @return
	 */
	public static double randomRange(double min, double max)
	{
		double diffValue = max-min;
		
		return ( Math.random()*diffValue )+min;
	}
	
	/**
	 * returns the square root of the value.
	 * @param value
	 * @return
	 */
	public static double sqrt(double value)
	{
		return Math.sqrt(value);
	}
	
	/**
	 * returns value squared.
	 * @param value
	 * @return
	 */
	public static double sqr(double value)
	{
		return value*value;
	}
	
	/**
	 * returns value raised to the specified power.
	 * @param value
	 * @param pow
	 * @return
	 */
	public static double pow(double value, double pow)
	{
		return Math.pow(value, pow);
	}
	
	/**
	 * returns value raised to the specified power.
	 * @param value
	 * @param pow
	 * @return
	 */
	public static float pow(float value, float pow)
	{
		return (float)Math.pow((double)value, (double)pow);
	}
	
	/**
	 * returns the cosine of the value
	 * the value expected is in radians.
	 * @param value
	 * @return
	 */
	public static double cos(double value)
	{
		return Math.cos(value);
	}
	
	/**
	 * returns the sine of the value
	 * the value expected is in radians.
	 * @param value
	 * @return
	 */
	public static double sin(double value)
	{
		return Math.sin(value);
	}
	
	/**
	 * returns the tangent of the value
	 * the value expected is in radians.
	 * @param value
	 * @return
	 */
	public static double tan(double value)
	{
		return Math.tan(value);
	}
	
	/**
	 * returns the cosine of the value.
	 * the value expected is in degrees.
	 * @param value
	 * @return
	 */
	public static double dcos(double value)
	{
		return Math.cos( degToRad(value));
	}
	
	/**
	 * returns the sin of the value.
	 * the value expected is in degrees.
	 * @param value
	 * @return
	 */
	public static double dsin(double value)
	{
		return Math.sin( degToRad(value));
	}
	
	/**
	 * returns the tangent of the value.
	 * the value expected is in degrees.
	 * @param value
	 * @return
	 */
	public static double dtan(double value)
	{
		return Math.tan( degToRad(value));
	}
	
	/**
	 * returns the inverse cosine of the value.
	 * @param value
	 * @return
	 */
	public static double arccos(double value)
	{
		return Math.acos(value);
	}
	
	/**
	 * returns the inverse sine of the value.
	 * @param value
	 * @return
	 */
	public static double arcsin(double value)
	{
		return Math.asin(value);
	}
	
	/**
	 * returns the inverse tanngent of the value.
	 * @param value
	 * @return
	 */
	public static double arctan(double value)
	{
		return Math.atan(value);
	}
	
	/**
	 * returns the logarithm of the value
	 * @param value
	 * @return
	 */
	public static double log(double value)
	{
		return Math.log(value);
	}
	
	/**
	 * returns the logarithm of the value.
	 * Base 10.
	 * @param value
	 * @return
	 */
	public static double log10(double value)
	{
		return Math.log10(value);
	}
	
	////////General Section
	
	/**
	 * Clamps the value between the minimum and maximum bounds specified.
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	public static double clamp(double value, double min, double max)
	{
		if(value>max)
		{
			return max;
		}
		else if(value<min)
		{
			return min;
		}
		else
		{
			return value;
		}
	}
	
	/**
	 * Clamps the value between the minimum and maximum bounds specified.
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	public static float clamp(float value, float min, float max)
	{
		if(value>max)
			return max;
		else if(value<min)
			return min;
		else
			return value;
	}
	
	/**
	 * Clamps the value between the minimum and maximum bounds specified.
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	public static long clamp(long value, long min, long max)
	{
		if(value>max)
			return max;
		else if(value<min)
			return min;
		else
			return value;
	}
	
	/**
	 * Clamps the value between the minimum and maximum bounds specified.
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	public static int clamp(int value, int min, int max)
	{
		if(value>max)
			return max;
		else if(value<min)
			return min;
		else
			return value;
	}
	
	/**
	 * Clamps the vector between the minimum and maximum vector bounds specified
	 * based on the x value alone.
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	public static Vec2f clampByX(Vec2f value, Vec2f min, Vec2f max)
	{
		Vec2f newVec = new Vec2f(value.x,value.y);
		
		if(value.x>max.x)
		{
			newVec.x=max.x;
			newVec.y=max.y;
		}
		else if(value.x<min.x)
		{
			newVec.x=min.x;
			newVec.y=min.y;
		}

		return newVec;
	}
	
	/**
	 * Clamps the vector between the minimum and maximum vector bounds specified
	 * based on the x value alone.
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	public static Vec3f clampByX(Vec3f value, Vec3f min, Vec3f max)
	{
		Vec3f newVec = new Vec3f(value.x,value.y,value.z);
		
		if(value.x>max.x)
		{
			newVec.x=max.x;
			newVec.y=max.y;
			newVec.z=max.z;
		}
		else if(value.x<min.x)
		{
			newVec.x=min.x;
			newVec.y=min.y;
			newVec.z=min.z;
		}
		
		return newVec;
	}
	
	/**
	 * Clamps the vector between the minimum and maximum vector bounds specified
	 * based on the x value alone.
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	public static Vec4f clampByX(Vec4f value, Vec4f min, Vec4f max)
	{
		Vec4f newVec = new Vec4f(value.x,value.y,value.z,value.w);
		if(value.x>max.x)
		{
			newVec.x=max.x;
			newVec.y=max.y;
			newVec.z=max.z;
			newVec.w=max.w;
		}
		else if(value.x<min.x)
		{
			newVec.x=min.x;
			newVec.y=min.y;
			newVec.z=min.z;
			newVec.w=min.w;
		}
		
		return newVec;
	}
	
	/**
	 * Converts the value from degrees to radians.
	 * @param value
	 * @return
	 */
	public static double degToRad(double value)
	{
		return (value*PI) / 180.0;
	}
	
	/**
	 * Converts the value from radians to degress.
	 * @param value
	 * @return
	 */
	public static double radToDeg(double value)
	{
		return (value*180.0) / PI;
	}
	
	/**
	 * Converts the value to a String.
	 * @param a
	 * @return
	 */
	public static String toString(int a)
	{
		return Integer.toString(a);
	}
	
	/**
	 * Converts the value to a String.
	 * @param a
	 * @return
	 */
	public static String toString(double a)
	{
		return Double.toString(a);
	}
	
	/**
	 * Converts the value to a String.
	 * @param a
	 * @return
	 */
	public static String toString(long a)
	{
		return Long.toString(a);
	}
	
	/**
	 * Converts the value to a String.
	 * @param a
	 * @return
	 */
	public static String toString(float a)
	{
		return Float.toString(a);
	}
	
	/**
	 * Converts the value to a String.
	 * @param a
	 * @return
	 */
	public static String toString(boolean a)
	{
		return Boolean.toString(a);
	}
	
	private static char numberToHexChar(int a)
	{
		char c = '0';
		
		switch(a)
		{
		case 0:
			c='0';
			break;
		case 1:
			c='1';
			break;
		case 2:
			c='2';
			break;
		case 3:
			c='3';
			break;
		case 4:
			c='4';
			break;
		case 5:
			c='5';
			break;
		case 6:
			c='6';
			break;
		case 7:
			c='7';
			break;
		case 8:
			c='8';
			break;
		case 9:
			c='9';
			break;
		case 10:
			c='A';
			break;
		case 11:
			c='B';
			break;
		case 12:
			c='C';
			break;
		case 13:
			c='D';
			break;
		case 14:
			c='E';
			break;
		case 15:
			c='F';
			break;
		default:
			c='0';
			break;
		}
		
		return c;
	}
	
	/**
	 * Converts a byte into a Hexadecimal String.
	 * @param a
	 * @return
	 */
	public static String toHexString(byte a)
	{
		int unsignedByte = a < 0 ? 256+a : a;
		
		int firstVal = unsignedByte/16;
		int secondVal = (int) (unsignedByte&0xf);
		
		String tempString = numberToHexChar(firstVal)+""+numberToHexChar(secondVal);
		return tempString;
	}
	
	/**
	 * Converts a byte array into a Hexadecimal String.
	 * @param a
	 * @return
	 */
	public static String toHexString(byte[] a)
	{
		String tempString = "";
		int unsignedByte, firstVal, secondVal;
		
		for(int i=0; i<a.length; i++)
		{
			unsignedByte = a[i] < 0 ? 256+a[i] : a[i];
			
			firstVal = unsignedByte/16;
			secondVal = (int) (unsignedByte&0xf);
			
			tempString += numberToHexChar(firstVal)+""+numberToHexChar(secondVal);
		}
		
		return tempString;
	}
	
	/**
	 * Converts a short into a Hexadecimal String.
	 * Unlike the java version, this String maintains the
	 * same length regardless of the value.
	 * @param a
	 * @return
	 */
	public static String toHexString(short a)
	{
		return toHexString(toByteArray(a));
	}
	
	/**
	 * Converts an integer into a Hexadecimal String.
	 * Unlike the java version, this String maintains the
	 * same length regardless of the value.
	 * @param a
	 * @return
	 */
	public static String toHexString(int a)
	{
		return toHexString(toByteArray(a));
	}
	
	/**
	 * Converts an long into a Hexadecimal String.
	 * Unlike the java version, this String maintains the
	 * same length regardless of the value.
	 * @param a
	 * @return
	 */
	public static String toHexString(long a)
	{
		return toHexString(toByteArray(a));
	}
	
	public static short byteArrayToShort(byte[] a)
	{
		short p = 0;
		if(a.length>=2)
		{
			p = (short) (a[0] << 8);
			p += (short) (a[1] << 0);
		}
		return p;
	}
	
	public static int byteArrayToInteger(byte[] a)
	{
		int p = 0;
		if(a.length>=4)
		{
			p = (int) (a[0] << 24);
			p += (int) (a[1] << 16);
			p += (int) (a[2] << 8);
			p += (int) (a[3] << 0);
		}
		return p;
	}
	
	public static long byteArrayToLong(byte[] a)
	{
		long p = 0;
		if(a.length>=4)
		{
			p = (long) (a[0] << 56);
			p += (long) (a[1] << 48);
			p += (long) (a[2] << 40);
			p += (long) (a[3] << 32);
			p += (long) (a[0] << 24);
			p += (long) (a[1] << 16);
			p += (long) (a[2] << 8);
			p += (long) (a[3] << 0);
		}
		return p;
	}
	/**
	 * Converts a short into a byte array.
	 * @param a
	 * @return
	 */
	public static byte[] toByteArray(short a)
	{
		byte[] array = new byte[]{
				(byte) ((a>>8) & 0xFF),
				(byte) ((a>>0) & 0xFF)
		};
		
		return array;
	}
	
	/**
	 * Converts an integer into a byte array.
	 * @param a
	 * @return
	 */
	public static byte[] toByteArray(int a)
	{
		byte[] array = new byte[]{
				(byte) ((a>>24) & 0xFF),
				(byte) ((a>>16) & 0xFF),
				(byte) ((a>>8) & 0xFF),
				(byte) ((a>>0) & 0xFF)
		};
		
		return array;
	}
	
	/**
	 * Converts a long into a byte array.
	 * @param a
	 * @return
	 */
	public static byte[] toByteArray(long a)
	{
		byte[] array = new byte[]{
				(byte) ((a>>56) & 0xFF),
				(byte) ((a>>48) & 0xFF),
				(byte) ((a>>40) & 0xFF),
				(byte) ((a>>32) & 0xFF),
				(byte) ((a>>24) & 0xFF),
				(byte) ((a>>16) & 0xFF),
				(byte) ((a>>8) & 0xFF),
				(byte) ((a>>0) & 0xFF)
		};
		
		return array;
	}
	
	/**
	 * Converts the String to a Integer.
	 * @param a
	 * @return
	 */
	public static int parseInt(String a)
	{
		try
		{
			return Integer.parseInt(a);
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Converts the String to a Double.
	 * @param a
	 * @return
	 */
	public static double parseDouble(String a)
	{
		try
		{
			return Double.parseDouble(a);
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			return 0.0;
		}
	}
	
	/**
	 * Converts the String to a Long.
	 * @param a
	 * @return
	 */
	public static long parseLong(String a)
	{
		try
		{
			return Long.parseLong(a);
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			return 0L;
		}
	}
	
	/**
	 * Converts the String to a Float.
	 * @param a
	 * @return
	 */
	public static float parseFloat(String a)
	{
		try
		{
			return Float.parseFloat(a);
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			return 0f;
		}
	}
	
	/**
	 * Converts the String to a Boolean.
	 * @param a
	 * @return
	 */
	public static boolean parseBoolean(String a)
	{
		try
		{
			return Boolean.parseBoolean(a);
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * This method determines how much should you increase your x value
	 * based on the direction and speed traveled.
	 * @param direction
	 * @param speed
	 * @return
	 */
	public static float lengthDirX(float direction, float speed)
	{
		return (float) (dcos(direction)*speed);
	}
	
	/**
	 * This method determines how much should you increase your y value
	 * based on the direction and speed traveled.
	 * @param direction
	 * @param speed
	 * @return
	 */
	public static float lengthDirY(float direction, float speed)
	{
		return (float) (dsin(direction)*speed);
	}
	
	/**
	 * This method determines how much should you increase your x value
	 * based on the direction and speed traveled.
	 * @param direction
	 * @param speed
	 * @return
	 */
	public static double lengthDirX(double direction, double speed)
	{
		return (double) (dcos(direction)*speed);
	}
	
	/**
	 * This method determines how much should you increase your y value
	 * based on the direction and speed traveled.
	 * @param direction
	 * @param speed
	 * @return
	 */
	public static double lengthDirY(double direction, double speed)
	{
		return (double) (dsin(direction)*speed);
	}
	
	///////Vector Section
	
	/**
	 * returns the dot product of the two vectors.
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static double dot(double x1, double y1, double x2, double y2)
	{
		return x1*x2 + y1*y2;
	}
	
	/**
	 * returns the dot product of the two vectors.
	 * @param x1
	 * @param y1
	 * @param z1
	 * @param x2
	 * @param y2
	 * @param z2
	 * @return
	 */
	public static double dot(double x1, double y1, double z1, double x2, double y2, double z2)
	{
		return x1*x2 + y1*y2 + z1*z2;
	}
	
	/**
	 * returns the dot product of the two vectors.
	 * @param x1
	 * @param y1
	 * @param z1
	 * @param w1
	 * @param x2
	 * @param y2
	 * @param z2
	 * @param w2
	 * @return
	 */
	public static double dot(double x1, double y1, double z1, double w1, double x2, double y2, double z2, double w2)
	{
		return x1*x2 + y1*y2 + z1*z2 + w1*w2;
	}
	
	/**
	 * returns the dot product of the two vectors.
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double dot(Vec2f v1, Vec2f v2)
	{
		return v1.x*v2.x + v1.y*v2.y;
	}
	
	/**
	 * returns the dot product of the two vectors.
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double dot(Vec3f v1, Vec3f v2)
	{
		return v1.x*v2.x + v1.y*v2.y + v1.z*v2.z;
	}
	
	/**
	 * returns the dot product of the two vectors.
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double dot(Vec4f v1, Vec4f v2)
	{
		return v1.x*v2.x + v1.y*v2.y + v1.z*v2.z + v1.w*v2.w;
	}
	
	/**
	 * Adds two vectors and returns a new vector.
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Vec2f vectorAdd(Vec2f v1, Vec2f v2)
	{
		return new Vec2f(v1.x+v2.x, v1.y+v2.y);
	}
	
	/**
	 * Adds two vectors and returns a new vector.
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Vec3f vectorAdd(Vec3f v1, Vec3f v2)
	{
		return new Vec3f(v1.x+v2.x, v1.y+v2.y, v1.z+v2.z);
	}
	
	/**
	 * Adds two vectors and returns a new vector.
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Vec4f vectorAdd(Vec4f v1, Vec4f v2)
	{
		return new Vec4f(v1.x+v2.x, v1.y+v2.y, v1.z+v2.z, v1.w+v2.w);
	}
	
	/**
	 * Subtracts two vectors and returns a new vector.
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Vec2f vectorSubtract(Vec2f v1, Vec2f v2)
	{
		return new Vec2f(v1.x-v2.x, v1.y-v2.y);
	}
	
	/**
	 * Subtracts two vectors and returns a new vector.
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Vec3f vectorSubtract(Vec3f v1, Vec3f v2)
	{
		return new Vec3f(v1.x-v2.x, v1.y-v2.y, v1.z-v2.z);
	}
	
	/**
	 * Subtracts two vectors and returns a new vector.
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Vec4f vectorSubtract(Vec4f v1, Vec4f v2)
	{
		return new Vec4f(v1.x-v2.x, v1.y-v2.y, v1.z-v2.z, v1.w-v2.w);
	}
	
	/**
	 * Multiplies the vector by a single value and returns a
	 * new vector.
	 * @param vec
	 * @param value
	 * @return
	 */
	public static Vec2f vectorMult(Vec2f vec, double value)
	{
		return new Vec2f(vec.x*value, vec.y*value);
	}
	
	/**
	 * Multiplies the vector by a single value and returns a
	 * new vector.
	 * @param vec
	 * @param value
	 * @return
	 */
	public static Vec3f vectorMult(Vec3f vec, double value)
	{
		return new Vec3f(vec.x*value, vec.y*value, vec.z*value);
	}
	
	/**
	 * Multiplies the vector by a single value and returns a
	 * new vector.
	 * @param vec
	 * @param value
	 * @return
	 */
	public static Vec4f vectorMult(Vec4f vec, double value)
	{
		return new Vec4f(vec.x*value, vec.y*value, vec.z*value, vec.w*value);
	}
	
	/**
	 * Returns a new vector with the same direction, but with a magnitude
	 * (or length) of one.
	 * @param vec
	 * @return
	 */
	public static Vec2f normalize(Vec2f vec)
	{
		double length = vec.getLength();
		try
		{
			return new Vec2f(vec.x/length, vec.y/length); 
		}
		catch(Exception e)
		{
			System.err.println(e);
			System.err.println("Can't Normalize Vector");
			return new Vec2f(0,0);
		}
	}
	
	/**
	 * Returns a new vector with the same direction, but with a magnitude
	 * (or length) of one.
	 * @param vec
	 * @return
	 */
	public static Vec3f normalize(Vec3f vec)
	{
		double length = vec.getLength();
		try
		{
			return new Vec3f(vec.x/length, vec.y/length, vec.z/length); 
		}
		catch(Exception e)
		{
			System.err.println(e);
			System.err.println("Can't Normalize Vector");
			return new Vec3f(0,0,0);
		}
	}
	
	/**
	 * Returns a new vector with the same direction, but with a magnitude
	 * (or length) of one.
	 * @param vec
	 * @return
	 */
	public static Vec4f normalize(Vec4f vec)
	{
		double length = vec.getLength();
		try
		{
			return new Vec4f(vec.x/length, vec.y/length,vec.z/length,vec.w/length); 
		}
		catch(Exception e)
		{
			System.err.println(e);
			System.err.println("Can't Normalize Vector");
			return new Vec4f(0,0,0,0);
		}
	}
	
	/**
	 * Returns a new vector that represents the rate of change relative
	 * to one of the input vector.
	 * @param vec
	 * @return
	 */
	public static Vec2f fakeSlope(Vec2f vec)
	{
		double maxValue = max( new double[]{abs(vec.x),abs(vec.y)} );
		
		try
		{
			return new Vec2f( vec.x / maxValue, vec.y / maxValue);
		}
		catch(Exception e)
		{
			return new Vec2f(0,0);
		}
	}
	
	/**
	 * Returns a new vector that represents the rate of change relative
	 * to one of the input vector.
	 * @param vec
	 * @return
	 */
	public static Vec3f fakeSlope(Vec3f vec)
	{
		double maxValue = max( new double[]{abs(vec.x),abs(vec.y),abs(vec.z)} );
		
		try
		{
			return new Vec3f( vec.x / maxValue, vec.y / maxValue, vec.z / maxValue);
		}
		catch(Exception e)
		{
			return new Vec3f(0,0,0);
		}
	}
	
	/**
	 * Returns a new vector that represents the rate of change relative
	 * to one of the input vector.
	 * @param vec
	 * @return
	 */
	public static Vec4f fakeSlope(Vec4f vec)
	{
		double maxValue = max( new double[]{abs(vec.x),abs(vec.y),abs(vec.z),abs(vec.w)} );
		
		try
		{
			return new Vec4f( vec.x / maxValue, vec.y / maxValue, vec.z / maxValue, vec.w / maxValue);
		}
		catch(Exception e)
		{
			return new Vec4f(0,0,0,0);
		}
	}
	
	/**
	 * Performs the cross product of two vectors. Can only be done with
	 * 3 dimensional vectors. The cross product gives a vector that is
	 * perpendicular to both input vectors.
	 * @param vec1
	 * @param vec2
	 * @return
	 */
	public static Vec3f crossProduct(Vec3f vec1, Vec3f vec2)
	{
		return new Vec3f( vec1.y*vec2.z - vec1.z*vec2.y,
						vec1.z*vec2.x - vec1.x*vec2.z,
						vec1.x*vec2.y - vec1.y*vec2.x );
	}
	
	/**
	 * Returns a new vector that has been reflected across the specified
	 * vector. Vec1 will be reflected across Vec2.
	 * @param vec1
	 * @param vec2
	 * @return
	 */
	public static Vec2f reflectVector(Vec2f vec1, Vec2f vec2)
	{
		//v` = v - 2 * ( v dot n ) * n;
		//v` = new vector
		//v = old vector
		//n = normalized vector reflected across
		
		Vec2f nVec2 = normalize(vec2);
		double value = 2 * dot(vec1, nVec2);
		return vectorSubtract(vec1, vectorMult(nVec2,value) );
	}
	
	/**
	 * Returns a new vector that has been reflected across the specified
	 * vector. Vec1 will be reflected across Vec2.
	 * @param vec1
	 * @param vec2
	 * @return
	 */
	public static Vec3f reflectVector(Vec3f vec1, Vec3f vec2)
	{
		//v` = v - 2 * ( v dot n ) * n;
		//v` = new vector
		//v = old vector
		//n = normalized vector reflected across
		
		Vec3f nVec2 = normalize(vec2);
		double value = 2 * dot(vec1, nVec2);
		return vectorSubtract(vec1, vectorMult(nVec2,value) );
	}
	
	/**
	 * Returns a new vector that has been reflected across the specified
	 * vector. Vec1 will be reflected across Vec2.
	 * @param vec1
	 * @param vec2
	 * @return
	 */
	public static Vec4f reflectVector(Vec4f vec1, Vec4f vec2)
	{
		//v` = v - 2 * ( v dot n ) * n;
		//v` = new vector
		//v = old vector
		//n = normalized vector reflected across
		
		Vec4f nVec2 = normalize(vec2);
		double value = 2 * dot(vec1, nVec2);
		return vectorSubtract(vec1, vectorMult(nVec2,value) );
	}
	
	public static double angleBetweenVectors(Vec2f vec1, Vec2f vec2)
	{
		double angle = 0;
		
		//Dot product = v1.x * v2.x + v1.y * v2.y;
		//Dot product also equals = v1.length * v2.length * cos( angleBetweenVecs );
		
		double lengthTogether = vec1.getLength() * vec2.getLength();
		double dotValue = dot(vec1, vec2);
		
		try
		{
			angle = arccos(dotValue / lengthTogether);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		//cos( x ) = dotValue / lengthTogether;
		//x = arccos(dotValue / lengthTogether);
		
		return angle;
	}
	
	////////Quaternion Section
	
	/**
	 * Returns a normalized Quaternion. Much like normalizing a vector,
	 * normalizing a quaternion forces the length to be one while maintaining
	 * the same ratio between each value. Quaternions do not have a direction,
	 * but the concept is the same.
	 * @param vec
	 * @return
	 */
	public static Quat4f normalize(Quat4f vec)
	{
		double length = vec.getLength();
		try
		{
			return new Quat4f(vec.x/length, vec.y/length,vec.z/length,vec.w/length); 
		}
		catch(Exception e)
		{
			System.err.println(e);
			System.err.println("Can't Normalize Vector");
			return new Quat4f(1,0,0,0);
		}
	}
	
	/**
	 * Creates a new quaternion that can be used for rotation.
	 * The rotation expects a value in degrees.
	 * The x, y, and z values represents the axis of rotation.
	 * @param rotation
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public static Quat4f createRotationQuaternion(double rotation, double x, double y, double z)
	{
		double cosf = dcos( rotation/2.0 );
		double sinf = dsin( rotation/2.0 );
		
		Vec3f axis = normalize(new Vec3f(x,y,z));
		return new Quat4f(cosf, axis.x*sinf, axis.y*sinf, axis.z*sinf);
	}
	
	/**
	 * Determines and returns the conjugate of the input quaternion.
	 * @param q
	 * @return
	 */
	public static Quat4f quaternionConjugate(Quat4f q)
	{
		return new Quat4f(q.w,-q.x,-q.y,-q.z);
	}
	
	/**
	 * Converts a quaternion into a 3x3 Matrix that can be used for
	 * rotation.
	 * @param q
	 * @return
	 */
	public static Mat3f quaternionToMat3f(Quat4f q)
	{
		return new Mat3f( 1-(2*q.y*q.y) - (2*q.z*q.z), (2*q.x*q.y) - (2*q.z*q.w), (2*q.x*q.z) + (2*q.y*q.w),
						(2*q.x*q.y) + (2*q.z*q.w), 1 - (2*q.x*q.x) - (2*q.z*q.z), (2*q.y*q.z) - (2*q.x*q.w),
						(2*q.x*q.z) - (2*q.y*q.w), (2*q.y*q.z) + (2*q.x*q.w), 1 - (2*q.x*q.x) - (2*q.y*q.y) );
	}
	
	/**
	 * Converts a quaternion into a 4x4 Matrix that can be used for
	 * rotation.
	 * @param q
	 * @return
	 */
	public static Mat4f quaternionToMat4f(Quat4f q)
	{
		return new Mat4f( 1-(2*q.y*q.y) - (2*q.z*q.z), (2*q.x*q.y) - (2*q.z*q.w), (2*q.x*q.z) + (2*q.y*q.w), 0.0,
						(2*q.x*q.y) + (2*q.z*q.w), 1 - (2*q.x*q.x) - (2*q.z*q.z), (2*q.y*q.z) - (2*q.x*q.w), 0.0,
						(2*q.x*q.z) - (2*q.y*q.w), (2*q.y*q.z) + (2*q.x*q.w), 1 - (2*q.x*q.x) - (2*q.y*q.y), 0.0,
						0.0, 0.0, 0.0, 1.0);
	}
	
	/**
	 * Returns two quaternions multiplied together.
	 * quaternion multiplication is not commutative meaning q1 * q2 != q2 * q1
	 * @param q1
	 * @param q2
	 * @return
	 */
	public static Quat4f quaternionMult(Quat4f q1, Quat4f q2)
	{
		double w = q1.w*q2.w - q1.x*q2.x - q1.y*q2.y - q1.z*q2.z;
		double x = q1.w*q2.x + q1.x*q2.w + q1.y*q2.z - q1.z*q2.y;
		double y = q1.w*q2.y - q1.x*q2.z + q1.y*q2.w + q1.z*q2.x;
		double z = q1.w*q2.z + q1.x*q2.y - q1.y*q2.x + q1.z*q2.w;
		
		return new Quat4f(w,x,y,z);
	}
	
	/**
	 * Rotates a vector by a quaternion then returns the results.
	 * @param q1
	 * @param vec
	 * @return
	 */
	public static Vec3f quaternionRotation(Quat4f q1, Vec3f vec)
	{
		Quat4f newPos = quaternionMult( quaternionMult(q1, new Quat4f(0.0,vec.x,vec.y,vec.z)), quaternionConjugate(q1));
		return new Vec3f(newPos.x,newPos.y,newPos.z);
	}
	
	
	/////////Matrix Section
	
	/**
	 * Creates a 2x2 matrix that can be used for rotation.
	 * The input is expected to be in degrees.
	 * @param rotation
	 * @return
	 */
	public static Mat2f createRotationMatrix2D(double rotation)
	{
		return new Mat2f( dcos(rotation ), dsin(rotation ),
						-dsin(rotation ), dcos(rotation ) );
	}
	
	/**
	 * Creates a 3x3 matrix that can be used for rotation.
	 * This matrix rotates around the X axis.
	 * The input is expected to be in degrees.
	 * @param rotation
	 * @return
	 */
	public static Mat3f createXRotation3D(double rotation)
	{
		return new Mat3f( 1.0, 0.0, 0.0,
						0.0, dcos(rotation),-dsin(rotation),
						0.0, dsin(rotation), dcos(rotation) );
	}
	
	/**
	 * Creates a 3x3 matrix that can be used for rotation.
	 * This matrix rotates around the Y axis.
	 * The input is expected to be in degrees.
	 * @param rotation
	 * @return
	 */
	public static Mat3f createYRotation3D(double rotation)
	{
		return new Mat3f( dcos(rotation), 0.0, dsin(rotation),
						0.0, 1.0, 0.0,
						-dsin(rotation), 0.0, dcos(rotation) );
	}
	
	/**
	 * Creates a 3x3 matrix that can be used for rotation.
	 * This matrix rotates around the Z axis.
	 * The input is expected to be in degrees.
	 * @param rotation
	 * @return
	 */
	public static Mat3f createZRotation3D(double rotation)
	{
		return new Mat3f(dcos(rotation), -dsin(rotation), 0.0,
						dsin(rotation), dcos(rotation),0.0,
						0.0, 0.0, 1.0 );
	}
	
	/**
	 * Creates a 4x4 matrix that can be used for rotation.
	 * This matrix rotates around the X axis.
	 * The input is expected to be in degrees.
	 * @param rotation
	 * @return
	 */
	public static Mat4f createXRotation4D(double rotation)
	{
		return new Mat4f( 1.0, 0.0, 0.0, 0.0,
						0.0, dcos(rotation),-dsin(rotation), 0.0,
						0.0, dsin(rotation), dcos(rotation), 0.0,
						0.0, 0.0, 0.0, 1.0);
	}
	
	/**
	 * Creates a 4x4 matrix that can be used for rotation.
	 * This matrix rotates around the Y axis.
	 * The input is expected to be in degrees.
	 * @param rotation
	 * @return
	 */
	public static Mat4f createYRotation4D(double rotation)
	{
		return new Mat4f( dcos(rotation), 0.0, dsin(rotation),0.0,
						0.0, 1.0, 0.0, 0.0,
						-dsin(rotation), 0.0, dcos(rotation), 0.0,
						0.0, 0.0, 0.0, 1.0);
	}
	
	/**
	 * Creates a 4x4 matrix that can be used for rotation.
	 * This matrix rotates around the Z axis.
	 * The input is expected to be in degrees.
	 * @param rotation
	 * @return
	 */
	public static Mat4f createZRotation4D(double rotation)
	{
		return new Mat4f(dcos(rotation), -dsin(rotation), 0.0, 0.0,
						dsin(rotation), dcos(rotation),0.0, 0.0,
						0.0, 0.0, 1.0, 0.0,
						0.0, 0.0, 0.0, 1.0);
	}
	
	/**
	 * Creates a single 3x3 matrix that can be used for rotation.
	 * This matrix rotates around all 3 axis in this order:
	 * Z, Y, X
	 * The order of rotation will result in different result with the same
	 * rotation values.
	 * @param xrot
	 * @param yrot
	 * @param zrot
	 * @return
	 */
	public static Mat3f createRotationMatrix3D(double xrot, double yrot, double zrot)
	{
		return matrixMult(createXRotation3D(xrot),matrixMult(createYRotation3D(yrot),createZRotation3D(zrot)));
	}
	
	/**
	 * Creates a single 4x4 matrix that can be used for rotation.
	 * This matrix rotates around all 3 axis in this order:
	 * Z, Y, X
	 * The order of rotation will result in different result with the same
	 * rotation values.
	 * @param xrot
	 * @param yrot
	 * @param zrot
	 * @return
	 */
	public static Mat4f createRotationMatrix4D(double xrot, double yrot, double zrot)
	{
		return matrixMult(createXRotation4D(xrot),matrixMult(createYRotation4D(yrot),createZRotation4D(zrot)));
	}
	
	/**
	 * Creates a 3D projection matrix to be used for rendering.
	 * This matrix will make things further away from the camera appear smaller
	 * than things closer to the camera. This matrix will also correctly position
	 * everything upon your screen after perspective division.
	 * Fov = Field of View
	 * Width = width of the area you are rendering to
	 * Height = height of the area you are rendering to
	 * zNear = The closest distance from which an object will be rendered.
	 * zFar = The farthest distance from which an object will be rendered.
	 * 
	 * zNear and zFar are not exactly as defined, but very close. zNear 
	 * can not be 0.
	 * 
	 * Default Values:
	 * fov is 60
	 * zNear is 10 
	 * zFar is 1000
	 * 
	 * @param fov
	 * @param width
	 * @param height
	 * @param zNear
	 * @param zFar
	 * @return
	 */
	public static Mat4f createProjectionMatrix(double fov, double width, double height, double zNear, double zFar)
	{
		
		double cotValue = 1.0/tan(degToRad(fov/2));
		double tanValue = tan(degToRad(fov/2));
		double ratio = width/height;
		double zRange = zFar - zNear;
		
		
		return new Mat4f( cotValue/ratio, 0.0, 0.0, 0.0,
							0.0, cotValue, 0.0, 0.0,
							0.0, 0.0, -zFar/zRange, -1.0,
							0.0, 0.0, -(2*zFar*zNear)/zRange, 0.0
						);
	}
	
	/**
	 * Creates a view matrix used for rendering. Apply before applying the
	 * projection matrix. The view matrix is used to shift positions and rotate
	 * positions relative to the position of the camera. Used to emulate a camera.
	 * 
	 * type is a variable that determines whether or not this is a 2D scene or 3D
	 * scene. Accepted values are GameMath.TYPE_2D or GameMath.TYPE_3D. The view
	 * matrix will be different depending on whether or not it is 2D or 3D.
	 * 
	 * GameMath.TYPE_2D = false
	 * GameMath.TYPE_3D = true
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param xRot
	 * @param yRot
	 * @param zRot
	 * @param type
	 * @return
	 */
	public static Mat4f createViewMatrix(double x, double y, double z, double xRot, double yRot, double zRot, boolean type)
	{
		
		//Done assuming that the up axis = <0,0,1>
		Vec3f axis1;
		Vec3f axis2;
		Vec3f axis3;
		
		if (type==TYPE_2D)
		{
			//-x is left, +x is right
			//-y is up, +y is down
			axis1=new Vec3f(1,0,0);
			axis2=new Vec3f(0,1,0);
			axis3=new Vec3f(0,0,1);
			
			/*
			//xrotation, twist, twist head
			axis1 = quaternionRotation( createRotationQuaternion(xRot,axis2.x, axis2.y, axis2.z), axis1);
			axis3 = quaternionRotation( createRotationQuaternion(xRot,axis2.x, axis2.y, axis2.z), axis3);
			axis2 = crossProduct(axis1, axis3);
			
			//yrotation, look up and down
			axis3 = quaternionRotation( createRotationQuaternion(yRot,axis1.x, axis1.y, axis1.z), axis3);
			axis2 = quaternionRotation( createRotationQuaternion(yRot,axis1.x, axis1.y, axis1.z), axis2);
			axis1 = crossProduct(axis3, axis2);
			
			//zrotation, look left and right
			axis1 = quaternionRotation( createRotationQuaternion(zRot,axis3.x, axis3.y, axis3.z), axis1);
			axis2 = quaternionRotation( createRotationQuaternion(zRot,axis3.x, axis3.y, axis3.z), axis2);
			axis3 = crossProduct(axis2,axis1);
			*/
		}
		else if (type==TYPE_3D)
		{
			//z axis = up
			axis1=new Vec3f(0,0,1);
			axis2=new Vec3f(-1,0,0);
			axis3=new Vec3f(0,1,0);
			
			//xrotation, twist, twist head
			axis1 = quaternionRotation( createRotationQuaternion(yRot,axis2.x, axis2.y, axis2.z), axis1);
			axis3 = quaternionRotation( createRotationQuaternion(yRot,axis2.x, axis2.y, axis2.z), axis3);
			axis2 = crossProduct(axis1, axis3);
			
			//yrotation, look up and down
			axis3 = quaternionRotation( createRotationQuaternion(xRot,axis1.x, axis1.y, axis1.z), axis3);
			axis2 = quaternionRotation( createRotationQuaternion(xRot,axis1.x, axis1.y, axis1.z), axis2);
			axis1 = crossProduct(axis3, axis2);
			
			//zrotation, look left and right
			axis1 = quaternionRotation( createRotationQuaternion(zRot,axis3.x, axis3.y, axis3.z), axis1);
			axis2 = quaternionRotation( createRotationQuaternion(zRot,axis3.x, axis3.y, axis3.z), axis2);
			axis3 = crossProduct(axis2,axis1);
			
		}
		else
		{
			axis1=new Vec3f(1,0,0);
			axis2=new Vec3f(0,0,1);
			axis3=new Vec3f(0,1,0);
		}

		//Mat4f tempMat = createRotationMatrix4D(xRot, yRot, zRot);
		
		
		Mat4f tempMat = new Mat4f(axis1.x, axis1.y, axis1.z, 0.0,
				axis2.x, axis2.y, axis2.z, 0.0,
				axis3.x, axis3.y, axis3.z, 0.0,
				0.0, 0.0, 0.0, 1.0
		);
		
		Mat4f tempMat2 = new Mat4f(1.0, 0.0, 0.0, 0.0,
									0.0, 1.0, 0.0, 0.0,
									0.0, 0.0, 1.0, 0.0,
									x, y, -z, 1.0
								);
		return matrixMult(tempMat2,tempMat);
	}
	
	/**
	 * Creates an orthographic Matrix to be used for rendering. An Orthographic
	 * matrix is best used for 2D rendering.
	 * 
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 * @return
	 */
	public static Mat4f createOrthographicMatrix(double left, double top, double right, double bottom)
	{
		double far = 1.0;
		double near = -1.0;
		
		return new Mat4f(2.0/(right-left), 0.0, 0.0, 0.0,
						0.0, 2.0/(top-bottom), 0.0, 0.0,
						0.0, 0.0, -2/(far-near), 0.0,
						(left+right)/(left-right), (bottom+top)/(bottom-top), (far+near)/(far-near), 1.0);
		
	}
	
	/**
	 * Creates an orthographic Matrix to be used for rendering. An Orthographic
	 * matrix is best used for 2D rendering.
	 * 
	 * @param width
	 * @param height
	 * @param length
	 * @return
	 */
	public static Mat4f createOrthographicMatrix(double width, double height, double length)
	{
		
		return new Mat4f(2.0/width, 0.0, 0.0, 0.0,
						0.0, 2.0/height, 0.0, 0.0,
						0.0, 0.0, -2/length, 0.0,
						-1.0, 1.0, 0.0, 1.0);
		
	}
	
	/**
	 * Creates a single matrix that can be used to translate, rotate, and 
	 * scale a point.
	 * 
	 * Returns a 4x4 Matrix.
	 * @param x
	 * @param y
	 * @param z
	 * @param xRot
	 * @param yRot
	 * @param zRot
	 * @param xScale
	 * @param yScale
	 * @param zScale
	 * @return
	 */
	public static Mat4f createModelMatrix(double x, double y, double z, double xRot, double yRot, double zRot, double xScale, double yScale, double zScale)
	{
		Mat4f rotMat = createRotationMatrix4D(xRot,yRot,zRot);
		
		Mat4f scaleMat = new Mat4f(xScale, 0.0, 0.0, 0.0,
								0.0, yScale, 0.0, 0.0,
								0.0, 0.0, zScale, 0.0,
								0.0, 0.0, 0.0, 1.0);
		Mat4f newMat = matrixMult(scaleMat,rotMat);
		
		newMat.setValue(x, 0, 3);
		newMat.setValue(y, 1, 3);
		newMat.setValue(z, 2, 3);
		
		return newMat;
		
	}
	
	/**
	 * Returns the matrix transposed.
	 * @param mat
	 * @return
	 */
	public static Mat2f getTranspose( Mat2f mat)
	{
		return new Mat2f( mat.getValue(0, 0),mat.getValue(1, 0),
				mat.getValue(0, 1),mat.getValue(1, 1) );
	}
	
	/**
	 * Returns the matrix transposed.
	 * @param mat
	 * @return
	 */
	public static Mat3f getTranspose( Mat3f mat)
	{
		return new Mat3f( mat.getValue(0, 0), mat.getValue(1, 0), mat.getValue(2, 0),
						mat.getValue(0, 1), mat.getValue(1, 1), mat.getValue(2, 1),
						mat.getValue(0, 2), mat.getValue(1, 2), mat.getValue(2, 2) );
	}
	
	/**
	 * Returns the matrix transposed.
	 * @param mat
	 * @return
	 */
	public static Mat4f getTranspose( Mat4f mat)
	{
		return new Mat4f( mat.getValue(0, 0), mat.getValue(1, 0), mat.getValue(2, 0), mat.getValue(3, 0),
						mat.getValue(0, 1), mat.getValue(1, 1), mat.getValue(2, 1), mat.getValue(3, 1),
						mat.getValue(0, 2), mat.getValue(1, 2), mat.getValue(2, 2), mat.getValue(3, 2),
						mat.getValue(0, 3), mat.getValue(1, 3), mat.getValue(2, 3), mat.getValue(3, 3) );
	}

	/**
	 * Returns the Cofactor of the matrix.
	 * @param mat
	 * @return
	 */
	public static Mat3f getCofactor( Mat3f mat )
	{
		return new Mat3f(mat.getValue(0,0),-mat.getValue(0,1),mat.getValue(0, 2),
						-mat.getValue(1,0),mat.getValue(1,1),-mat.getValue(1,2),
						mat.getValue(2,0),-mat.getValue(2,1),mat.getValue(2,2) );
	}
	
	/**
	 * Returns the Cofactor of the matrix.
	 * @param mat
	 * @return
	 */
	public static Mat4f getCofactor( Mat4f mat)
	{
		return new Mat4f( mat.getValue(0,0),-mat.getValue(0,1),mat.getValue(0, 2),-mat.getValue(0,3),
						-mat.getValue(1,0),mat.getValue(1,1),-mat.getValue(1,2),mat.getValue(1,3),
						mat.getValue(2,0),-mat.getValue(2,1),mat.getValue(2,2),-mat.getValue(2,3),
						-mat.getValue(3,0),mat.getValue(3,1),-mat.getValue(3,2),mat.getValue(3,3));
	}
	
	/**
	 * Returns the matrix of minor of the input matrix.
	 * @param mat
	 * @return
	 */
	public static Mat3f getMinor( Mat3f mat )
	{
		
		double value1 = mat.getValue(1, 1)*mat.getValue(2, 2) - mat.getValue(1, 2)*mat.getValue(2, 1);
		double value2 = mat.getValue(1, 0)*mat.getValue(2, 2) - mat.getValue(1, 2)*mat.getValue(2, 0);
		double value3 = mat.getValue(1, 0)*mat.getValue(2, 1) - mat.getValue(1, 1)*mat.getValue(2, 0);
		
		double value4 = mat.getValue(0, 1)*mat.getValue(2, 2) - mat.getValue(0, 2)*mat.getValue(2, 1);
		double value5 = mat.getValue(0, 0)*mat.getValue(2, 2) - mat.getValue(0, 2)*mat.getValue(2, 0);
		double value6 = mat.getValue(0, 0)*mat.getValue(2, 1) - mat.getValue(0, 1)*mat.getValue(2, 0);
		
		double value7 = mat.getValue(0, 1)*mat.getValue(1, 2) - mat.getValue(0, 2)*mat.getValue(1, 1);
		double value8 = mat.getValue(0, 0)*mat.getValue(1, 2) - mat.getValue(0, 2)*mat.getValue(1, 0);
		double value9 = mat.getValue(0, 0)*mat.getValue(1, 1) - mat.getValue(0, 1)*mat.getValue(1, 0);
		
		return new Mat3f( value1,value2,value3,
						value4,value5,value6,
						value7,value8,value9 );
		
	}
	
	/**
	 * Returns the matrix of minor of the input matrix.
	 * @param mat
	 * @return
	 */
	public static Mat4f getMinor(Mat4f mat)
	{
		
		double value1 = mat.getValue(1, 1)*(mat.getValue(2, 2)*mat.getValue(3, 3) - mat.getValue(2, 3)*mat.getValue(3, 2))
						-mat.getValue(1,2)*(mat.getValue(2, 1)*mat.getValue(3, 3) - mat.getValue(2, 3)*mat.getValue(3, 1))
						+mat.getValue(1,3)*(mat.getValue(2,1)*mat.getValue(3,2) - mat.getValue(2,2)*mat.getValue(3,1));

		double value2 = mat.getValue(1, 0)*(mat.getValue(2, 2)*mat.getValue(3, 3) - mat.getValue(2, 3)*mat.getValue(3, 2))
						-mat.getValue(1,2)*(mat.getValue(2, 0)*mat.getValue(3, 3) - mat.getValue(2, 3)*mat.getValue(3, 0))
						+mat.getValue(1,3)*(mat.getValue(2,0)*mat.getValue(3,2) - mat.getValue(2,2)*mat.getValue(3,0));
		
		double value3 = mat.getValue(1, 0)*(mat.getValue(2, 1)*mat.getValue(3, 3) - mat.getValue(2, 3)*mat.getValue(3, 1))
						-mat.getValue(1,1)*(mat.getValue(2, 0)*mat.getValue(3, 3) - mat.getValue(2, 3)*mat.getValue(3, 0))
						+mat.getValue(1,3)*(mat.getValue(2,0)*mat.getValue(3,1) - mat.getValue(2,1)*mat.getValue(3,0));

		double value4 = mat.getValue(1, 0)*(mat.getValue(2, 1)*mat.getValue(3, 2) - mat.getValue(2, 2)*mat.getValue(3, 1))
						-mat.getValue(1,1)*(mat.getValue(2, 0)*mat.getValue(3, 2) - mat.getValue(2, 2)*mat.getValue(3, 0))
						+mat.getValue(1,2)*(mat.getValue(2,0)*mat.getValue(3,1) - mat.getValue(2,1)*mat.getValue(3,0));
				
		double value5 = mat.getValue(0,1)*(mat.getValue(2,2)*mat.getValue(3,3) - mat.getValue(2,3)*mat.getValue(3,2))
						-mat.getValue(0,2)*(mat.getValue(2,1)*mat.getValue(3,3) - mat.getValue(2,3)*mat.getValue(3,1))
						+mat.getValue(0,3)*(mat.getValue(2,1)*mat.getValue(3,2) - mat.getValue(2,2)*mat.getValue(3,1));
		
		double value6 = mat.getValue(0,0)*(mat.getValue(2,2)*mat.getValue(3,3) - mat.getValue(2,3)*mat.getValue(3,2))
						-mat.getValue(0,2)*(mat.getValue(2,0)*mat.getValue(3,3) - mat.getValue(2,3)*mat.getValue(3,0))
						+mat.getValue(0,3)*(mat.getValue(2,0)*mat.getValue(3,2) - mat.getValue(2,2)*mat.getValue(3,0));

		double value7 = mat.getValue(0,0)*(mat.getValue(2,1)*mat.getValue(3,3) - mat.getValue(2,3)*mat.getValue(3,1))
						-mat.getValue(0,1)*(mat.getValue(2,0)*mat.getValue(3,3) - mat.getValue(2,3)*mat.getValue(3,0))
						+mat.getValue(0,3)*(mat.getValue(2,0)*mat.getValue(3,1) - mat.getValue(2,1)*mat.getValue(3,0));

		double value8 = mat.getValue(0,0)*(mat.getValue(2,1)*mat.getValue(3,2) - mat.getValue(2,2)*mat.getValue(3,1))
						-mat.getValue(0,1)*(mat.getValue(2,0)*mat.getValue(3,2) - mat.getValue(2,2)*mat.getValue(3,0))
						+mat.getValue(0,2)*(mat.getValue(2,0)*mat.getValue(3,1) - mat.getValue(2,1)*mat.getValue(3,0));
				
		double value9 = mat.getValue(0,1)*(mat.getValue(1,2)*mat.getValue(3,3) - mat.getValue(1,3)*mat.getValue(3,2))
						-mat.getValue(0,2)*(mat.getValue(1,1)*mat.getValue(3,3) - mat.getValue(1,3)*mat.getValue(3,1))
						+mat.getValue(0,3)*(mat.getValue(1,1)*mat.getValue(3,2) - mat.getValue(1,2)*mat.getValue(3,1));

		double value10 = mat.getValue(0,0)*(mat.getValue(1,2)*mat.getValue(3,3) - mat.getValue(1,3)*mat.getValue(3,2))
						-mat.getValue(0,2)*(mat.getValue(1,0)*mat.getValue(3,3) - mat.getValue(1,3)*mat.getValue(3,0))
						+mat.getValue(0,3)*(mat.getValue(1,0)*mat.getValue(3,2) - mat.getValue(1,2)*mat.getValue(3,0));

		double value11 = mat.getValue(0,0)*(mat.getValue(1,1)*mat.getValue(3,3) - mat.getValue(1,3)*mat.getValue(3,1))
						-mat.getValue(0,1)*(mat.getValue(1,0)*mat.getValue(3,3) - mat.getValue(1,3)*mat.getValue(3,0))
						+mat.getValue(0,3)*(mat.getValue(1,0)*mat.getValue(3,1) - mat.getValue(1,1)*mat.getValue(3,0));

		double value12 = mat.getValue(0,0)*(mat.getValue(1,1)*mat.getValue(3,2) - mat.getValue(1,2)*mat.getValue(3,1))
						-mat.getValue(0,1)*(mat.getValue(1,0)*mat.getValue(3,2) - mat.getValue(1,2)*mat.getValue(3,0))
						+mat.getValue(0,2)*(mat.getValue(1,0)*mat.getValue(3,1) - mat.getValue(1,1)*mat.getValue(3,0));

		double value13 = mat.getValue(0,1)*(mat.getValue(1,2)*mat.getValue(2,3) - mat.getValue(1,3)*mat.getValue(2,2))
						-mat.getValue(0,2)*(mat.getValue(1,1)*mat.getValue(2,3) - mat.getValue(1,3)*mat.getValue(2,1))
						+mat.getValue(0,3)*(mat.getValue(1,1)*mat.getValue(2,2) - mat.getValue(1,2)*mat.getValue(2,1));

		double value14 = mat.getValue(0,0)*(mat.getValue(1,2)*mat.getValue(2,3) - mat.getValue(1,3)*mat.getValue(2,2))
						-mat.getValue(0,2)*(mat.getValue(1,0)*mat.getValue(2,3) - mat.getValue(1,3)*mat.getValue(2,0))
						+mat.getValue(0,3)*(mat.getValue(1,0)*mat.getValue(2,2) - mat.getValue(1,2)*mat.getValue(2,0));

		double value15 = mat.getValue(0,0)*(mat.getValue(1,1)*mat.getValue(2,3) - mat.getValue(1,3)*mat.getValue(2,1))
						-mat.getValue(0,1)*(mat.getValue(1,0)*mat.getValue(2,3) - mat.getValue(1,3)*mat.getValue(2,0))
						+mat.getValue(0,3)*(mat.getValue(1,0)*mat.getValue(2,1) - mat.getValue(1,1)*mat.getValue(2,0));

		double value16 = mat.getValue(0,0)*(mat.getValue(1,1)*mat.getValue(2,2) - mat.getValue(1,2)*mat.getValue(2,1))
						-mat.getValue(0,1)*(mat.getValue(1,0)*mat.getValue(2,2) - mat.getValue(1,2)*mat.getValue(2,0))
						+mat.getValue(0,2)*(mat.getValue(1,0)*mat.getValue(2,1) - mat.getValue(1,1)*mat.getValue(2,0));

		return new Mat4f(value1,value2,value3,value4,
						value5,value6,value7,value8,
						value9,value10,value11,value12,
						value13,value14,value15,value16);
	}
	
	/**
	 * Returns Inverse of the matrix.
	 * @param mat
	 * @return
	 */
	public static Mat3f getInverse(Mat3f mat)
	{
		double det = 0.0;
		try
		{
			det = 1.0/mat.getDeterminate();
			return matrixMult(det ,getTranspose( getCofactor( getMinor(mat) ) ) );
		}
		catch(Exception e)
		{
			System.err.println(e);
			System.err.println("NO INVERSE OF MATRIX, Determinate == 0");
			return new Mat3f();
		}
	}
	
	/**
	 * Returns Inverse of the matrix.
	 * @param mat
	 * @return
	 */
	public static Mat4f getInverse(Mat4f mat)
	{
		double det = 0.0;
		try
		{
			det = 1.0/mat.getDeterminate();
			return matrixMult(det ,getTranspose( getCofactor( getMinor(mat) ) ) );
		}
		catch(Exception e)
		{
			System.err.println(e);
			System.err.println("NO INVERSE OF MATRIX, Determinate == 0");
			return new Mat4f();
		}
	}
	
	/**
	 * Returns Inverse of the matrix.
	 * @param mat
	 * @return
	 */
	public static Mat2f getInverse( Mat2f mat)
	{
		double det=0.0;
		try
		{
			det = 1.0/mat.getDeterminate();
		}
		catch(Exception e)
		{
			System.err.println(e);
			System.err.println("NO INVERSE OF MATRIX, Determinate == 0");
		}
		
		return new Mat2f( det*mat.getValue(1, 1),-det*mat.getValue(0, 1),
						-det*mat.getValue(1, 0),-det*mat.getValue(0, 0) );
	}
	
	/**
	 * Multiplies the 2 matrices together and returns the result.
	 * 
	 * Matrix multiplication is not commutative.
	 * matrix1 * matrix2 != matrix2 * matrix1
	 * 
	 * @param mat1
	 * @param mat2
	 * @return
	 */
	public static Mat2f matrixMult(Mat2f mat1, Mat2f mat2)
	{
		double[] tempArray = new double[4];
		tempArray[0] = dot(mat1.getValue(0, 0),mat1.getValue(0, 1),mat2.getValue(0, 0),mat2.getValue(1, 0));
		tempArray[1] = dot(mat1.getValue(0, 0),mat1.getValue(0, 1),mat2.getValue(0, 1),mat2.getValue(1, 1));
		tempArray[2] = dot(mat1.getValue(1, 0),mat1.getValue(1, 1),mat2.getValue(0, 0),mat2.getValue(1, 0));
		tempArray[3] = dot(mat1.getValue(1, 0),mat1.getValue(1, 1),mat2.getValue(0, 1),mat2.getValue(1, 1));
		
		return new Mat2f(tempArray[0],tempArray[1],
						tempArray[2],tempArray[3]);
	}
	
	/**
	 * Multiplies the 2 matrices together and returns the result.
	 * 
	 * Matrix multiplication is not commutative.
	 * matrix1 * matrix2 != matrix2 * matrix1
	 * 
	 * @param mat1
	 * @param mat2
	 * @return
	 */
	public static Mat3f matrixMult(Mat3f mat1, Mat3f mat2)
	{
		double[] tempArray = new double[9];
		tempArray[0] = dot(mat1.getValue(0, 0),mat1.getValue(0, 1),mat1.getValue(0, 2), mat2.getValue(0, 0),mat2.getValue(1, 0),mat2.getValue(2, 0));
		tempArray[1] = dot(mat1.getValue(0, 0),mat1.getValue(0, 1),mat1.getValue(0, 2), mat2.getValue(0, 1),mat2.getValue(1, 1),mat2.getValue(2, 1));
		tempArray[2] = dot(mat1.getValue(0, 0),mat1.getValue(0, 1),mat1.getValue(0, 2), mat2.getValue(0, 2),mat2.getValue(1, 2),mat2.getValue(2, 2));
		
		tempArray[3] = dot(mat1.getValue(1, 0),mat1.getValue(1, 1),mat1.getValue(1, 2), mat2.getValue(0, 0),mat2.getValue(1, 0),mat2.getValue(2, 0));
		tempArray[4] = dot(mat1.getValue(1, 0),mat1.getValue(1, 1),mat1.getValue(1, 2), mat2.getValue(0, 1),mat2.getValue(1, 1),mat2.getValue(2, 1));
		tempArray[5] = dot(mat1.getValue(1, 0),mat1.getValue(1, 1),mat1.getValue(1, 2), mat2.getValue(0, 2),mat2.getValue(1, 2),mat2.getValue(2, 2));
		
		tempArray[6] = dot(mat1.getValue(2, 0),mat1.getValue(2, 1),mat1.getValue(2, 2), mat2.getValue(0, 0),mat2.getValue(1, 0),mat2.getValue(2, 0));
		tempArray[7] = dot(mat1.getValue(2, 0),mat1.getValue(2, 1),mat1.getValue(2, 2), mat2.getValue(0, 1),mat2.getValue(1, 1),mat2.getValue(2, 1));
		tempArray[8] = dot(mat1.getValue(2, 0),mat1.getValue(2, 1),mat1.getValue(2, 2), mat2.getValue(0, 2),mat2.getValue(1, 2),mat2.getValue(2, 2));
		
		return new Mat3f(tempArray[0],tempArray[1],tempArray[2],
						tempArray[3],tempArray[4],tempArray[5],
						tempArray[6],tempArray[7],tempArray[8]);
	}
	
	/**
	 * Multiplies the 2 matrices together and returns the result.
	 * 
	 * Matrix multiplication is not commutative.
	 * matrix1 * matrix2 != matrix2 * matrix1
	 * 
	 * @param mat1
	 * @param mat2
	 * @return
	 */
	public static Mat4f matrixMult(Mat4f mat1, Mat4f mat2)
	{
		double[] tempArray = new double[16];
		tempArray[0] = dot(mat1.getValue(0, 0),mat1.getValue(0, 1),mat1.getValue(0, 2),mat1.getValue(0, 3), mat2.getValue(0, 0),mat2.getValue(1, 0),mat2.getValue(2, 0),mat2.getValue(3, 0));
		tempArray[1] = dot(mat1.getValue(0, 0),mat1.getValue(0, 1),mat1.getValue(0, 2),mat1.getValue(0, 3), mat2.getValue(0, 1),mat2.getValue(1, 1),mat2.getValue(2, 1),mat2.getValue(3, 1));
		tempArray[2] = dot(mat1.getValue(0, 0),mat1.getValue(0, 1),mat1.getValue(0, 2),mat1.getValue(0, 3), mat2.getValue(0, 2),mat2.getValue(1, 2),mat2.getValue(2, 2),mat2.getValue(3, 2));
		tempArray[3] = dot(mat1.getValue(0, 0),mat1.getValue(0, 1),mat1.getValue(0, 2),mat1.getValue(0, 3), mat2.getValue(0, 3),mat2.getValue(1, 3),mat2.getValue(2, 3),mat2.getValue(3, 3));
		
		tempArray[4] = dot(mat1.getValue(1, 0),mat1.getValue(1, 1),mat1.getValue(1, 2),mat1.getValue(1, 3), mat2.getValue(0, 0),mat2.getValue(1, 0),mat2.getValue(2, 0),mat2.getValue(3, 0));
		tempArray[5] = dot(mat1.getValue(1, 0),mat1.getValue(1, 1),mat1.getValue(1, 2),mat1.getValue(1, 3), mat2.getValue(0, 1),mat2.getValue(1, 1),mat2.getValue(2, 1),mat2.getValue(3, 1));
		tempArray[6] = dot(mat1.getValue(1, 0),mat1.getValue(1, 1),mat1.getValue(1, 2),mat1.getValue(1, 3), mat2.getValue(0, 2),mat2.getValue(1, 2),mat2.getValue(2, 2),mat2.getValue(3, 2));
		tempArray[7] = dot(mat1.getValue(1, 0),mat1.getValue(1, 1),mat1.getValue(1, 2),mat1.getValue(1, 3), mat2.getValue(0, 3),mat2.getValue(1, 3),mat2.getValue(2, 3),mat2.getValue(3, 3));
		
		tempArray[8] = dot(mat1.getValue(2, 0),mat1.getValue(2, 1),mat1.getValue(2, 2),mat1.getValue(2, 3), mat2.getValue(0, 0),mat2.getValue(1, 0),mat2.getValue(2, 0),mat2.getValue(3, 0));
		tempArray[9] = dot(mat1.getValue(2, 0),mat1.getValue(2, 1),mat1.getValue(2, 2),mat1.getValue(2, 3), mat2.getValue(0, 1),mat2.getValue(1, 1),mat2.getValue(2, 1),mat2.getValue(3, 1));
		tempArray[10] = dot(mat1.getValue(2, 0),mat1.getValue(2, 1),mat1.getValue(2, 2),mat1.getValue(2, 3), mat2.getValue(0, 2),mat2.getValue(1, 2),mat2.getValue(2, 2),mat2.getValue(3, 2));
		tempArray[11] = dot(mat1.getValue(2, 0),mat1.getValue(2, 1),mat1.getValue(2, 2),mat1.getValue(2, 3), mat2.getValue(0, 3),mat2.getValue(1, 3),mat2.getValue(2, 3),mat2.getValue(3, 3));
		
		tempArray[12] = dot(mat1.getValue(3, 0),mat1.getValue(3, 1),mat1.getValue(3, 2),mat1.getValue(3, 3), mat2.getValue(0, 0),mat2.getValue(1, 0),mat2.getValue(2, 0),mat2.getValue(3, 0));
		tempArray[13] = dot(mat1.getValue(3, 0),mat1.getValue(3, 1),mat1.getValue(3, 2),mat1.getValue(3, 3), mat2.getValue(0, 1),mat2.getValue(1, 1),mat2.getValue(2, 1),mat2.getValue(3, 1));
		tempArray[14] = dot(mat1.getValue(3, 0),mat1.getValue(3, 1),mat1.getValue(3, 2),mat1.getValue(3, 3), mat2.getValue(0, 2),mat2.getValue(1, 2),mat2.getValue(2, 2),mat2.getValue(3, 2));
		tempArray[15] = dot(mat1.getValue(3, 0),mat1.getValue(3, 1),mat1.getValue(3, 2),mat1.getValue(3, 3), mat2.getValue(0, 3),mat2.getValue(1, 3),mat2.getValue(2, 3),mat2.getValue(3, 3));
		
		return new Mat4f(tempArray[0],tempArray[1],tempArray[2],tempArray[3],
						tempArray[4],tempArray[5],tempArray[6],tempArray[7],
						tempArray[8],tempArray[9],tempArray[10],tempArray[11],
						tempArray[12],tempArray[13],tempArray[14],tempArray[15]);
	}
	
	/**
	 * Multiplies the vector by the matrix and returns the result.
	 * @param matrix
	 * @param pos
	 * @return
	 */
	public static Vec2f matrixMultVec(Mat2f matrix, Vec2f pos)
	{
		double xx = dot(matrix.getValue(0),matrix.getValue(1),pos.x,pos.y);
		double yy = dot(matrix.getValue(2),matrix.getValue(3),pos.x,pos.y);
		
		return new Vec2f(xx,yy);
	}
	
	/**
	 * Multiplies the vector by the matrix and returns the result.
	 * @param matrix
	 * @param pos
	 * @return
	 */
	public static Vec3f matrixMultVec(Mat3f matrix, Vec3f pos)
	{
		double xx = dot(matrix.getValue(0),matrix.getValue(1),matrix.getValue(2),pos.x,pos.y,pos.z);
		double yy = dot(matrix.getValue(3),matrix.getValue(4),matrix.getValue(5),pos.x,pos.y,pos.z);
		double zz = dot(matrix.getValue(6),matrix.getValue(7),matrix.getValue(8),pos.x,pos.y,pos.z);
		
		return new Vec3f(xx,yy,zz);
	}
	
	/**
	 * Multiplies the vector by the matrix and returns the result.
	 * @param matrix
	 * @param pos
	 * @return
	 */
	public static Vec4f matrixMultVec(Mat4f matrix, Vec4f pos)
	{
		double xx = dot(matrix.getValue(0),matrix.getValue(1),matrix.getValue(2),matrix.getValue(3),pos.x,pos.y,pos.z,pos.w);
		double yy = dot(matrix.getValue(4),matrix.getValue(5),matrix.getValue(6),matrix.getValue(7),pos.x,pos.y,pos.z,pos.w);
		double zz = dot(matrix.getValue(8),matrix.getValue(9),matrix.getValue(10),matrix.getValue(11),pos.x,pos.y,pos.z,pos.w);
		double ww = dot(matrix.getValue(12),matrix.getValue(13),matrix.getValue(14),matrix.getValue(15),pos.x,pos.y,pos.z,pos.w);
		
		return new Vec4f(xx,yy,zz,ww);
	}
	
	/**
	 * Multiplies the matrix by a single value and returns the result.
	 * @param a
	 * @param mat
	 * @return
	 */
	public static Mat2f matrixMult(double a, Mat2f mat)
	{
		return new Mat2f( a*mat.getValue(0, 0), a*mat.getValue(0, 1),
						a*mat.getValue(1, 0), a*mat.getValue(1, 1) );
	}
	
	/**
	 * Multiplies the matrix by a single value and returns the result.
	 * @param a
	 * @param mat
	 * @return
	 */
	public static Mat3f matrixMult(double a, Mat3f mat)
	{
		return new Mat3f( a*mat.getValue(0, 0), a*mat.getValue(0, 1), a*mat.getValue(0, 2),
						a*mat.getValue(1, 0), a*mat.getValue(1, 1), a*mat.getValue(1, 2),
						a*mat.getValue(2, 0), a*mat.getValue(2, 1), a*mat.getValue(2, 2) );
	}
	
	/**
	 * Multiplies the matrix by a single value and returns the result.
	 * @param a
	 * @param mat
	 * @return
	 */
	public static Mat4f matrixMult(double a, Mat4f mat)
	{
		return new Mat4f( a*mat.getValue(0, 0), a*mat.getValue(0, 1), a*mat.getValue(0, 2), a*mat.getValue(0, 3),
						a*mat.getValue(1, 0), a*mat.getValue(1, 1), a*mat.getValue(1, 2), a*mat.getValue(1, 3),
						a*mat.getValue(2, 0), a*mat.getValue(2, 1), a*mat.getValue(2, 2), a*mat.getValue(2, 3),
						a*mat.getValue(3, 0), a*mat.getValue(3, 1), a*mat.getValue(3, 2), a*mat.getValue(3, 3) );
	}
	
	/**
	 * Adds the two matrices together and returns the result.
	 * @param mat1
	 * @param mat2
	 * @return
	 */
	public static Mat2f matrixAdd(Mat2f mat1, Mat2f mat2)
	{
		return new Mat2f(mat1.getValue(0)+mat2.getValue(0), mat1.getValue(1)+mat2.getValue(1),
						mat1.getValue(2)+mat2.getValue(2), mat1.getValue(3)+mat2.getValue(3) );
	}
	
	/**
	 * Adds the two matrices together and returns the result.
	 * @param mat1
	 * @param mat2
	 * @return
	 */
	public static Mat3f matrixAdd(Mat3f mat1, Mat3f mat2)
	{
		return new Mat3f(mat1.getValue(0)+mat2.getValue(0), mat1.getValue(1)+mat2.getValue(1), mat1.getValue(2)+mat2.getValue(2),
						mat1.getValue(3)+mat2.getValue(3), mat1.getValue(4)+mat2.getValue(4), mat1.getValue(5)+mat1.getValue(5),
						mat1.getValue(6)+mat2.getValue(6), mat1.getValue(7)+mat2.getValue(7), mat1.getValue(8)+mat2.getValue(8));
	}
	
	/**
	 * Adds the two matrices together and returns the result.
	 * @param mat1
	 * @param mat2
	 * @return
	 */
	public static Mat4f matrixAdd(Mat4f mat1, Mat4f mat2)
	{
		return new Mat4f(mat1.getValue(0)+mat2.getValue(0), mat1.getValue(1)+mat2.getValue(1), mat1.getValue(2)+mat2.getValue(2), mat1.getValue(3)+mat2.getValue(3), 
						mat1.getValue(4)+mat2.getValue(4), mat1.getValue(5)+mat1.getValue(5), mat1.getValue(6)+mat2.getValue(6), mat1.getValue(7)+mat2.getValue(7), 
						mat1.getValue(8)+mat2.getValue(8), mat1.getValue(9)+mat2.getValue(9), mat1.getValue(10)+mat2.getValue(10), mat1.getValue(11)+mat2.getValue(11),
						mat1.getValue(12)+mat2.getValue(12), mat1.getValue(13)+mat2.getValue(13), mat1.getValue(14)+mat1.getValue(14), mat1.getValue(15)+mat2.getValue(15));
	}
	
	/**
	 * Subtracts the two matrices and returns the result.
	 * @param mat1
	 * @param mat2
	 * @return
	 */
	public static Mat2f matrixSub(Mat2f mat1, Mat2f mat2)
	{
		return new Mat2f(mat1.getValue(0)-mat2.getValue(0), mat1.getValue(1)-mat2.getValue(1),
						mat1.getValue(2)-mat2.getValue(2), mat1.getValue(3)-mat2.getValue(3) );
	}
	
	/**
	 * Subtracts the two matrices and returns the result.
	 * @param mat1
	 * @param mat2
	 * @return
	 */
	public static Mat3f matrixSub(Mat3f mat1, Mat3f mat2)
	{
		return new Mat3f(mat1.getValue(0)-mat2.getValue(0), mat1.getValue(1)-mat2.getValue(1), mat1.getValue(2)-mat2.getValue(2),
						mat1.getValue(3)-mat2.getValue(3), mat1.getValue(4)-mat2.getValue(4), mat1.getValue(5)-mat1.getValue(5),
						mat1.getValue(6)-mat2.getValue(6), mat1.getValue(7)-mat2.getValue(7), mat1.getValue(8)-mat2.getValue(8));
	}
	
	/**
	 * Subtracts the two matrices and returns the result.
	 * @param mat1
	 * @param mat2
	 * @return
	 */
	public static Mat4f matrixSub(Mat4f mat1, Mat4f mat2)
	{
		return new Mat4f(mat1.getValue(0)-mat2.getValue(0), mat1.getValue(1)-mat2.getValue(1), mat1.getValue(2)-mat2.getValue(2), mat1.getValue(3)-mat2.getValue(3), 
						mat1.getValue(4)-mat2.getValue(4), mat1.getValue(5)-mat1.getValue(5), mat1.getValue(6)-mat2.getValue(6), mat1.getValue(7)-mat2.getValue(7), 
						mat1.getValue(8)-mat2.getValue(8), mat1.getValue(9)-mat2.getValue(9), mat1.getValue(10)-mat2.getValue(10), mat1.getValue(11)-mat2.getValue(11),
						mat1.getValue(12)-mat2.getValue(12), mat1.getValue(13)-mat2.getValue(13), mat1.getValue(14)-mat1.getValue(14), mat1.getValue(15)-mat2.getValue(15));
	}
	
}