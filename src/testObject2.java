import openGLEngine.*;

public class testObject2 extends parentGameObject {

	private Vec4f color = new Vec4f(1f,0f,0f,1f);
	
	public testObject2()
	{
		levelEditCol = new Circle(0,0,16);
		//guiThing();
	}
	/*
	public void guiThing()
	{
		editParts.add( GuiElement.createButton(Display.getWidth()-192, 36, Display.getWidth()-160, 52));
		editParts.get(0).setDescription("COLOR: ");
		editParts.get(0).setDescriptionOffset(36, -4);
	}
	
	public void guiUpdate()
	{
		editParts.get(0).setDescription("COLOR: "+color.colorToHexString());
		
		if(editParts.get(0).switchValue==true)
		{
			String temp = Game.createInputPopUp("COLOR");
			color.x = GameMath.parseFloat(temp);
			
			editParts.get(0).switchValue=false;
		}
		
	}
	*/
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		//System.out.println(color.x+", "+color.y+", "+color.z);
		GameRender.setColor(color);
		GameRender.drawCircle((int)position.x, (int)position.y, 16, false);
	}

}
