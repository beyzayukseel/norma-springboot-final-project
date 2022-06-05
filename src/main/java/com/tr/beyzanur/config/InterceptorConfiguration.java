package com.tr.beyzanur.config;

import com.tr.beyzanur.aop.TransactionControllerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    private final TransactionControllerInterceptor transactionControllerInterceptor;

    @Autowired
    public InterceptorConfiguration(TransactionControllerInterceptor transactionControllerInterceptor) {
       this.transactionControllerInterceptor = transactionControllerInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(transactionControllerInterceptor).addPathPatterns("/api/v1/transactions/*");
    }
}
