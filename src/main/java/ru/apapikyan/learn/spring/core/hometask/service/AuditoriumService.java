package ru.apapikyan.learn.spring.core.hometask.service;

import ru.apapikyan.learn.spring.core.hometask.domain.Auditorium;
import ru.apapikyan.learn.spring.core.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Set;

/**
 * @author Yuriy_Tkach
 */
public interface AuditoriumService extends AbstractDomainObjectService<Auditorium> {

    /**
     * Finding auditorium by name
     *
     * @param name Name of the auditorium
     * @return found auditorium or <code>null</code>
     */
    public
    @Nullable
    Auditorium getByName(@Nonnull String name);
}
