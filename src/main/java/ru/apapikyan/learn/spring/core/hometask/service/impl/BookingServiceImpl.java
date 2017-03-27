package ru.apapikyan.learn.spring.core.hometask.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.apapikyan.learn.spring.core.hometask.dao.BookingDAO;
import ru.apapikyan.learn.spring.core.hometask.domain.*;
import ru.apapikyan.learn.spring.core.hometask.service.BookingServiceV2;
import ru.apapikyan.learn.spring.core.hometask.service.DiscountService;
import ru.apapikyan.learn.spring.core.hometask.service.EventServiceV2;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by Artur_Papikyan on 15.04.2016.
 */
@Service
public class BookingServiceImpl implements BookingServiceV2 {

    @Autowired
    DiscountService discountService;

    @Autowired
    EventServiceV2 eventService;

    @Autowired
    BookingDAO dao;

    private BookingServiceImpl(){}

    public BookingServiceImpl(BookingDAO dao, EventServiceV2 eventService, DiscountService discountService) {
        this.dao = dao;
        this.discountService = discountService;
        this.eventService = eventService;
    }

    //TODO REFACTOR. DUPLICATE PRICE LOGIC in methods  <getTicketsPrice> and <bookTicket>
    @Override
    public double getTicketsPrice(@Nonnull EventV2 event, @Nonnull LocalDateTime dateTime, @Nullable User user, @Nonnull Set<Long> seats) {
        double basePrice = event.getBasePrice();
        AirEvent airEvent = eventService.getAirEventByDateAndTime(event, dateTime);

        if (airEvent == null) {
            System.out.println(String.format("There is no air event [%s] at specified datetime [%s]", event.getName(), dateTime));
            return 0;
        }

        Auditorium auditorium = airEvent.getAuditorium();

        double vipSeatPrice = airEvent.getAuditorium().getVipFactor() * basePrice;

        Double priceSummary = 0.0;

        Set<Ticket> currentPurchase = new HashSet<Ticket>();

        for (Long seat : seats) {
            double seatPrice = basePrice;

            if (auditorium.getVipSeats().contains(seat)) {
                seatPrice = vipSeatPrice;
            }

            seatPrice = seatPrice * eventService.getEventRatingFactor(event.getRating());

            Ticket nextTicket = Ticket.createUnboundTicket(airEvent, seat, seatPrice);

            byte discount = discountService.getDiscount(user, airEvent, currentPurchase, nextTicket);

            seatPrice = (seatPrice * (100 - discount)) / 100;

            nextTicket.setPrice(seatPrice);

            currentPurchase.add(nextTicket);

            priceSummary += seatPrice;
        }

        return priceSummary;
    }

    @Override
    public void bookTickets(@Nullable User user, @Nonnull Set<Ticket> tickets) {
        for (Ticket ticketToCreate : tickets) {
            this.save(bookTicket(user, ticketToCreate, tickets));
        }
    }

    //TODO REFACTOR. DUPLICATE PRICE LOGIC in methods  <getTicketsPrice> and <bookTicket>
    @Override
    public Ticket bookTicket(@Nullable User user, @Nonnull Ticket ticket, Set<Ticket> currentPurchase) {

        AirEvent airEvent = ticket.getAirEvent();

        EventV2 event = airEvent.getEvent();
        Auditorium auditorium = airEvent.getAuditorium();

        double basePrice = event.getBasePrice();
        double vipSeatPrice = airEvent.getAuditorium().getVipFactor() * basePrice;
        double seatPrice = basePrice;

        Long seat = Long.valueOf(ticket.getSeat());

        if (auditorium.getVipSeats().contains(seat)) {
            seatPrice = vipSeatPrice;
        }

        seatPrice = seatPrice * eventService.getEventRatingFactor(event.getRating());


        byte discount = discountService.getDiscount(user, airEvent, currentPurchase, ticket);
        seatPrice = (seatPrice * (100 - discount)) / 100;

        ticket.setPrice(seatPrice);

        //TODO WHAT IS RIGHT WAY TO SET UP CROSS REFERENCE????
        if (user != null) {
            ticket.setUser(user);
            user.getTickets().add(ticket);
        }

        ticket.setUser(user);


        return ticket;
    }

//    @Nonnull
//    @Override
//    public Double calculateTicketPrice(@Nonnull User user, @Nonnull AirEvent airEvent, @Nonnull Long seat) {
//
//        final Double basePrice = airEvent.getEvent().getBasePrice();
//
//        final Collection<Long> vipSeats = airEvent.getAuditorium().getVipSeats();
//
//        Double totalPrice = basePrice;
//
//        if (vipSeats.contains(seat)) {
//            totalPrice += basePrice * 2;
//        }
//
//        final int discount = 0;//discountService.getDiscount(user, airEvent);
//
//        totalPrice = (totalPrice * (100 - discount)) / 100;
//
//        return totalPrice;
//    }

    private void save(Set<Ticket> tickets) {
        for (Ticket ticket : tickets) this.save(ticket);
    }


    @Nonnull
    private List<Ticket> findTicketsByAirEvent(AirEvent airEvent) {
        ArrayList<Ticket> searchResult = new ArrayList<Ticket>();

        for (Ticket ticket : dao.getAll()) {
            if (ticket.getAirEvent().equals(airEvent)) {
                searchResult.add(ticket);
            }
        }

        return searchResult;
    }

    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull EventV2 event, @Nonnull LocalDateTime airDateTime) {
        AirEvent selectedAirEvent = eventService.getAirEventByDateAndTime(event, airDateTime);

        return new HashSet(findTicketsByAirEvent(selectedAirEvent));
    }


    @Nonnull
    //@Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull AirEvent airEvent) {
        return new HashSet<Ticket>(findTicketsByAirEvent(airEvent));
    }

    @Override
    public Ticket save(@Nonnull Ticket object) {
        if (object.getId() == null || dao.getById(object.getId()) == null) {
            return dao.create(object);
        } else {
            return dao.update(object);
        }
    }

    @Override
    public void remove(@Nonnull Ticket object) {
        if (object.getId() != null) {
            dao.delete(object);
        }
    }

    @Override
    public Ticket getById(@Nonnull Long id) {
        return dao.getById(id);
    }

    @Nonnull
    @Override
    public Collection<Ticket> getAll() {
        return dao.getAll();
    }
}