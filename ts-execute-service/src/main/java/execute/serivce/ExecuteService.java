package execute.serivce;

import execute.domain.TicketExecuteInfo;
import execute.domain.TicketExecuteResult;

public interface ExecuteService {

    TicketExecuteResult ticketExecute(TicketExecuteInfo info);

}
