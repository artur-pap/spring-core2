package ru.apapikyan.learn.spring.core.hometask.service;

import ru.apapikyan.learn.spring.core.hometask.domain.AirEvent;
import ru.apapikyan.learn.spring.core.hometask.domain.Ticket;
import ru.apapikyan.learn.spring.core.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public interface AspectService {

    Set<Object> getAll();
    void printStatistics();
}
