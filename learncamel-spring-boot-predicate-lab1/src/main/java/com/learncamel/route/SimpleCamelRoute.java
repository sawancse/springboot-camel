package com.learncamel.route;

import com.learncamel.exception.CamelCustomException;
import com.learncamel.predicate.MyPredicate;
import com.learncamel.process.MyProcessor;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Predicate;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.logging.Logger;

@Component
@Slf4j
public class SimpleCamelRoute extends RouteBuilder {

	@Autowired
	com.learncamel.mail.MailProcessor mailProcessor;

	@Override
    public void configure() throws Exception {

    	
    	onException(CamelCustomException.class).log(LoggingLevel.ERROR, "DataException in the route ${body}")
    	//.process(mailProcessor);\

    	Predicate isNotDev =  header("env").isNotEqualTo("mock");

    	
    	from("file:data/input?noop=true")
    	.doTry().filter(new MyPredicate())
    	.to("file:data/output")
         .doCatch(CamelCustomException.class).process(new Processor() {

             public void process(Exchange exchange) throws Exception {
                 System.out.println("handling ex");
             }
         }).log("Received body ");

    	 from("file:dataFile?noop=true").process(new MyProcessor()).to("file:data/output");
    }
}
