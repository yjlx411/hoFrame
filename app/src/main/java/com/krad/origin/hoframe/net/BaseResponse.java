package com.krad.origin.hoframe.net;

import com.google.gson.annotations.SerializedName;

public class BaseResponse<T> {
    @SerializedName("result")
    private Object result;
    private T data;
    @SerializedName("error_code")
    private int code;
    @SerializedName("reason")
    private String message;

    private Throwable throwable;

    private int flag;
    private StateMode stateMode;

    public void setResult(Object result) {
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    public void setStateMode(StateMode stateMode) {
        this.stateMode = stateMode;
    }

    public StateMode getStateMode() {
        return stateMode;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }
}
