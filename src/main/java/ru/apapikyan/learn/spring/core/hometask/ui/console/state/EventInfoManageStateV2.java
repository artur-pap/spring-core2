package ru.apapikyan.learn.spring.core.hometask.ui.console.state;

import ru.apapikyan.learn.spring.core.hometask.domain.Auditorium;
import ru.apapikyan.learn.spring.core.hometask.domain.EventV2;
import ru.apapikyan.learn.spring.core.hometask.service.AuditoriumService;
import ru.apapikyan.learn.spring.core.hometask.service.EventServiceV2;
import ru.apapikyan.learn.spring.core.hometask.service.impl.EventServiceImpl2;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class EventInfoManageStateV2 extends AbstractState {

    private EventV2 event;
    private EventServiceV2 eventService;
    private AuditoriumService auditoriumService;

    public EventInfoManageStateV2(EventV2 event, EventServiceV2 eventService, AuditoriumService auditoriumService) {
        this.event = event;
        //TODO ADD METHODS TO EventServiceV2 to user abstract class w/ interface
        this.eventService = eventService;
        this.auditoriumService = auditoriumService;
    }

    @Override
    protected void printDefaultInformation() {
        System.out.println("Information about Event: " + event.getName());
    }

    @Override
    protected int printMainActions() {
        System.out.println(" 1) View air dates");
        System.out.println(" 2) Add air date");
        System.out.println(" 3) View assigned auditoriums");
        System.out.println(" 4) Assign auditorium");
        return 4;
    }

    @Override
    protected void runAction(int action) {
        switch (action) {
            case 1:
                viewAirDates();
                break;
            case 2:
                addAirDate();
                break;
            case 3:
                viewAssignedAuditoriums();
                break;
            case 4:
                assignAuditorium();
                break;
            default:
                System.err.println("Unknown action");
        }
    }

    private void assignAuditorium() {
        System.out.println("Select auditorium:");
        List<Auditorium> list = auditoriumService.getAll().stream().collect(Collectors.toList());
        for (int i = 0; i < list.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + list.get(i).getName());
        }
        int auditoriumIndex = readIntInput("Input index: ", list.size()) - 1;

        Auditorium aud = list.get(auditoriumIndex);
        System.out.println("Assigning auditorium: " + aud.getName());

        NavigableSet<LocalDateTime> dateSet = eventService.getAllAirDatesForEvent(event);
        ArrayList<LocalDateTime> datesList = new ArrayList<LocalDateTime>();

        datesList.addAll(dateSet);

        if (datesList.size() == 0) {
            System.err.println("Failed to assign for any air dateTime.\r\nReason: there is no any air dates? provide at least one airdate");
            return;
        }

        for(int i = 0; i < datesList.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + formatDateTime(datesList.get(i)));
        }

        int dateTimeIndex = readIntInput("Input air dateTime index: ", datesList.size()) - 1;
        LocalDateTime dt = datesList.get(dateTimeIndex);

        if (eventService.assignAuditorium(event, dt, aud)) {
            System.out.println("Assigned auditorium for air dateTime: " + formatDateTime(dt));
        } else {
            System.err.println("Failed to assign for air dateTime: " + formatDateTime(dt));
        }
    }

    private void viewAssignedAuditoriums() {
        System.out.println("Event airs in: ");
        eventService.getAssignedAuditoriums(event).forEach(a -> System.out.println(a.getName()));
    }

    private void addAirDate() {
        LocalDateTime airDate = readDateTimeInput("Air date (" + DATE_TIME_INPUT_PATTERN + "): ");
        eventService.addAirDateTime(event, airDate);
        //eventService.save(airEvent);
    }

    private void viewAirDates() {
        System.out.println("Event airs on: ");
        eventService.getAllAirDatesForEvent(event).forEach(dt -> System.out.println(formatDateTime(dt)));
    }

}
