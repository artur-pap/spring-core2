package ru.apapikyan.learn.spring.core.hometask.ui.console;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.apapikyan.learn.spring.core.hometask.configuration.AspectsConfig;
import ru.apapikyan.learn.spring.core.hometask.configuration.AuditoriumConfig;
import ru.apapikyan.learn.spring.core.hometask.configuration.SpringConfiguration;
import ru.apapikyan.learn.spring.core.hometask.domain.*;
import ru.apapikyan.learn.spring.core.hometask.service.*;
import ru.apapikyan.learn.spring.core.hometask.ui.console.state.MainState;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

/**
 * Simple console UI application for the hometask code. UI provides different
 * action to input and output data. In order for the application to work, the
 * Spring context initialization code should be placed into
 * {@link #initContext()} method.
 *
 * @author Yuriy_Tkach
 */
public class SpringHometaskConsoleUI {

    private ApplicationContext context;

    public static void main(String[] args) {
        SpringHometaskConsoleUI ui = new SpringHometaskConsoleUI();
        ui.initContext();
        ui.run();
    }

    private void initContext() {
        //context = new ClassPathXmlApplicationContext("spring.xml");
        //System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "profile name");

        context = new AnnotationConfigApplicationContext(AspectsConfig.class, SpringConfiguration.class, AuditoriumConfig.class);
    }

    private void run() {
        System.out.println("Welcome to movie theater console service");

        fillInitialData();

        MainState state = new MainState(context);

        state.run();

        System.out.println("Exiting.. Thank you.");
    }

    private void fillInitialData() {
        UserService userService = context.getBean(UserService.class);
        AuditoriumService auditoriumService = context.getBean(AuditoriumService.class);
        EventServiceV2 eventService = context.getBean(EventServiceV2.class);
        BookingServiceV2 bookingService = context.getBean(BookingServiceV2.class);

        System.out.println("Breakpoint 1");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        User user = new User();
        user.setFirstName("Name1");
        user.setLastName("LName1");
        user.setEmail("user1@epam.com");
        user.setBirthday(LocalDateTime.parse("1985-03-24 12:00", dtf));//.atTime(12, 0));

        userService.save(user);

        user = new User();
        user.setFirstName("Name2");
        user.setLastName("LName2");
        user.setEmail("user2@epam.com");
        user.setBirthday(LocalDateTime.parse("1989-04-23 12:00", dtf));
        userService.save(user);


        Auditorium auditorium = auditoriumService.getAll().iterator().next();
        if (auditorium == null) {
            throw new IllegalStateException("Failed to fill initial data - no auditoriums returned from AuditoriumService");
        }
        if (auditorium.getNumberOfSeats() <= 0) {
            throw new IllegalStateException("Failed to fill initial data - no seats in the auditorium " + auditorium.getName());
        }

        EventV2 event1 = eventService.getById(1L);
        EventV2 event2 = eventService.getById(2L);

        //TODO ADD FACTORY FOR DOMAIN OBJECT
        eventService.save(new EventV2("Grand concert", EventRating.HIGH, 10.0));

        LocalDateTime airDate = LocalDateTime.parse("2016-03-22 19:30", dtf);
        eventService.addAirDateTime(event1, airDate, auditorium);
        AirEvent airEvent1 = eventService.getAirEventByDateAndTime(event1, airDate);


        airDate = LocalDateTime.parse("2016-03-22 22:30", dtf);
        eventService.addAirDateTime(event2, airDate, auditorium);
        AirEvent airEvent2 = eventService.getAirEventByDateAndTime(event2, airDate);

        Ticket ticket1 = new Ticket(user, airEvent1, 7);
        bookingService.bookTickets(null, Collections.singleton(ticket1));

        if (auditorium.getNumberOfSeats() > 1) {
            User userNotRegistered = new User();
            userNotRegistered.setEmail("somebody@a.b");
            userNotRegistered.setFirstName("A");
            userNotRegistered.setLastName("Somebody");
            Ticket ticket2 = new Ticket(userNotRegistered, airEvent2, 2);
            bookingService.bookTickets(null, Collections.singleton(ticket2));
        }

        System.out.println("Breakpoint 2");

    }
}
