package login.queue;

import com.google.gson.Gson;
import login.domain.LoginInfo;
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

	public void sendLoginInfoToSso(LoginInfo loginInfo){
		System.out.println("[Login Service][Sending Bean] Send Login Into To SSO");
		Gson gson = new Gson();
		String loginSsoQueueInfoData = gson.toJson(loginInfo);
		System.out.println("[Login Service][Sending Bean] Sending Data:" + loginSsoQueueInfoData);
		source.output().send(MessageBuilder.withPayload(loginSsoQueueInfoData).build());
	}
}
