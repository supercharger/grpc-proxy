package io.grpc.examples.experimental.proxy;

import java.io.Serializable;

public class HelloResponse implements Serializable {

	private String message;
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
