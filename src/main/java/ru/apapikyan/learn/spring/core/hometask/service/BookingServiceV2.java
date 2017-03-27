package ru.apapikyan.learn.spring.core.hometask.service;

import ru.apapikyan.learn.spring.core.hometask.domain.AirEvent;
import ru.apapikyan.learn.spring.core.hometask.domain.EventV2;
import ru.apapikyan.learn.spring.core.hometask.domain.Ticket;
import ru.apapikyan.learn.spring.core.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author Artur_Papikyan
 */
public interface BookingServiceV2 extends AbstractDomainObjectService<Ticket> {

    /**
     * Getting price when buying all supplied seats for particular event
     *
     * @param event    EventV2 to get base ticket price, vip seats and other
     *                 information
     * @param dateTime Date and time of event air
     * @param user     User that buys ticket could be needed to calculate discount.
     *                 Can be <code>null</code>
     * @param seats    Set of seat numbers that user wants to buy
     * @return total price
     */
    public double getTicketsPrice(@Nonnull EventV2 event, @Nonnull LocalDateTime dateTime, @Nullable User user, @Nonnull Set<Long> seats);

    /**
     * Books tickets in internal system. If user is not
     * <code>null</code> in a ticket then booked tickets are saved with it
     *
     * @param tickets Set of tickets
     */
    public void bookTickets(@Nullable User user, @Nonnull Set<Ticket> tickets);

    /**
     * Books tickets in internal system. If user is not
     * <code>null</code> in a ticket then booked tickets are saved with it
     *
     * @param ticket ticket tu purchase
     */
    public Ticket bookTicket(@Nullable User user, @Nonnull Ticket ticket, Set<Ticket> currentPurchase);

    /**
     * Getting all purchased tickets for event on specific air date and time
     *
     * @param event    EventV2 to get tickets for
     * @param dateTime Date and time of airing of event
     * @return set of all purchased tickets
     */
    public
    @Nonnull
    Set<Ticket> getPurchasedTicketsForEvent(@Nonnull EventV2 event, @Nonnull LocalDateTime dateTime);


//    public
//    @Nonnull
//    Double calculateTicketPrice(@Nonnull final User user, @Nonnull final AirEvent airEvent, @Nonnull Long seat);
}
