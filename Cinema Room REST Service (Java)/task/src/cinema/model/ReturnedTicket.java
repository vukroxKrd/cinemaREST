package cinema.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReturnedTicket {
    @JsonProperty("ticket")
    private final Seat returned_ticket;
    public ReturnedTicket(int row, int column, int price) {
        this.returned_ticket = new Seat();
        this.returned_ticket.setRow(row);
        this.returned_ticket.setColumn(column);
        this.returned_ticket.setPrice(price);
    }

    public Seat getReturned_ticket() {
        return returned_ticket;
    }
}
