package ru.apapikyan.learn.spring.core.hometask.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.apapikyan.learn.spring.core.hometask.dao.AuditoriumDAO;
import ru.apapikyan.learn.spring.core.hometask.domain.Auditorium;
import ru.apapikyan.learn.spring.core.hometask.service.AuditoriumService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

/**
 * Created by Artur_Papikyan on 14.04.2016.
 */
@Service
public class AuditoriumServiceImpl implements AuditoriumService {

    @Autowired
    @Qualifier("auditoriumDbDAOImpl")
    private AuditoriumDAO dao;

    private AuditoriumServiceImpl() {
        System.out.println("Private constructor: " + this.getClass().getName());
    }

    public AuditoriumServiceImpl(AuditoriumDAO dao) {//, List<Auditorium> auditoriums) {
        this.dao = dao;
    }

    @Override
    public Auditorium save(@Nonnull Auditorium object) {
        if (object.getId() == null || dao.getById(object.getId()) == null) {
            return dao.create(object);
        } else {
            return dao.update(object);
        }
    }

    @Override
    public void remove(@Nonnull Auditorium object) {
        dao.delete(object);
    }

    @Override
    public Auditorium getById(@Nonnull Long id) {
        return dao.getById(id);
    }

    @Nonnull
    @Override
    public Collection<Auditorium> getAll() {
        return dao.getAll();
    }


    @Nullable
    @Override
    public Auditorium getByName(@Nonnull String name) {
        return dao.getByName(name);
    }
}