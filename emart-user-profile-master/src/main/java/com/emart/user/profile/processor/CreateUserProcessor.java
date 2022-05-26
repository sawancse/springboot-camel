package com.emart.user.profile.processor;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.emart.user.profile.model.User;

public class CreateUserProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        User user = new User();
		user.setEmail("sawancse@gmail.com");
		
		exchange.getIn().setBody(user);
    }

}