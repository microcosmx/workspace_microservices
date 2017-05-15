package fdse.microservice.service;

import fdse.microservice.domain.Information;

public interface StationService {
    //CRUD
    boolean create(Information info);
    boolean exist(Information info);
    //boolean update(Information info);
    boolean delete(Information info);
}
