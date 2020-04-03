package com.github.hcsp.starter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class MyConfiguration implements WebMvcConfigurer {
    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        handlers.add(myProcessor());
    }

    @Bean
    public MyResponseBodyMethodProcessorDecorator myProcessor() {
        return new MyResponseBodyMethodProcessorDecorator();
    }

}
