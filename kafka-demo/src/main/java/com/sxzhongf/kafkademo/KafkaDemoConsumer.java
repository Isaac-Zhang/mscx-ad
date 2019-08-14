package com.sxzhongf.kafkademo;

import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.FencedInstanceIdException;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * KafkaDemoConsumer for kafka原生消费API使用
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/8/14
 */
public class KafkaDemoConsumer {

    //创建kafka consumer
    private static KafkaConsumer<String, String> consumer;
    private static Properties properties;

    static {
        properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");// kafka broker list addrs
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //配置kafka消费者组 KafkaDemo
        properties.put("group.id", "KafkaDemo");
    }

    /**
     * 消费kafka队列消息，并自动提交位移量
     */
    private static void consumeMessageAutoCommit() {

        //设置 kafka 位移自动提交开启,默认5s提交一次
        properties.put("enable.auto.commit", true);
        consumer = new KafkaConsumer<>(properties);

        //订阅一个指定的topic
        consumer.subscribe(Collections.singleton("mscx-kafka-demo-partitioner"));

        try {
            //消费订阅到的消息
            while (true) {
                boolean tag = true;
                // 拉取消息并设置超时时间
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("Topic : %s, Partition: %s, Offset : %s , key : %s, value : %s\n"
                            , record.topic(), record.partition(), record.offset(), record.key(), record.value());

                    if (record.value().equals("done")) {
                        tag = false;
                    }
                }

                //获取消息是否已经消费结束
                if (!tag) break;
            }
        } finally {
            consumer.close();
        }
    }

    /**
     * 手动提交同步位移
     */
    private static void consumeMessageControlSyncCommit() {
        // 关闭自动字体位移
        properties.put("auto.commit.offset", false);
        consumer = new KafkaConsumer<>(properties);

        //订阅一个指定的topic
        consumer.subscribe(Collections.singleton("mscx-kafka-demo-partitioner"));
        while (true) {
            boolean tag = true;

            // 消费消息
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("Topic : %s, Partition: %s, Offset : %s , key : %s, value : %s\n"
                        , record.topic(), record.partition(), record.offset(), record.key(), record.value());

                if (record.value().equals("done")) {
                    tag = false;
                }
            }
            try {
                // 会造成线程阻塞，尽量少提交，但是可以主动失败重试。
                consumer.commitSync();
            } catch (CommitFailedException exception) {
                System.err.println(exception.getMessage());
            }

            //获取消息是否已经消费结束
            if (!tag) break;
        }
        consumer.close();
    }

    /**
     * 手动提交同步位移
     */
    private static void consumeMessageControlAsyncCommit() {
        // 关闭自动字体位移
        properties.put("auto.commit.offset", false);
        consumer = new KafkaConsumer<>(properties);

        //订阅一个指定的topic
        consumer.subscribe(Collections.singleton("mscx-kafka-demo-partitioner"));
        while (true) {
            boolean tag = true;

            // 消费消息
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("Topic : %s, Partition: %s, Offset : %s , key : %s, value : %s\n"
                        , record.topic(), record.partition(), record.offset(), record.key(), record.value());

                if (record.value().equals("done")) {
                    tag = false;
                }
            }
            try {
                //异步提交，不会重试。。
                //多个异步commit 交替提交，有可能造成offset的互相覆盖，会造成消息有可能重复消费
                //必须 commit1 和 commit2,commit1 的offset 是100，commit2 的offset 是150，
                //当commit1 异步提交失败，commit2异步提交成功，根据offset = 150，然后commitAsync()如果实现了重试，
                //重试的时候,commit1又提交成功，offset -> 150 -> 100,offset回退，就会造成消息重复消费。
                consumer.commitAsync();
            } catch (FencedInstanceIdException exception) {
                System.err.println(exception.getMessage());
            }

            //获取消息是否已经消费结束
            if (!tag) break;
        }
        consumer.close();
    }


    public static void main(String[] args) {
//        consumeMessageAutoCommit();
//        consumeMessageControlSyncCommit();
        consumeMessageControlAsyncCommit();
    }
}
