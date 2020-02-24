package com.github.hcsp.starter.annotation;

import com.github.hcsp.starter.MyResponseBody;
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

/**
 * @author BirdSnail
 * @date 2020/2/24
 */
public class MyResponseBodyMethodProcessorDecorator implements HandlerMethodReturnValueHandler, ApplicationContextAware {

    private RequestResponseBodyMethodProcessor delegate;
    private ApplicationContext appContext;

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return (AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), MyResponseBody.class) ||
                returnType.hasMethodAnnotation(MyResponseBody.class));
    }

    @Override
    public void handleReturnValue(Object value, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest request) throws Exception {
        if (delegate == null) {
            delegate = (RequestResponseBodyMethodProcessor) appContext.getBean(RequestMappingHandlerAdapter.class)
                    .getReturnValueHandlers().stream()
                    .filter(h -> h instanceof RequestResponseBodyMethodProcessor)
                    .findFirst()
                    .get();
        }

        delegate.handleReturnValue(new ResponseBodyWrapper("ok", value), returnType, mavContainer, request);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appContext = applicationContext;
    }
}
