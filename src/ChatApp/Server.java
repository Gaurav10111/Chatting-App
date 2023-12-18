package ChatApp;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

public class Server implements Runnable{

    Socket socket;
    public static Vector client = new Vector();
    
	public Server(Socket socket)
	{
		try {
			this.socket = socket;
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
	}
	
	public void run()
	{
	   try {
		   BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		   BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		   client.add(bw);
		   
		   while(true)
		   {
			   String data = br.readLine().trim();
			   System.out.println("Recevied "+data);
			   
			   for(int i=0; i<client.size(); i++)
			   {
				   try
				   {
					   BufferedWriter bw1 = (BufferedWriter) client.get(i);
					   bw1.write(data);
					   bw1.write("\r\n");
					   bw1.flush();
					   
				   }catch(Exception e)
				   {
					   System.out.println(e);
				   }
			   }
		   }
	   }
	   catch(Exception e)
	   {
		   System.out.println(e);
	   }
	}
	
	public static void main(String arg[])
	{
		
		try
		{
			ServerSocket skt = new ServerSocket(2600);
			while(true)
			{
				Socket socket = skt.accept();
				Server server = new Server(socket);
				Thread thread = new Thread(server);
				thread.start();
			}
			
		}
		catch(Exception o)
		{
			System.out.println(o);
		}
	}
}
