package io.grpc.examples.experimental.proxy;

public class GreeterServiceImpl implements GreeterService {

	@Override
	public HelloResponse hello(HelloRequest request1, HelloRequest request2) {
		System.out.println("request1 :" + request1.getName() + " request2:" + request2.getName());
		
		HelloResponse resultResponse = new HelloResponse();
		resultResponse.setMessage("success");
		return resultResponse;
	}

}
