package ru.apapikyan.learn.spring.core.hometask.dao;

import ru.apapikyan.learn.spring.core.hometask.domain.DomainObject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

/**
 * Created by Artur_Papikyan on 19.04.2016.
 */
public interface AbstractDAO<T extends DomainObject> {

    public
    @Nullable
    T create(@Nonnull T object);

    public
    @Nullable
    T update(@Nonnull T object);

    public
    @Nullable
    T delete(@Nonnull T object);

    public
    @Nullable
    T getById(@Nonnull Long id);

    public
    @Nonnull
    Set<T> getAll();
}
