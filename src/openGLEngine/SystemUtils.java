package openGLEngine;

import java.io.*;

public class SystemUtils {

	/**
	 * This is a generic way to have a true copy of an object. At least so far.
	 * 
	 * This is not a "shallow copy". A shallow copy behaves kinda like a pointer.
	 * All primitives in a shallow copy are separated but non primitives aren't. They
	 * will be modified by both objects.
	 * 
	 * This is a "deep copy". A deep copy behaves like what you'd expect it too.
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T copyObject(T object)
	{
		try
		{
			ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectDataStream = new ObjectOutputStream(byteOutputStream);
			
			objectDataStream.writeObject(object);
			
			ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteOutputStream.toByteArray());
			ObjectInputStream newObjectDataStream = new ObjectInputStream(byteInputStream);
			
			return (T) newObjectDataStream.readObject();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
