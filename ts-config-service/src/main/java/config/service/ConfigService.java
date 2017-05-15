package config.service;

import config.domain.Config;
import config.domain.Information;

public interface ConfigService {
    String create(String name, String value,String description);
    String createByJson(Information info);
    String update(String name, String value,String description);
    Config query(String name);
    String delete(String name);
}
