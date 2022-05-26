package com;

import org.apache.camel.builder.RouteBuilder;

public class OrderRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        
        from("direct:processOrder")
            .split(body().method("getItems"), new OrderItemStrategy())
            // each splitted message is send to this bean to process it
            .to("direct:processItem")
         .end();
    
        
        from("direct:processItem")
            .choice()
                .when(body().method("getType").isEqualTo("Book"))
                    .to("bean:itemService?method=processBook").
                otherwise()
                    .to("bean:itemService?method=processPhone");
    }
}
