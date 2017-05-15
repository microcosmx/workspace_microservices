package config.controller;

/**
 * Created by Chenjie Xu on 2017/5/11.
 */

import config.domain.Config;
import config.domain.Information;
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
    @RequestMapping(value="/config/createbyjson", method = RequestMethod.POST)
    public String delete(@RequestBody Information info){
        return configService.createByJson(info);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/config/update", method = RequestMethod.POST)
    public String update(@RequestParam String name,@RequestParam String value,@RequestParam String description){
        return configService.update(name,value,description);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/config/query", method = RequestMethod.POST)
    public Config query(@RequestParam String name){
        return configService.query(name);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/config/delete", method = RequestMethod.POST)
    public String delete(@RequestParam String name){
        return configService.delete(name);
    }
}
