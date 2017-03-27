package ru.apapikyan.learn.spring.core.hometask.domain;

import org.junit.Test;
import ru.apapikyan.learn.spring.core.hometask.dao.AuditoriumDAO;
import ru.apapikyan.learn.spring.core.hometask.dao.memory.AuditoriumDAOImpl;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

/**
 * @author Yuriy_Tkach
 */
public class TestAuditorium {

    @Test
    public void testCountVips() {
        Auditorium a = new Auditorium();
        a.setVipSeats(Stream.of(1L, 2L, 3L).collect(Collectors.toSet()));
        assertEquals(0, a.countVipSeats(Arrays.asList(10L, 20L, 30L)));
        assertEquals(1, a.countVipSeats(Arrays.asList(10L, 2L, 30L)));
        assertEquals(2, a.countVipSeats(Arrays.asList(10L, 2L, 3L, 4L, 5L, 6L)));
    }


    @Test
    public void testGetAllSeats() {
        String name = null;
        //@Nonnull checks
        Auditorium a = new Auditorium(1L, name, 10L, "1,2,3");
        a.setNumberOfSeats(10);
        assertEquals(10, a.getAllSeats().size());
    }

    @Test
    public void testAddAuditoriums() {
        AuditoriumDAO aDAO = new AuditoriumDAOImpl();

//        Long id = generateId(records);
//        Auditorium auditorium = new Auditorium(id, "Auditorium1", 10L, "1,2,3");a
        Auditorium auditorium = new Auditorium("Auditorium1", 10L, new LinkedHashSet<Long>((Arrays.asList(1L, 2L, 3L))));
        aDAO.create(auditorium);

        Auditorium auditorium2 = new Auditorium("Auditorium1", 10L, new LinkedHashSet<Long>((Arrays.asList(1L, 2L, 3L))));
        aDAO.create(auditorium2);
    }


    private Long generateId(Map<Long, Auditorium> records) {
        Set<Long> ids = records.keySet();

        if (ids.isEmpty()) {
            return 1L;
        }

        return Collections.max(ids) + 1;
    }
}
