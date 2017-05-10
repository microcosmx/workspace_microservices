package fdse.microservice.service;

public interface StationService {
    //CRUD
    boolean create(String name);
    boolean exist(String name);
    //boolean update(Information info);
    boolean delete(String name);
}
