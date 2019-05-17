package openGLEngine;

public class RefObject<T> {

	public T value;
	public RefObject()
	{
		
	}
	public RefObject(T value)
	{
		this.value = value;
	}
	
	public static void swap(RefObject a, RefObject b)
	{
		RefObject v3 = new RefObject(b.value);
		b.value = a.value;
		a.value = v3.value;
	}
}
