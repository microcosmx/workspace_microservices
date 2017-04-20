import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import orders.domain.Order;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

//{"id":"0bff8e54-26fc-4f4c-ad89-c45e9ec5a3d1","boughtDate":1491446005883,"travelDate":1491446005883,"accountId":"c5e84370-484e-4d16-9490-71627540f752","trainNumber":{"type":0,"number":2001},"coachNumber":5,"seatClass":2,"seatNumber":{"position":"A","lineNum":5,"seatNumber":0,"normalSeat":false},"from":"上海虹桥","to":"南京南","status":1}

public class testCreateOrder {
    public static void main(String[] args)throws Exception{
        URL url = new URL("http://localhost:12031/createNewOrders");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-Type","application/json; charset=UTF-8");
        connection.connect();
        //POST请求
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        //注册的新账户对象
        //!!!!注意使用gson进行日期转换！
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
        Order o = new Order();
        o.setAccountId(UUID.fromString("c5e84370-484e-4d16-9490-71627540f752"));
        String jsonStr = gson.toJson(o);
        System.out.println(jsonStr);
        //写入
        out.write(jsonStr.getBytes("UTF-8"));//这样可以处理中文乱码问题
        out.flush();
        out.close();
        //读取响应
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        String lines;
        StringBuffer sb = new StringBuffer("");
        while ((lines = reader.readLine()) != null) {
            lines = new String(lines.getBytes(), "utf-8");
            sb.append(lines);
        }
        System.out.println(sb);
        reader.close();
        // 断开连接
        connection.disconnect();
    }
}
