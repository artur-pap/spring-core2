package ru.apapikyan.learn.spring.core.hometask.ui.console;

import ru.apapikyan.learn.spring.core.hometask.domain.Auditorium;

import java.util.*;

/**
 * Created by Artur_Papikyan on 14.04.2016.
 */
public class TestClass {

    private static HashMap<Long, SomeObject> records = new HashMap<>();

    private static void testNavSet() {
        NavigableSet<Auditorium> someNSet = new TreeSet<Auditorium>();

        Auditorium auditorium1 = new Auditorium(5L, "Auditorium1", 50L, "1,2,3,4,5,6,7");
        Auditorium auditorium2 = new Auditorium(5L, "Auditorium1", 50L, "1,2,3,4,5,6,7");

        someNSet.add(auditorium1);
        System.out.println("Added: " + auditorium1.toString());
    }

    private static void testSetCollections() {
        HashSet<Long> seats = new HashSet<Long>(Arrays.asList(Long.valueOf(2L)));
        HashSet<Long> seatsToBuy = new HashSet<Long>(Arrays.asList(Long.valueOf(2L)));


        seats.forEach(s -> printObjHash(s));
        seatsToBuy.forEach(s -> printObjHash(s));
        System.out.println("Contains " + seats.contains(seatsToBuy));



        seats.clear();
        seats.add(Long.valueOf(2L));

        seatsToBuy.clear();
        seatsToBuy.add(Long.valueOf(2L));

        System.out.println("seats.contains(2L) " + seats.contains(2L));
        System.out.println("seats.contains(seatsToBuy) " + seats.contains(seatsToBuy));
    }

    private static void printObjHash(Long s) {
        System.out.println(s.hashCode());
    }

    public static void main(String[] args) {

        //testSomeObjectsEquality();
        testSetCollections();

        System.exit(0);

        NavigableSet<Auditorium> auditoriums = new TreeSet();

        Auditorium aud = new Auditorium();


        //List<Long> longList = Arrays.asList(1L, 2L, 3L);
        HashSet<Long> longSet = new HashSet<>();
        Collections.addAll(longSet, 1L, 2L, 3L);

        aud.setId(1L);
        aud.setName("name1");
        aud.setVipSeats(longSet);
        aud.setNumberOfSeats(50);
        aud.setVipFactor(Double.valueOf(2.0));

        auditoriums.add(aud);
    }

    private static void testSomeObjectsEquality() {
        SomeObject so1 = new SomeObject("Some1");
        Long id = generateId(records.keySet());
        so1.id = id;

        records.put(id, so1);

        SomeObject so2 = new SomeObject(1L, "Some1");
        records.put(id, so2);
    }

    private static Long generateId(Set<Long> ids) {
        //Set<Long> ids = records.keySet();
        if (ids.isEmpty()) {
            return 1L;
        }

        return Collections.max(ids) + 1;
    }
}

class SomeObject {
    public Long id;
    public String name;

    SomeObject(String name) {
        this.name = name;
    }

    SomeObject(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
