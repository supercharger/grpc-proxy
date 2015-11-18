package io.grpc.examples.experimental.proxy;

public class GreeterServiceImpl implements GreeterService {

	@Override
	public HelloResponse hello(HelloRequest request1, HelloRequest request2) {
		System.out.println("Hello :" + request1.getName());
		
		HelloResponse resultResponse = new HelloResponse();
		resultResponse.setMessage("success");
		return resultResponse;
	}

}
