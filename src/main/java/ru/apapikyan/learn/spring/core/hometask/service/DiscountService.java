package ru.apapikyan.learn.spring.core.hometask.service;

import ru.apapikyan.learn.spring.core.hometask.domain.AirEvent;
import ru.apapikyan.learn.spring.core.hometask.domain.EventV2;
import ru.apapikyan.learn.spring.core.hometask.domain.Ticket;
import ru.apapikyan.learn.spring.core.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author Yuriy_Tkach
 */
public interface DiscountService {

    /**
     * Getting discount based on some rules for user that buys some number of
     * tickets for the specific date time of the event
     *
     * @param user            User that buys tickets. Can be <code>null</code>
     * @param event           EventV2 that tickets are bought for
     * @param airDateTime     The date and time event will be aired
     * @param numberOfTickets Number of tickets that user buys
     * @return discount value from 0 to 100
     */
    //byte getDiscount(@Nullable User user, @Nonnull EventV2 event, @Nonnull LocalDateTime airDateTime, long numberOfTickets);

    /**
     * Getting discount based on some rules for user that buys some number of
     * tickets for the specific date time of the event
     *
     * @param user            User that buys tickets. Can be <code>null</code>
     * @param airEvent        EventV2 that tickets are bought for
     * @param numberOfTickets Number of tickets that user buys
     * @return discount value from 0 to 100
     */
    //byte getDiscount(@Nullable User user, @Nonnull AirEvent airEvent, long numberOfTickets);

    /**
     *
     * @param user
     * @param airEvent
     * @param currentPurchase
     * @param nextTicket
     * @return
     */
    byte getDiscount(@Nullable User user, @Nonnull AirEvent airEvent, Set<Ticket> currentPurchase, Ticket nextTicket);
}
