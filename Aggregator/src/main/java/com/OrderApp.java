package com;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.model.Item;
import com.model.Order;

public class OrderApp {

	public static void main(String[] args) {
		 try {
	            ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
	                    "camel-context.xml");

	            CamelContext camelContext = applicationContext.getBean("orderCtx",
	                    CamelContext.class);
	            
	            camelContext.start();
	                
	            ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
	            
	            List<Item> items = new ArrayList<Item>();
	            items.add(new Item("1", "Camel in Action book", "Book"));
	            items.add(new Item("2", "Apple IPhone8", "Phone"));
	            
	            Order myOrder = new Order();
	            myOrder.setItems(items);
	                        
	            Order respOrder = producerTemplate.requestBody(
	                    "direct:processOrder", myOrder, Order.class);
	            
	            System.out.println("resp order:"+respOrder);

	            camelContext.stop();
	            
	        } catch (Exception e) {
	            
	            e.printStackTrace();
	        
	        } 

	    }

}
