package ru.apapikyan.learn.spring.core.hometask.ui.console.state;

import org.springframework.context.ApplicationContext;
import ru.apapikyan.learn.spring.core.hometask.domain.*;
import ru.apapikyan.learn.spring.core.hometask.service.AbstractDomainObjectService;
import ru.apapikyan.learn.spring.core.hometask.service.BookingServiceV2;
import ru.apapikyan.learn.spring.core.hometask.service.EventServiceV2;
import ru.apapikyan.learn.spring.core.hometask.service.UserService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BookingState extends AbstractState {

    private final BookingServiceV2 bookingService;
    private final UserService userService;
    private final EventServiceV2 eventService;

    public BookingState(ApplicationContext context) {
        this.bookingService = context.getBean(BookingServiceV2.class);
        this.userService = context.getBean(UserService.class);
        this.eventService = context.getBean(EventServiceV2.class);
    }

    @Override
    protected void printDefaultInformation() {
        System.out.println("Lets book tickets!");
    }

    @Override
    protected int printMainActions() {
        System.out.println(" 1) Get tickets price");
        System.out.println(" 2) Book tickets");
        System.out.println(" 3) Get booked tickets");
        return 4;
    }

    @Override
    protected void runAction(int action) {
        switch (action) {
            case 1:
                getTicketsPrice();
                break;
            case 2:
                bookTickets();
                break;
            case 3:
                getBookedTickets();
                break;
            default:
                System.err.println("Unknown action");
        }
    }

    private void getBookedTickets() {
        System.out.println("> Select event: ");
        EventV2 event = selectDomainObject(eventService, e -> e.getName());
        if (event == null) {
            System.err.println("No event found");
            return;
        }

        System.out.println("> Select air dates: ");
        LocalDateTime airDate = selectAirDate(eventService.getAllAirDatesForEvent(event));

        printDelimiter();
        Set<Ticket> bookedTickets = bookingService.getPurchasedTicketsForEvent(event, airDate);

        for(Ticket t : bookedTickets) {
            User u = t.getUser();
            String s = u == null ? "ANONYMOUS" : t.getUser().getEmail();
            System.out.println("Seat " + t.getSeat() + "\t for " + s);
        }
        //bookedTickets.forEach(t -> System.out.println("Seat " + t.getSeat() + "\t for " + t.getUser() == null ? "ANONYMOUS" : t.getUser().getEmail()));
    }

    private void bookTickets() {
        System.out.println("> Select event: ");
        final EventV2 event = selectDomainObject(eventService, e -> e.getName());

        if (event == null) {
            System.err.println("No event found");
            return;
        }

        System.out.println("> Select air dates: ");

        final LocalDateTime airDate = selectAirDate(eventService.getAllAirDatesForEvent(event));

        if (airDate == null) {
            System.err.println("No air dates found.");
            return;
        }

        System.out.println("> Select seats: ");

        final Set<Long> seats = selectSeats(event, airDate);
        if (seats == null || seats.size() == 0) {
            System.err.println("No seats found.");
            return;
        }

        System.out.println("> Select user: ");

        final User userForBooking;
        User user = selectDomainObject(userService, u -> u.getFirstName() + " " + u.getLastName());
        if (user == null) {
            System.out.println("No user found. Input user info for booking: ");
            String email = readStringInput("Email: ");
            String firstName = readStringInput("First name: ");
            String lastName = readStringInput("Last name: ");
            userForBooking = new User();
            userForBooking.setEmail(email);
            userForBooking.setFirstName(firstName);
            userForBooking.setLastName(lastName);
        } else {
            userForBooking = user;
        }

        AirEvent airEvent = eventService.getAirEventByDateAndTime(event, airDate);

        Set<Ticket> ticketsToBook = seats.stream().map(seat -> Ticket.createUnboundTicket(airEvent, seat)).collect(Collectors.toSet());

        bookingService.bookTickets(userForBooking, ticketsToBook);//getTicketsPrice(event, airDate, user, seats);

        //double price = bookingService.getTicketsPrice(event, airDate, user, seats);

        //System.out.println("Tickets booked! Total price: " + price);
    }

    private void getTicketsPrice() {
        System.out.println("> Select event: ");
        EventV2 event = selectDomainObject(eventService, e -> e.getName());
        if (event == null) {
            System.err.println("No event found");
            return;
        }

        System.out.println("> Select air dates: ");
        LocalDateTime airDate = selectAirDate(eventService.getAllAirDatesForEvent(event));

        if (airDate == null) {
            System.err.println(String.format("No air dates found. Please define some air date for event [%s].", event.getName()));
            return;
        }

        System.out.println("> Select seats: ");
        Set<Long> seats = selectSeats(event, airDate);

        System.out.println("> Select user: ");
        User user = selectDomainObject(userService, u -> u.getFirstName() + " " + u.getLastName());
        if (user == null) {
            System.out.println("No user found");
        }

        double price = bookingService.getTicketsPrice(event, airDate, user, seats);
        printDelimiter();
        System.out.println("Price for tickets: " + price);
    }

    private Set<Long> selectSeats(EventV2 event, LocalDateTime airDate) {

        AirEvent airEvent = eventService.getAirEventByDateAndTime(event, airDate);

        if (airEvent == null) {
            return null;
        }

        Auditorium aud = airEvent.getAuditorium();

        Set<Ticket> tickets = bookingService.getPurchasedTicketsForEvent(event, airDate);
        List<Long> bookedSeats = tickets.stream().map(t -> t.getSeat()).collect(Collectors.toList());
        List<Long> freeSeats = aud.getAllSeats().stream().filter(seat -> !bookedSeats.contains(seat))
                .collect(Collectors.toList());

        System.out.println("Free seats: ");
        System.out.println(freeSeats);

        return inputSeats();
    }

    private Set<Long> inputSeats() {
        Set<Long> set = readInput("Input seats (comma separated): ", s ->
                Arrays.stream(s.split(","))
                        .map(String::trim)
                        .mapToLong(Long::parseLong)
                        .boxed().collect(Collectors.toSet()));
        return set;
    }

    private LocalDateTime selectAirDate(NavigableSet<LocalDateTime> airDates) {
        List<LocalDateTime> list = airDates.stream().collect(Collectors.toList());

        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                System.out.println("[" + (i + 1) + "] " + formatDateTime(list.get(i)));
            }
            int dateIndex = readIntInput("Input air date index: ", list.size()) - 1;

            return list.get(dateIndex);
        } else {
            return null;
        }
    }

    private <T extends DomainObject> T selectDomainObject(AbstractDomainObjectService<T> service, Function<T, String> displayFunction) {
        if (!service.getAll().isEmpty()) {
            service.getAll().forEach(obj -> System.out.println("[" + obj.getId() + "] " + displayFunction.apply(obj)));
            long id = readIntInput("Input id (-1 for nothing): ");
            return service.getById(id);
        } else {
            return null;
        }
    }

}
