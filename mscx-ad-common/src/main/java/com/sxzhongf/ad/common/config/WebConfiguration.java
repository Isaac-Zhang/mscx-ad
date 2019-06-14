package com.sxzhongf.ad.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * WebConfiguration for 对Spring的配置和行为进行定制修改
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang</a>
 * @see WebMvcConfigurer
 * @since 2019/6/13
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    /**
     * 匹配路由请求规则
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {

    }

    /**
     * 注册自定义的Formatter 和 Convert
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {

    }

    /**
     * 添加静态资源处理器
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }

    /**
     * 添加自定义视图控制器
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

    }

    /**
     * 添加自定义方法参数处理器
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {

    }

    /**
     * 配置消息转换器
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //清空所有转换器
        converters.clear();

        converters.add(new MappingJackson2HttpMessageConverter());
    }
}
