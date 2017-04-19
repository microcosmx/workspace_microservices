import query.domain.Contacts;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class testModifyContact {
    public static void main(String[] args) throws Exception{

        Contacts contacts = new Contacts();
        contacts.setId(UUID.fromString("47e26764-d93e-4e1f-bb64-7c2edc315e0c"));
        contacts.setAccountId(UUID.fromString("c5e84370-484e-4d16-9490-71627540f752"));
        contacts.setName("测试啦冀超");
        contacts.setDocumentType(1);
        contacts.setDocumentNumber("142603199601311011");
        contacts.setPhoneNumber("+86 15221870263");



        URL url = new URL("http://localhost:12332/saveContactsInfo");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("PUT");
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-Type","application/json; charset=UTF-8");
        connection.connect();
        //POST请求
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        //添加新联系人
        JSONObject obj = new JSONObject(contacts);
        //写入
        out.write(obj.toString().getBytes("UTF-8"));//这样可以处理中文乱码问题
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
