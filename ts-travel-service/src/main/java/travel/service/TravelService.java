package travel.service;

import travel.domain.*;

/**
 * Created by Chenjie Xu on 2017/5/9.
 */
public interface TravelService {
    String create(Information info);
    Trip retrieve(Information2 info);
    String update(Information info);
    String delete(Information2 info);
    TripResponse query(QueryInfo info);
}
