package com.learncamel.route;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.learncamel.bean.Item;
import com.learncamel.bean.ItemSvc;
import com.learncamel.bean.Order;
import com.learncamel.domain.OrderItemStrategy;


@Component
public class SimpleCamelRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        
    	from("timer:hello?period=10s")
    	 .process(new Processor() {
    //	.process(new Processor()){

			@Override
			public void process(Exchange exchange) throws Exception {
				// TODO Auto-generated method stub
				   
	            List<Item> items = new ArrayList<Item>();
	            items.add(new Item("1", "Camel in Action book", "Book"));
	            items.add(new Item("2", "Apple IPhone8", "Phone"));
	            
	            Order myOrder = new Order();
	            myOrder.setItems(items);
	            exchange.getIn().setBody(myOrder);    
			}
    		
    	})
		.to("direct:processOrder")
		.wireTap("direct:sendEmail") //fire and forget mechansim
		.to("vm:sendEmail&timeout=2s") 
		.to("direct:doPayment")
		.wireTap("direct:sendEmail");
		
        from("direct:processOrder")
            .split(body().method("getItems"), new OrderItemStrategy())
            // each splitted message is send to this bean to process it
            .to("direct:processItem")
         .end();
    
        
        from("direct:processItem")
            .choice()
                .when(body().method("getType").isEqualTo("Book"))
              //  .filter().method(ItemSvc.class, "processBook")
                   //.to("bean:ItemService.class?method=processBook")
                   .bean(ItemSvc.class, "processBook")
                .otherwise()
                	//.filter().method(ItemSvc.class, "processPhone")
                	.bean(ItemSvc.class, "processPhone");
                    //.to("bean:itemService?method=processPhone");
    }
}
