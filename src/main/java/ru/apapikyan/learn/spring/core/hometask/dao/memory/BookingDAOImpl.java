package ru.apapikyan.learn.spring.core.hometask.dao.memory;

import org.springframework.stereotype.Repository;
import ru.apapikyan.learn.spring.core.hometask.dao.BookingDAO;
import ru.apapikyan.learn.spring.core.hometask.domain.Ticket;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by Artur_Papikyan on 20.04.2016.
 */
@Repository
public class BookingDAOImpl implements BookingDAO {
    private NavigableMap<Long, Ticket> records = new TreeMap<Long, Ticket>();

    private BookingDAOImpl() {}

    public BookingDAOImpl(@Nonnull NavigableMap<Long, Ticket> records) {
        this.records = records;
    }

    @Nullable
    @Override
    public Ticket create(@Nonnull Ticket object) {
        object.setId(generateId());

        records.put(object.getId(), object);

        return this.getById(object.getId());
    }

    @Nullable
    @Override
    public Ticket update(@Nonnull Ticket object) {
        if (!records.containsValue(object) || object.getId() == null) {
            System.out.println(String.format("ERROR! Can't update NON existing object [%s]\r\nCreate First!", object));
            return null;
        }

        records.put(object.getId(), object);

        return this.getById(object.getId());
    }

    @Nullable
    @Override
    public Ticket delete(@Nonnull Ticket object) {
        if (!records.containsValue(object) || object.getId() == null) {
            System.out.println(String.format("ERROR! Can't delete NON existing object [%s]\r\nCreate First!", object));
            return  null;
        }

        return records.remove(object.getId());
    }

    @Nullable
    @Override
    public Ticket getById(@Nonnull Long id) {
        return records.get(id);
    }

    @Nullable
    @Override
    public Set<Ticket> getAll() {
        return new HashSet<Ticket>(records.values());
    }

    private Long generateId() {
        Set<Long> ids = records.keySet();
        if (ids.isEmpty()) {
            return 1L;
        }
        return Collections.max(ids) + 1;
    }
}
