package openGLEngine;


import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

public class Mouse 
{

	public boolean[] mouseUp = new boolean[12];
	public boolean[] mouseDown = new boolean[12];
	public boolean[] mousePressed = new boolean[12];
	
	public double wheelY=0;
	public double wheelX=0;
	
	public double mouseX=0;
	public double mouseY=0;
	
	//0 = left
	//1 = right
	//2 = middle
	
	public GLFWScrollCallback scrollWheel = new GLFWScrollCallback() {

		@Override
		public void invoke(long window, double xoffset, double yoffset) {
			// TODO Auto-generated method stub
			wheelY=yoffset;
			wheelX=xoffset;
		}
		
	};
	
	public GLFWCursorPosCallback mousePosition = new GLFWCursorPosCallback() {

		@Override
		public void invoke(long window, double xpos, double ypos) {
			// TODO Auto-generated method stub
			mouseX=xpos;
			mouseY=ypos;
		}
		
		
	};
	
	public GLFWMouseButtonCallback mouseButtons = new GLFWMouseButtonCallback() {
		@Override
		public void invoke(long window, int key, int action, int mods) {
			// TODO Auto-generated method stub
			if(action==0)
			{
				//release
				setMouseUp(key);
			}
			if(action==1)
			{
				//pressed
				setMousePressed(key);
			}
		}
	};
	
	public void update()
	{
		for(int i=0;i<mouseUp.length;i++)
		{
			if (mouseDown[i]==true)
			{
				mousePressed[i]=false;
				mouseUp[i]=false;
			}
			else if (mouseUp[i]==true)
			{
				mouseUp[i]=false;
				mousePressed[i]=false;
				mouseDown[i]=false;
			}
		}
		
		wheelX=0;
		wheelY=0;
	}
	
	private void setMouseUp(int button)
	{
		mouseUp[button]=true;
		mouseDown[button]=false;
		mousePressed[button]=false;
	}
	
	private void setMousePressed(int button)
	{
		mousePressed[button]=true;
		mouseDown[button]=true;
		mouseUp[button]=false;
	}
	
	public boolean getMouseDown(int button)
	{
		return mouseDown[button];
	}
	public boolean getMousePressed(int button)
	{
		return mousePressed[button];
	}
	public boolean getMouseUp(int button)
	{
		return mouseUp[button];
	}
	
	public boolean getMouseWheelUp()
	{
		return (wheelY==1.0);
	}
	
	public boolean getMouseWheelDown()
	{
		return (wheelY==-1.0);
	}
	
	public boolean getMouseWheelLeft()
	{
		return (wheelX==-1.0);
	}
	
	public boolean getMouseWheelRight()
	{
		return (wheelX==1.0);
	}
	
	public double getMouseX()
	{
		return mouseX;
	}
	
	public double getMouseY()
	{
		return mouseY;
	}
}