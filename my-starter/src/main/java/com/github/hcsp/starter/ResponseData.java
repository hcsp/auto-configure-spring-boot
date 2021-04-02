package com.github.hcsp.starter;

import lombok.Data;

/**
 * @author steven-wan
 * @desc
 * @date 2021-04-01 10:52
 */
@Data
public class ResponseData<T> {
    private String status = "ok";

    private T data;
}
