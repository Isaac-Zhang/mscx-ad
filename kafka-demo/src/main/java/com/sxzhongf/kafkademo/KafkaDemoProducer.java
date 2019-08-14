package com.sxzhongf.kafkademo;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.Future;

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
        properties.put("bootstrap.servers", "localhost:9092");// kafka broker list addrs
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

        //不检测发送结果，会造成消息有丢失的风险
        producer.send(record);
        producer.close();
    }

    /**
     * 测试同步发送消息
     */
    private static void sendMessageSync() throws Exception {
        ProducerRecord<String, String> record = new ProducerRecord<>(
                "mscx-kafka-demo", "demo-sync-key", "demo-sync-value"
        );

        Future<RecordMetadata> recordMetadataFuture = producer.send(record);
        RecordMetadata recordMetadata = recordMetadataFuture.get();

        System.out.printf("Topic : %s, Partition: %s, Offset : %s"
                , recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());

        producer.close();
    }

    /**
     * 异步发送消息处理
     */
    private static void sendMessageAsynCallback() {
        ProducerRecord<String, String> record = new ProducerRecord<>(
                "mscx-kafka-demo", "demo-asyn-key", "demo-asyn-value"
        );

        producer.send(record, new ProducerDemoCallback());
        producer.close();
    }

    public static void main(String[] args) throws Exception {
//        sendMessageIgnoreResult();
//        sendMessageSync();
        sendMessageAsynCallback();
    }
}
