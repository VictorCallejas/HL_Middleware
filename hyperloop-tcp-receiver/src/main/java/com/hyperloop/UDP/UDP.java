package com.hyperloop.UDP;

import java.io.InputStream;
import java.net.*;
import java.util.Properties;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

public class UDP implements Callable {

	static Properties propertyFile = new Properties();
	static private InputStream input = UDP.class.getClassLoader().getResourceAsStream("hyperloop.properties");
	
	static String payload;
	static int port, packetSize;
	
	static boolean firstTime = true;
	
	static private DatagramSocket socket;
	static private DatagramPacket packet;

	@Override
	public Object onCall(MuleEventContext context) throws Exception {
		
		if(firstTime) {
			config(context);
			firstTime = false;
		}
		
		 try {
			 
		   String paquete1 = null, paquete2 = null, paquete3 = null;
		   
		   while(paquete1 == null || paquete2 == null || paquete3 == null) {
			   packet = new DatagramPacket(new byte[packetSize], packetSize );
			   socket.receive(packet);
			  
			   if(packet.getLength() > 1300) {
				   paquete1 = new String(packet.getData()).trim();
			   }else if(packet.getLength() > 1000){
				   paquete2 = new String(packet.getData()).trim();
			   }else {
				   paquete3 = new String(packet.getData()).trim();
			   }
			   
		   } 
	        
	        payload = paquete1 + paquete2 + paquete3;
			 
	        System.out.println("PAQUETE DATA RECIBIDO:   " + packet.getAddress() + " " + payload ) ;
	        
	        
	     }
	     catch( Exception e )
	     {
	        System.out.println("Error: "+ e );
	        firstTime = true;
	     }
		 
		 return payload;
		 
	}
	
	 public static void config(MuleEventContext context) {
		 try {
		    System.out.println("Configuring Data UDP Socket...");
		    
		    input = UDP.class.getClassLoader().getResourceAsStream("hyperloop.properties");
		    
			propertyFile.load(input);
			port = Integer.parseInt(propertyFile.getProperty("udp.reciever.port"));
			packetSize = Integer.parseInt(propertyFile.getProperty("udp.size.packet1"));
			
			socket = new DatagramSocket( port ); 
			socket.setReceiveBufferSize(packetSize);
	        packet = new DatagramPacket( new byte[packetSize], packetSize );
	        
	        System.out.println("Data UDP Socket created");
		 }catch(Exception e) {
			 System.out.print("Error en data flow: "+ e);
			 firstTime = true;
		 }
	 }
}