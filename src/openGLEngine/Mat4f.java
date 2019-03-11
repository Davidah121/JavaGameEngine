package openGLEngine;

import java.io.Serializable;

public class Mat4f implements Serializable{
	
	private double[] matrix = new double[16];
	
	public static Mat4f getIdentityMatrix()
	{
		Mat4f mMat = new Mat4f(1,0,0,0,
								0,1,0,0,
								0,0,1,0,
								0,0,0,1);
		return mMat;
	}
	/**
	 * Creates a new Square 4x4 Matrix. The values are initialized
	 * to 0.
	 * Values are stored in a one dimensional array.
	 */
	public Mat4f()
	{
		///
	}
	
	/**
	 * Creates a new Square 3x3 Matrix.
	 * Values are stored in a one dimensional array.
	 * @param x1
	 * @param x2
	 * @param x3
	 * @param x4
	 * @param x5
	 * @param x6
	 * @param x7
	 * @param x8
	 * @param x9
	 * @param x10
	 * @param x11
	 * @param x12
	 * @param x13
	 * @param x14
	 * @param x15
	 * @param x16
	 */
	public Mat4f( double x1, double x2, double x3, double x4, 
				double x5, double x6, double x7, double x8, 
				double x9, double x10, double x11, double x12,
				double x13, double x14, double x15, double x16)
	{
		this.matrix[0]=x1;
		this.matrix[1]=x2;
		this.matrix[2]=x3;
		this.matrix[3]=x4;
		
		this.matrix[4]=x5;
		this.matrix[5]=x6;
		this.matrix[6]=x7;
		this.matrix[7]=x8;
		
		this.matrix[8]=x9;
		this.matrix[9]=x10;
		this.matrix[10]=x11;
		this.matrix[11]=x12;
		
		this.matrix[12]=x13;
		this.matrix[13]=x14;
		this.matrix[14]=x15;
		this.matrix[15]=x16;
		
	}
	
	public Mat4f(Mat4f other)
	{
		for(int i=0; i<other.matrix.length; i++)
		{
			matrix[i]=other.matrix[i];
		}
	}
	
	/**
	 * Gets the value at the specified index.
	 * @param index
	 * @return double
	 */
	public double getValue(int index)
	{
		return matrix[index];
	}
	
	/**
	 * Sets the value at the specified index.
	 * @param value
	 * @param index
	 */
	public void setValue(double value, int index)
	{
		this.matrix[index] = value;
	}
	
	/**
	 * Gets the value at the specified row and column.
	 * @param row
	 * @param column
	 * @return double
	 */
	public double getValue(int row, int column)
	{
		return matrix[column + row*4];
	}
	
	/**
	 * Sets the value at the specified row and column.
	 * @param value
	 * @param row
	 * @param column
	 */
	public void setValue(double value, int row, int column)
	{
		this.matrix[column + row*4] = value;
	}
	
	/**
	 * Gets the determinate of this matrix.
	 * @return double
	 */
	public double getDeterminate()
	{
		
		double value1 = matrix[5]*(matrix[10]*matrix[15] - matrix[11]*matrix[14]);
		double value2 = matrix[6]*(matrix[9]*matrix[15] - matrix[11]*matrix[13]);
		double value3 = matrix[7]*(matrix[9]*matrix[14] - matrix[10]*matrix[13]);
		
		double value4 = matrix[4]*(matrix[10]*matrix[15] - matrix[11]*matrix[14]);
		double value5 = matrix[6]*(matrix[8]*matrix[15] - matrix[11]*matrix[12]);
		double value6 = matrix[7]*(matrix[8]*matrix[14] - matrix[10]*matrix[12]);
		
		double value7 = matrix[4]*(matrix[9]*matrix[15] - matrix[11]*matrix[13]);
		double value8 = matrix[5]*(matrix[8]*matrix[15] - matrix[11]*matrix[12]);
		double value9 = matrix[7]*(matrix[8]*matrix[13] - matrix[9]*matrix[12]);
		
		double value10 = matrix[4]*(matrix[9]*matrix[14] - matrix[10]*matrix[13]);
		double value11 = matrix[5]*(matrix[8]*matrix[14] - matrix[10]*matrix[12]);
		double value12 = matrix[6]*(matrix[8]*matrix[13] - matrix[9]*matrix[12]);
		
		
		
		return matrix[0]*(value1-value2+value3) - matrix[1]*(value4-value5+value6) + matrix[2]*(value7-value8+value9) - matrix[3]*(value10-value11+value12);
	}
	
	/**
	 * Returns this matrix as an array.
	 * @return double[]
	 */
	public double[] getRawArray()
	{
		return matrix;
	}
	
	/**
	 * Returns this matrix as a float array.
	 * @return float[]
	 */
	public float[] getRawArrayFloat()
	{
		float[] tempMat = new float[16];
		
		for(int i=0;i<16;i++)
		{
			tempMat[i]=(float)matrix[i];
		}
		return tempMat;
	}
}