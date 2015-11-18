package io.grpc.proxy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import io.grpc.MethodDescriptor.Marshaller;

public class ResponseMarshaller implements Marshaller<Object> {

	@Override
	public InputStream stream(Object value) {
		ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
		try {
			ObjectOutputStream stream = new ObjectOutputStream(outputstream);
			stream.writeObject(value);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		byte[] arr = outputstream.toByteArray();
		InputStream messageIs = new ByteArrayInputStream(arr);
		
		return messageIs;
	}

	@Override
	public Object parse(InputStream stream) {
		Object value = null;
		try {
			ObjectInputStream objstream = new ObjectInputStream(stream);
			value  = (Object) objstream.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	

}
