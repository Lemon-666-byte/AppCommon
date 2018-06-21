package com.net.bean;

/**
 * @Description:api统一返回json实体
 */
public class Result<T> {

    /**
     * 返回状态，0：成功
     */
    private int Success;

    /**
     * 简短描述
     */
    private String Message;

    /**
     * 具体数据json串
     */
    private T Data;

    private String MethodName;

    public int getSuccess() {
        return Success;
    }

    public void setSuccess(int success) {
        Success = success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }

    public String getMethodName() {
        return MethodName;
    }

    public void setMethodName(String methodName) {
        MethodName = methodName;
    }
}
