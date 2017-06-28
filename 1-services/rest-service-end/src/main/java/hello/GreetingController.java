package hello;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

    private final AtomicLong counter = new AtomicLong();
    
    @Autowired
    private MsgSendingBean sendingBean;

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="cal", defaultValue="50") String cal) throws Exception {
    	// log.info(cal);

    	double cal2 = Math.abs(Double.valueOf(cal)-50); 
    	log.info(String.valueOf(cal2));
    	
    	//async messaging
    	log.info("message 1");
    	sendingBean.sayHello("message 1:" + cal2);
//    	log.info("message 2");
//    	sendingBean.sayHello("message 2:" + cal2*2);
//    	log.info("message 3");
//    	sendingBean.sayHello("message 3:" + cal2*3);
        
    	Greeting value  = new Greeting(counter.incrementAndGet(), Double.valueOf(cal2)<100);
    	log.info("----------start memory alloc-----------");
    	memory();
        
        log.info("--------service end-----------");
        log.info(value.toString());
        return value.toString();
    }
    
    
    private void memory() {
		List<int[]> list = new ArrayList<int[]>();

		Runtime run = Runtime.getRuntime();
		int i = 1;
		while (true) {
			int[] arr = new int[1024 * 8];
			list.add(arr);

			if (i++ % 1000 == 0) {
				try {
					Thread.sleep(600);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.print("最大内存=" + run.maxMemory() / 1024 / 1024 + "M,");
				System.out.print("已分配内存=" + run.totalMemory() / 1024 / 1024 + "M,");
				System.out.print("剩余空间内存=" + run.freeMemory() / 1024 / 1024 + "M");
				System.out.println(
						"最大可用内存=" + (run.maxMemory() - run.totalMemory() + run.freeMemory()) / 1024 / 1024 + "M");
			}
		}
	}
}
