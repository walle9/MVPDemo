package com.example.mvpdemo.model.http;


/**
 * @date create at 2019/4/2 17:53
 * @describe 描述:有解析数据的返回
 */
public class HttpResult<T> {

    private int code;
    private String msg;
    //@SerializedName(value = "result")
    private T data;

    public int getCode() {
        return code;
    }

    public HttpResult setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public HttpResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public HttpResult setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "HttpResult{" + "code=" + code + ", msg='" + msg + '\'' + ", data=" + data + '}';
    }
}
