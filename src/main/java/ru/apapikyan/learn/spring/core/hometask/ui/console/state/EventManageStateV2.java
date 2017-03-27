package ru.apapikyan.learn.spring.core.hometask.ui.console.state;

import org.springframework.context.ApplicationContext;
import ru.apapikyan.learn.spring.core.hometask.domain.EventRating;
import ru.apapikyan.learn.spring.core.hometask.domain.EventV2;
import ru.apapikyan.learn.spring.core.hometask.service.AuditoriumService;
import ru.apapikyan.learn.spring.core.hometask.service.EventServiceV2;

import java.util.Locale;

/**
 * State for managing events
 *
 * @author Yuriy_Tkach
 */
public class EventManageStateV2 extends AbstractDomainObjectManageState<EventV2, EventServiceV2> {

    private AuditoriumService auditoriumService;

    public EventManageStateV2(ApplicationContext context) {
        super(context.getBean(EventServiceV2.class));
        this.auditoriumService = context.getBean(AuditoriumService.class);
    }

    @Override
    protected String getObjectName() {
        return EventV2.class.getSimpleName().toLowerCase(Locale.ROOT);
    }

    @Override
    protected void printObject(EventV2 eventV2) {
        System.out.println("[" + eventV2.getId() + "] " + eventV2.getName() + " (Rating: " + eventV2.getRating() + ", Price: "
                + eventV2.getBasePrice() + ")");
    }

    @Override
    protected EventV2 createObject() {
        System.out.println("Adding event");
        String name = readStringInput("Name: ");
        EventRating rating = readEventRating();
        double basePrice = readDoubleInput("Base price: ");

        return new EventV2(name, rating, basePrice);
    }

    private EventRating readEventRating() {
        EventRating rating = null;
        do {
            String str = readStringInput("Rating (LOW, MID, HIGH): ");
            try {
                rating = EventRating.valueOf(str);
            } catch (Exception e) {
                rating = null;
            }
        } while (rating == null);
        return rating;
    }

    @Override
    protected int printSubActions(int maxDefaultActions) {
        int index = maxDefaultActions;
        System.out.println(" " + (++index) + ") Find event by name");
        System.out.println(" " + (++index) + ") Manage event info (air dates, auditoriums)");
        return index - maxDefaultActions;
    }

    @Override
    protected void runSubAction(int action, int maxDefaultActions) {
        int index = action - maxDefaultActions;
        switch (index) {
            case 1:
                findEventByName();
                break;
            case 2:
                manageEventInfo();
                break;
            default:
                System.err.println("Unknown action");
        }
    }

    private void manageEventInfo() {
        int id = readIntInput("Input event id: ");

        EventV2 eventV2 = service.getById(Long.valueOf(id));
        if (eventV2 == null) {
            System.out.println("Not found (searched for " + id + ")");
        } else {
            printDelimiter();

            AbstractState manageState = new EventInfoManageStateV2(eventV2, service, auditoriumService);
            manageState.run();
        }
    }

    private void findEventByName() {
        String name = readStringInput("Input event name: ");
        EventV2 eventV2 = service.getByName(name);
        if (eventV2 == null) {
            System.out.println("Not found (searched for " + name + ")");
        } else {
            printObject(eventV2);
        }
    }

}
