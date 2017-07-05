package hello;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;

public class SchedulerBean {
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);
    
    public static ConcurrentHashMap<String, Double> priceMap = new ConcurrentHashMap<String, Double>();
    
    @Autowired
	private ProductRepository repository;

	public void schedulerSync(){
		
		priceMap.put("p1", 6.0);
		
    	int interval = 600; //600s
    	
    	Calendar calendar = Calendar.getInstance();
    	
    	calendar.setTime(new Date());
    	int hour = calendar.get(Calendar.HOUR_OF_DAY);
    	int min = calendar.get(Calendar.MINUTE);
    	int sec = calendar.get(Calendar.SECOND);
    	
        calendar.set(Calendar.HOUR_OF_DAY, hour); // 控制时
        calendar.set(Calendar.MINUTE, min);       // 控制分
        calendar.set(Calendar.SECOND, 0);       // 控制秒
		Date time = calendar.getTime();         // 得出执行任务的时间,此处为当前时间的起始分钟
		Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
            	priceMap.forEach((k,v)->{
            		Product p = repository.findByName(k);
            		if(p != null){
            			priceMap.put(k, p.price);
            		}
            	});
            	// fetch all cache
    			log.info("---------------scheduler cached maps:");
    			log.info(priceMap.toString());
            }
        }, time, 1000*interval);// 这里设定将每10s固定执行
    }

}
