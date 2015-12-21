package io.grpc.proxy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import io.grpc.MethodDescriptor.Marshaller;
import io.grpc.examples.experimental.proxy.HelloResponse;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

public class ResponseMarshaller implements Marshaller<Object> {

	@Override
	public InputStream stream(Object value) {
		ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
		Schema objSchema = RuntimeSchema.getSchema(value.getClass());
		LinkedBuffer writeBuffer1 = LinkedBuffer.allocate(1000000);
		try {
			ProtobufIOUtil.writeTo(outputstream, value, objSchema, writeBuffer1);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		byte[] arr = outputstream.toByteArray();
		InputStream messageIs = new ByteArrayInputStream(arr);
		return messageIs;
	}

	@Override
	public Object parse(InputStream stream) {
		Schema<HelloResponse> respSchema = RuntimeSchema.getSchema(HelloResponse.class);
		HelloResponse helloResponse = respSchema.newMessage();
		try {
			ProtobufIOUtil.mergeFrom(stream, helloResponse, respSchema);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return helloResponse;
	}

	

}
