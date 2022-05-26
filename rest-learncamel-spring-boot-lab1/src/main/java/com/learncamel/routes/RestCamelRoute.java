package com.learncamel.routes;

import com.learncamel.alert.MailProcessor;
import com.learncamel.exception.DataException;
import com.learncamel.processor.CountrySelectProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Created by z001qgd on 1/3/18.
 */
@Component
public class RestCamelRoute extends RouteBuilder{

    @Autowired
    Environment environment;

    @Qualifier("dataSource")
    @Autowired
    DataSource dataSource;

    @Autowired
    CountrySelectProcessor countrySelectProcessor;

    @Autowired
    MailProcessor mailProcessor;


    @Override
    public void configure() throws Exception {

        onException(PSQLException.class).log(LoggingLevel.ERROR,"PSQLException in the route ${body}")
                .maximumRedeliveries(3).redeliveryDelay(3000).backOffMultiplier(2).retryAttemptedLogLevel(LoggingLevel.ERROR);

        onException(DataException.class,RuntimeException.class).log(LoggingLevel.ERROR, "DataException in the route ${body}")
                .process(mailProcessor);


        from("{{fromRoute}}")
                .process(countrySelectProcessor)
                    .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                    .setHeader(Exchange.HTTP_URI,simple("https://restcountries.eu/rest/v2/alpha/${header.countryId}"))
                    .to("https://restcountries.eu/rest/v2/alpha/us").convertBodyTo(String.class)
                    .log("The REST COUNTRIES api response is ${body}")
                    .removeHeader(Exchange.HTTP_URI)
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .to("{{toRoute}}");

        }
}
