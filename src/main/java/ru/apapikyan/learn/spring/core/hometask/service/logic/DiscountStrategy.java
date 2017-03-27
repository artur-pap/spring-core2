package ru.apapikyan.learn.spring.core.hometask.service.logic;

import ru.apapikyan.learn.spring.core.hometask.domain.AirEvent;
import ru.apapikyan.learn.spring.core.hometask.domain.EventV2;
import ru.apapikyan.learn.spring.core.hometask.domain.Ticket;
import ru.apapikyan.learn.spring.core.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by Artur_Papikyan on 15.04.2016.
 */
public interface DiscountStrategy {

    //byte calculateDiscount(User user, @Nonnull AirEvent airEvent);

    byte calculateDiscount(@Nullable User user, @Nonnull AirEvent airEvent, @Nonnull Set<Ticket> currentPurchase, @Nonnull Ticket nextTicket);
}


