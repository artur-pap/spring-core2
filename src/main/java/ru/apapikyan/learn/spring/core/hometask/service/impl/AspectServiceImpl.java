package ru.apapikyan.learn.spring.core.hometask.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.apapikyan.learn.spring.core.hometask.service.AspectService;
import ru.apapikyan.learn.spring.core.hometask.service.aspects.CounterAspect;
import ru.apapikyan.learn.spring.core.hometask.service.aspects.DiscountAspect;
import ru.apapikyan.learn.spring.core.hometask.service.aspects.LuckyWinnerAspect;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Artur_Papikyan on 28.04.2016.
 */
@Service
public class AspectServiceImpl implements AspectService {

    @Autowired
    @Qualifier("counterAspect")
    private CounterAspect counterAspect;

    @Autowired
    private DiscountAspect discountAspect;

    @Autowired
    private LuckyWinnerAspect luckyWinnerAspect;

    @Autowired
    public AspectServiceImpl(CounterAspect counterAspect, DiscountAspect discountAspect, LuckyWinnerAspect luckyWinnerAspect) {
        this.counterAspect = counterAspect;
        this.discountAspect = discountAspect;
        this.luckyWinnerAspect = luckyWinnerAspect;

        aspects.add(counterAspect);
        aspects.add(discountAspect);
        aspects.add(luckyWinnerAspect);
    }

    //@SuppressWarnings("SpringJavaAutowiringInspection")
    //@Resource(name = "aspects")
    private Set<Object> aspects = new HashSet<>();

    public void setAspects(Set<Object> aspects) {
        this.aspects = aspects;
    }

    public Set<Object> getAspects() {
        return aspects;
    }

    @Override
    public Set<Object> getAll() {
        return aspects;
    }

    @Override
    public void printStatistics() {
        System.out.println("EMPTY");
    }
}
