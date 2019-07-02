package com.sxzhongf.ad.sender.kafka;

import com.alibaba.fastjson.JSON;
import com.sxzhongf.ad.mysql.dto.MysqlRowData;
import com.sxzhongf.ad.sender.ISender;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * KafkaSender for 投递Binlog增量数据到kafka消息队列
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2019/7/1
 */
@Component(value = "kafkaSender")
public class KafkaSender implements ISender {

    @Value("${adconf.kafka.topic}")
    private String topic;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    /**
     * 发送数据到kafka队列
     */
    @Override
    public void sender(MysqlRowData rowData) {
        kafkaTemplate.send(
                topic, JSON.toJSONString(rowData)
        );
    }

    /**
     * 消费kafka消息
     */
    @KafkaListener(topics = {"ad-search-mysql-data"}, groupId = "ad-search")
    public void processMysqlRowData(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMsg = Optional.ofNullable(record.value());
        if (kafkaMsg.isPresent()) {
            Object message = kafkaMsg.get();
            MysqlRowData rowData = JSON.parseObject(
                    message.toString(),
                    MysqlRowData.class
            );
            System.out.println("kafka process MysqlRowData: " + JSON.toJSONString(rowData));
//            sender.sender();
        }

    }
}
