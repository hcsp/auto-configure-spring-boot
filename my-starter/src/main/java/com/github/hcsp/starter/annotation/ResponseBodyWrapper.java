package com.github.hcsp.starter.annotation;

import lombok.Data;

/**
 * 将原有的返回值封装成固定的格式
 * @author BirdSnail
 * @date 2020/2/24
 */
@Data
public class ResponseBodyWrapper {

    private String status;

    private Object data;

    public ResponseBodyWrapper(String status, Object data) {
        this.status = status;
        this.data = data;
    }
}
