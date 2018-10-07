package openGLEngine;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Networking {

	private static Socket socket = new Socket();
	private static Scanner input;
	private static ServerSocket serSocket;
	
	private static PrintStream printOut;
	
	private static final byte CLIENT_MODE = 1;
	private static final byte SERVER_MODE = 2;
	
	private static byte mode = 0;
	
	public static void serverInit(int port)
	{
		if(mode==0)
		{
			try {
				serSocket = new ServerSocket(port);
				socket = serSocket.accept();
				input = new Scanner(socket.getInputStream());
				printOut = new PrintStream(socket.getOutputStream());
				
				mode=SERVER_MODE;
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("THIS APPLICATION HAS ALREADY BEEN INITIALIZED");
		}
		
	}
	
	public static void initClient(String ipAddress, int port)
	{
		if(mode==0)
		{
			try {
				socket = new Socket(ipAddress, port);
				input = new Scanner(socket.getInputStream());
				printOut = new PrintStream(socket.getOutputStream());
				
				mode = CLIENT_MODE;
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("THIS APPLICATION HAS ALREADY BEEN INITIALIZED");
		}
		
	}
	
	public static void sendData(String data)
	{
		printOut.println(data);
	}
	
	public static String readData(String data)
	{
		return input.nextLine();
	}
	
	public static void close()
	{
		input.close();
		printOut.close();
		
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			serSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
