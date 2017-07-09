package org.services.analysis;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by hh on 2017-07-08.
 */
public class TraceTranslator {
    public static void main(String[] args) throws JSONException {

//        String traceStr = readFile("src/main/resources/sample/traces1.json");
        String traceStr = readFile("./sample/trace-data.json");
        JSONArray tracelist = new JSONArray(traceStr);

        HashMap<String,HashMap<String,Integer>> clocks = new HashMap<String,HashMap<String,Integer>>();
        List<HashMap<String,String>> logs = new ArrayList<HashMap<String, String>>();

        for (int k = 0; k < tracelist.length(); k++) {

            JSONArray traceobj = tracelist.getJSONArray(k);
//            System.out.println(traceobj);
//            JSONArray traceobj = (JSONArray) tracelist.get(k);

            // String call = readFile("./sample/call1x.json");
            // JSONArray spanlist = new JSONArray(call);

            List<HashMap<String, String>> serviceList = new ArrayList<HashMap<String, String>>();
            String traceId = ((JSONObject) traceobj.get(0)).getString("traceId");
            for (int j = 0; j < traceobj.length(); j++) {
                JSONObject spanobj = (JSONObject) traceobj.get(j);

                // String traceId = spanobj.getString("traceId");
                String id = spanobj.getString("id");
                String pid = "";
                if (spanobj.has("parentId")) {
                    pid = spanobj.getString("parentId");
                }
                String name = spanobj.getString("name");

                HashMap<String, String> content = new HashMap<String, String>();
                content.put("spanid", id);
                content.put("parentid", pid);
                content.put("spanname", name);
                if(spanobj.has("annotations")){
                    JSONArray annotations = spanobj.getJSONArray("annotations");
                    for (int i = 0; i < annotations.length(); i++) {
                        JSONObject anno = annotations.getJSONObject(i);
                        if ("cs".equals(anno.getString("value"))) {
                            JSONObject endpoint = anno.getJSONObject("endpoint");
                            String service = endpoint.getString("serviceName");
                            content.put("clientName", service);
                            String csTime = String.valueOf(anno.getLong("timestamp"));
                            content.put("csTime",csTime);
                        }
                        if ("sr".equals(anno.getString("value"))) {
                            JSONObject endpoint = anno.getJSONObject("endpoint");
                            String service = endpoint.getString("serviceName");
                            content.put("serverName", service);
                            String srTime = String.valueOf(anno.getLong("timestamp"));
                            content.put("srTime",srTime);
                        }
                        if ("ss".equals(anno.getString("value"))) {
                            JSONObject endpoint = anno.getJSONObject("endpoint");
                            String service = endpoint.getString("serviceName");
                            content.put("serverName", service);
                            String ssTime = String.valueOf(anno.getLong("timestamp"));
                            content.put("ssTime",ssTime);
                        }
                        if ("cr".equals(anno.getString("value"))) {
                            JSONObject endpoint = anno.getJSONObject("endpoint");
                            String service = endpoint.getString("serviceName");
                            content.put("clientName", service);
                            String crTime = String.valueOf(anno.getLong("timestamp"));
                            content.put("crTime",crTime);
                        }
                    }

                    if (name.contains("message:")) {
                        if ("message:input".equals(name)) {
                            content.put("api", content.get("service") + "." + "message_received");
                        }
                    } else {
                        JSONArray binaryAnnotations = spanobj.getJSONArray("binaryAnnotations");
                        for (int i = 0; i < binaryAnnotations.length(); i++) {
                            JSONObject anno = binaryAnnotations.getJSONObject(i);
                            if ("error".equals(anno.getString("key"))) {
                                content.put("error", anno.getString("value"));
                            }
                            if ("mvc.controller.class".equals(anno.getString("key"))
                                    && !"BasicErrorController".equals(anno.getString("value"))) {
                                String classname = anno.getString("value");
                                content.put("classname", classname);
                            }
                            if ("mvc.controller.method".equals(anno.getString("key"))
                                    && !"errorHtml".equals(anno.getString("value"))) {
                                String methodname = anno.getString("value");
                                content.put("methodname", methodname);
                            }
                            if ("spring.instance_id".equals(anno.getString("key"))) {
                                String instance_id = anno.getString("value");
                                if(instance_id.indexOf(content.get("serverName")) != -1){
                                    content.put("server_instance_id", instance_id);
                                }else if(instance_id.indexOf(content.get("clientName")) != -1){
                                    content.put("client_instance_id", instance_id);
                                }
                            }
                            if ("http.method".equals(anno.getString("key"))) {
                                String httpMethod = anno.getString("value");
                                content.put("httpMethod", httpMethod);
                            }

                        }
                        content.put("traceId" , traceId);
                    }

                    serviceList.add(content);
                }
            }

            // filter validate service api
            List<HashMap<String, String>> processList = serviceList.stream()
                    .filter(elem -> !"message:output".equals(elem.get("spanname"))).collect(Collectors.toList());
            // processList.stream().forEach(n -> System.out.println(n));
            boolean failed = processList.stream().anyMatch(pl -> pl.containsKey("error"));

            processList.forEach(n -> {


                if(n.get("srTime") != null){
                    HashMap<String,String> log = new HashMap<String,String>();
                    log.put("timestamp",n.get("srTime"));
                    log.put("traceId" , n.get("traceId"));
                    log.put("spanId" , n.get("spanid"));
                    log.put("parentId" , n.get("parentid"));
                    log.put("hostName" , n.get("serverName"));
                    log.put("host" , n.get("server_instance_id"));
                    log.put("srcName" , n.get("clientName"));
                    log.put("src" , n.get("client_instance_id"));
                    log.put("event", "received message from " + n.get("client_instance_id"));
                    logs.add(log);
                }
                if(n.get("ssTime") != null){
                    HashMap<String,String> log = new HashMap<String,String>();
                    log.put("timestamp",n.get("ssTime"));
                    log.put("traceId" , n.get("traceId"));
                    log.put("spanId" , n.get("spanid"));
                    log.put("parentId" , n.get("parentid"));
                    log.put("hostName" , n.get("serverName"));
                    log.put("host" , n.get("server_instance_id"));
                    log.put("destName" , n.get("clientName"));
                    log.put("dest" , n.get("client_instance_id"));
                    log.put("event" , "sending result to " + n.get("client_instance_id"));
                    logs.add(log);
                }
                if(n.get("crTime") != null){
                    HashMap<String,String> log = new HashMap<String,String>();
                    log.put("timestamp",n.get("crTime"));
                    log.put("traceId" , n.get("traceId"));
                    log.put("spanId" , n.get("spanid"));
                    log.put("parentId" , n.get("parentid"));
                    log.put("hostName" , n.get("clientName"));
                    log.put("host" , n.get("client_instance_id"));
                    log.put("srcName" , n.get("serverName"));
                    log.put("src" , n.get("server_instance_id"));
                    log.put("event" , "received message from " + n.get("server_instance_id"));
                    logs.add(log);
                }
                if(n.get("csTime") != null){
                    HashMap<String,String> log = new HashMap<String,String>();
                    log.put("timestamp",n.get("csTime"));
                    log.put("traceId" , n.get("traceId"));
                    log.put("spanId" , n.get("spanid"));
                    log.put("parentId" , n.get("parentid"));
                    log.put("hostName" , n.get("clientName"));
                    log.put("host" , n.get("client_instance_id"));
                    log.put("destName" , n.get("serverName"));
                    log.put("dest" , n.get("server_instance_id"));
                    log.put("event" , "sending request " + n.get("httpMethod") + " to " + n.get("server_instance_id"));
                    logs.add(log);
                }


            });

        }

        List<HashMap<String,String>> list = logs.stream().sorted((log1,log2) -> {
            Long time1 = Long.valueOf(log1.get("timestamp"));
            Long time2 = Long.valueOf(log2.get("timestamp"));
            return time1.compareTo(time2);
        }).collect(Collectors.toList());

        list.forEach(n -> {
            if(clocks.containsKey(n.get("host"))){
                HashMap<String,Integer> clock = clocks.get(n.get("host"));
                if(n.get("src") != null){
                    clock = (HashMap<String,Integer>)clocks.get(n.get("src")).clone();

                    clock.put(n.get("host"),Integer.valueOf(clock.get(n.get("host")) +1));
                }else{
                    clock.put(n.get("host"),Integer.valueOf(clock.get(n.get("host")) +1));
                }
                n.put("clock",clock.toString());
                clocks.put(n.get("host"), clock);
            }else{
                HashMap<String,Integer> clock = new HashMap<String,Integer>();
                if(n.get("src") != null){
                    clock = (HashMap<String,Integer>)clocks.get(n.get("src")).clone();
                    clock.put(n.get("host"),1);
                }else{
                    clock.put(n.get("host"),1);
                }
                n.put("clock",clock.toString());
                clocks.put(n.get("host"), clock);
            }
        });

//        list.forEach(n -> System.out.println(n));
        writeFile("./sample/trace-data-shiviz.txt", list);

    }


    public static String readFile(String path) {
        File file = new File(path);
        BufferedReader reader = null;
        String laststr = "";
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr = laststr + tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return laststr;
    }

    public static boolean writeFile(String path, List<HashMap<String,String>> logs){
        File writer = new File(path);
        BufferedWriter out = null;
        try{
            writer.createNewFile(); // 创建新文件
            out = new BufferedWriter(new FileWriter(writer));
            Iterator<HashMap<String,String>> iterator = logs.iterator();
            while(iterator.hasNext()){
                HashMap<String,String> map = iterator.next();
                System.out.println(map.toString());
                out.write(map.toString() + "\r\n");
            }
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }finally{
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e1) {
                }
            }
        }

        return true;
    }
}

