
import openGLEngine.*;

public class MainGame {
	
	public static void main(String[] args)
	{
		Game.init(1280, 720, "TITLE", false);
		
		Resources.init();
		
		//Game.addObject( new Obj3() );
		Game.addObject( new randomObject() );
		Game.run();
		
		Game.end();
		
	}

}
