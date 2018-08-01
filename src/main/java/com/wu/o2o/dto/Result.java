package com.wu.o2o.dto;

public class Result<T> {

	
	private boolean success;//是否标志成功
	private T data;
	private String errorMsg;
	private int errorCode;
	
	public Result() {
	}

	public Result(boolean success, T data) {
		super();
		this.success = success;
		this.data = data;
	}

	public Result(boolean success, String errorMsg, int errorCode) {
		super();
		this.success = success;
		this.errorMsg = errorMsg;
		this.errorCode = errorCode;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	
}
