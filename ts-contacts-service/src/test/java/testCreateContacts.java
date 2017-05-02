import com.google.gson.Gson;
import contacts.domain.AddContactsInfo;
import contacts.domain.DocumentType;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class testCreateContacts {
    public static void main(String[] args)throws Exception{
        URL url = new URL("http://localhost:12347/createNewContacts");
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
        //添加新联系人
        AddContactsInfo aci = new AddContactsInfo();
        aci.setAccountId(UUID.fromString("c5e84370-484e-4d16-9490-71627540f752"));
        aci.setDocumentNumber("142603199601311011");
        aci.setDocumentType(DocumentType.ID_CARD.getCode());
        aci.setName("chenxin");
        aci.setPhoneNumber("+86 15221870263");
        Gson gson = new Gson();
        String str = gson.toJson(aci);
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
