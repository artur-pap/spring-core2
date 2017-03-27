package ru.apapikyan.learn.spring.core.hometask.dao.memory;

import org.springframework.stereotype.Repository;
import ru.apapikyan.learn.spring.core.hometask.dao.AuditoriumDAO;
import ru.apapikyan.learn.spring.core.hometask.domain.Auditorium;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Artur_Papikyan on 20.04.2016.
 */
@Repository
public class AuditoriumDAOImpl implements AuditoriumDAO {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Resource(name = "auditoriums")
    private Map<Long, Auditorium> records;

    @Nullable
    @Override
    public Auditorium create(@Nonnull Auditorium object) {
        object.setId(generateId());

        this.records.put(object.getId(), object);

        return getById(object.getId());
    }

    @Nullable
    @Override
    public Auditorium update(@Nonnull Auditorium object) {
        if (!this.records.containsValue(object) || object.getId() == null) {
            System.out.println(String.format("ERROR! Can't update NON existing object [%s]\r\nCreate First!", object));
            return null;
        }

        this.records.put(object.getId(), object);

        return this.getById(object.getId());
    }

    @Nullable
    @Override
    public Auditorium delete(@Nonnull Auditorium object) {
        if (!this.records.containsValue(object) || object.getId() == null) {
            System.out.println(String.format("ERROR! Can't delete NON existing object [%s]\r\nCreate First!", object));
            return null;
        }

        return this.records.remove(object.getId());
    }

    @Nullable
    @Override
    public Auditorium getById(@Nonnull Long id) {
        return this.records.get(id);
    }

    @Nullable
    @Override
    public Set<Auditorium> getAll() {
        return new HashSet<Auditorium>(this.records.values());
    }

    @Nullable
    @Override
    public Auditorium getByName(@Nonnull String name) {
        Optional<Auditorium> result = this.records.values().stream().filter(aud -> aud.getName().equals(name)).findFirst();

        if (!result.isPresent()) {
            return null;
        } else {

            return result.get();
        }
    }

    private Long generateId() {
        Set<Long> ids = this.records.keySet();
        if (ids.isEmpty()) {
            return 1L;
        }
        return Collections.max(ids) + 1;
    }
}