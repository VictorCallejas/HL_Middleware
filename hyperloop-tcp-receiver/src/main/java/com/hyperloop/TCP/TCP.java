package com.hyperloop.TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Properties;
import java.util.Random;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

public class TCP implements Callable {

	static private Properties propertyFile = new Properties();
	static private InputStream input = TCP.class.getClassLoader().getResourceAsStream("hyperloop.properties");
	
	static public Socket clientSocket;
	
	static private DataOutputStream out;
	static private DataInputStream in;
	static private String token;
	static private Random r = new  Random();
	
	static private Exception e;

	@Override
	public Object onCall(MuleEventContext context) throws Exception {
		
		if(clientSocket == null) {
			intializeSocket();
		}

		token = Long.toString(r.nextLong());
		System.out.println(token);
		out.writeUTF(token);
		out.flush();	
		
		String actToken = in.readUTF();
		System.out.println("RECIBIDO: "+actToken);
		
		if(token.equals(actToken)) {
			return context.transformMessageToString();
		}

		throw e;//token missmatched try reconect
		
	}
	
	
	
	public static void intializeSocket() throws Exception{
		
		input = TCP.class.getClassLoader().getResourceAsStream("hyperloop.properties");
		
		propertyFile.load(input);
		
		clientSocket = new Socket();
		
		SocketAddress sockaddr = new InetSocketAddress(propertyFile.getProperty("tcp.pod.ip"),
				Integer.parseInt(propertyFile.getProperty("tcp.pod.port")));
		
		clientSocket.connect(sockaddr, 400);

		clientSocket.setTcpNoDelay(true);
		
		clientSocket.setSoTimeout(300);
		
		out = new DataOutputStream(clientSocket.getOutputStream());
		
		in = new DataInputStream(clientSocket.getInputStream());
		
	}

}
