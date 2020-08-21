package com.hyperloop.UDPSpaceX;

import java.io.InputStream;
import java.net.*;
import java.util.Properties;


import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

public class UDPSpaceX implements Callable {
	
	static Properties propertyFile = new Properties();
	static private InputStream input = UDPSpaceX.class.getClassLoader().getResourceAsStream("hyperloop.properties");
	
	static String payload;
	static int port, packetSize;
	
	static boolean firstTime = true;
	
	static private DatagramSocket socket;
	static private DatagramPacket packet;
	
	 @Override	    
	 public Object onCall(MuleEventContext context) throws Exception {
		 
		if(firstTime) {
			config();
			firstTime = false;
		}
		 
		try{

			socket.receive(packet);
				 
		    payload=new String(packet.getData());
				 
		    System.out.println( "PAQUETE PARA SPACEX RECIBIDO:   " + packet.getAddress() + ": " + payload);
	 
		 }catch(Exception e){
			 System.out.println("ERROR: " + e);
			 firstTime = true;
		 }

		 return payload;
	 }
	 
	 public static void config() {
		 try {
			 System.out.println("Configuring SpaceX UDP Socket...");
			 
			 input = UDPSpaceX.class.getClassLoader().getResourceAsStream("hyperloop.properties");
			 
			 propertyFile.load(input);
			 port = Integer.parseInt(propertyFile.getProperty("udp.reciever.spacex.port"));
			 packetSize = Integer.parseInt(propertyFile.getProperty("udp.size.packet2"));
			 
			 socket = new DatagramSocket( port );
			 packet = new DatagramPacket( new byte[packetSize], packetSize );
			 
			 System.out.println("SpaceX UDP Socket created");
		 }catch(Exception e) {
			 System.out.print("Error en spacex flow: "+ e);
			 firstTime = true;
		 }
	 }
}