package config.init;

import config.domain.Information;
import config.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by Chenjie Xu on 2017/6/5.
 */
@Component
public class InitData implements CommandLineRunner{

    @Autowired
    ConfigService service;

    @Override
    public void run(String... args) throws Exception {
        Information info = new Information();

        info.setName("DirectTicketAllocationProportion");
        info.setValue("50%");
        info.setDescription("");
        service.create(info);
    }
}
