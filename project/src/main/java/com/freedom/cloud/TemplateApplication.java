package com.freedom.cloud;

import com.freedom.cloud.constant.FeignServiceConstant;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@MapperScan("com.freedom.**.dao")
@SpringBootApplication(scanBasePackages = "com.freedom.*")
@EnableFeignClients(basePackages = FeignServiceConstant.FEIGN_CLIENT_SCAN_BASE_PACKAGES)
public class TemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemplateApplication.class, args);
    }

}
