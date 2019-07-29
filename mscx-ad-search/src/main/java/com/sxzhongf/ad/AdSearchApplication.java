package com.sxzhongf.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * AdSearchApplication for 广告搜索服务启动类
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/21
 */
@EnableFeignClients  //启动Feign 客户端，为了访问其他微服务
@EnableDiscoveryClient // 开启服务发现组件，在这里等同于 @EnableEurekaClient
@EnableHystrix // 开启hystrix 断路器
@EnableCircuitBreaker // 断路器
@EnableHystrixDashboard // 开启hystrix 监控
@SpringBootApplication
public class AdSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdSearchApplication.class, args);
    }

    /**
     * 注册{@link RestTemplate}Bean
     * @return
     */
    @Bean
    @LoadBalanced
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
