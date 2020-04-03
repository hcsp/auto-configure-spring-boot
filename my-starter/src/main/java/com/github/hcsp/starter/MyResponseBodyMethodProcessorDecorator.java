package com.github.hcsp.starter;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.HashMap;
import java.util.Objects;

public class MyResponseBodyMethodProcessorDecorator implements HandlerMethodReturnValueHandler, ApplicationContextAware {

    private ApplicationContext applicationContext;
    private RequestResponseBodyMethodProcessor delegate;

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return (AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), MyResponseBody.class) ||
                returnType.hasMethodAnnotation(MyResponseBody.class));
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        if (delegate == null) {
            delegate = (RequestResponseBodyMethodProcessor) Objects.requireNonNull(applicationContext.getBean(RequestMappingHandlerAdapter.class)
                    .getReturnValueHandlers())
                    .stream().filter(i -> i instanceof RequestResponseBodyMethodProcessor)
                    .findFirst()
                    .get();
        }
        HashMap<String, Object> returnMap = new HashMap<>(16);
        returnMap.put("status", "ok");
        returnMap.put("data", returnValue);
        delegate.handleReturnValue(returnMap, returnType, mavContainer, webRequest);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
