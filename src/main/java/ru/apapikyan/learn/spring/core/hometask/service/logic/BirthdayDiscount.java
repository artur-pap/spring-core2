package ru.apapikyan.learn.spring.core.hometask.service.logic;

import org.springframework.format.annotation.DateTimeFormat;
import ru.apapikyan.learn.spring.core.hometask.domain.AirEvent;
import ru.apapikyan.learn.spring.core.hometask.domain.Ticket;
import ru.apapikyan.learn.spring.core.hometask.domain.User;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Set;

/**
 * Created by Artur_Papikyan on 15.04.2016.
 */
public class BirthdayDiscount implements DiscountStrategy {

    private final byte C_WITHIN_DAYS_AMOUNT = 5;
    private byte discountPercent = 0;

    public BirthdayDiscount(byte discountPercent) {
        this.discountPercent = discountPercent;
    }

    private BirthdayDiscount() {}

    @Override
    public byte calculateDiscount(User user, @Nonnull AirEvent airEvent, @Nonnull Set<Ticket> currentPurchase, @Nonnull Ticket nextTicket) {
        //IN THIS STRATEGY WE WILL IGNORE NUMBER AND ORDER OF CURRENT TICKETS PURCHASE
        byte discount = 0;

        if (user != null && user.getBirthday() != null) {
            discount = birthdayWithin5DaysOfAirDate(user, airEvent.getAirDateTime()) ? discountPercent : 0;
        }

        return discount;
    }

    public boolean birthdayWithin5DaysOfAirDate(User user, LocalDateTime airDateTime) {
        DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE;
        //TODO !!! we will check only days w/o hours and minutes!!!
        LocalDateTime ldtB = user.getBirthday();

        Calendar birthdayCalendar = Calendar.getInstance();
        birthdayCalendar.set(airDateTime.getYear(), ldtB.getMonthValue(), ldtB.getDayOfMonth());

        Calendar airCalendar = Calendar.getInstance();
        airCalendar.set(airDateTime.getYear(), airDateTime.getMonthValue(), airDateTime.getDayOfMonth());

        Calendar airCalendar2 = (Calendar) airCalendar.clone();
        airCalendar2.add(Calendar.DATE, C_WITHIN_DAYS_AMOUNT);

        boolean result = birthdayCalendar.after(airCalendar) && birthdayCalendar.before(airCalendar2);

        return result;
    }
}