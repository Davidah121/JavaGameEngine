package openGLEngine;

import java.io.Serializable;

public class Mat3f implements Serializable{
	
	private double[] matrix = new double[9];
	
	static Mat3f getIdentityMatrix()
	{
		Mat3f mMat = new Mat3f(1,0,0,
								0,1,0,
								0,0,1);
		return mMat;
	}
	/**
	 * Creates a new Square 3x3 Matrix. The values are initialized
	 * to 0.
	 * Values are stored in a one dimensional array.
	 */
	public Mat3f()
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
	 */
	public Mat3f( double x1, double x2, double x3, 
				double x4, double x5, double x6,
				double x7, double x8, double x9)
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
	}
	
	public Mat3f(Mat3f other)
	{
		for(int i=0; i<other.matrix.length; i++)
		{
			matrix[i]=other.matrix[i];
		}
	}
	
	/**
	 * Gets the value at the specified row and column.
	 * @param row
	 * @param column
	 * @return double
	 */
	public double getValue(int row, int column)
	{
		return matrix[column + row*3];
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
		this.matrix[index]=value;
	}
	
	/**
	 * Sets the value at the specified row and column.
	 * @param value
	 * @param row
	 * @param column
	 */
	public void setValue(double value, int row, int column)
	{
		this.matrix[column + row*3] = value;
	}
	
	/**
	 * Gets the determinate of this matrix.
	 * @return double
	 */
	public double getDeterminate()
	{
		double value1 = matrix[0]*(matrix[4]*matrix[8] - matrix[5]*matrix[7]);
		
		double value2 = matrix[1]*(matrix[3]*matrix[8] - matrix[5]*matrix[6]);
		
		double value3 = matrix[2]*(matrix[3]*matrix[7] - matrix[4]*matrix[6]);
		
		return value1-value2+value3;
	}
	
	/**
	 * Returns the matrix as an array
	 * @return double[]
	 */
	public double[] getRawArray()
	{
		return matrix;
	}
	
	/**
	 * Returns the matrix as a float array.
	 * @return float[]
	 */
	public float[] getRawArrayFloat()
	{
		float[] tempMat = new float[9];
		
		for(int i=0;i<9;i++)
		{
			tempMat[i]=(float)matrix[i];
		}
		return tempMat;
	}
}