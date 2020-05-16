package person.rulo.kafka.learning.producerapi.send.thread;

/**
 * @Author rulo
 * @Date 2020/5/9 17:14
 */
public class SingleThreadSender {

    public static void main(String[] args) {
        AsyncSenderThread asyncThreadSender = new AsyncSenderThread();
        asyncThreadSender.start();
    }

}
