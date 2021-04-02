package com.github.hcsp.starter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author steven-wan
 * @desc
 * @date 2021-04-01 11:13
 */
@Configuration
public class WebConfiger implements WebMvcConfigurer {


    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        returnValueHandlers.add(myResponseBodyMethodProcessor());
    }

    @Bean
    public MyResponseBodyMethodProcessor myResponseBodyMethodProcessor() {

        return new MyResponseBodyMethodProcessor();
    }


}
