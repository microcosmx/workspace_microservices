package preserveOther.queue;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import preserveOther.domain.GetTripAllDetailInfo;

@Component
@EnableBinding(Source.class)
public class MsgSendingBean {

	private Source source;

	@Autowired
	public MsgSendingBean(Source source) {
		this.source = source;
	}

	public void sendSeachTravlDetailInfo(GetTripAllDetailInfo gtdi){
		System.out.println("[Preserve Other Service][Sending Bean] Send a gtdi to queue.");
		Gson gson = new Gson();
		String gtdiString = gson.toJson(gtdi);
		source.output().send(MessageBuilder.withPayload(gtdiString).build());
	}
}
