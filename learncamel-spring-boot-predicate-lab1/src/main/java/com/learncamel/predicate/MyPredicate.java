package com.learncamel.predicate;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;

public class MyPredicate implements Predicate {
	public boolean matches(Exchange exchange) {
		String body = exchange.getIn().getBody(String.class);
		if (body.contains("Camel")) {
			return true;
		} else if (body.startsWith("Secret")) {
			return true;
		}
		return false;
	}
}
