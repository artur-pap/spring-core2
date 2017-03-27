package ru.apapikyan.learn.spring.core.hometask.domain;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.apapikyan.learn.spring.core.hometask.dao.AuditoriumDAO;
import ru.apapikyan.learn.spring.core.hometask.dao.memory.AuditoriumDAOImpl;
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
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Artur_Papikyan on 15.04.2016.
 */
public class TestAirEvents {

    private ApplicationContext context;

    //private EventV2 event;

    private Auditorium auditorium1;

    private BookingServiceV2 bookingService;
    private EventServiceV2 eventService;
    private DiscountService discountService;
    private AuditoriumService auditoriumService;

    private AuditoriumDAO dao;

    private LocalDateTime airDateTime1;
    private LocalDateTime airDateTime2;

    private User user;

    @Before
    public void initEvent() {
        context = new ClassPathXmlApplicationContext("spring.xml");

        bookingService = context.getBean(BookingServiceImpl.class);
        eventService = context.getBean(EventServiceImpl2.class);
        discountService = context.getBean(DiscountServiceImpl.class);
        auditoriumService = context.getBean(AuditoriumServiceImpl.class);

        auditorium1 = new Auditorium(77L, "Auditorium77", 10L, "1,2,3,4,5,6,7,8,9,10");
        auditoriumService.save(auditorium1);

        EventV2 event = new EventV2(77L, "Deadman", EventRating.HIGH, 20.0);
        eventService.save(event);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        airDateTime1 = LocalDate.parse("2016-04-20", dtf).atTime(0, 0);
        eventService.addAirDateTime(event, airDateTime1, auditorium1);

        airDateTime2 = LocalDate.parse("2016-04-29", dtf).atTime(0, 0);
        eventService.addAirDateTime(event, airDateTime2, auditorium1);

        user = new User();
        user.setId(1L);
        user.setFirstName("FName");
        user.setLastName("FName");
        user.setEmail("user1@test.com");

        Calendar calendar = Calendar.getInstance();
        LocalDateTime bd = LocalDate.parse("1985-04-22", dtf).atTime(0, 0);
        user.setBirthday(bd);
    }

    @Test
    public void testEventsEquality1() {
        EventV2 sameEvent = new EventV2(77L, "Deadman", EventRating.HIGH, 20.0);
        EventV2 eventFromStore = eventService.getByName("Deadman");

        assertEquals(sameEvent, eventFromStore);
        System.out.println("testEventsEquality: PASSED");
    }

    @Test
    public void testAirEventsEqual() {
        EventV2 event = eventService.getByName("Deadman");
        AirEvent airEvent1 = eventService.getAirEventByDateAndTime(event, airDateTime1);
        AirEvent airEvent2 = eventService.getAirEventByDateAndTime(event, airDateTime2);
        System.out.println(airEvent1);
        System.out.println(airEvent2);

        AirEvent sameAirEvent1 = AirEvent.createAirEvent(event, auditorium1, airDateTime1);

        System.out.println(sameAirEvent1);
        assertTrue(airEvent1.equals(sameAirEvent1));

        AirEvent anotherSameAirEvent2 = AirEvent.createAirEvent(event, auditorium1, airDateTime2);
        assertTrue(airEvent2.equals(anotherSameAirEvent2));

        System.out.println("testAirEventsEquality: PASSED");
    }

    @Test
    public void testAirEventsNotEquality() {
        EventV2 event = eventService.getByName("Deadman");

        AirEvent airEvent1 = eventService.getAirEventByDateAndTime(event, airDateTime1);
        AirEvent airEvent2 = eventService.getAirEventByDateAndTime(event, airDateTime2);
        assertNotEquals(airEvent1, airEvent2);

        System.out.println("testAirEventsNotEquality: PASSED");
    }
}
