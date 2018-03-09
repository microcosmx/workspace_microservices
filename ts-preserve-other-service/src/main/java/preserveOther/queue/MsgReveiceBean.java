/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package preserveOther.queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.web.client.RestTemplate;
import preserveOther.domain.OrderTicketsInfo;
import preserveOther.domain.OrderTicketsResult;
import preserveOther.domain.ReservationQueuePayload;
import preserveOther.domain.ReserveQueueInformation;
import preserveOther.service.PreserveOtherService;
import preserveOther.service.PreserveOtherServiceImpl;

import java.util.concurrent.atomic.AtomicLong;

@EnableBinding(Sink.class)
public class MsgReveiceBean {

	@Autowired
	private PreserveOtherService preserveService;

	@Autowired
	private PreserveOtherServiceImpl preserveOtherServiceImpl;

	@StreamListener(Sink.INPUT)
	public void loggerSink(Object payload) {

		ReservationQueuePayload payloadEntity = (ReservationQueuePayload)payload;

		OrderTicketsInfo oti = payloadEntity.getOti();
		String userId = payloadEntity.getUserId();
		String loginToken = payloadEntity.getLoginToken();
		String requestId = payloadEntity.getRequestId();

		OrderTicketsResult result = preserveService.preserve(oti, userId, loginToken);

		ReserveQueueInformation info = new ReserveQueueInformation(requestId,userId);
		info.setResult(result);

		preserveOtherServiceImpl.arrayList.add(info);

	}
}
