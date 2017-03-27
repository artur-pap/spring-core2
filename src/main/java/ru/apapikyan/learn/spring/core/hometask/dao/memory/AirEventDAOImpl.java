package ru.apapikyan.learn.spring.core.hometask.dao.memory;

import org.springframework.stereotype.Repository;
import ru.apapikyan.learn.spring.core.hometask.dao.AirEventDAO;
import ru.apapikyan.learn.spring.core.hometask.domain.AirEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by Artur_Papikyan on 22.04.2016.
 */
@Repository
public class AirEventDAOImpl implements AirEventDAO {

    private NavigableMap<Long, AirEvent> records = new TreeMap<Long, AirEvent>();

    private AirEventDAOImpl() {}

    public AirEventDAOImpl(@Nonnull NavigableMap<Long, AirEvent> records) {
        this.records = new TreeMap<Long, AirEvent>(records);
    }

    @Nullable
    @Override
    public AirEvent create(@Nonnull AirEvent object) {
        object.setId(generateId());

        records.put(object.getId(), object);

        return this.getById(object.getId());
    }

    @Nullable
    @Override
    public AirEvent update(@Nonnull AirEvent object) {
        if (!records.containsValue(object) || object.getId() == null) {
            System.out.println(String.format("ERROR! Can't update NON existing object [%s]\r\nCreate First!", object));
            return null;
        }

        records.put(object.getId(), object);

        return this.getById(object.getId());
    }

    @Nullable
    @Override
    public AirEvent delete(@Nonnull AirEvent object) {
        if (!records.containsValue(object) || object.getId() == null) {
            System.out.println(String.format("ERROR! Can't delete NON existing object [%s]\r\nCreate First!", object));
            return null;
        }

        return records.remove(object.getId());
    }

    @Nullable
    @Override
    public AirEvent getById(@Nonnull Long id) {
        return records.get(id);
    }

    @Nonnull
    @Override
    public Set<AirEvent> getAll() {
        return new HashSet<AirEvent>(records.values()) ;
    }

    private Long generateId() {
        Set<Long> ids = records.keySet();
        if (ids.isEmpty()) {
            return 1L;
        }
        return Collections.max(ids) + 1;
    }
}
