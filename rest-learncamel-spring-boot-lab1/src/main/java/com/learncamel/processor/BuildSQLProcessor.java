package com.learncamel.processor;


import com.learncamel.domain.Country;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BuildSQLProcessor implements Processor{
    @Override
    public void process(Exchange exchange) throws Exception {

        Country country = (Country) exchange.getIn().getBody();

        StringBuilder builder = new StringBuilder();

        builder.append("INSERT INTO COUNTRY (NAME, COUNTRY_CODE, POPULATION) VALUES ('");
        builder.append(country.getName()+"','"+country.getAlpha3Code()+"',"+country.getPopulation()+");");

        log.info("Query is :"+ builder.toString());

        exchange.getIn().setBody(builder.toString());
        exchange.getIn().setHeader("countryId",country.getAlpha3Code());

    }
}
