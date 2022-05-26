package com.emart.user.profile.route;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;
import javax.xml.bind.JAXBContext;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.springframework.util.StringUtils;

import com.emart.user.profile.exception.GeneralExceptionProcessor;
import com.emart.user.profile.exception.ValidationException;
import com.emart.user.profile.model.User;
import com.emart.user.profile.sql.UserProfileSql;
import com.emart.user.profile.util.UserStatus;

@Component
public class UserProfileRouteBuilder extends RouteBuilder {

	private String deadEndChannelDestination = "file:user-profile-failures"; // To DO: read from property file
	private String duplicateEntry = "file:duplicate-entry";

	@Override
	public void configure() throws Exception {

		onException(PersistenceException.class).maximumRedeliveries(0).redeliveryDelay(5000).logRetryAttempted(true)
				.retryAttemptedLogLevel(LoggingLevel.WARN).handled(true).to(deadEndChannelDestination)
				.process(new GeneralExceptionProcessor());

		// onException(org.hibernate.exception.ConstraintViolationException.class).handled(true).process(new
		// GeneralExceptionProcessor());

		onException(ValidationException.class, org.hibernate.exception.ConstraintViolationException.class).handled(true)
				.process(new GeneralExceptionProcessor());

		// XML Data Format
		JAXBContext con = JAXBContext.newInstance(User.class);
		JaxbDataFormat xmlDataFormat = new JaxbDataFormat(con);
		xmlDataFormat.setContextPath(User.class.getPackage().toString());

		Class dataClass = User.class;
		JacksonDataFormat format = new JacksonDataFormat();
		format.setUnmarshalType(dataClass);

		String jpaString = String.format(
				"jpa://%1$s?resultClass=%1$s&namedQuery=User.findByEmail" + "&parameters={\"email\":${headers.email}}",
				User.class.getName());

		rest("/{{api.ver}}/user").id("user-route-id").description("GET URL User Route").consumes("application/json")
				.produces("application/json,application/xml").put("/createUser").outType(String.class).responseMessage()
				.code(HttpStatus.OK.value()).message(HttpStatus.OK.getReasonPhrase()).responseModel(String.class)
				.endResponseMessage().route().routeId("createUser-route-id").to("direct:createUser").end().endRest();

		rest("/{{api.ver}}/user").id("userLookUpByEmail-user-route-id").description("userLookUpByEmail Route")
				.consumes("application/json").produces("application/json,application/xml").get("/userLookUpByEmail")
				.responseMessage().code(HttpStatus.OK.value())
				.message(HttpStatus.OK.getReasonPhrase()).responseModel(User.class).endResponseMessage().route()
				.routeId("userLookUpByEmail-route-id").to("direct:userLookUpByEmail").end().endRest();

		rest("/{{api.ver}}/user").id("userLookUpByMobile-user-route-id").description("userLookUpByMobile Route")
		.consumes("application/json").produces("application/json,application/xml").get("/userLookUpByMobile")
		.responseMessage().code(HttpStatus.OK.value())
		.message(HttpStatus.OK.getReasonPhrase()).responseModel(User.class).endResponseMessage().route()
		.routeId("userLookUpByMobile-route-id").to("direct:userLookUpByMobile").end().endRest();
		
		from("direct:createUser").process(new Processor() {

			public void process(Exchange exchange) throws Exception {
				String email = exchange.getIn().getHeader("Email", String.class);
				String mobile = exchange.getIn().getHeader("Mobile", String.class);
				String name = exchange.getIn().getHeader("Name", String.class);
				String password = exchange.getIn().getHeader("Password", String.class);
				if (StringUtils.isEmpty(email)) {
					throw new ValidationException("Missing query param 'email'!");
				}

				if (StringUtils.isEmpty(mobile)) {
					throw new ValidationException("Missing query param 'mobile'!");
				}

				if (StringUtils.isEmpty(name)) {
					throw new ValidationException("Missing query param 'name'!");
				}
				if (StringUtils.isEmpty(password)) {
					throw new ValidationException("Missing query param 'password'!");
				}

				User user = new User();
				user.setName(name);
				user.setEmail(email);
				user.setMobile(mobile);
				user.setPassword(password);
				user.setStatus(UserStatus.REGISTERED.toString());
				user.setUserType("CONSUMER");
				user.setExpiry("6Min");
				
				exchange.getIn().setBody(user);
			}
		}).log("Persisted User Record to DB: ${body}!").to("jpa://com.emart.user.profile.model.User").end();

		from("direct:userLookUpByEmail").process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {
				
				String email = exchange.getIn().getHeader("email", String.class);
				if (StringUtils.isEmpty(email)) {
					throw new ValidationException("Missing param 'email'!");
				}
				
				exchange.getIn().setHeader("email", email);
				log.info("jpaString1 : " + UserProfileSql.findUserByEmail());

			}
		}).toD(UserProfileSql.findUserByEmail())
		.log("Found User Record for a email Id from DB: ${body}!").end();
		
		from("direct:userLookUpByMobile").process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {
				String mobile = exchange.getIn().getHeader("mobile", String.class);
				if (StringUtils.isEmpty(mobile)) {
					throw new ValidationException("Missing param 'mobile'!");
				}
				exchange.getIn().setHeader("mobile", mobile);
				log.info("jpaString1 : " + UserProfileSql.findUserByMobile());

			}
		}).toD(UserProfileSql.findUserByMobile())
		.log("Found User Record for an mobile no from DB: ${body}!").end();

	}

}