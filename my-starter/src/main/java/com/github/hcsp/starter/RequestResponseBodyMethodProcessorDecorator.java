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
import java.util.Map;

public class RequestResponseBodyMethodProcessorDecorator implements HandlerMethodReturnValueHandler, ApplicationContextAware {

    private ApplicationContext applicationContext;
    private RequestResponseBodyMethodProcessor delegate;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        return (AnnotatedElementUtils.hasAnnotation(methodParameter.getContainingClass(), MyResponseBody.class) ||
                methodParameter.hasMethodAnnotation(MyResponseBody.class));
    }

    @Override
    public void handleReturnValue(Object o, MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) throws Exception {
        if (delegate == null) {
            delegate = (RequestResponseBodyMethodProcessor) applicationContext.getBean(RequestMappingHandlerAdapter.class)
                    .getReturnValueHandlers()
                    .stream()
                    .filter(p -> p instanceof RequestResponseBodyMethodProcessor)
                    .findFirst()
                    .get();
        }
        Map<String, Object> returnValueMap = new HashMap<>(2);
        returnValueMap.put("status", "ok");
        returnValueMap.put("data", o);
        delegate.handleReturnValue(returnValueMap, methodParameter, modelAndViewContainer, nativeWebRequest);
    }
}
