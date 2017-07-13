package hello;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class StatusBean {
	public List<String> chartMsgs = new ArrayList<String>();
	
	public void init(){
		
		chartMsgs.clear();
		
    }
	
	public void addKey(String key){
		chartMsgs.add(key);
	}
}


