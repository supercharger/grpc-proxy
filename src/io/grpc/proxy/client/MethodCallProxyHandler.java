package io.grpc.proxy.client;

import io.grpc.CallOptions;
import io.grpc.ClientCall;
import io.grpc.ManagedChannel;
import io.grpc.MethodDescriptor;
import io.grpc.MethodDescriptor.Marshaller;
import io.grpc.MethodDescriptor.MethodType;
import io.grpc.examples.experimental.proxy.HelloResponse;
import io.grpc.proxy.MessageTransfer;
import io.grpc.proxy.ProtoStuffReqMsgTransMarshaller;
import io.grpc.proxy.ResponseMarshaller;
import io.grpc.stub.ClientCalls;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MethodCallProxyHandler implements InvocationHandler {
	private final static Marshaller<MessageTransfer> REQUEST_MARSHALLER  = new ProtoStuffReqMsgTransMarshaller();
	private final static Marshaller<Object> RESPONSE_MARSHALLER = new ResponseMarshaller();
	
	private final ManagedChannel channel;
	private final String interfaceName;

	public MethodCallProxyHandler(ManagedChannel channel, String interfaceName) {
		this.channel = channel;
		this.interfaceName = interfaceName;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		  String fullMethodName = interfaceName + "/" + method.getName();
		
	      MethodDescriptor<MessageTransfer, Object> methodDescriptor = MethodDescriptor.create(MethodType.UNARY, fullMethodName, 
	    				REQUEST_MARSHALLER, RESPONSE_MARSHALLER);
	      ClientCall<MessageTransfer,Object> newCall = channel.newCall(methodDescriptor, CallOptions.DEFAULT);
	      
	      MessageTransfer requestParamsTransfer =  new MessageTransfer(args);
	      
	      Object response = ClientCalls.blockingUnaryCall(newCall, requestParamsTransfer);
	      return response;
	}

	

}
