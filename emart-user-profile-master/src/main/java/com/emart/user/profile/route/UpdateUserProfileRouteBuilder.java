package com.emart.user.profile.route;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.PersistenceException;
import javax.xml.bind.JAXBContext;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.springframework.util.StringUtils;

import com.emart.user.profile.exception.GeneralExceptionProcessor;
import com.emart.user.profile.exception.ValidationException;
import com.emart.user.profile.model.User;
import com.emart.user.profile.sql.UserProfileSql;

@Component
public class UpdateUserProfileRouteBuilder extends RouteBuilder {

	private String deadEndChannelDestination = "file:user-profile-failures"; // To DO: read from property file
	private String duplicateEntry = "file:duplicate-entry";

	@Override
	public void configure() throws Exception {

		onException(PersistenceException.class).maximumRedeliveries(0).redeliveryDelay(5000).logRetryAttempted(true)
				.retryAttemptedLogLevel(LoggingLevel.WARN).handled(true).to(deadEndChannelDestination)
				.process(new GeneralExceptionProcessor());

		// onException(org.hibernate.exception.ConstraintViolationException.class).handled(true).process(new
		// GeneralExceptionProcessor());

		// onException(ValidationException.class,org.hibernate.exception.ConstraintViolationException.class).handled(true).process(new
		// GeneralExceptionProcessor());

		/*
		 * // XML Data Format JAXBContext con = JAXBContext.newInstance(User.class);
		 * JaxbDataFormat xmlDataFormat = new JaxbDataFormat(con);
		 * xmlDataFormat.setContextPath(User.class.getPackage().toString());
		 */

		rest("/{{api.ver}}/user").id("update-user-route-id").description("UPDATE User Route")
				.consumes("application/json").produces("application/json").put("/updateUser").outType(String.class)
				.responseMessage().code(HttpStatus.OK.value()).message(HttpStatus.OK.getReasonPhrase())
				.responseModel(String.class).endResponseMessage().route().routeId("updateUser-route-id")
				.to("direct:updateUser").end().endRest();

		from("direct:updateUser").process(new Processor() {

			public void process(Exchange exchange) throws Exception {
				String oldEmail = exchange.getIn().getHeader("oldEmail", String.class);
				// String mobile = exchange.getIn().getHeader("Mobile", String.class);
				String newEmail = exchange.getIn().getHeader("newEmail", String.class);
				exchange.getIn().setHeader("oldEmail", oldEmail);
				exchange.getIn().setHeader("newEmail", newEmail);
				log.info("updateEmail Query: " + UserProfileSql.updateEmail());
				if (StringUtils.isEmpty(oldEmail)) {
					throw new ValidationException("Missing param 'email'!");
				}

				if (StringUtils.isEmpty(newEmail)) {
					throw new ValidationException("Missing param 'mobile'!");
				}

			}
		}).log("Persisted User Record to DB: ${body}!").toD(UserProfileSql.updateEmail()).end();

	}

}