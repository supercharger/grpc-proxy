package io.grpc.examples.experimental.proxy;

import io.grpc.proxy.annotation.GrpcMethod;
import io.grpc.proxy.annotation.GrpcService;

@GrpcService
public interface GreeterService {
	
	@GrpcMethod
	public HelloResponse hello(HelloRequest request1, HelloRequest request2);

}
