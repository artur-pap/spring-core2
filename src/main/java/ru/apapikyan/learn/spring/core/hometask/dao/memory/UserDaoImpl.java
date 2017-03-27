package ru.apapikyan.learn.spring.core.hometask.dao.memory;

import org.springframework.stereotype.Repository;
import ru.apapikyan.learn.spring.core.hometask.dao.UserDAO;
import ru.apapikyan.learn.spring.core.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by Artur_Papikyan on 20.04.2016.
 */
@Repository
public class UserDAOImpl implements UserDAO {

    private NavigableMap<Long, User> records = new TreeMap<Long, User>();

    private UserDAOImpl(){}

    @Nullable
    @Override
    public User create(@Nonnull User object) {
        object.setId(generateId());

        records.put(object.getId(), object);

        return this.getById(object.getId());
    }

    @Nullable
    @Override
    public User update(@Nonnull User object) {
        if (!records.containsValue(object) || object.getId() == null) {
            System.out.println(String.format("ERROR! Can't update NON existing object [%s]\r\nCreate First!", object));
            return null;
        }

        records.put(object.getId(), object);

        return this.getById(object.getId());
    }

    @Nullable
    @Override
    public User delete(@Nonnull User object) {
        if (!records.containsValue(object) || object.getId() == null) {
            System.out.println(String.format("ERROR! Can't delete NON existing object [%s]\r\nCreate First!", object));
            return  null;
        }

        return records.remove(object.getId());
    }

    @Nullable
    @Override
    public User getById(@Nonnull Long id) {
        return records.get(id);
    }

    @Nullable
    @Override
    public Set<User> getAll() {
        return new HashSet<User>(records.values());
    }

    private Long generateId() {
        Set<Long> ids = records.keySet();
        if (ids.isEmpty()) {
            return 1L;
        }
        return Collections.max(ids) + 1;
    }
}
