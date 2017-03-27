package ru.apapikyan.learn.spring.core.hometask.service.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import ru.apapikyan.learn.spring.core.hometask.domain.EventV2;
import ru.apapikyan.learn.spring.core.hometask.domain.Ticket;
import ru.apapikyan.learn.spring.core.hometask.domain.User;
import ru.apapikyan.learn.spring.core.hometask.service.AspectService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Created by Artur_Papikyan on 20.04.2016.
 */
@Aspect
public class CounterAspect {

    private Map<EventV2, Integer> counterGetEventByName;
    private Map<EventV2, Integer> counterGetTicketsPrice;
    private Map<EventV2, Integer> counterBookTicket;

    public CounterAspect() {
        counterGetEventByName = new HashMap<>();
        counterGetTicketsPrice = new HashMap<>();
        counterBookTicket = new HashMap<>();
    }

    @Pointcut("execution(* ru.apapikyan.learn.spring.core.hometask.service.EventServiceV2.getByName(..))")
    private void getEventByNameMethodCall() {
    }

    @AfterReturning(pointcut = "getEventByNameMethodCall()", returning = "eventObject")
    public void countGetByName(Object eventObject) {
        System.out.println("adviceAfterReturning.GetEventByName");

        EventV2 event = (EventV2) eventObject;

        if (!counterGetEventByName.containsKey(event)) {
            counterGetEventByName.put(event, 0);
        }
        counterGetEventByName.put(event, counterGetEventByName.get(event) + 1);
    }

    @Pointcut("execution(* ru.apapikyan.learn.spring.core.hometask.service.BookingServiceV2.getTicketsPrice(..))")
    private void getTicketsPriceMethodCall() {
    }

    @AfterReturning(pointcut = "getTicketsPriceMethodCall()")
    public void counterGetTicketsPrice(JoinPoint joinPoint) {
        System.out.println("adviceAfterReturning.GetTicketsPrice");

        EventV2 event = (EventV2) joinPoint.getArgs()[0];

        if (!counterGetTicketsPrice.containsKey(event)) {
            counterGetTicketsPrice.put(event, 0);
        }

        counterGetTicketsPrice.put(event, counterGetTicketsPrice.get(event) + 1);
    }

    @Pointcut("execution(* ru.apapikyan.learn.spring.core.hometask.service.BookingServiceV2.bookTickets(..)) && args(user, tickets)")
    private void callBookTicketsMethod(User user, Set<Ticket> tickets) {}

    @Before("callBookTicketsMethod(user, tickets)")
    public void adviceBeforeBookTickets(JoinPoint joinPoint, User user, Set<Ticket> tickets) {
        System.out.println("adviceBefore.BookTickets");

        //TODO. AS I CANT CATCH NESTED METHOD BY AOP. Should USE Aspect Weaving OR refactor code to avoid nested calls, e.g. bookTicket and BookTickets
        Ticket someTicket = (Ticket) tickets.toArray()[0];

        EventV2 event = someTicket.getAirEvent().getEvent();

        if (!counterBookTicket.containsKey(event)) {
            counterBookTicket.put(event, 0);
        }

        counterBookTicket.put(event, counterBookTicket.get(event) + 1);
    }

    @Override
    public String toString() {
        return "CounterAspect{" +
                "getEventByName = " + counterGetEventByName.values().size() +
                "; getTicketsPrice = " + counterGetTicketsPrice.values().size() +
                "; bookTicket = " + counterBookTicket.size() +
                '}';
    }
}