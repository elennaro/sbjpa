package com.elennaro.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan(basePackages = "com.elennaro.controllers")
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry vcr) {
        vcr.addViewController("/").setViewName("home");
        vcr.addViewController("/home").setViewName("home");
        vcr.addViewController("/login").setViewName("login");
        vcr.addViewController("/secret").setViewName("secret");
    }
}
