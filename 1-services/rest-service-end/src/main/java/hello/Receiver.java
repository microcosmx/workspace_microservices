package hello;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
	
	private final AtomicLong counter = new AtomicLong();
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
    	
    	try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	log.info("-----------mq message received------------");
    	log.info("latch count: " + latch.getCount());
    	log.info("all count: " + counter.incrementAndGet() + ", " + "message: <" + message + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
