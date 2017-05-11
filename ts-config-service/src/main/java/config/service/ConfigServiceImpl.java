package config.service;

import config.domain.Config;
import config.repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    ConfigRepository repository;

    public String create(String name, String value,String description){
        if(repository.findByName(name) != null){
            String result = "Config " + name + " already exists.";
            return result;
        }else{
            Config config = new Config(name,value,description);
            repository.save(config);
            return "true";
        }
    }

    public String update(String name, String value,String description){
        if(repository.findByName(name) == null){
            String result = "Config " + name + " doesn't exist.";
            return result;
        }else{
            Config config = new Config(name,value,description);
            repository.save(config);
            return "true";
        }
    }

    public Config query(String name){
        if(repository.findByName(name) == null){
            return null;
        }else{
            return repository.findByName(name);
        }
    }

    public String delete(String name){
        if(repository.findByName(name) == null){
            String result = "Config " + name + " doesn't exist.";
            return result;
        }else{
            repository.deleteByName(name);
            return "true";
        }
    }
}
