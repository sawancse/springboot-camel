package com.learncamel.routes.jms2jdbc;

import com.learncamel.routes.exception.ExceptionProcessor;
import com.learncamel.routes.jdbc.InsertProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.postgresql.util.PSQLException;

import javax.jms.JMSException;

public class Jms2DBRoute extends RouteBuilder {



    public void configure() throws Exception {

        onException(PSQLException.class).handled(true).log("Exception While inserting messages.").process(new ExceptionProcessor());

        from("activemq:queue:testQueue")
                .to("log:?level=INFO&showBody=true");

    }
}
