package com.learncamel.process;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.learncamel.exception.CamelCustomException;

public class MyProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        System.out.println("Exception Thrown");
        throw new CamelCustomException();
    }

}