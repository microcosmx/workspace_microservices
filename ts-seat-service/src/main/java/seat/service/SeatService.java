package seat.service;

import seat.domain.SeatRequest;

public interface SeatService {

    int distributeSeat(SeatRequest seatRequest);
}
