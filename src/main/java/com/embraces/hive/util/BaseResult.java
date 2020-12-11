package com.embraces.hive.util;

/**
 * @Author Lijl
 * @ClassName BaseResult
 * @Description TODO
 * @Date 2020/10/20 15:35
 * @Version 1.0
 */
public class BaseResult<T> {

    private int code;
    private String message;
    private T data;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public BaseResult(){}

    public BaseResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
