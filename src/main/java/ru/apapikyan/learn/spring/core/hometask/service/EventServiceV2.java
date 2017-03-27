package ru.apapikyan.learn.spring.core.hometask.service;

import com.sun.istack.internal.NotNull;
import ru.apapikyan.learn.spring.core.hometask.domain.AirEvent;
import ru.apapikyan.learn.spring.core.hometask.domain.Auditorium;
import ru.apapikyan.learn.spring.core.hometask.domain.EventRating;
import ru.apapikyan.learn.spring.core.hometask.domain.EventV2;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.NavigableSet;
import java.util.Set;

/**
 * @author Yuriy_Tkach
 *
 */
public interface EventServiceV2 extends AbstractDomainObjectService<EventV2> {

    /**
     * Finding event by name
     *
     * @param name Name of the event
     * @return found event or <code>null</code>
     */
    public
    @Nullable
    EventV2 getByName(@Nonnull String name);

    /*
     * Finding all events that air on specified date range
     *
     * @param from Start date
     *
     * @param to End date inclusive
     *
     * @return Set of events
     */
    public
    @Nonnull
    Set<EventV2> getForDateRange(@Nonnull LocalDate from,
                               @Nonnull LocalDate to);

    /*
     * Return events from 'now' till the the specified date time
     *
     * @param to End date time inclusive
     * s
     * @return Set of events
     */
    public
    @Nonnull
    Set<EventV2> getNextEvents(@Nonnull LocalDateTime to);

    /**
     * Checks if event airs on particular date and time
     *
     * @param dateTime Date and time to check
     * @return <code>true</code> event airs on that date and time
     */
    boolean airsOnDateTime(EventV2 event,  LocalDateTime dateTime);

    /**
     * Checks if event airs on particular date
     *
     * @param date Date to ckeck
     * @return <code>true</code> event airs on that date
     */
    boolean airsOnDate(EventV2 event, LocalDate date);

    /**
     * Checking if event airs on dates between <code>from</code> and
     * <code>to</code> inclusive
     *
     * @param from Start date to check
     * @param to   End date to check
     * @return <code>true</code> event airs on dates
     */
    boolean airsOnDates(EventV2 event, LocalDate from, LocalDate to);


    /**
     * Checks if event is aired on particular <code>dateTime</code> and assigns
     * auditorium to it.
     *
     * @param event
     * @param dateTime   Date and time of aired event for which to assign
     * @param auditorium Auditorium that should be assigned
     */
    boolean assignAuditorium(EventV2 event, LocalDateTime dateTime, Auditorium auditorium);

    /**
     * Adding date and time of event air and assigning auditorium to that
     *
     * @param event
     * @param dateTime   Date and time to add
     * @param auditorium Auditorium to add if success in date time add
     * @return <code>true</code> if successful, <code>false</code> if already
     * there
     */
    boolean addAirDateTime(EventV2 event, LocalDateTime dateTime, Auditorium auditorium);

    /**
     * Add date and time of event air
     *
     * @param event
     * @param dateTime Date and time to add
     * @return <code>true</code> if successful, <code>false</code> if already
     * there
     */
    boolean addAirDateTime(EventV2 event, LocalDateTime dateTime);

    /**
     * Removes auditorium assignment from event
     *
     * @param event
     * @param dateTime Date and time to remove auditorium for
     * @return <code>true</code> if successful, <code>false</code> if not
     * removed
     */
    boolean removeAuditoriumAssignment(EventV2 event, LocalDateTime dateTime);

    /**
     * Removes the date and time of event air. If auditorium was assigned to
     * that date and time - the assignment is also removed
     *
     * @param event
     * @param dateTime Date and time to remove
     * @return <code>true</code> if successful, <code>false</code> if not there
     */
    boolean removeAirDateTime(EventV2 event, LocalDateTime dateTime);


    /**
     *
     * @param eventV2
     * @return
     */
    Collection<Auditorium> getAssignedAuditoriums(EventV2 eventV2);

    /**
     * Finding event by name
     *
     * @param event event which air date time need to be found
     * @param dateTime air datetime of specified event
     * @return found air event or <code>null</code>
     */
    public
    @Nullable
    AirEvent getAirEventByDateAndTime(@NotNull EventV2 event, @NotNull LocalDateTime dateTime);

    /**
     *
     * @param rating
     * @return
     */
    public
    @Nonnull
    Double getEventRatingFactor(EventRating rating);

    @Nonnull
    public
    NavigableSet<LocalDateTime> getAllAirDatesForEvent(@Nonnull EventV2 event);
}