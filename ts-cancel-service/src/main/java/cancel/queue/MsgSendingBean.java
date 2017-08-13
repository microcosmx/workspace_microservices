package cancel.queue;

import cancel.domain.ChangeOrderInfo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Source.class)
public class MsgSendingBean {

	private Source source;

	@Autowired
	public MsgSendingBean(Source source) {
		this.source = source;
	}

	public void sendCancelInfoToOrderOther(ChangeOrderInfo changeOrderInfo){
		System.out.println("[Cancel Order Service][Sending Bean] Send Change Order Into To SSO");
		Gson gson = new Gson();
		String changeOrderInfoData = gson.toJson(changeOrderInfo);
		System.out.println("[Cancel Order Service][Sending Bean] Sending Data:" + changeOrderInfoData);
		source.output().send(MessageBuilder.withPayload(changeOrderInfoData).build());
	}
}
