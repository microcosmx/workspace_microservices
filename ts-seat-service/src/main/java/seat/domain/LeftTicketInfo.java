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
    private Set<Integer> soldTickets;

    public LeftTicketInfo(){

    }

    public int getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(int seatNum) {
        this.seatNum = seatNum;
    }

    public Set<Integer> getSoldTickets() {
        return soldTickets;
    }

    public void setSoldTickets(Set<Integer> soldTickets) {
        this.soldTickets = soldTickets;
    }
}
