package com.learncamel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.apache.camel.Processor;


public class MyProcessor implements Processor { 
	
	@Override
	public void process(Exchange exchange) throws Exception {
		// TODO Auto-generated method stub
		String body = exchange.getIn().getBody().toString();
		System.out.println("body: " + body);
		exchange.setProperty("fileName", "fileoutput.txt");
		exchange.getIn().setHeader("fileName", "fileOutput.txt");
		
		exchange.getIn().setBody(body + "item body");
	}
	
	public boolean isGoldCustomer(@Header("level") String level)
	 { 
		System.out.println("calling isGoldCustomer m/d");
		return level.equals("gold"); 
	}
	
	 public boolean isGoldCustomerExchange(Exchange exchange) {
		 System.out.println("calling isGoldCustomerExchange m/d");
		 if(exchange.getIn().getHeader("level").equals("gold"))
			 return true;
		return false;
	  }
}
