package config.service;

import config.domain.Config;
import config.domain.Information;
import config.domain.Information2;

public interface ConfigService {
    String create(Information info);
    String update(Information info);
    Config query(Information2 info);
    String delete(Information2 info);
}
