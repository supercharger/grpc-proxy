package io.grpc.proxy.server;

import java.lang.reflect.Method;

import io.grpc.proxy.MessageTransfer;
import io.grpc.stub.ServerCalls.UnaryMethod;
import io.grpc.stub.StreamObserver;


public class MethodInvocation implements UnaryMethod<MessageTransfer,Object> {
	private final Object serviceToInvoke;
	private final Method method;
	
	public MethodInvocation(Object serviceToInvoke, Method method) {
		this.serviceToInvoke = serviceToInvoke;
		this.method = method;
	}

	@Override
	public  void invoke(MessageTransfer request, StreamObserver<Object> responseObserver) {
		if (method.getParameterTypes().length != request.getLength()) {
			return;
		}
		try {
			Object[] requestParams  = request.retriveRequestParams();
			Object returnObj = method.invoke(serviceToInvoke, requestParams);
			responseObserver.onNext(returnObj);
		} catch(Exception ex) {
			responseObserver.onError(ex);
		} finally {
			responseObserver.onCompleted();
		}
	}

	

}
