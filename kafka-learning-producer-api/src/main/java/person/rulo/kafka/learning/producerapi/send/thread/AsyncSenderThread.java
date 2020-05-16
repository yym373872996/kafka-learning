package person.rulo.kafka.learning.producerapi.send.thread;

import org.apache.kafka.clients.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import person.rulo.kafka.learning.common.utils.PropertiesUtils;

import java.util.Properties;

/**
 * @Author rulo
 * @Date 2020/5/9 15:52
 */
public class AsyncSenderThread extends Thread {

    private static final Logger LOG = LoggerFactory.getLogger(AsyncSenderThread.class);

    public Properties setProps() throws Exception {
        Properties props = PropertiesUtils.getProperties("producer.properties");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }

    @Override
    public void run() {
        Producer<String, String> producer = null;
        try {
            producer = new KafkaProducer<>(setProps());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String topic = "topic-async-thread";
        for (int i = 0; i < 100; i++) {
            String key = Integer.toString(i);
            String value = "msg" + Integer.toString(i);
            LOG.info("发送消息到队列" + topic + ": key=" + key + ", value=" + value);
            producer.send(new ProducerRecord<String, String>(topic, key, value), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e != null) {
                        LOG.error("消息发送异常：" + e.getMessage());
                    } else {
                        LOG.info("消息发送成功，当前offset: " + recordMetadata.offset());
                    }
                }
            });
        }
        try {
            // 间隔 3 秒
            sleep(3000);
        } catch (InterruptedException e) {
            LOG.error("线程异常中断：" + e.getMessage());
        }
        // 关闭生产者对象
        producer.close();
    }
}
