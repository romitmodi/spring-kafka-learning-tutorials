package learning.spring.kafka.tutorials.context;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import learning.spring.kafka.tutorials.listener.SpringKafkaListener;

@EnableKafka
@Configuration
@PropertySource("classpath:spring-kafka.properties")
public class SpringKafkaContext {

	private static Logger LOG = LoggerFactory.getLogger(SpringKafkaContext.class);

	@Autowired
	Environment env;

	@Bean
	public ConsumerFactory<String, Object> getSpringKafkaConsumerFactory() {
		LOG.info("getSpringKafkaConsumerFactory");
		Map<String, Object> props = new HashMap<String, Object>();
		props.put(ConsumerConfig.CLIENT_ID_CONFIG, env.getProperty("spring.kafka.application.id"));
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty("spring.kafka.bootstrap.server"));
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, env.getProperty("spring.kafka.offset.strategey"));
		props.put(ConsumerConfig.GROUP_ID_CONFIG, env.getProperty("spring.kafka.group.id"));
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(JsonDeserializer.VALUE_TYPE_METHOD, String.class);
		return new DefaultKafkaConsumerFactory<String, Object>(props);
	}

	@Bean
	public SpringKafkaListener messageListener() {
		return new SpringKafkaListener();
	}

	@Bean
	public KafkaMessageListenerContainer<String, Object> kafkaMessageListenerContainer(
	        ConsumerFactory<String, Object> consumerFactory, SpringKafkaListener listener) {
		ContainerProperties containerProperties = new ContainerProperties(env.getProperty("spring.kafka.topic"));
		containerProperties.setMessageListener(listener);
		return new KafkaMessageListenerContainer<String, Object>(consumerFactory, containerProperties);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(
	        ConsumerFactory<String, Object> consumerFactory) {
		LOG.info("kafkaListenerContainerFactory");
		ConcurrentKafkaListenerContainerFactory<String, Object> concurrentKafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<String, Object>();
		concurrentKafkaListenerContainerFactory.setConsumerFactory(consumerFactory);
		return concurrentKafkaListenerContainerFactory;
	}

}
