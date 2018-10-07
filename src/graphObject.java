import java.util.ArrayList;

import openGLEngine.GameMath;
import openGLEngine.GameRender;
import openGLEngine.parentGameObject;
import openGLEngine.quickIO;

public class graphObject extends parentGameObject {

	private ArrayList<Integer> dataPoints = new ArrayList<Integer>();
	
	public graphObject()
	{
		quickIO file = new quickIO("C:\\Users\\Alan\\Desktop\\DataPoints.txt", quickIO.TYPE_READ);
		
		String text = file.readNextLn();
		String[] splitText = text.split(", ");
		
		for(int i=0; i<splitText.length; i++)
		{
			dataPoints.add( GameMath.parseInt(splitText[i]));
			
			System.out.println(dataPoints.get( dataPoints.size()-1));
		}
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
		GameRender.setColor(1f, 0f, 0f, 1f);
		for(int i=0; i<dataPoints.size(); i++)
		{
			GameRender.drawCircle(i*8, -dataPoints.get(i)+300, 2, false);
		}
		
		GameRender.setColor(0f, 1f, 0f, 1f);
		for(int i=0; i<dataPoints.size()-1; i++)
		{
			GameRender.drawLine(i*8, -dataPoints.get(i)+300, (i+1)*8, -dataPoints.get(i+1)+300);
		}
	}

}
