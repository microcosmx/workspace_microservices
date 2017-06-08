package login.controller;

import login.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import login.service.AccountLoginService;

@RestController
public class AccountLoginController {

    @Autowired
    private AccountLoginService accountService;

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Accounts Login Service ] !";
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public LoginResult login(@RequestBody LoginInfo li, @CookieValue String YsbCaptcha){
        System.out.println("[Login Service][Login] Verification Code:" + li.getVerificationCode() +
                " VerifyCookie:" + YsbCaptcha);
        return accountService.login(li,YsbCaptcha);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public LogoutResult logout(@RequestBody LogoutInfo li){
        System.out.println("[Login Service][Logout] Logout ID:" + li.getId() + " Token:" + li.getToken());
        return accountService.logout(li);
    }
}