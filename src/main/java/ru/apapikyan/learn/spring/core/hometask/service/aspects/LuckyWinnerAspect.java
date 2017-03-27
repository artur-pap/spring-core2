package ru.apapikyan.learn.spring.core.hometask.service.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import ru.apapikyan.learn.spring.core.hometask.domain.Ticket;
import ru.apapikyan.learn.spring.core.hometask.domain.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Artur_Papikyan on 20.04.2016.
 */
@Aspect
public class LuckyWinnerAspect {

    public static final Double C_PROBABILITY = 0.6;
    private Map<Ticket, User> luckyTickets;

    public LuckyWinnerAspect() {
        luckyTickets = new HashMap<>();
    }

    @Pointcut("execution(* ru.apapikyan.learn.spring.core.hometask.service.BookingServiceV2.bookTicket(..))")
    private void bookTicketMethodCall() {
    }

    @Around("bookTicketMethodCall()")
    public Ticket countBookTicket(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Ticket ticket = (Ticket) proceedingJoinPoint.proceed();
        User user = ticket.getUser();

        //return Math.random() * 100 < C_PROBABILITY + (seed / 10);
        if (Math.random() > C_PROBABILITY) {
            ticket.setPrice(0.0);
        }

        return ticket;
    }

    @Override
    public String toString() {
        return "LuckyWinnerAspect{" +
                "luckyTickets = " + luckyTickets.size() +
                '}';
    }
}