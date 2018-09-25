package com.lxisoft.redalert.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.lxisoft.redalert")
public class FeignConfiguration {

}
