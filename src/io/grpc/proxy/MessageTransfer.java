package io.grpc.proxy;

import java.io.Serializable;

public class MessageTransfer implements Serializable{
	private int length;
	private Object requestParam1;
	private Object requestParam2;
	private Object requestParam3;
	private Object requestParam4;
	private Object requestParam5;
	
	
	
	public Object getRequestParam1() {
		return requestParam1;
	}
	public void setRequestParam1(Object requestParam1) {
		this.requestParam1 = requestParam1;
	}
	public Object getRequestParam2() {
		return requestParam2;
	}
	public void setRequestParam2(Object requestParam2) {
		this.requestParam2 = requestParam2;
	}
	public Object getRequestParam3() {
		return requestParam3;
	}
	public void setRequestParam3(Object requestParam3) {
		this.requestParam3 = requestParam3;
	}
	public Object getRequestParam4() {
		return requestParam4;
	}
	public void setRequestParam4(Object requestParam4) {
		this.requestParam4 = requestParam4;
	}
	public Object getRequestParam5() {
		return requestParam5;
	}
	public void setRequestParam5(Object requestParam5) {
		this.requestParam5 = requestParam5;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	
	
	public Object[] retriveRequestParams() {
		Object[] requestParams = new Object[this.length];
		switch(this.length) {
			case 1:
				requestParams[0] = this.requestParam1;
				break;
			case 2:
				requestParams[0] = this.requestParam1;
				requestParams[1] = this.requestParam2;
				break;
			case 3: 
				requestParams[0] = this.requestParam1;
				requestParams[1] = this.requestParam2;
				requestParams[2] = this.requestParam3;
			case 4: 
				requestParams[0] = this.requestParam1;
				requestParams[1] = this.requestParam2;
				requestParams[2] = this.requestParam3;
				requestParams[3] = this.requestParam4;
				
			case 5: 
				requestParams[0] = this.requestParam1;
				requestParams[1] = this.requestParam2;
				requestParams[2] = this.requestParam3;
				requestParams[3] = this.requestParam4;
				requestParams[4] = this.requestParam5;
				
		}
		return requestParams;
	}
	
	
	public MessageTransfer() {
		
	}
	
	public MessageTransfer(Object[] args) {
		this.length = args.length;
		
		switch(args.length) {
		case 1:
			this.setRequestParam1(args[0]);
			break;
		case 2:
			this.setRequestParam1(args[0]);
			this.setRequestParam2(args[1]);
			break;
		case 3:
			this.setRequestParam1(args[0]);
			this.setRequestParam2(args[1]);
			this.setRequestParam3(args[2]);
			break;
		case 4:
			this.setRequestParam1(args[0]);
			this.setRequestParam2(args[1]);
			this.setRequestParam3(args[2]);
			this.setRequestParam4(args[3]);
			break;
		case 5: 
			this.setRequestParam1(args[0]);
			this.setRequestParam2(args[1]);
			this.setRequestParam3(args[2]);
			this.setRequestParam4(args[3]);
			this.setRequestParam5(args[3]);
			break;
		}
	}
	
	
}
