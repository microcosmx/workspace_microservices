package sso.queue;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import sso.domain.LoginResult;

@Component
@EnableBinding(Source.class)
public class MsgSendingBean {

	private Source source;

	@Autowired
	public MsgSendingBean(Source source) {
		this.source = source;
	}

	public void sendLoginInfoToSso(LoginResult loginResult){
		System.out.println("[Sso Service][Sending Bean] Send Login Into To SSO");
		Gson gson = new Gson();
		String loginResultData = gson.toJson(loginResult);
		System.out.println("[Sso Service][Sending Bean] Sending Data:" + loginResultData);
		source.output().send(MessageBuilder.withPayload(loginResultData).build());
	}
}
