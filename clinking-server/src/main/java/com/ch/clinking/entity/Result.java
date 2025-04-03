package com.ch.clinking.entity;

public class Result {
    private int code;
    private Object data;
    private String msg;

    public Result() {

    }

    public Result(Object data) {this.data = data;}

    public static Result success() {
        Result result = new Result();
        result.setCode(1);
        result.setMsg("成功");
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result(data);
        result.setCode(1);
        result.setMsg("成功");
        return result;
    }

    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

//    /**
//     * 带参数构造
//     * @param code 返回结果编码
//     * @param msg 返回结果描述
//     * @param data 结果描述
//     */
//    public Result(Integer code, Object data, String msg) {
//        this.code = code;
//        this.msg = msg;
//        this.data = data;
//    }


    public int getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
