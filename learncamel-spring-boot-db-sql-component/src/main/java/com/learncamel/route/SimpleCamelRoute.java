package com.learncamel.route;

import com.learncamel.domain.Item;
import com.learncamel.process.BuildSQLProcessor;
import com.learncamel.process.SuccessProcessor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Component
@Slf4j
public class SimpleCamelRoute  extends RouteBuilder{


    @Autowired
    Environment environment;

    @Qualifier("dataSource")
    @Autowired
    DataSource dataSource;

    @Autowired
    BuildSQLProcessor buildSQLProcessor;

    @Autowired
    SuccessProcessor successProcessor;



    @Override
    public void configure() throws Exception {

        log.info("Starting the Camel Route");

        DataFormat bindy = new BindyCsvDataFormat(Item.class);

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
                .split(body())
                    .log("Record is ${body}")
                    .process(buildSQLProcessor)
                    .to("sql://query?useMessageBodyForSql=true")
                .end()
                .setHeader("sku", constant("100"))
                .to("sql:select * from items where sku = :#sku")
                .process(new Processor() {
                	@Override
                    public void process(Exchange exchange)
                            throws Exception {
                        System.out.println(exchange.getIn() .getBody().getClass());
                        System.out.println(exchange.getIn().getBody());
                        ArrayList<Map<String, String>> dataList = (ArrayList<Map<String, String>>) exchange.getIn().getBody();
        				System.out.println(dataList);
        				for (Map<String, String> data : dataList) {
        					System.out.println(data.get("sku"));
        				}
                    }
                })
        .process(successProcessor)
        .to("{{toRoute3}}");

        log.info("Ending the Camel Route");


    }
}
