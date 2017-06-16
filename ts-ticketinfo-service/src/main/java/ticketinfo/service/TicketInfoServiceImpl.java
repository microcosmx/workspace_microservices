package ticketinfo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ticketinfo.domain.QueryForStationId;
import ticketinfo.domain.QueryForTravel;
import ticketinfo.domain.ResultForTravel;

/**
 * Created by Chenjie Xu on 2017/6/6.
 */
@Service
public class TicketInfoServiceImpl implements TicketInfoService{

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public ResultForTravel queryForTravel(QueryForTravel info){
        ResultForTravel result = restTemplate.postForObject(
                "http://ts-basic-service:15680/basic/queryForTravel", info, ResultForTravel.class);
        return result;
    }

    @Override
    public String queryForStationId(QueryForStationId info){
        String id = restTemplate.postForObject(
                "http://ts-basic-service:15680/basic/queryForStationId", info,String.class);
        return id;
    }
}
