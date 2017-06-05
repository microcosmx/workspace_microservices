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

        info.setName("直达车票分配比例");
        info.setValue("50%");
        info.setDescription("查询车票配比，以车站ABC为例，A是始发站，B是途径的车站，C是终点站，分配AC 50%，如果总票数100，那么AC有50张票，AB和BC也各" +
                "有50张票，因为AB和AC拼起来正好是一张AC。");
        service.create(info);
    }
}
