package register.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface TrainService {
    //CRUD
    boolean create(HttpServletRequest request, HttpServletResponse response);
    String retrieve(HttpServletRequest request, HttpServletResponse response);
    boolean update(HttpServletRequest request, HttpServletResponse response);
    boolean delete(HttpServletRequest request, HttpServletResponse response);
}
