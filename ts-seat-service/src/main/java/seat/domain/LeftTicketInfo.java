package seat.domain;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class LeftTicketInfo {
    @Valid
    @NotNull
    private int seatNum;

    @Valid
    @NotNull
    private Set<Ticket> soldTickets;

    public LeftTicketInfo(){

    }

    public int getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(int seatNum) {
        this.seatNum = seatNum;
    }

    public Set<Ticket> getSoldTickets() {
        return soldTickets;
    }

    public void setSoldTickets(Set<Ticket> soldTickets) {
        this.soldTickets = soldTickets;
    }

}
