package ru.apapikyan.learn.spring.core.hometask.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.apapikyan.learn.spring.core.hometask.dao.EventDAO;
import ru.apapikyan.learn.spring.core.hometask.dao.UserDAO;
import ru.apapikyan.learn.spring.core.hometask.domain.Ticket;
import ru.apapikyan.learn.spring.core.hometask.domain.User;
import ru.apapikyan.learn.spring.core.hometask.service.UserService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Artur_Papikyan on 14.04.2016.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO dao;

    private UserServiceImpl(){}

    public UserServiceImpl(UserDAO dao) {
        this.dao = dao;
    }

    @Override
    public User save(@Nonnull User object) {
        if(object.getId() == null || dao.getById(object.getId()) == null) {
            return dao.create(object);
        } else {
            return dao.update(object);
        }
    }

    @Override
    public void remove(@Nonnull User object) {
        if(object.getId() != null && dao.getById(object.getId()) != null) {
            User removedUser = dao.delete(object);
        }
    }

    @Nullable
    @Override
    public User getUserByEmail(@Nonnull String email) {
        for (User user : dao.getAll()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }

        return null;
    }

    @Nullable
    @Override
    public void removeTickets(@Nonnull User user, @Nonnull Collection<Ticket> tickets) {
        tickets.forEach(user::removeTicket);
    }

    @Override
    public User getById(@Nonnull Long id) {
        return dao.getById(id);
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        return dao.getAll();
    }
}