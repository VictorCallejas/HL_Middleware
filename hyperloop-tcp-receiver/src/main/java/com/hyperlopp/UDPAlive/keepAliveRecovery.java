package com.hyperlopp.UDPAlive;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

public class keepAliveRecovery implements Callable {
	static protected int tries = 0;
	@Override
	public Object onCall(MuleEventContext context) throws Exception {
		
		System.out.println("NOT ALIVE");
		tries++;
		if(tries > 2) {
			tries=0;
			keepAlive.closeSocket();
		}
		
		return context;
	}
}
