package travel2.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import travel2.domain.GetTripAllDetailResult;

@Component
@EnableBinding(Source.class)
public class MsgSendingBean {

	private Source source;

	@Autowired
	public MsgSendingBean(Source source) {
		this.source = source;
	}

	public void returnTravel2DetailInfoResult(GetTripAllDetailResult gtdr) {
		System.out.println("[Travel Other Service][Sending Bean] Send a GTDR to queue.");
		source.output().send(MessageBuilder.withPayload(gtdr).build());
	}
}


