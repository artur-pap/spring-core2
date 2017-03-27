package ru.apapikyan.learn.spring.core.hometask.domain;

import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Created by Artur_Papikyan on 15.04.2016.
 */
public class AirEvent extends DomainObject {
    private EventV2 event;
    private Auditorium auditorium;
    private LocalDateTime airDateTime;

    @Nonnull
    public static AirEvent createAirEvent(@Nonnull EventV2 event, @Nullable Auditorium auditorium, @Nonnull LocalDateTime airDateTime) {
        AirEvent airEvent = new AirEvent();
        airEvent.setEvent(event);
        airEvent.setAuditorium(auditorium);
        airEvent.setAirDateTime(airDateTime);

        return airEvent;
    }

    @Nonnull
    public EventV2 getEvent() {
        return event;
    }

    public void setEvent(EventV2 event) {
        this.event = event;
    }

    @Nonnull
    public Auditorium getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }

    @Nonnull
    public LocalDateTime getAirDateTime() {
        return airDateTime;
    }

    public void setAirDateTime(LocalDateTime airDateTime) {
        this.airDateTime = airDateTime;
    }

    /*****************************************************************************************************************/
    @Override
    public int hashCode() {
        //TODO TEST EQUALITY AND hashCode METHOD!!!!!
        return Objects.hash(event.getName(), auditorium, airDateTime);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        AirEvent other = (AirEvent) obj;

        if (event == null) {
            if (other.event != null) {
                return false;
            }
        } else if (!event.equals(other.event)) {
            return false;
        }

        if (airDateTime == null) {
            if (other.airDateTime != null) {
                return false;
            }
        } else if (!airDateTime.equals(other.airDateTime)) {
            return false;
        }

        //TODO THERE IS RESTRICTION IN HOME TASK - 1 EVENT - AUDITORIUM
        if (auditorium == null) {
            if (other.auditorium != null) {
                return false;
            }
        } else if (!auditorium.equals(other.auditorium)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "AirEvent[\r\n" +
                event +
                "\r\nTIME: " + airDateTime +
                "\r\nAUDITORIUM: " + auditorium +
                "\r\n]";
    }
}
