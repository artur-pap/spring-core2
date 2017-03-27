package ru.apapikyan.learn.spring.core.hometask.domain;

import com.sun.istack.internal.NotNull;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * @author Yuriy_Tkach
 */
public class Auditorium extends DomainObject implements Comparable<Auditorium> {

    public static final String C_NAME = "name";
    public static final String C_NUMBER_OF_SEATS = "numberOfSeats";
    public static final String C_VIP_SEATS = "vipSeats";

    private String name;

    private long numberOfSeats;

    private Set<Long> vipSeats = Collections.emptySet();

    private static Double defaultVipFactor = 2.0;

    private Double vipFactor;

    /**
     *
     */
    public Auditorium() {
        init();
    }

    /**
     *
     * @param id
     * @param name
     * @param numberOfSeats
     * @param vipSeatsString
     */
    public Auditorium(Long id, @NotNull String name, Long numberOfSeats, String vipSeatsString) {
        init();

        this.setId(id);
        this.name = name;
        this.numberOfSeats = numberOfSeats;

        String[] arrVS = vipSeatsString.split(",");
        if (arrVS.length == 0) {
            System.out.println("VIP seats undefined for auditorium " + name);
        } else {
            Collection<Long> vSeats = Arrays.asList(arrVS).stream().map(s -> Long.parseLong(s)).collect(Collectors.toList());
            this.setVipSeats(new TreeSet<>(vSeats));
        }
    }

    public Auditorium(String name, Long numberOfSeats, Set<Long> vipSeats) {
        init();

        this.name = name;
        this.numberOfSeats = numberOfSeats;
        this.vipSeats = vipSeats;
    }

    public Auditorium(Long id, String name, Long numberOfSeats, Set<Long> vipSeats) {
        init();

        this.setId(id);

        this.name = name;
        this.numberOfSeats = numberOfSeats;
        this.vipSeats = vipSeats;
    }

   private Auditorium(String vipSeatsString) {
        init();

        String[] arrVS = vipSeatsString.split(",");
        if (arrVS.length == 0) {
            System.out.println("VIP seats undefined for auditorium " + name);
        } else {
            Collection<Long> vSeats = Arrays.asList(arrVS).stream().map(s -> Long.parseLong(s)).collect(Collectors.toList());
            this.setVipSeats(new TreeSet<>(vSeats));

        }
       this.vipSeats = vipSeats;
    }

    private void init() {
        if (this.vipFactor == null) {
            vipFactor = defaultVipFactor;
        }
    }

    /**
     * Counts how many vip seats are there in supplied <code>seats</code>
     *
     * @param seats Seats to process
     * @return number of vip seats in request
     */
    public long countVipSeats(Collection<Long> seats) {
        return seats.stream().filter(seat -> vipSeats.contains(seat)).count();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(long numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Set<Long> getAllSeats() {
        return LongStream.range(1, numberOfSeats + 1).boxed().collect(Collectors.toSet());
    }

    public Set<Long> getVipSeats() {
        return vipSeats;
    }

    public String getVipSeatsAsString() {
        return StringUtils.join(this.vipSeats, ',');
    }

    public void setVipSeats(Set<Long> vipSeats) {
        this.vipSeats = vipSeats;
    }

    public Double getVipFactor() {
        return this.vipFactor;
    }

    public void setVipFactor(Double vipFactor) {
        this.vipFactor = vipFactor;
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
        Auditorium other = (Auditorium) obj;
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
    public int compareTo(Auditorium other) {
        if (other == null) {
            return 1;
        }

        int result = getName().compareTo(other.getName());
        return result;
    }

    @Override
    public String toString() {
        return "Auditorium{" +
                "name='" + name + '\'' +
                ", number of seats=" + numberOfSeats +
                ", vip seats =" + vipSeats
                + '}';
    }
}
