package train.service;

import train.domain.TrainType;

public interface TrainService {
    //CRUD
    boolean create(String id, int economyClass, int confortClass);
    TrainType retrieve(String id);
    boolean update(String id, int economyClass, int confortClass);
    boolean delete(String id);
}
