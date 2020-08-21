package com.hyperlopp.UDPAlive;
import java.io.InputStream;

import java.net.*;
import java.util.Properties;
import java.util.Random;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;






public class keepAlive implements Callable{

    static Properties propertyFile = new Properties();
    static private InputStream input; 
    
    static String payload;
    static int podPort, middlewarePort;
    static InetAddress podIp;

	static private DatagramSocket socket;
    static private DatagramPacket packet;
    
    static boolean isFirstExecution = true;
    static private Random r = new Random();

    static private String token;
    static private Exception e;
    static private byte[] buffer;
    static final private int TIMEOUT_RESPONSE = 400; //time to wait for the response
    static final private int UPPER_TOKEN_BOUND = 101;//the upper bound is excluded so this is from 0 to upperBound-1
    

	@Override
	public Object onCall(MuleEventContext context) throws Exception {

		
		if(isFirstExecution == true || socket.isClosed()) {
           getSocketInformation(context);
           createUdpSocket(context);
           isFirstExecution = false;
		}

		token = Integer.toString(r.nextInt(UPPER_TOKEN_BOUND));     
        buffer = token.getBytes();     
 
        //create packet with the token       
      
        packet = new DatagramPacket(buffer, buffer.length, podIp, podPort);
        socket.send(packet);
             

        try {
            packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);            
            final String receivedToken= new String( packet.getData(), 0, packet.getLength());
                       
            if(token.equals(receivedToken)) {
    			return context.transformMessageToString();
    		}else {
    			
    			 System.out.print("token missmatched try reconect: received "+ receivedToken + " sended " + token);
    			 System.out.print(e.getMessage());
            	 throw e;//token missmatched try reconect
            }
          
        } catch (SocketTimeoutException  ste) {
            System.out.print("SocketTimeoutException udp message with token not received in : "+ TIMEOUT_RESPONSE); 
            System.out.println(ste); 
            throw ste;
        }   	
		
    }
    

    private static void getSocketInformation(MuleEventContext context) {
        try {
            input = keepAlive.class.getClassLoader().getResourceAsStream("hyperloop.properties");
            propertyFile.load(input);
            podPort = Integer.parseInt(propertyFile.getProperty("udp.keepAlive.pod.port"));
            podIp = InetAddress.getByName(propertyFile.getProperty("pod.ip"));
            middlewarePort = Integer.parseInt(propertyFile.getProperty("udp.keepAlive.reciever.port"));           
        } catch (final Exception e) {
            System.out.print("Error getting data from properties: "+ e);
        }
        
    }

    public static void createUdpSocket(MuleEventContext context) {
        try {
            System.out.println("Configuring Data UDP Socket...");   
            socket = new DatagramSocket(middlewarePort);
            socket.setSoTimeout(TIMEOUT_RESPONSE);  
            System.out.println("Data UDP Socket created on port "+ middlewarePort);
        } catch (final Exception e) {
            System.out.print("Error en data flow: "+ e);
            isFirstExecution = true;
        }
    }
    
    public static void closeSocket() {
    	socket.close();
    }
    
    
   
}