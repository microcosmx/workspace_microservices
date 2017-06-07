package ticketinfo.service;

import ticketinfo.domain.QueryForTravel;
import ticketinfo.domain.ResultForTravel;

/**
 * Created by Chenjie Xu on 2017/6/6.
 */
public interface TicketInfoService {
    ResultForTravel queryForTravel(QueryForTravel info);
}
