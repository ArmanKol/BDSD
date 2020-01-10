package nl.hu;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaProducerTransactie extends Thread{
	private final static String TOPIC = "transactie";
    private final static String BOOTSTRAP_SERVERS ="localhost:9092,localhost:9093,localhost:9094";
	 
	private static Producer<String, String> createProducer() {
	    Properties props = new Properties();
	    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,BOOTSTRAP_SERVERS);
	    props.put(ProducerConfig.CLIENT_ID_CONFIG, "BDSDKafkaProducer");
	    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
	    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
	    return new KafkaProducer<>(props);
	}
	
	static void runProducer(final int sendMessageCount) throws Exception {
	      final Producer<String, String> producer = createProducer();
	      long time = System.currentTimeMillis();

	      try {
	          for (long index = time; index < time + sendMessageCount; index++) {
	              final ProducerRecord<String, String> record =
	                      new ProducerRecord<>(TOPIC, "1", "0");

	              RecordMetadata metadata = producer.send(record).get();

	              long elapsedTime = System.currentTimeMillis() - time;
	              System.out.printf("sent record(key=%s value=%s) " + "meta(partition=%d, offset=%d) time=%d\n",
	            		  record.key(), record.value(), metadata.partition(), metadata.offset(), elapsedTime);

	          }
	      } finally {
	          producer.flush();
	          producer.close();
	      }
	 }
	
	public void run(){
		try {
			runProducer(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
}