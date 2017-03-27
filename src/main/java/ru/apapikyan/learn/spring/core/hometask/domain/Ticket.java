package ru.apapikyan.learn.spring.core.hometask.domain;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Yuriy_Tkach
 */
public class Ticket extends DomainObject {

    private User user;

    private AirEvent airEvent;

    private long seat;

    private Double price;

    public static Ticket createUnboundTicket(@Nonnull AirEvent airEvent, @Nonnull Long seat) {
        return createUnboundTicket(airEvent, seat, 0.0);
    }

    public static Ticket createUnboundTicket(@Nonnull AirEvent airEvent, @Nonnull Long seat, Double ticketPrice) {
        return new Ticket(null, airEvent, seat, ticketPrice);
    }

    public static Ticket createTicket(User user, @Nonnull AirEvent airEvent, @Nonnull Long seat) {
        return new Ticket(user, airEvent, seat, Double.valueOf("0"));
    }

    public Ticket(User user, @Nonnull AirEvent airEvent, @Nonnull long seat) {
        this(user, airEvent, seat, Double.valueOf("0"));
    }

    public Ticket(User user, @Nonnull AirEvent airEvent, @Nonnull long seat, Double ticketPrice) {
        this.user = user;
        this.airEvent = airEvent;
        this.seat = seat;
        this.price = ticketPrice;


        if (user != null) {
            user.getTickets().add(this);
        }
    }

    public AirEvent getAirEvent() {
        return this.airEvent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getSeat() {
        return seat;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(airEvent, user, seat);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        Ticket other = (Ticket) obj;

        if (airEvent == null) {
            if (other.airEvent != null) {
                return false;
            }
        } else if (!airEvent.equals(other.airEvent)) {
            return false;
        }

        if (seat != other.seat) {
            return false;
        }

        return true;
    }
}
