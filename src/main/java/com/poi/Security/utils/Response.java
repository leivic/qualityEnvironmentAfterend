package com.poi.Security.utils;

import lombok.Data;

@Data
public class Response {
    private String code;
    private String msg;
    private Object data;
    public Response() {
        this.code = "200";
        this.msg = "SUCCESS";
    }
    public Response(String code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
