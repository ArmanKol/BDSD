package nl.hu;

import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.json.JSONObject;

import persistence.TransactieDaoImpl;

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
	
	static void runProducer() throws Exception {
	      final Producer<String, String> producer = createProducer();
	      
	      TransactieDaoImpl transactieDaoImpl = new TransactieDaoImpl();
	      List<Transaction> transactions = transactieDaoImpl.findAll();
	      try {
	          for (Transaction t : transactions) {
	        	  JSONObject jsonObject = new JSONObject();
	        	  
	        	  jsonObject.put("productID", t.getProductId());
	        	  jsonObject.put("datum", t.getDateInString());
	        	  jsonObject.put("filiaalID", t.getFiliaalID());
	        	  
	              final ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, Integer.toString(t.getCustomerId()), jsonObject.toString());
	              producer.send(record);
	              //RecordMetadata metadata = producer.send(record).get();

	          }
	      } finally {
	          producer.flush();
	          producer.close();
	      }
	 }
	
	public void run(){
		try {
			runProducer();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
}