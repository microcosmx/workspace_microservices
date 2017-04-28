import com.google.gson.Gson;
import contacts.domain.Contacts;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class testModifyContact {
    public static void main(String[] args) throws Exception{

        Contacts contacts = new Contacts();
        contacts.setId(UUID.fromString("ff30bb7b-6b77-4d9b-b34d-576b4f80739a"));
        contacts.setAccountId(UUID.fromString("c5e84370-484e-4d16-9490-71627540f752"));
        contacts.setName("称心");
        contacts.setDocumentType(1);
        contacts.setDocumentNumber("142603199601311011");
        contacts.setPhoneNumber("+86 15221870263");

        URL url = new URL("http://localhost:12347/saveContactsInfo");
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
        Gson gson = new Gson();
        String str = gson.toJson(contacts);
        System.out.println(str);
        //写入
        out.write(str.getBytes("UTF-8"));//这样可以处理中文乱码问题
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
