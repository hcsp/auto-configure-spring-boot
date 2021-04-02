package com.github.hcsp.starter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

/**
 * @author steven-wan
 * @desc
 * @date 2021-04-01 10:44
 */
public class MyResponseBodyMethodProcessor implements HandlerMethodReturnValueHandler, ApplicationContextAware {

    private RequestResponseBodyMethodProcessor delegate;
    ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext var1) {
        applicationContext = var1;
    }


    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        return methodParameter.hasMethodAnnotation(MyResponseBody.class);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) throws Exception {
        RequestMappingHandlerAdapter requestMappingHandlerAdapter = applicationContext.getBean(RequestMappingHandlerAdapter.class);
        delegate = (RequestResponseBodyMethodProcessor) requestMappingHandlerAdapter.getReturnValueHandlers().stream()
                .filter(handlerMethodReturnValueHandler1 -> handlerMethodReturnValueHandler1 instanceof RequestResponseBodyMethodProcessor)
                .findFirst().get();
        ResponseData responseData = new ResponseData();
        responseData.setData(returnValue);
        this.delegate.handleReturnValue(responseData, methodParameter, modelAndViewContainer, nativeWebRequest);
    }
}
