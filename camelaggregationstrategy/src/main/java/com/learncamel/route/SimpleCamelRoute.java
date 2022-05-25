package com.learncamel.route;

import com.learncamel.bean.MyBean;
import com.learncamel.domain.Item;
import lombok.extern.slf4j.Slf4j;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * Created by z001qgd on 1/24/18.
 */
@Component
@Slf4j
public class SimpleCamelRoute  extends RouteBuilder{


    @Autowired
    Environment environment;


    @Override
    public void configure() throws Exception {

        log.info("Starting the Camel Route");

        from("file:data/input?delete=true&readLock=none").process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				// TODO Auto-generated method stub
				String body = exchange.getIn().getBody().toString();
				exchange.setProperty("foo", "foo");
				exchange.getIn().setHeader("level", "gold");
				log.info("body is : " + body);
			}
		}).to("direct:start");
        
        
        from("direct:start") 
    	.filter().method(MyBean.class, "isGoldCustomerExchange")
    		.log("new body is ${body}")
    		.to("mock:gold")
    		.log("body is ${body}")
    	.end()
    	.to("mock:all");  

        
        from("direct:start1").routeId("start1")
        .to("direct:foo").to("log:foo").to("mock:result");

        from("direct:foo").routeId("foo")
        .transform(constant("Bye World"));


    }
}
