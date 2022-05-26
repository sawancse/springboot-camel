package com.learncamel.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SimpleCamelRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        
    	
    	from("timer:hello?period=10s")
    	   	.pollEnrich("file:data/input?delete=true&readLock=none")
    		.to("direct-vm:start");
    	
    	  from("direct-vm:start")
    	  	//.to("http://google.com")
    	  	.to("ahc:http://localhost")
            .to("ahc:http://localhost?order=123&detail=short")
    	  	 .circuitBreaker()
    	  	.transform().constant("Fallback message")
    	  	.end()
    	  	.log("body ${body}")
    	  	.to("mock:result").end(); 

    	  //or
    	  from("direct:start1")
          .setHeader(Exchange.HTTP_QUERY, constant("order=123&detail=short"))
          .to("ahc:http://localhost");
    	  
    }
}
