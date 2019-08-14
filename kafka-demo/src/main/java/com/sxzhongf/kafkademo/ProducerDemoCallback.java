package com.sxzhongf.kafkademo;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * ProducerDemoCallback for 实现异步生产消息结束后的回调处理
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/8/14
 */
public class ProducerDemoCallback implements Callback {

    @Override
    public void onCompletion(RecordMetadata recordMetadata, Exception exception) {
        if (null != exception) {
            System.out.println(exception.getStackTrace());
            return;
        }
        System.out.printf("Asyn Topic : %s, Partition: %s, Offset : %s"
                , recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());

    }
}
