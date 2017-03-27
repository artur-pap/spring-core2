package ru.apapikyan.learn.spring.core.hometask.dao;

import ru.apapikyan.learn.spring.core.hometask.domain.Auditorium;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Created by Artur_Papikyan on 22.04.2016.
 */
public interface AuditoriumDAO extends AbstractDAO<Auditorium> {

    @Nullable
    public Auditorium getByName(@Nonnull String name);
}
