package openGLEngine;
import java.io.*;

public class quickIO
{
	private FileInputStream inFile;
	private InputStream localFile;
	private FileOutputStream outFile;
	private String FileName;
	private int type;
	
	public static final int TYPE_READ = 0;
	public static final int TYPE_WRITE = 1;
	public static final int TYPE_WRITE_APPEND = 2;
	
	/**
	 * Creates a quickIO object that reads a file or writes to a file.
	 * name refers to the file name.
	 * type refers to whether this file will be opened for reading or writing.
	 * 
	 * Possible values for type are TYPE_READ, TYPE_WRITE, or TYPE_WRITE_APPEND.
	 * @param name
	 * @param type
	 */
	public quickIO(String name, int type)
	{
		this.FileName=name;
		this.type=type;
		File file = new File(name);
		
		if (type==0)
		{
			try
			{
				if(file.exists())
					inFile = new FileInputStream(file); //Found the file outside the jarfile
				else
					localFile = ClassLoader.getSystemClassLoader().getResourceAsStream(file.toString()); //Search inside the jar file for the file.
			}
			catch(Exception e)
			{
				System.out.println(e);
				inFile = null;
				localFile = null;
			}
		}
		else if (type==1)
		{
			try
			{
				outFile = new FileOutputStream(name,false);
			}
			catch(Exception e)
			{
				System.out.println(e);
				outFile = null;
			}
		}
		else if (type==2)
		{
			try
			{
				outFile = new FileOutputStream(name,true);
			}
			catch(Exception e)
			{
				System.out.println(e);
				outFile = null;
			}
		}
		
	}
	
	/**
	 * Returns the file name of the file that was opened.
	 * @return String
	 */
	public String getFileName()
	{
		return FileName;
	}
	
	/**
	 * Returns the amount of characters left in this file.
	 * Returns -1 if the file is not opened for reading.
	 * @return int
	 */
	public int dataLeft()
	{
		if(type==0)
		{
			try
			{
				if(inFile!=null)
					return ( inFile.available() );
				else
					return ( localFile.available() );
			}
			catch(Exception e)
			{
				System.out.println(e);
				return 0;
			}
		}
		else
		{
			System.out.println("File is not opened for reading.");
			return -1;
		}
	}
	
	/**
	 * Determines if the end of the file has been reached.
	 * Returns true if the file is not opened for reading.
	 * @return boolean
	 */
	public boolean endOfFile()
	{
		if(type==0)
		{
			try
			{
				if(inFile!=null)
					return ( inFile.available() <= 0 );
				else
					return ( localFile.available() <= 0 );
			}
			catch(Exception e)
			{
				System.out.println(e);
				return true;
			}
		}
		else
		{
			System.out.println("File is not opened for reading.");
			return true;
		}
	}
	
	/**
	 * Reads a byte from the file.
	 * Returns 0 if any exception occurs or the file is not opened for
	 * reading.
	 * @return int
	 */
	public int readByte()
	{
		if (type==0)
		{
			try
			{
				if(inFile!=null)
					return inFile.read();
				else
					return localFile.read();
			}
			catch(Exception e)
			{
				System.out.println(e);
				return 0;
			}
		}
		else
		{
			System.out.println("File is not opened for reading.");
			return 0;
		}
	}
	
	/**
	 * Reads the next character and returns it as a String.
	 * @return String
	 */
	public String readNext()
	{
		
		char c = 'a';
		String text = "";
		
		if(type==0)
		{
			try
			{
				if(inFile!=null)
					c = (char)inFile.read();
				else
					c = (char)localFile.read();
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
			text+=c;
		}
		else
		{
			System.out.println("File is not opened for reading.");
		}
		
		return text;
	}
	
	/**
	 * Reads the next line and returns it as a String.
	 * @return String
	 */
	public String readNextLn()
	{
		
		String text = "";
		int byteData=0;
		
		boolean hasReadData=false;
		
		if (type==0)
		{
			if(inFile!=null)
			{
				try
				{
					while( inFile.available() > 0 )
					{
						try
						{
							byteData=inFile.read();
							
							if (byteData>=32)
							{
								text += (char)byteData;
								hasReadData=true;
							}
							else
							{
								if (hasReadData==true)
									break;
							}
							
						}
						catch(Exception e)
						{
							System.out.println(e);
							break;
						}
					}
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
			}
			else
			{
				try
				{
					while( localFile.available() > 0 )
					{
						try
						{
							byteData=localFile.read();
							
							if (byteData>=32)
							{
								text += (char)byteData;
								hasReadData=true;
							}
							else
							{
								if (hasReadData==true)
									break;
							}
							
						}
						catch(Exception e)
						{
							System.out.println(e);
							break;
						}
					}
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
			}
		}
		else
		{
			System.out.println("File is not opened for reading.");
			return null;
		}
		
		return text;
	}
	
	/**
	 * Writes the input text to the file.
	 * Does nothing if the file is not opened for writing.
	 * @param str
	 */
	public void write(String str)
	{
		if (type==1 || type==2)
		{
			byte[] m = str.getBytes();
			try
			{
				outFile.write(m);
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
		else
		{
			System.out.println("File is not opened for writing.");
		}
	}
	
	/**
	 * Writes a line break to the file. The line break used are the two
	 * bytes 13 and 10.
	 * Does nothing if the file is not opened for writing.
	 */
	public void writeln()
	{
		if (type==1 || type==2)
		{
			try
			{
				byte[] m = new byte[]{13,10};
				outFile.write(m);
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
		else
		{
			System.out.println("File is not opened for writing.");
		}
	}
	
	/**
	 * Closes the file.
	 */
	public void close()
	{
		if (type==0)
		{
			try
			{
				if(inFile!=null)
					inFile.close();
				else
					localFile.close();
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
		else if (type==1 || type==2)
		{
			try
			{
				outFile.close();
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
	}
}