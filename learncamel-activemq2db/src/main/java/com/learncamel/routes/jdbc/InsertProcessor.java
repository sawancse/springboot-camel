package com.learncamel.routes.jdbc;

import org.apache.camel.Exchange;

public class InsertProcessor implements org.apache.camel.Processor {

    public void process(Exchange exchange) throws Exception {

        String input = (String) exchange.getIn().getBody();
        System.out.println("Input to be persisted : " + input);

        String insertQuery = "INSERT INTO messages2 values ( '1','" + input+"')";
        System.out.println("Insert Query is : " + insertQuery);
        exchange.getIn().setBody(insertQuery);

    }
}
