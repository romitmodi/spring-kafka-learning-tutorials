package learning.spring.kafka.tutorials.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;

public class SpringKafkaListener implements AcknowledgingMessageListener<String, Object> {

	private static Logger LOG = LoggerFactory.getLogger(SpringKafkaListener.class);

	public void onMessage(ConsumerRecord<String, Object> data, Acknowledgment acknowledgment) {
		LOG.info("Hello World");
		acknowledgment.acknowledge();
	}

}
