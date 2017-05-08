import com.google.gson.Gson;
import login.domain.LoginInfo;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * {"id":"c5e84370-484e-4d16-9490-71627540f752","password":"","gender":3,"name":"AfterChangeInfo","documentType":0,"documentNum":"0123456789","phoneNum":"352323"}
 */
public class testLogin {
    public static void main(String[] args) throws Exception{
        URL url = new URL("http://localhost:12349/login");
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
        //登录信息
        LoginInfo li = new LoginInfo();
        li.setPhoneNum("352323");
        li.setPassword("jichaofudan");
        Gson gson = new Gson();
        String str = gson.toJson(li);
        //写入
        out.write(str.getBytes("UTF-8"));//这样可以处理中文乱码问题
        System.out.println(str);
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
