package com.payfa.models;

public class ErrorModel {

    public ErrorModel() {

    }

    public ErrorModel(String code, String msg) {
        this.code = Integer.parseInt(code);
        this.msg = msg;
    }

    public ErrorModel(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int code;
    public String msg;
}
