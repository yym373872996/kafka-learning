package person.rulo.kafka.learning.streamsapi.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import person.rulo.kafka.learning.common.utils.PropertiesUtils;

import java.util.Properties;
import java.util.Scanner;

/**
 * @Author rulo
 * @Date 2020/5/8 21:39
 *
 * 同步发送
 */
public class PlaintextWordSender {

    public static void main(String[] args) throws Exception {

        Properties props = PropertiesUtils.getProperties("producer.properties");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // 用 String 格式发送消息
        Producer<String, String> producer = new KafkaProducer<>(props);
        String topic = "streams-plaintext-input";
        // 控制台输入的行将作为消息发送到队列
        while (true) {
            Scanner reader = new Scanner(System.in);
            String value = reader.nextLine();
            // 调用 send() 发送消息后，再通过 get() 方法等待返回结果
            producer.send(new ProducerRecord<String, String>(topic, value));
            System.out.println("消息被发送到队列" + topic + ": " + value);
        }
        // 关闭生产者对象
//        producer.close();
    }

}
