package ru.apapikyan.learn.spring.core.hometask.domain;

import java.util.*;

/**
 * @author Artur_Papikyan
 */
public class EventV2 extends DomainObject implements Comparable<EventV2> {

    public static final String C_NAME = "name";
    public static final String C_RATING = "rating";
    public static final String C_BASE_PRICE = "baseprice";

    private String name;
    private double basePrice;
    private EventRating rating;

    private EventV2(){}

    public EventV2(String name, EventRating rating, Double basePrice) {
        this.name = name;
        this.name = name;
        this.rating = rating;
        this.basePrice = basePrice;
    }

    public EventV2(Long id, String name, EventRating rating, Double basePrice) {
        setId(id);
        this.name = name;
        this.rating = rating;
        this.basePrice = basePrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public EventRating getRating() {
        return rating;
    }

    public void setRating(EventRating rating) {
        this.rating = rating;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
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
        EventV2 other = (EventV2) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", price=" + basePrice +
                ", rating=" + rating +
                '}';
    }

    @Override
    public int compareTo(EventV2 other) {
        if (other == null) {
            return 1;
        }
        return this.name.compareTo(other.getName());
    }
}