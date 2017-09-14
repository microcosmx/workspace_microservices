package fdse.microservice.service;

import fdse.microservice.domain.Contacts;
import fdse.microservice.domain.QueryForStationId;
import fdse.microservice.domain.QueryForTravel;
import fdse.microservice.domain.ResultForTravel;

/**
 * Created by Chenjie Xu on 2017/6/6.
 */
public interface BasicService {
    ResultForTravel queryForTravel(QueryForTravel info);
    String queryForStationId(QueryForStationId info);
}
