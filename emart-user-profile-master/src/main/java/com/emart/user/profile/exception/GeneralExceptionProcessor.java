package com.emart.user.profile.exception;

import javax.ws.rs.core.MediaType;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emart.user.profile.route.UserProfileRouteBuilder;

public class GeneralExceptionProcessor implements org.apache.camel.Processor {
	private Logger log = LoggerFactory.getLogger(UserProfileRouteBuilder.class);

	public void process(Exchange exchange) throws Exception {

		Exception e = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
		String failureEndoint = (String) exchange.getProperty(Exchange.FAILURE_ENDPOINT);

		log.error("General Exception Occurred: " + e.getMessage(), e);
		log.error("Failed Endpoint : " + failureEndoint);

		UserProfileErrorDetail error = new UserProfileErrorDetail();
		error.setException(e);
		error.setErrorMessage(e.getMessage());
		error.setErrorClass(e.getClass().toString());
		error.setFailureEndPoint(failureEndoint);
		error.setErrorCode(500);

		exchange.getIn().setHeader(Exchange.CONTENT_TYPE, MediaType.APPLICATION_XML);
		exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 500);
		exchange.getIn().setBody(error);

	}
}
