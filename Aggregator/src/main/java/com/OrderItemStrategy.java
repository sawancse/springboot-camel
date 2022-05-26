package com;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import com.model.Item;
import com.model.Order;

public class OrderItemStrategy implements AggregationStrategy {
    
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        
           if (oldExchange == null) {
               
               Item newItem= newExchange.getIn().getBody(Item.class);
               System.out.println("Aggregate first item: " + newItem);
               
               Order currentOrder = new Order();
               currentOrder.setId("ORD"+System.currentTimeMillis());
               List<Item> currentItems = new ArrayList<Item>();
   
               currentItems.add(newItem);
               currentOrder.setItems(currentItems);
               currentOrder.setTotalPrice(newItem.getPrice());
               
               newExchange.getIn().setBody(currentOrder);
               
                // the first time we aggregate we only have the new exchange,
                // so we just return it
                return newExchange;
            }
           
            Order order = oldExchange.getIn().getBody(Order.class);
            Item newItem= newExchange.getIn().getBody(Item.class);
     
            System.out.println("Aggregate old items: " + order);
            System.out.println("Aggregate new item: " + newItem);
            
            order.getItems().add(newItem);
           
            double totalPrice = order.getTotalPrice() + newItem.getPrice();
            order.setTotalPrice(totalPrice);

            // return old as this is the one that has all the orders gathered until now
            return oldExchange;
    }


}
