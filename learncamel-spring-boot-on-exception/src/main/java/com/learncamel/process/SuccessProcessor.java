package com.learncamel.process;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

/**
 * Created by z001qgd on 2/3/18.
 */
@Component
public class SuccessProcessor implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.getIn().setBody("Data Updated SuccessFully");
    }
}
