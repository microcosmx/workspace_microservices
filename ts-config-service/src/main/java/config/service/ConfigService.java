package config.service;

import config.domain.Config;

public interface ConfigService {
    String create(String name, String value,String description);
    String update(String name, String value,String description);
    Config query(String name);
    String delete(String name);
}
