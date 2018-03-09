package preserveOther.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import preserveOther.domain.OrderTicketsInfo;
import preserveOther.domain.ReservationQueuePayload;

@Component
@EnableBinding(Source.class)
public class MsgSendingBean {

	private Source source;

	@Autowired
	public MsgSendingBean(Source source) {
		this.source = source;
	}

	public void sayHello(OrderTicketsInfo oti, String userId, String loginToken, String requestId) {

		ReservationQueuePayload payload = new ReservationQueuePayload(oti,userId,loginToken, requestId);

		source.output().send(MessageBuilder.withPayload(payload).build());
	}
}
