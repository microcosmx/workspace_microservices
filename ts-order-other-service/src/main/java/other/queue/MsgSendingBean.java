package other.queue;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import other.domain.ChangeOrderResult;

@Component
@EnableBinding(Source.class)
public class MsgSendingBean {

	private Source source;

	@Autowired
	public MsgSendingBean(Source source) {
		this.source = source;
	}

	public void sendLoginInfoToSso(ChangeOrderResult changeOrderResult){
		System.out.println("[Order Other Service][Sending Bean] Send Login Into To SSO");
		Gson gson = new Gson();
		String changeOrderResultData = gson.toJson(changeOrderResult);
		System.out.println("[Orde Other Service][Sending Bean] Sending Data:" + changeOrderResultData);
		source.output().send(MessageBuilder.withPayload(changeOrderResultData).build());
	}
}
