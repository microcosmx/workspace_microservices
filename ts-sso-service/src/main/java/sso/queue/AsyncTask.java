package sso.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import sso.domain.LoginInfo;
import sso.domain.LoginResult;
import sso.domain.PutLoginResult;
import sso.service.AccountSsoService;

@Component  
public class AsyncTask {

    @Autowired
    private MsgSendingBean sendingBean;

    @Autowired
    private AccountSsoService service;

    @Async("mySimpleAsync")
    public void putLoginResultIntoReturnQueue(LoginInfo loginInfo){
        //1.调用service进行执行执行
        System.out.println("[SSO Service][Async Task] SSO准备开始执行登录操作");
        LoginResult loginResult = service.login(loginInfo);
        PutLoginResult tokenResult = service.loginPutToken(loginResult.getAccount().getId().toString());
        if(tokenResult.isStatus() == true){
            loginResult.setToken(tokenResult.getToken());
            loginResult.setMessage(tokenResult.getMsg());
        }else{
            loginResult.setStatus(false);
            loginResult.setMessage(tokenResult.getMsg());
            loginResult.setAccount(null);
            loginResult.setToken(null);
        }
        //2.获取result
        sendingBean.sendLoginInfoToSso(loginResult);
    }
      
}  
