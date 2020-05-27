package person.rulo.kafka.learning.streamsapi.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import person.rulo.kafka.learning.common.utils.PropertiesUtils;

import java.util.Arrays;
import java.util.Properties;

/**
 * @Author rulo
 * @Date 2020/5/11 17:22
 */
public class WordCountReceiver {

    public static void main(String[] args) throws Exception {

        Properties props = PropertiesUtils.getProperties("consumer.properties");
        // key的反序列化方式
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // value的反序列化方式
        props.put("value.deserializer", "org.apache.kafka.common.serialization.LongDeserializer");
        // 创建一个消费者程序对象
        org.apache.kafka.clients.consumer.Consumer<String, Long> consumer = new KafkaConsumer<>(props);
        // 订阅消费主题集合
        consumer.subscribe(Arrays.asList("streams-wordcount-output"));
        // 实时消费标识
        boolean flag = true;
        while (flag) {
            // 获取主题消息数据
            ConsumerRecords<String, Long> records = consumer.poll(100);
            for (ConsumerRecord<String, Long> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            }
        }
        // 关闭消费者对象
        consumer.close();
    }
}
