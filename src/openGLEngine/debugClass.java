package openGLEngine;

public class debugClass {

	public final static String createCommand = "/create";
	public final static String saveCommand = "/save";
	public final static String loadCommand = "/load";
	public final static String playCommand = "/play";
	public final static String pauseCommand = "/pause";
	public final static String helpCommand = "/help";
	public final static String debugCommand = "/debug";
	public final static String levelEditCommand = "/leveledit";
	public final static String deleteCommand = "/delete";
	
	private static boolean inLevelEdit = false;
	private static boolean inDebugMode = false;
	private static boolean enabled = false;
	private static boolean paused = false;
	private static String text = "";
	private static String outputText = "";
	
	private static final char lineBreak = 15;
	
	public static boolean isPaused()
	{
		return paused;
	}
	
	public static boolean isInDebugMode()
	{
		return inDebugMode;
	}
	
	public static void update()
	{
		
		if (enabled==true)
		{
			if( Input.getKeyboardHasBeenUsed() )
			{
				if( (int)Input.getLastKey() >=32 && (int)Input.getLastKey()<=127 )
				{
					text+=Input.getLastKey();
					text = text.toLowerCase();
				}
				
				if( Input.getLastKey()==Input.VK_ENTER )
				{
					String[] splitText = text.split(" ");
					
					if(text.equals(pauseCommand))
					{
						paused=true;
						outputText = "Pausing Update System";
					}
					else if(text.equals(playCommand))
					{
						paused=false;
						outputText = "UnPausing Update System";
					}
					else if(splitText[0].equals(saveCommand))
					{
						if(!splitText[1].equals(""))
						{
							//Level l = new Level();
							//l.saveLevel();
							
							//l.exportLevel(splitText[1]);
							
							outputText = "Saved and Exported Level";
						}
						else
						{
							outputText = "No file specified after load command.";
						}
					}
					else if(splitText[0].equals(loadCommand))
					{
						if(!splitText[1].equals(""))
						{
							Level l = new Level();
							
							l.loadExternalLevel(splitText[1]);
							
							l.useLevel();
							
							outputText = "Loaded Level";
						}
						else
						{
							outputText = "No file specified after load command.";
						}
					}
					else if(splitText[0].equals(debugCommand))
					{
						if(splitText.length>1)
						{
							if(splitText[1].equals("on"))
							{
								inDebugMode=true;
								outputText = "Turning on Debug Mode";
							}
							else if(splitText[1].equals("off"))
							{
								inDebugMode=false;
								outputText = "Turning off Debug Mode";
							}
						}
						else
						{
							outputText = "Incomplete command. Missing additional paramaters.";
						}
					}
					else if(splitText[0].equals(levelEditCommand))
					{
						if(splitText.length>1)
						{
							if(splitText[1].equals("on"))
							{
								inLevelEdit=true;
								outputText = "Turning on Level Edit Mode";
							}
							else if(splitText[1].equals("off"))
							{
								inLevelEdit=false;
								outputText = "Turning off Level Edit Mode";
							}
						}
						else
						{
							outputText = "Incomplete command. Missing additional paramaters.";
						}
					}
					else if(splitText[0].equals(helpCommand))
					{
						if(splitText.length>1)
						{
							if(splitText[1].equals(pauseCommand))
							{
								outputText = "Pauses all update functions.";
								outputText+=lineBreak;
								outputText += "All rendering functions are still run.";
							}
							else if(splitText[1].equals(playCommand))
							{
								outputText = "Resumes all update functions.";
							}
							else if(splitText[1].equals(saveCommand))
							{
								outputText = "Saves all objects in a level file.";
								outputText+=lineBreak;
								outputText += "requires a file name after the command";
							}
							else if(splitText[1].equals(loadCommand))
							{
								outputText = "Load a level file.";
								outputText+=lineBreak;
								outputText += "requires a file name after the command";
							}
							else if(splitText[1].equals(debugCommand))
							{
								outputText = "Enter or exit debug mode.";
								outputText+=lineBreak;
								outputText += "requires on or off after the command";
							}
							else if(splitText[1].equals(levelEditCommand))
							{
								outputText = "Enter or exit level edit mode.";
								outputText+=lineBreak;
								outputText += "requires on or off after the command";
							}
							else if(splitText[1].equals(helpCommand))
							{
								outputText = "Helps people, I don't know man.";
								outputText+=lineBreak;
								outputText += "can add commands after to get more info.";
							}
						}
						else
						{
							outputText = "Commands are "+saveCommand+" "
									+loadCommand+" "+playCommand+" "+pauseCommand+" "+helpCommand+" "
									+debugCommand+" "+levelEditCommand;
							outputText += lineBreak;
							outputText += "type /help along with a command to get more information";
						}
					}
					else if(splitText[0].equals(createCommand))
					{
						//notReady
					}
					else if(splitText[0].equals(deleteCommand))
					{
						long id = GameMath.parseLong(splitText[1]);
						Game.destroyObjectById(id);
					}
					else
					{
						outputText = "Unknown Command.";
					}
					//reset Text
					text = "";
				}
				
				if( Input.getKeyPressed(Input.VK_BACKSPACE) )
				{
					//doStuff
					if(text.length()>0)
					{
						text = text.substring(0, text.length()-1);
					}
				}
				
			}
		}
		
		if(Input.getKeyPressed(Input.VK_GRAVE_ACCENT))
		{
			enabled = !enabled;
			text="";
		}
	}
	
	public static void draw()
	{
		if(inDebugMode==true)
		{
			Vec4f preColor = GameRender.getColor();
			
			GameRender.setColor(.8f,.3f, 0f, 1f);
			GameRender.drawText("DebugMode", 640-160, 0);
			
			GameRender.setColor(preColor);
		}
		if(inLevelEdit==true)
		{
			Vec4f preColor = GameRender.getColor();
			
			GameRender.setColor(.8f,.3f, 0f, 1f);
			GameRender.drawText("LevelEditMode", 640-160, 32);
			
			GameRender.setColor(preColor);
		}
		if(enabled==true)
		{
			Vec4f preColor = GameRender.getColor();
			
			GameRender.setColor(.3f,.3f,.3f,.5f);
			GameRender.drawRect(0, 360, 640, 480, false);
			//Text Typed
			GameRender.setColor(1f, 1f, 1f, 1f);
			GameRender.drawText(": "+text, 0, 376);
			
			//Output Message
			GameRender.setColor(.8f, .3f, 0f, 1f);
			GameRender.drawText(outputText, 0, 408);
			
			GameRender.setColor(preColor);
			
		}
	}
}
