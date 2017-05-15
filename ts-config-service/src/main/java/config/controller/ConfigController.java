package config.controller;

/**
 * Created by Chenjie Xu on 2017/5/11.
 */

import config.domain.Config;
import config.domain.Information;
import config.domain.Information2;
import config.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ConfigController {
    @Autowired
    private ConfigService configService;

//    @RequestMapping(value="/config/create", method = RequestMethod.POST)
//    public String create(@RequestParam String name,@RequestParam String value,@RequestParam String description){
//        return configService.create(name,value,description);
//    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/config/create", method = RequestMethod.POST)
    public String delete(@RequestBody Information info){
        return configService.create(info);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/config/update", method = RequestMethod.POST)
    public String update(@RequestBody Information info){
        return configService.update(info);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/config/query", method = RequestMethod.POST)
    public Config query(@RequestBody Information2 info){
        return configService.query(info);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/config/delete", method = RequestMethod.POST)
    public String delete(@RequestBody Information2 info){
        return configService.delete(info);
    }
}
