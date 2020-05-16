package person.rulo.kafka.learning.consumerapi.rec.assign;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import person.rulo.kafka.learning.common.utils.PropertiesUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

/**
 * @Author rulo
 * @Date 2020/5/15 19:33
 */
public class AssignReceiver {

    public static void main(String[] args) throws Exception {

        Properties props = PropertiesUtils.getProperties("consumer.properties");
        // key的反序列化方式
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // value的反序列化方式
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // 创建一个消费者程序对象
        Consumer<String, String> consumer = new KafkaConsumer<>(props);
        // 设置自定义分区
        TopicPartition tp = new TopicPartition("topic-sync", 0);
        // 手动指定消费的分区
        consumer.assign(Collections.singleton(tp));
        // 实时消费标识
        boolean flag = true;
        while (flag) {
            // 获取主题消息数据
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            }
        }
        // 关闭消费者对象
        consumer.close();
    }

}
