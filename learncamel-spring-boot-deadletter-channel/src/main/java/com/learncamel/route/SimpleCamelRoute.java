package com.learncamel.route;

import com.learncamel.domain.Item;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.LoggingLevel;
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

        DataFormat bindy = new BindyCsvDataFormat(Item.class);

     //   errorHandler(deadLetterChannel("log:errorInRoute?level=ERROR&showProperties=true")
         //       /*.maximumRedeliveries(3).redeliveryDelay(3000).backOffMultiplier(2).retryAttemptedLogLevel(LoggingLevel.ERROR)*/);
             
        errorHandler(deadLetterChannel("jms:queue:dead")
        	.useOriginalMessage().maximumRedeliveries(5).redeliveryDelay(5000));

        from("{{startRoute}}")
                .log("Timer Invoked and the body" + environment.getProperty("message"))
                .choice()
                    .when((header("env").isNotEqualTo("mock")))
                        .pollEnrich("{{fromRoute}}")
                    .otherwise()
                        .log("mock env flow and the body is ${body}")
                .end()
                .to("{{toRoute1}}")
                .unmarshal(bindy)
                .log("The unmarshaled object is ${body}")
                .end();



    }
}
