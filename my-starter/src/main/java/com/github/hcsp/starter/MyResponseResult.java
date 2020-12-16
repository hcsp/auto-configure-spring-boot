package com.github.hcsp.starter;

import java.io.Serializable;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class MyResponseResult implements Serializable {
    private String status;
    private Object data;

    public MyResponseResult(String status, Object data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public MyResponseResult setStatus(String status) {
        this.status = status;
        return this;
    }

    public Object getData() {
        return data;
    }

    public MyResponseResult setData(Object data) {
        this.data = data;
        return this;
    }
}
