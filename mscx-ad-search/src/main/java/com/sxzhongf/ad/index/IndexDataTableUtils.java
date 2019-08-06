package com.sxzhongf.ad.index;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * IndexDataTableUtils for 所有索引服务需要缓存的Java Bean
 * Spring中以Aware结尾的接口，表明我想要某某某，
 * 比如 XXXAware表明当前实现类想要XXX
 * {@link ApplicationContextAware} 表明当前类想要一个 ApplicationContext
 *
 * 使用方式：
 * 获取{@link com.sxzhongf.ad.index.creative.CreativeIndexAwareImpl}索引服务类
 * 如下：
 * {@code
 *   IndexDataTableUtils.of(CreativeIndexAwareImpl.class)
 * }
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/6/25
 */
@Component
public class IndexDataTableUtils implements ApplicationContextAware, PriorityOrdered {

    //注入ApplicationContext
    private static ApplicationContext applicationContext;

    /**
     * 定义用于保存所有Index的Map
     * Class标示我们的索引类
     */
    private static final Map<Class, Object> dataTableMap = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        IndexDataTableUtils.applicationContext = applicationContext;
    }

    /**
     * 获取索引服务缓存
     */
    public static <T> T of(Class<T> klass) {
        T instance = (T) dataTableMap.get(klass);
        //如果获取到索引bean，直接返回当前bean
        if (null != instance) {
            return instance;
        }
        //首次获取索引bean为空，写入Map
        dataTableMap.put(klass, bean(klass));
        return (T) dataTableMap.get(klass);
    }

    /**
     * 获取Spring 容器中的Bean对象
     */
    private static <T> T bean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    /**
     * 获取Spring 容器中的Bean对象
     */
    private static <T> T bean(Class klass) {
        return (T) applicationContext.getBean(klass);
    }

    @Override
    public int getOrder() {
        return PriorityOrdered.HIGHEST_PRECEDENCE;
    }
}
