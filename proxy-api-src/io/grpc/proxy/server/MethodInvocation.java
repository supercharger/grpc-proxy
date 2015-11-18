package io.grpc.proxy.server;

import java.lang.reflect.Method;

import io.grpc.stub.ServerCalls.UnaryMethod;
import io.grpc.stub.StreamObserver;


public class MethodInvocation implements UnaryMethod<Object[],Object> {
	private final Object serviceToInvoke;
	private final Method method;
	
	public MethodInvocation(Object serviceToInvoke, Method method) {
		this.serviceToInvoke = serviceToInvoke;
		this.method = method;
	}

	@Override
	public  void invoke(Object[] request, StreamObserver<Object> responseObserver) {
		if (method.getParameterTypes().length != request.length ) {
			return;
		}
		try {
			Object returnObj = method.invoke(serviceToInvoke, request);
			responseObserver.onNext(returnObj);
		} catch(Exception ex) {
			responseObserver.onError(ex);
			ex.printStackTrace();
		} finally {
			responseObserver.onCompleted();
		}
	}

}
