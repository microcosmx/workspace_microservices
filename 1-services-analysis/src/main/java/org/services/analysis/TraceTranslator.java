package org.services.analysis;

import java.io.*;
import java.util.*;
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
//        String traceStr = readFile("./sample/trace-data.json");
        String traceStr = readFile("./sample/traces-error-normal.json");

        JSONArray tracelist = new JSONArray(traceStr);

        List<HashMap<String,String>> logs = new ArrayList<HashMap<String, String>>();

        for (int k = 0; k < tracelist.length(); k++) {

            JSONArray traceobj = tracelist.getJSONArray(k);

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
                                JSONObject endpoint = anno.getJSONObject("endpoint");
                                String ipv4 = endpoint.getString("ipv4");
                                String port = String.valueOf(endpoint.get("port"));

                                if(instance_id.indexOf(content.get("serverName")) != -1){
                                    String new_instance_id = ipv4 + ":" + content.get("serverName") + ":" + port;
                                    content.put("server_instance_id", new_instance_id);
                                }else if(instance_id.indexOf(content.get("clientName")) != -1){
                                    String new_instance_id = ipv4 + ":" + content.get("clientName") + ":" + port;
                                    content.put("client_instance_id", new_instance_id);
                                }
                            }
                            if ("http.method".equals(anno.getString("key"))) {
                                String httpMethod = anno.getString("value");
                                content.put("httpMethod", httpMethod);
                            }

                        }
                        content.put("traceId" , traceId);
                        content.put("api",
                                content.get("serverName") + "." + content.get("classname") + "." + content.get("methodname"));

                    }

                    serviceList.add(content);
                }
            }

            // filter validate service api
            List<HashMap<String, String>> processList = serviceList.stream()
                    .filter(elem -> !"message:output".equals(elem.get("spanname"))).collect(Collectors.toList());
            boolean failed = processList.stream().anyMatch(pl -> pl.containsKey("error"));

            processList.forEach(n -> {

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
                    log.put("event" , n.get("serverName") + ":" + n.get("api"));
                    if(null != n.get("api")){
                        log.put("api", n.get("api"));
                    }
                    if(n.containsKey("error")){
                        log.put("error", n.get("error"));
                    }
                    logs.add(log);
                }
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
//                    log.put("event", "received message from " + n.get("client_instance_id"));
                    log.put("event", "");
                    if(n.containsKey("error")){
                        log.put("error", n.get("error"));
                    }
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
//                    log.put("event" , "sending result to " + n.get("client_instance_id"));
                    log.put("event", "");
                    if(n.containsKey("error")){
                        log.put("error", n.get("error"));
                    }
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
//                    log.put("event" , "received message from " + n.get("server_instance_id"));
                    log.put("event", "");
                    if(n.containsKey("error")){
                        log.put("error", n.get("error"));
                    }
                    logs.add(log);
                }


            });

        }



//        writeFile("./sample/trace-data-shiviz.txt", list);
        HashMap<String,String> traceIds = new HashMap<String,String>();
        logs.forEach(n -> {
            if(!traceIds.containsKey(n.get("traceId"))){
                traceIds.put(n.get("traceId"),"");
            }
        });

        List<List<HashMap<String,String>>> list = new ArrayList<List<HashMap<String,String>>>();
        HashMap<List<HashMap<String,String>>, Boolean> failures = new HashMap<List<HashMap<String,String>>, Boolean>();
        traceIds.forEach((n,s) -> {
            List l = logs.stream().filter(elem -> {
                return n.equals(elem.get("traceId"));
            }).collect(Collectors.toList());
            List<HashMap<String,String>> listWithClock = clock(l);
            boolean failed = listWithClock.stream().anyMatch(pl -> pl.containsKey("error"));
            failures.put(listWithClock,failed);
            list.add(listWithClock);
        });

        //(elem -> !"message:output".equals(elem.get("spanname"))).collect(Collectors.toList());
//        List<HashMap<String,String>> list = clock(logs);
        writeFile("./output/shiviz-log-error-normal.txt", list, failures);


    }

    public static List<HashMap<String,String>> clock(List<HashMap<String,String>> logs){
        HashMap<String,HashMap<String,Integer>> clocks = new HashMap<String,HashMap<String,Integer>>();

        List<HashMap<String,String>> list = logs.stream().sorted((log1,log2) -> {
            Long time1 = Long.valueOf(log1.get("timestamp"));
            Long time2 = Long.valueOf(log2.get("timestamp"));
            return time1.compareTo(time2);
        }).collect(Collectors.toList());

        list.forEach(n -> {
            if(clocks.containsKey(n.get("host"))){
                HashMap<String,Integer> clock = clocks.get(n.get("host"));

                if(n.get("src") != null){
                    HashMap<String,Integer> srcClock = (HashMap<String,Integer>)clocks.get(n.get("src")).clone();

                    Iterator<Map.Entry<String,Integer>> iterator = srcClock.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, Integer> entry = iterator.next();
                        if(clock.get(entry.getKey()) != null){
                            if(entry.getValue() <= clock.get(entry.getKey())){
                                //don't change clock
                            }else{  //update clock
                                clock.put(entry.getKey(),entry.getValue());
                            }
                        }else{   //update clock
                            clock.put(entry.getKey(),entry.getValue());
                        }
                    }

                    clock.put(n.get("host"),clock.get(n.get("host")) +1);

                }else{
                    clock.put(n.get("host"),clock.get(n.get("host")) +1);
                }
                n.put("clock",clock.toString());
                clocks.put(n.get("host"), clock);
            }else{
                HashMap<String,Integer> clock = new HashMap<String,Integer>();
                if(n.get("src") != null){
                    HashMap<String,Integer> srcClock = (HashMap<String,Integer>)clocks.get(n.get("src")).clone();

                    Iterator<Map.Entry<String,Integer>> iterator = srcClock.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, Integer> entry = iterator.next();
                        if(clock.get(entry.getKey()) != null){
                            if(entry.getValue() <= clock.get(entry.getKey())){
                                //don't change clock
                            }else{  //update clock
                                clock.put(entry.getKey(),entry.getValue());
                            }
                        }else{   //update clock
                            clock.put(entry.getKey(),entry.getValue());
                        }
                    }
                    clock.put(n.get("host"),1);
                }else{
                    clock.put(n.get("host"),1);
                }
                n.put("clock",clock.toString());
                clocks.put(n.get("host"), clock);
            }
        });

        return list;
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

    public static boolean write(String path, List<HashMap<String,String>> logs){
        File writer = new File(path);
        BufferedWriter out = null;
        try{
            writer.createNewFile(); // 创建新文件
            out = new BufferedWriter(new FileWriter(writer));
            Iterator<HashMap<String,String>> iterator = logs.iterator();
            while(iterator.hasNext()){
                HashMap<String,String> map = iterator.next();
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


//    public static boolean writeFile(String path, List<HashMap<String,String>> logs){
//        File writer = new File(path);
//        BufferedWriter out = null;
//        try{
//            writer.createNewFile(); // 创建新文件
//            out = new BufferedWriter(new FileWriter(writer));
//            Iterator<HashMap<String,String>> iterator = logs.iterator();
//            while(iterator.hasNext()){
//                HashMap<String,String> map = iterator.next();
//                Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();
//                out.write("{");
//                while (entries.hasNext()) {
//                    Map.Entry<String, String> entry = entries.next();
//                    if(entry.getKey().equals("clock")){
//                        String clocks = entry.getValue();
//                        String[] c = clocks.split(",");
//                        out.write("clock={");
//                        for(int i=0,length=c.length; i<length; i++){
//                            c[i] = "\"" + c[i].substring(1,c[i].indexOf("=")) + "\":"
//                                    + c[i].substring(c[i].indexOf("=")+1);
//                            if(i < length-1){
//                                out.write(c[i] + ",");
//                            }else{
//                                out.write(c[i]);
//                            }
//
//                        }
//                        out.write(", ");
//
//                    }else{
//                        out.write(entry.toString() + ", ");
//                    }
//                }
//
//                out.write("}\r\n");
//            }
//        }catch(IOException e){
//            e.printStackTrace();
//            return false;
//        }finally{
//            if (out != null) {
//                try {
//                    out.flush();
//                    out.close();
//                } catch (IOException e1) {
//                }
//            }
//        }
//
//        return true;
//    }

    public static boolean writeFile(String path, List<List<HashMap<String,String>>> logs, HashMap<List<HashMap<String,String>>, Boolean> failures){
        File writer = new File(path);
        BufferedWriter out = null;
        try{
            writer.createNewFile(); // 创建新文件
            out = new BufferedWriter(new FileWriter(writer));
            int fail = 0;
            int success = 0;

            Iterator<List<HashMap<String,String>>> iterator1 = logs.iterator();
            while(iterator1.hasNext()){
                List<HashMap<String,String>> list = iterator1.next();

                boolean failed = failures.get(list);
                if(failed){
                    out.write("\r\n=== Fail execution " + (fail++) + " ===\r\n");
                }else{
                    out.write("\r\n=== Success execution " + (success++) + " ===\r\n");
                }

                Iterator<HashMap<String,String>> iterator = list.iterator();
                while(iterator.hasNext()){
                    HashMap<String,String> map = iterator.next();
                    Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();
                    out.write("{");
                    while (entries.hasNext()) {
                        Map.Entry<String, String> entry = entries.next();
                        if(entry.getKey().equals("clock")){
                            String clocks = entry.getValue();
                            String[] c = clocks.split(",");
                            out.write("clock={");
                            for(int i=0,length=c.length; i<length; i++){
                                c[i] = "\"" + c[i].substring(1,c[i].indexOf("=")) + "\":"
                                        + c[i].substring(c[i].indexOf("=")+1);
                                if(i < length-1){
                                    out.write(c[i] + ",");
                                }else{
                                    out.write(c[i]);
                                }

                            }
                            out.write(", ");

                        }else{
                            out.write(entry.toString() + ", ");
                        }
                    }

                    out.write("}\r\n");
                }
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

