package com.learncamel.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.learncamel.constants.CommonConstants;

/**
 * Created by z001qgd on 1/24/18.
 */
@Component
public class SimpleCamelRoute  extends RouteBuilder{

    @Autowired
    Environment environment;

    @Override
    public void configure() throws Exception {

        from("{{startRoute}}").routeId("route-1")
                .log("Timer Invoked and the body" + environment.getProperty("message"))
                .to("log:?level=INFO&showBody=true&showHeaders=true")
                .choice()
                    .when((header("env").isNotEqualTo("mock")))
                        .pollEnrich("{{fromRoute}}")
                    .otherwise()
                        .log("mock env flow and the body is ${body}")
                .end()
                .to("{{toRoute1}}");
        
        

        from(CommonConstants.startRoute).routeId("route-2")
                .log("Timer Invoked and the body" + environment.getProperty("message"))
                .to("log:?level=INFO&showBody=true&showHeaders=true")
                .choice()
                    .when((header("env").isNotEqualTo("mock")))
                        .pollEnrich("{{fromRoute}}")
                    .otherwise()
                        .log("mock env flow and the body is ${body}")
                .end()
                .to("{{toRoute1}}");
        


    }
}
