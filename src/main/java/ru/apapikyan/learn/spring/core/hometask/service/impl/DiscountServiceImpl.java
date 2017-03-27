package ru.apapikyan.learn.spring.core.hometask.service.impl;

import org.springframework.stereotype.Service;
import ru.apapikyan.learn.spring.core.hometask.domain.AirEvent;
import ru.apapikyan.learn.spring.core.hometask.domain.EventV2;
import ru.apapikyan.learn.spring.core.hometask.domain.Ticket;
import ru.apapikyan.learn.spring.core.hometask.domain.User;
import ru.apapikyan.learn.spring.core.hometask.service.DiscountService;
import ru.apapikyan.learn.spring.core.hometask.service.EventServiceV2;
import ru.apapikyan.learn.spring.core.hometask.service.aspects.DiscountAspect;
import ru.apapikyan.learn.spring.core.hometask.service.logic.DiscountStrategy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by Artur_Papikyan on 15.04.2016.
 */
@Service
public class DiscountServiceImpl implements DiscountService {

    private Collection<DiscountStrategy> discountStrategies;
    private EventServiceV2 eventService;
    private DiscountAspect discountAspect;

    private DiscountServiceImpl(){}

    public DiscountServiceImpl(List<DiscountStrategy> discountStrategies, EventServiceV2 eventService) {
        this.discountStrategies = discountStrategies;
        this.eventService = eventService;
    }

    @Override
    public byte getDiscount(@Nullable User user, @Nonnull AirEvent airEvent, Set<Ticket> currentPurchase, Ticket nextTicket) {
        byte discount = 0;

        for (DiscountStrategy discountStrategy : discountStrategies) {
            //byte newDiscount = discountStrategy.calculateDiscount(user, airEvent);
            byte newDiscount = discountStrategy.calculateDiscount(user, airEvent, currentPurchase, nextTicket);
            if (discount < newDiscount) {
                discount = newDiscount;
            }
        }

        return discount;
    }
}
