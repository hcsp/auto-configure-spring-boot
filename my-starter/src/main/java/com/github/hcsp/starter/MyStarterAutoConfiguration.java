package com.github.hcsp.starter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;
import java.util.List;

@Configuration(proxyBeanMethods = false)
public class MyStarterAutoConfiguration implements WebMvcConfigurer {

    private final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

    public MyStarterAutoConfiguration(MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter) {
        this.mappingJackson2HttpMessageConverter = mappingJackson2HttpMessageConverter;
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        ObjectMapper objectMapper = mappingJackson2HttpMessageConverter.getObjectMapper();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        handlers.add(new MyResponseHandlerMethodProcessor(Collections.singletonList(mappingJackson2HttpMessageConverter)));
    }

}
