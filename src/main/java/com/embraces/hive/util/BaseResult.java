package com.embraces.hive.util;

/**
 * @Author Lijl
 * @ClassName BaseResult
 * @Description TODO
 * @Date 2020/10/20 15:35
 * @Version 1.0
 */
public class BaseResult {

    private int code;
    private String message;
    private Object data;

    public static BaseResult bulid(){
        return new BaseResult();
    }

    public static BaseResult ok(String msg){
        return new BaseResult(200,msg,null);
    }

    public static BaseResult ok(String msg, Object data){
        return new BaseResult(200,msg,data);
    }

    public static BaseResult error(String msg){
        return new BaseResult(500,msg,null);
    }

    public BaseResult error(String msg,Object data){
        return new BaseResult(500,msg,data);
    }

    public int getCode() {
        return code;
    }

    public BaseResult setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public BaseResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public BaseResult setData(Object data) {
        this.data = data;
        return this;
    }

    public BaseResult(){}

    public BaseResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
