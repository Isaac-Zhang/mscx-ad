package com.sxzhongf.kafkademo;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * KafkaDemoProducer for 测试kafka 原生API producer
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/8/14
 */
public class KafkaDemoProducer {

    private static Producer<String, String> producer;

    //初始化producer
    static {
        Properties properties = new Properties();
        properties.put("bootstrap.server", "127.0.0.1:9092");// kafka broker list addrs
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer<String, String>(properties);
    }

    /**
     * 测试发送kafka message
     * topic 必须是字符串，key/value可以是任意类型
     */
    private static void sendMessageIgnoreResult() {
        ProducerRecord<String, String> record = new ProducerRecord<>(
                "mscx-kafka-demo", "demo-key", "demo-value"
        );

        producer.send(record);
        producer.close();
    }

    public static void main(String[] args) {
        sendMessageIgnoreResult();
    }
}
