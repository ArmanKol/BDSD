package nl.hu;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaProducerTransactie {
	private final static String TOPIC = "transactie";
    private final static String BOOTSTRAP_SERVERS ="localhost:9092,localhost:9093,localhost:9094";
	 
	private static Producer<Long, String> createProducer() {
	        Properties props = new Properties();
	        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
	                                            BOOTSTRAP_SERVERS);
	        props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExampleProducer");
	        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
	                                        LongSerializer.class.getName());
	        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
	                                    StringSerializer.class.getName());
	        return new KafkaProducer<>(props);
	    }
	
}