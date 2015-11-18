package io.grpc.proxy.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import io.grpc.ManagedChannel;

public class ProxyClientBuilder {
	private final ManagedChannel channel;
	
	public ProxyClientBuilder(ManagedChannel channel) {
		this.channel = channel;
	}
	
	
	public  <T> T get(Class<T> typeClass) {
		InvocationHandler handler = new MethodCallProxyHandler(channel, typeClass.getName());
		T proxy = (T) Proxy.newProxyInstance(typeClass.getClassLoader(), new Class[] {typeClass}, handler);
		return proxy;
	}

}
