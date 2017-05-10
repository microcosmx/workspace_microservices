package train.service;

import train.domain.Information;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface TrainService {
    //CRUD
    boolean create(Information info);
    String retrieve(Information info);
    boolean update(Information info);
    boolean delete(Information info);
}
