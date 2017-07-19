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

package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.web.client.RestTemplate;

/**
 * @author Dave Syer
 *
 */
@EnableBinding(Sink.class)
public class MsgReveiceBean {

	private static Logger logger = LoggerFactory.getLogger(MsgReveiceBean.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	private final AtomicLong counter = new AtomicLong();
	public double lastVal = 30;

	@StreamListener(Sink.INPUT)
	public void loggerSink(Object payload) throws Exception {

		logger.info("-------------message received: " + payload);
		
		double newVal = Double.valueOf(payload.toString());
		
		logger.info(counter.get() + " : " + lastVal);
		
		int index = (int) ((counter.incrementAndGet()-1)%9);
		if(index < 3){
			lastVal = Math.min(lastVal, newVal);
		}else if(index < 6){
			lastVal = Math.min(lastVal, newVal);
		}else{
			lastVal = (lastVal + newVal)/2;
		}
		
		logger.info(index + " : " + lastVal);
		
		if(lastVal > 100){
			throw new Exception("error price calculation");
		}
	}

}



//@EnableBinding(CustomSink.class)
//public class MsgReveiceBean {
//	
//	private static Logger logger = LoggerFactory.getLogger(MsgReveiceBean.class);
//	
//	@Autowired
//	private CustomSink customSink;
//	
//	@StreamListener(CustomSink.INPUT)
//	public void loggerSink(Object payload) {
//		try {
//			Thread.sleep(1);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		logger.info("message received: " + payload);
//	}
//}
//
//interface CustomSink {
//	String INPUT = "custominput";
//	@Input(CustomSink.INPUT)
//	SubscribableChannel input();
//}