package hello;

import java.util.concurrent.ConcurrentHashMap;

public class StatusBean {
	public ConcurrentHashMap<String, String> statusMap = new ConcurrentHashMap<String, String>();
	
	public void init(){
		
		statusMap.put("status", "normal");
		
    }
}


