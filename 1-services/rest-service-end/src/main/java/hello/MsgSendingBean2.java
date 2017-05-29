package hello;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.SendTo;

@EnableBinding(Processor.class)
public class MsgSendingBean2 {

	private static Logger logger = LoggerFactory.getLogger(MsgSendingBean2.class);

	@SendTo(Processor.OUTPUT)
	public List<String> processor(List<String> inputStream) {
		List<String> filtered = inputStream.stream().filter(x -> Double.valueOf(x) > 200).collect(Collectors.toList()); 
		return filtered;
	}

	private static Double avg(List<String> data) {
		double sum = 0;
		double count = 0;
		for(String d : data) {
			count++;
			sum += Double.valueOf(d);
		}
		return sum/count;
	}

}