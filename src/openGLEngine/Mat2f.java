package openGLEngine;

public class Mat2f {
	
	private double[] matrix = new double[4];
	
	/**
	 * Creates a new Square 2x2 Matrix. The values are initialized
	 * to 0.
	 * Values are stored in a one dimensional array.
	 */
	public Mat2f()
	{
		///
	}
	
	/**
	 * Creates a new Square 2x2 Matrix. With the values specified.
	 * Values are stored in a one dimensional array.
	 * @param x1
	 * @param x2
	 * @param x3
	 * @param x4
	 */
	public Mat2f( double x1, double x2, double x3, double x4)
	{
		this.matrix[0]=x1;
		this.matrix[1]=x2;
		
		this.matrix[2]=x3;
		this.matrix[3]=x4;
	}
	
	/**
	 * Gets the value at the specified index.
	 * @param index
	 * @return
	 */
	public double getValue(int index)
	{
		return this.matrix[index];
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
		return this.matrix[column + row*2];
	}
	
	/**
	 * Sets the value at the specified row and column.
	 * @param value
	 * @param row
	 * @param column
	 */
	public void setValue(double value, int row, int column)
	{
		this.matrix[column + row*2] = value;
	}
	
	/**
	 * Gets the determinate of this matrix.
	 * @return double
	 */
	public double getDeterminate()
	{
		return matrix[0]*matrix[3] - matrix[1]*matrix[2];
	}
	
	/**
	 * Gets an array version of this matrix.
	 * @return double[]
	 */
	public double[] getRawArray()
	{
		return matrix;
	}
	
	/**
	 * Gets an float array version of this matrix.
	 * @return float[]
	 */
	public float[] getRawArrayFloat()
	{
		float[] tempMat = new float[4];
		
		for(int i=0;i<4;i++)
		{
			tempMat[i]=(float)matrix[i];
		}
		return tempMat;
	}
}