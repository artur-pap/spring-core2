package ru.apapikyan.learn.spring.core.hometask.service;

import ru.apapikyan.learn.spring.core.hometask.domain.Ticket;
import ru.apapikyan.learn.spring.core.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

/**
 * @author Yuriy_Tkach
 */
public interface UserService extends AbstractDomainObjectService<User> {

    /**
     * Finding user by email
     *
     * @param email Email of the user
     * @return found user or <code>null</code>
     */
    public
    @Nullable
    User getUserByEmail(@Nonnull String email);


    /**
     * Finding user by email
     *
     * @param user    User who bought ticket
     * @param tickets Collection of user tickets to remove
     */
    public void removeTickets(@Nonnull User user, @Nonnull Collection<Ticket> tickets);
}
