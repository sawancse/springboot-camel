package com.emart.user.profile.exception;

public abstract class AuthenticationException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public AuthenticationException() {
		super();
	}
	
	public AuthenticationException(String message){
		super(message);
	}
	
	public AuthenticationException(Throwable cause) {
		super(cause);
	}

	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	abstract public String getErrorCode();

}
