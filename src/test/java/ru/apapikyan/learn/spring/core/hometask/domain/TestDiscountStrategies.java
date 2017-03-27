package ru.apapikyan.learn.spring.core.hometask.domain;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.apapikyan.learn.spring.core.hometask.service.AuditoriumService;
import ru.apapikyan.learn.spring.core.hometask.service.BookingServiceV2;
import ru.apapikyan.learn.spring.core.hometask.service.DiscountService;
import ru.apapikyan.learn.spring.core.hometask.service.EventServiceV2;
import ru.apapikyan.learn.spring.core.hometask.service.impl.AuditoriumServiceImpl;
import ru.apapikyan.learn.spring.core.hometask.service.impl.BookingServiceImpl;
import ru.apapikyan.learn.spring.core.hometask.service.impl.DiscountServiceImpl;
import ru.apapikyan.learn.spring.core.hometask.service.impl.EventServiceImpl2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

/**
 * Created by Artur_Papikyan on 15.04.2016.
 */
public class TestDiscountStrategies {

    private ApplicationContext context;

    private EventV2 event;

    private User user;
    private Auditorium auditorium;

    private BookingServiceV2 bookingService;
    private EventServiceImpl2 eventService;
    //private DiscountService discountService;
    private AuditoriumService auditoriumService;

    private Ticket mockTicket;

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    static final Double basePrice = 10.0;

    @Before
    public void init() {
        context = new ClassPathXmlApplicationContext("spring.xml");

        bookingService = context.getBean(BookingServiceImpl.class);
        eventService = context.getBean(EventServiceImpl2.class);
        //discountService = context.getBean(DiscountServiceImpl.class);
        auditoriumService = context.getBean(AuditoriumServiceImpl.class);

        auditorium = new Auditorium(77L, "Auditorium1", 20L, "1,2,3,4,5,6,7,8,9,10");
        auditoriumService.save(auditorium);

        event = new EventV2(77L, "Deadman", EventRating.HIGH, basePrice);
        eventService.save(event);

        user = new User();
        user.setId(10L);
        user.setFirstName("FName");
        user.setLastName("FName");
        user.setEmail("user1@test.com");

        Calendar calendar = Calendar.getInstance();
        LocalDateTime bd = LocalDate.parse("1985-04-22", dtf).atTime(0, 0);
        user.setBirthday(bd);
    }

    @Test
    public void birthdayDiscountTest() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime airDateTime = LocalDate.parse("2016-04-20", dtf).atTime(0, 0);
        eventService.addAirDateTime(event, airDateTime, auditorium);

        AirEvent airEvent = eventService.getAirEventByDateAndTime(event, airDateTime);

        Set<Long> seats = new TreeSet<Long>(Arrays.asList(1L, 2L));
        Double price = bookingService.getTicketsPrice(event, airDateTime, user, seats);

        Integer numOfSeats = seats.size();

        Double compareTo = Double.valueOf(
                Double.valueOf(numOfSeats.toString()) *
                event.getBasePrice() * airEvent.getAuditorium().getVipFactor() * eventService.getEventRatingFactor(event.getRating()) * 0.90
        );
        assertTrue(price.equals(compareTo));
    }

    @Test
    public void every10thDiscountTest() {
        //TODO BOOK TICKETS!!!!!! OR DISCOUNT WILL NOT WORK!!!! [UPD1] FIXED BY NEW DISCOUNT SERVICE AND STRATEGY LOGIC
        Set<Long> seats = new TreeSet<Long>(Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L));

        LocalDateTime airDateTime = LocalDate.parse("2016-04-29", dtf).atTime(0, 0);
        eventService.addAirDateTime(event, airDateTime, auditorium);

        AirEvent airEvent = eventService.getAirEventByDateAndTime(event, airDateTime);

        Set<Ticket> ticketsToBook = seats.stream().map(seat -> Ticket.createUnboundTicket(airEvent, seat)).collect(Collectors.toSet());

        Double price = bookingService.getTicketsPrice(event, airDateTime, user, seats);

        assertTrue(price.equals(Double.valueOf(490.0)));
    }

    @Test
    public void bookTickets() {
        //TODO BOOK TICKETS!!!!!! OR DISCOUNT WILL NOT WORK!!!! [UPD1] FIXED BY NEW DISCOUNT SERVICE AND STRATEGY LOGIC
        Set<Long> seats = new TreeSet<Long>(Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L));

        LocalDateTime airDateTime = LocalDate.parse("2016-04-29", dtf).atTime(0, 0);
        eventService.addAirDateTime(event, airDateTime, auditorium);

        AirEvent airEvent = eventService.getAirEventByDateAndTime(event, airDateTime);

        Set<Ticket> ticketsToBook = seats.stream().map(seat -> Ticket.createUnboundTicket(airEvent, seat)).collect(Collectors.toSet());

        //bookingService.bookTickets(ticketsToBook);
        //bookingService.bookTickets(user, ticketsToBook);

        //assertTrue(price.equals(Double.valueOf(490.0)));
    }
}