
import java.util.ArrayList;

import openGLEngine.*;

public class LevelTestObject extends parentGameObject {

	//AddDeleteWindow ad = new AddDeleteWindow();
	public GuiElement list;
	public ArrayList<String> text = new ArrayList<String>();
	
	public LevelTestObject()
	{
		text.add("1");
		text.add("fds");
		text.add("0xdf");
		text.add("234");
		text.add("gqsd");
		text.add("lhow");
		text.add("943");
		text.add("uu53");
		text.add("uxfd");
		text.add("powe");
		text.add("butt");
		text.add("fgws");
		text.add("hjr");
		text.add("jfdksjoqpeofjoicj");
		text.add("public class");
		text.add("class AddDeleteWindow.java");
		text.add("new class of whatever may be needed");
		text.add("joioijoij");
		
		list = GuiElement.createList(0, 0, 128, 32, text);
		//Game.addGuiElement(list);
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
		System.out.println(Game.getGuiListSize());
		for(int i=0;i<Game.getGuiListSize();i++)
		{
			Game.getGuiElement(i).update( new Point(Input.getMouseX(), Input.getMouseY()));
		}
		
		//System.out.println(Level.getObjectClass(ad.getSelectedElement()));
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		for(int i=0;i<Game.getGuiListSize();i++)
			Game.getGuiElement(i).render();		
	}

}
