package io.grpc.examples.experimental.proxy;

import java.io.Serializable;

public class HelloRequest implements Serializable{
	private String name;
	
	public HelloRequest() {
	}
	
	public HelloRequest(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
