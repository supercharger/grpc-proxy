package io.grpc.proxy;

import io.grpc.MethodDescriptor.Marshaller;
import io.grpc.examples.experimental.proxy.HelloRequest;
import io.protostuff.CodedInput;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.ProtobufOutput;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ProtoStuffReqMsgTransMarshaller implements Marshaller<MessageTransfer>{
	@Override
	public InputStream stream(MessageTransfer messageTransfer) {
		ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
		Schema<MessageTransfer> messageTransSchema = RuntimeSchema.getSchema(MessageTransfer.class);
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
	
	

	@Override
	public MessageTransfer parse(InputStream stream) {
		Schema<MessageTransfer> messageTransSchema = RuntimeSchema.getSchema(MessageTransfer.class);
		MessageTransfer messageTrans = messageTransSchema.newMessage();
		try {

			ProtobufIOUtil.mergeFrom(stream, messageTrans, messageTransSchema);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return messageTrans;
	}
	

	
}
