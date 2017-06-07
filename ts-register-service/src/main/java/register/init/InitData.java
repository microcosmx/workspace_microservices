package register.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import register.service.RegisterService;

/**
 * Created by Chenjie Xu on 2017/6/5.
 */
@Component
public class InitData implements CommandLineRunner{
    @Autowired
    RegisterService service;

    public void run(String... args)throws Exception{

    }

}
