
import java.lang.reflect.Array;
import java.lang.reflect.Field;

import glLevelEditor.*;
import openGLEngine.*;
import java.util.*;

/*
 * Purpose:
 * 		To add objects into a level, save that level, load levels,
 * 		and use them in an actual game.
 * Requirements:
 * 		Can add an object, delete an object, set basic values for an object,
 * 		set unique values for objects, and add tiles.
 * 		Must be able to save and load levels.
 */

/*
 * 1st priority
 * Set up adding objects and deleting objects
 * 
 * 2nd priority
 * Set up editing objects for basic values
 * 
 * 3rd priority
 * Set up saving and loading
 * 
 * 4th priority
 * Set up editing objects for unique values
 * 
 * 5th priority
 * Add a camera to move around the scene
 * 
 * 6th priority
 * Add tile editing
 * 
 */
public class MainGame {
	
	public static void m()
	{
		VecObj pos1 = new VecObj(1,2);
		VecObj pos2 = SystemUtils.copyObject(pos1);
		
		
		System.out.println(pos1.pos.x);
		System.out.println(pos1.pos.y);
		
		System.out.println(pos2.pos.x);
		System.out.println(pos2.pos.y);
		
		pos1.pos.x = 3;
		pos1.pos.y = 32;
		
		System.out.println(pos1.pos.x);
		System.out.println(pos1.pos.y);
		
		System.out.println(pos2.pos.x);
		System.out.println(pos2.pos.y);
	}
	
	public static void m2(String arg1)
	{
		arg1 = "TEST2";
	}
	
	public static void main(String[] args)
	{
		
		Game.init(1280, 720, "TITLE", false);
		
		Resources.init();
		
		Game.setAutoUpdate(false);
		Game.setAutoRender(false);
		Game.setControlObject(new BasicEditor() );
		
		//((BasicEditor)Game.getControlObject()).addObject( new testObject() );
		
		//m();
		
		//Game.addObject( new randomObject() );
		Game.run();
		
		Game.end();
		
		/*
		EntityProcessor.processEntity( new randomObject() );
		RefObject<Integer> a = new RefObject<Integer>(0);
		RefObject<Integer> b = new RefObject<Integer>(1);
		
		System.out.println(a.value+","+b.value);
		RefObject.swap(a,b);
		System.out.println(a.value+","+b.value);
		*/
	}

}
