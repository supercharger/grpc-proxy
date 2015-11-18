package io.grpc.proxy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import io.grpc.MethodDescriptor.Marshaller;

public class RequestMarshaller implements Marshaller<Object[]>{
	

	@Override
	public InputStream stream(Object[] values) {
		//TODO: For simplicity, used java serialization. need to change to protobuf
		ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
		try {
			ObjectOutputStream stream = new ObjectOutputStream(outputstream);
			stream.writeObject(values);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		byte[] arr = outputstream.toByteArray();
		InputStream messageIs = new ByteArrayInputStream(arr);
		
		return messageIs;
	}

	@Override
	public Object[] parse(InputStream stream) {
		//TODO: For simplicity, used java serialization. need to change to protobuf
		Object[] values = null;
		try {
			ObjectInputStream objstream = new ObjectInputStream(stream);
			values  = (Object[]) objstream.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return values;
	}

}
