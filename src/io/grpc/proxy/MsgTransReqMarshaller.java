package io.grpc.proxy;

import io.grpc.MethodDescriptor.Marshaller;
import io.grpc.examples.experimental.proxy.HelloRequest;
import io.protostuff.CodedInput;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.DefaultIdStrategy;
import io.protostuff.runtime.Field;
import io.protostuff.runtime.IdStrategy;
import io.protostuff.runtime.RuntimeEnv;
import io.protostuff.runtime.RuntimeFieldFactory;
import io.protostuff.runtime.RuntimeSchema;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MsgTransReqMarshaller implements Marshaller<MessageTransfer>{
	private final int len;
	private Schema messageTransSchema;
	private Schema[] paramSchemas;
	private Schema<Integer> intergerSchema;
	
	public MsgTransReqMarshaller(Class<?>[] classes) throws InstantiationException, IllegalAccessException {
		len = classes.length;
		intergerSchema  =  RuntimeSchema.getSchema(Integer.class);
		paramSchemas = new Schema[len];
		for(int i = 0 ; i < len; i++) {
			paramSchemas[i] = RuntimeSchema.getSchema(classes[i]);
		}
	}
	
	

	@Override
	public InputStream stream(MessageTransfer messageTransfer) {
		ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
		LinkedBuffer writeBuffer1 = LinkedBuffer.allocate(1000000);
		try {
			ProtobufIOUtil.writeTo(outputstream, messageTransfer, messageTransSchema, writeBuffer1);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		byte[] arr = outputstream.toByteArray();
		InputStream messageIs = new ByteArrayInputStream(arr);
		return messageIs;
	}

	
	public MessageTransfer parse(InputStream stream)  {
		final CodedInput input = new CodedInput(stream, false);
		MessageTransfer msgTransfer = null;
		try {
			input.readRawVarint32();
			final int size = input.readInt32();
			Object[] params = new Object[size];
			for(int i = 0; i < size; i++) {
				input.readRawVarint32();
				params[i] = paramSchemas[i].newMessage();
				input.mergeObject(params[i], paramSchemas[i]);
			}
			msgTransfer = new MessageTransfer(params);
			return msgTransfer;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	
	
	
}
