package com.github.hcsp.starter;

import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.io.IOException;
import java.util.List;

public class MyResponseHandlerMethodProcessor extends RequestResponseBodyMethodProcessor {
    public MyResponseHandlerMethodProcessor(List<HttpMessageConverter<?>> converters) {
        super(converters);
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.getMethodAnnotation(MyResponseBody.class) != null;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws IOException, HttpMediaTypeNotAcceptableException, HttpMessageNotWritableException {
        super.handleReturnValue(new MyResponseVO("ok",returnValue), returnType, mavContainer, webRequest);
    }
}
