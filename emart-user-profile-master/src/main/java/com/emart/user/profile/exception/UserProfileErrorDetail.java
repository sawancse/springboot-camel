package com.emart.user.profile.exception;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ErrorDetail")
public class UserProfileErrorDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2136051516752944844L;

	private int errorCode;
	private String errorMessage;
	private String errorClass;
	private String failureEndPoint;
	private Exception exception;

	@XmlElement(name = "ErrorCode")
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	@XmlElement(name = "FailureEndPoint")
	public String getFailureEndPoint() {
		return failureEndPoint;
	}

	public void setFailureEndPoint(String failureEndPoint) {
		this.failureEndPoint = failureEndPoint;
	}

	@XmlElement(name = "ErrorMessage")
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@XmlElement(name = "ErrorClass")
	public String getErrorClass() {
		return errorClass;
	}

	public void setErrorClass(String errorClass) {
		this.errorClass = errorClass;
	}

	@XmlElement(name = "Exception")
	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

}
