package ru.apapikyan.learn.spring.core.hometask.service.impl;

import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.apapikyan.learn.spring.core.hometask.dao.AirEventDAO;
import ru.apapikyan.learn.spring.core.hometask.dao.EventDAO;
import ru.apapikyan.learn.spring.core.hometask.domain.AirEvent;
import ru.apapikyan.learn.spring.core.hometask.domain.Auditorium;
import ru.apapikyan.learn.spring.core.hometask.domain.EventRating;
import ru.apapikyan.learn.spring.core.hometask.domain.EventV2;
import ru.apapikyan.learn.spring.core.hometask.service.AuditoriumService;
import ru.apapikyan.learn.spring.core.hometask.service.EventServiceV2;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Artur_Papikyan on 15.04.2016.
 */
@Service
public class EventServiceImpl2 implements EventServiceV2 {

    //<bean name="someName" class="my.pkg.classes">
    //  <property name="type" value="my.pkg.types.MyEnumType.TYPE1" />
    //</bean>
    //add Map<EventRating, Double>
    public static final Double C_EVENT_HIGH_FACTOR = 2.0;

    //private HashMap<Long, EventV2> events = new HashMap<>();
    /**
     * All Air Events grouped by Event. Like cache.
     */
    private Map<EventV2, Set<AirEvent>> allAirEvents = new TreeMap<>();

    /**
     * All Air Events. Like cache, for fast access.
     */
    private NavigableMap<Long, AirEvent> airEvents = new TreeMap<Long, AirEvent>();

    @Autowired
    private AuditoriumService auditoriumService;

    @Autowired
    @Qualifier("eventDbDAOImpl")
    private EventDAO dao;

    @Autowired
    private AirEventDAO airEventDAO;

    public EventServiceImpl2() {
        System.out.println("Private constructor: " + this.getClass().getName());
    }

    /*
    * MODIFICATIONS
    */

    @Override
    public boolean assignAuditorium(EventV2 eventV2, LocalDateTime dateTime, Auditorium auditorium) {
        Set<AirEvent> eventAirs = this.allAirEvents.get(eventV2);

        if (eventAirs != null) {
            for (AirEvent ai : eventAirs) {
                if (ai.getAirDateTime().equals(dateTime)) {
                    if (ai.getAuditorium() != null) {
                        System.out.println(String.format("Error assigning auditorium [%s] at [%s] airDateTime of eventV2 [%s].\r\n" +
                                        "Event already has assignment at specified time on another auditorium - [%s]",
                                auditorium.getName(), dateTime, eventV2.getName(), ai.getAuditorium()));
                        return false;
                    } else {
                        ai.setAuditorium(auditoriumService.getByName(auditorium.getName()));
                        eventAirs.add(ai);
                        return true;
                    }
                }
            }
        }

        System.out.println(String.format("Error assigning auditorium [%s] at [%s] airDateTime of eventV2 [%s].\r\n" +
                        "There isn't any such airdate assigned to specified event.\r\n" +
                        "Add air date to event first then asign auditorium.",
                auditorium.getName(), dateTime, eventV2.getName()));
        return false;
    }

    @Override
    public boolean addAirDateTime(@Nonnull EventV2 event, @Nonnull LocalDateTime airDateTime, @Nonnull Auditorium auditorium) {
        Set<AirEvent> eventAirs = new HashSet<AirEvent>();

        if (this.allAirEvents.containsKey(event)) {
            eventAirs = this.allAirEvents.get(event);
        }

        AirEvent airEvent = this.airEventDAO.create(
                AirEvent.createAirEvent(getByName(event.getName()), auditorium, airDateTime)
        );

        eventAirs.add(airEvent);

        this.allAirEvents.put(event, eventAirs);

        return true;
    }

    @Override
    public boolean addAirDateTime(EventV2 event, LocalDateTime airDateTime) {
        Set<AirEvent> eventAirs = new HashSet<AirEvent>();

        if (this.allAirEvents.containsKey(event)) {
            eventAirs = this.allAirEvents.get(event);
        }

        AirEvent airEvent = this.airEventDAO.create(
                AirEvent.createAirEvent(getByName(event.getName()), null, airDateTime)
        );

        eventAirs.add(airEvent);
        this.allAirEvents.put(event, eventAirs);

        return true;
    }

    @Override
    public boolean removeAuditoriumAssignment(EventV2 event, LocalDateTime dateTime) {
        Set<AirEvent> eventAirs = new HashSet<AirEvent>();
        eventAirs = this.allAirEvents.get(event);

        AirEvent airEvent = null;

        for (AirEvent ai : eventAirs) {
            if (ai.getAirDateTime().equals(dateTime)) {
                airEvent = ai;
                break;
            }
        }

        if (airEvent != null) {
            airEvent.setAuditorium(null);
            airEventDAO.update(airEvent);
            eventAirs.add(airEvent);
            this.allAirEvents.put(event, eventAirs);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeAirDateTime(EventV2 event, LocalDateTime dateTime) {
        Set<AirEvent> eventAirs = new HashSet<AirEvent>();
        eventAirs = this.allAirEvents.get(event);

        AirEvent airEvent = null;

        for (AirEvent ai : eventAirs) {
            if (ai.getAirDateTime().equals(dateTime)) {
                airEvent = ai;
                break;
            }
        }

        if (airEvent != null) {
            airEventDAO.delete(airEvent);
            eventAirs.remove(airEvent);
            this.allAirEvents.put(event, eventAirs);
            return true;
        } else {
            return false;
        }
    }
    /*
    * SEARCH AND QUERY
    */

    @Nonnull
    @Override
    public Set<EventV2> getForDateRange(@Nonnull LocalDate from, @Nonnull LocalDate to) {
        Set<EventV2> retVal = new TreeSet<EventV2>();

        LocalDateTime now = LocalDateTime.now();

        Set<AirEvent> searchResults = this.airEventDAO.getAll().stream().filter(airEvent ->
                        airEvent.getAirDateTime().toLocalDate().compareTo(from) >= 0 &&
                                airEvent.getAirDateTime().toLocalDate().compareTo(to) <= 0
        ).collect(Collectors.toSet());

        return searchResults.stream().map(AirEvent::getEvent).collect(Collectors.toSet());
    }

    @Nonnull
    @Override
    public Set<EventV2> getNextEvents(@Nonnull LocalDateTime to) {
        Set<EventV2> retVal = new TreeSet<EventV2>();

        LocalDateTime now = LocalDateTime.now();

        Set<AirEvent> searchResults = this.airEventDAO.getAll().stream().filter(airEvent ->
                        airEvent.getAirDateTime().compareTo(now) >= 0 &&
                                airEvent.getAirDateTime().compareTo(to) <= 0
        ).collect(Collectors.toSet());

        return searchResults.stream().map(AirEvent::getEvent).collect(Collectors.toSet());
    }

    @Override
    public boolean airsOnDateTime(EventV2 event, LocalDateTime dateTime) {
        return this.airEventDAO.getAll().stream().anyMatch(airEvent ->
                        airEvent.getEvent().equals(event) &&
                                airEvent.getAirDateTime().compareTo(dateTime) == 0
        );
    }

    @Override
    public boolean airsOnDate(EventV2 event, LocalDate date) {
        return this.airEventDAO.getAll().stream().anyMatch(airEvent ->
                        airEvent.getEvent().equals(event) &&
                                airEvent.getAirDateTime().toLocalDate().compareTo(date) == 0
        );
    }

    @Override
    public boolean airsOnDates(EventV2 event, LocalDate from, LocalDate to) {
        return this.airEventDAO.getAll().stream().anyMatch(airEvent ->
                        airEvent.getEvent().equals(event) &&
                                airEvent.getAirDateTime().toLocalDate().compareTo(from) >= 0 &&
                                airEvent.getAirDateTime().toLocalDate().compareTo(to) <= 0
        );
    }

    @Override
    public EventV2 getById(@Nonnull Long id) {
        return dao.getById(id);
    }

    @Nullable
    @Override
    public NavigableSet<LocalDateTime> getAllAirDatesForEvent(@Nonnull EventV2 event) {
        NavigableSet<LocalDateTime> airDates = new TreeSet<LocalDateTime>();

        if (!allAirEvents.containsKey(event)) {
            System.out.println(String.format("No airdates defined for event [%s].\r\n" +
                    "Please define some.", event.getName()));
            return airDates;
        }

        for (AirEvent ai : allAirEvents.get(event)) {
            airDates.add(ai.getAirDateTime());
        }

        return airDates;
    }

    @Nonnull
    @Override
    public Collection<EventV2> getAll() {
        return dao.getAll();
    }

    @Nullable
    @Override
    public EventV2 getByName(@Nonnull String name) {
        for (EventV2 event : this.getAll()) {
            if (event.getName().equals(name)) {
                return event;
            }
        }

        return null;
    }

    @Override
    public Collection<Auditorium> getAssignedAuditoriums(EventV2 eventV2) {
        NavigableSet<Auditorium> auditoriums = new TreeSet<>();

        Set<AirEvent> allEventAirs = allAirEvents.get(eventV2);

        for (AirEvent ai : allEventAirs) {
            if (ai.getAuditorium() != null) {
                auditoriums.add(ai.getAuditorium());
            }
        }

        return auditoriums;
    }

    @Nullable
    @Override
    public AirEvent getAirEventByDateAndTime(@NotNull EventV2 eventV2, @NotNull LocalDateTime dateTime) {
        Set<AirEvent> selectedAirEvents = this.allAirEvents.get(eventV2);

        if (selectedAirEvents != null) {
            for (AirEvent ai : selectedAirEvents) {
                if (ai.getAirDateTime().equals(dateTime))
                    return ai;
            }
        }

        return null;
    }

   /*
    *
    * Simple DAO Methods
    *
    */

    @Override
    public EventV2 save(@Nonnull EventV2 object) {
        if (object.getId() == null || dao.getById(object.getId()) == null) {
            return dao.create(object);
        } else {
            return dao.update(object);
        }
    }

    @Override
    public void remove(@Nonnull EventV2 object) {
        //TODO !!!!ADD CHECK FOR EXISTING AIREVENTS AND BOOKED TICKETS
        dao.delete(object);
//        {
//            EventV2 removedEvent = events.remove(object.getId());
//            if (removedEvent == null) {
//                System.out.println(String.format("WARNING! EventV2 [%s] is null or not found in collection.", object.getName()));
//            } else {
//                System.out.println(String.format("EventV2 [%s] with all dates info was removed from collection.", removedEvent.getName()));
//            }
//        }
    }


    /**
     * Returns event rating factor
     *
     * @param rating
     * @return
     */
    @Nonnull
    @Override
    public Double getEventRatingFactor(EventRating rating) {
        Double ratingFactor = 1.0;

        switch (rating) {
            case LOW:
                ratingFactor = 0.8;
                break;
            case HIGH:
                ratingFactor = C_EVENT_HIGH_FACTOR;
                break;
            default:
                ratingFactor = 1.0;
        }

        return ratingFactor;
    }
}