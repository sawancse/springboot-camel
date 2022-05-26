package com.learncamel.routes;

import com.learncamel.alert.MailProcessor;
import com.learncamel.domain.Country;
import com.learncamel.exception.DataException;
import com.learncamel.processor.BuildSQLProcessor;
import com.learncamel.processor.CountrySelectProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.gson.GsonDataFormat;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@Slf4j
public class CountryRestRoute extends RouteBuilder{

    @Autowired
    Environment environment;

    @Qualifier("dataSource")
    @Autowired
    DataSource dataSource;

    @Autowired
    CountrySelectProcessor countrySelectProcessor;

    @Autowired
    MailProcessor mailProcessor;

    @Autowired
    BuildSQLProcessor buildSQLProcessor;



    @Override
    public void configure() throws Exception {

        onException(PSQLException.class).log(LoggingLevel.ERROR,"PSQLException in the route ${body}")
                .maximumRedeliveries(3).redeliveryDelay(3000).backOffMultiplier(2).retryAttemptedLogLevel(LoggingLevel.ERROR);

        onException(DataException.class,RuntimeException.class).log(LoggingLevel.ERROR, "DataException in the route ${body}")
                .process(mailProcessor);

        GsonDataFormat countryFormat = new GsonDataFormat(Country.class);

        from("restlet:http://localhost:8081/country?restletMethods=POST").routeId("countryPostRoute")
                .log("Received Body is ${body}")
                .convertBodyTo(String.class)
                .unmarshal(countryFormat)
                .log("Unmarshaled record is ${body}")
                .process(buildSQLProcessor)
                .to("{{dbNode}}")
                .to("{{selectNode}}")
                .convertBodyTo(String.class)
                .log("Inserted Country is ${body}");


    }
}
