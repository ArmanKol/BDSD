package nl.hu;

/**
 * Created by roelant on 19/12/2019.
 */
public class BDSDKafkaConsumerRunner {
    public static final String TOPIC = "transactie";

    public static void main(String[] args) {
        boolean isAsync = false;
        BDSDKafkaConsumer consumerThread = new BDSDKafkaConsumer(TOPIC, isAsync);
        // start the producer
        consumerThread.start();
    }
}
