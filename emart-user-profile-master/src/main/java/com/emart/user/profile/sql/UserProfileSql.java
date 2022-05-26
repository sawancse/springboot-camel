package com.emart.user.profile.sql;

public class UserProfileSql {

	public static String updateEmail() {
		String st=  "jpa://org.apache.camel.examples.MultiSteps?query=update com.emart.user.profile.model.User o set o.email='${headers.newEmail}' where o.email = '${headers.oldEmail}'";
		return st;
	}
	
	public static String findUserByEmail() {
		String st=  "jpa://org.apache.camel.examples.MultiSteps?query=select o from com.emart.user.profile.model.User o where o.email = '${headers.email}'";
		return st;
	}
	
	public static String findUserByMobile() {
		String st=  "jpa://org.apache.camel.examples.MultiSteps?query=select o from com.emart.user.profile.model.User o where o.mobile = '${headers.mobile}'";
		return st;
	}
	
}
