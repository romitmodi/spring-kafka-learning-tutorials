package learning.spring.kafka.tutorials.deserializer;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.serializer.Deserializer;

public class CustomJsonDeserializer<T> implements Deserializer<T> {

	public T deserialize(InputStream inputStream) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
