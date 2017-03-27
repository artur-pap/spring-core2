package ru.apapikyan.learn.spring.core.hometask.service.logic;

import ru.apapikyan.learn.spring.core.hometask.domain.AirEvent;
import ru.apapikyan.learn.spring.core.hometask.domain.Ticket;
import ru.apapikyan.learn.spring.core.hometask.domain.User;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * Created by Artur_Papikyan on 15.04.2016.
 */
public class Every10thDiscount implements DiscountStrategy {

    private byte discountPercent = 0;

    public Every10thDiscount(byte discountPercent) {
        this.discountPercent = discountPercent;
    }

    private Every10thDiscount() {
    }


    @Override
    public byte calculateDiscount(User user, @Nonnull AirEvent airEvent, @Nonnull Set<Ticket> currentPurchase, @Nonnull Ticket nextTicket) {
        //We  will count all tickets.
        //Tickets that user _already bought_ with tickets that he _want to buy_ or are in process
        byte discount = 0;
        int ticketsAmount = 0;

        if (user == null || user.getTickets() == null) {
            return 0;
        }

        //ticketsAmount += user.getTickets().size();
        ticketsAmount = user.getTickets().size();

        //TODO REMOVE currentPurchace logic
        //ticketsAmount += currentPurchase.size();

        discount = (ticketsAmount > 0 && ticketsAmount % 10 == 0) ? this.discountPercent : 0;

        if (discount > 0) {
            System.out.println(
                    String.format("[every10thDiscount]. User = [%s]; Num of tickets = [%s], discount = [%s]",
                            user, ticketsAmount, discount));
        }

        return discount;
    }
}
