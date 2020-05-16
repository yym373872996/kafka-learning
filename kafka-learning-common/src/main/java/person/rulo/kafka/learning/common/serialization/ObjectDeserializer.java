package person.rulo.kafka.learning.common.serialization;

import org.apache.kafka.common.serialization.Deserializer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

/**
 * @Author rulo
 * @Date 2020/5/15 20:17
 */
public class ObjectDeserializer implements Deserializer {
    @Override
    public void configure(Map map, boolean b) {

    }

    @Override
    public Object deserialize(String topic, byte[] data) {
        Object object = null;
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;

        try {
            // 反序列化
            bais = new ByteArrayInputStream(data);
            ois = new ObjectInputStream(bais);
            object = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bais != null) {
                try {
                    bais.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return object;
    }

    @Override
    public void close() {

    }
}
