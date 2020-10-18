package learning.spring.kafka.tutorials;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import learning.spring.kafka.tutorials.context.SpringKafkaContext;

public class SpringKafkaConsumer {

	private static Logger LOG = LoggerFactory.getLogger(SpringKafkaConsumer.class);

	@SuppressWarnings({ "static-access", "resource" })
	public static void main(String[] args) {
		try {
			new AnnotationConfigApplicationContext(SpringKafkaContext.class);
			LOG.info("Spring Kafka Consumer started");
			while (true) {
				Thread.currentThread().sleep(24 * 60 * 60);
			}
		} catch (InterruptedException e) {
			LOG.error(e.getMessage());
		} catch (Exception exception) {
			LOG.error(exception.getMessage());
		}
	}

}
