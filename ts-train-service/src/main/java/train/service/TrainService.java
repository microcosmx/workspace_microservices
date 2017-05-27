package train.service;

import train.domain.Information;
import train.domain.Information2;
import train.domain.TrainType;

public interface TrainService {
    //CRUD
    boolean create(Information info);

    TrainType retrieve(Information2 info);

    boolean update(Information info);

    boolean delete(Information2 info);

}
