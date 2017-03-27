package ru.apapikyan.learn.spring.core.hometask.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ru.apapikyan.learn.spring.core.hometask.domain.Auditorium;
import ru.apapikyan.learn.spring.core.hometask.service.aspects.CounterAspect;
import ru.apapikyan.learn.spring.core.hometask.service.aspects.DiscountAspect;
import ru.apapikyan.learn.spring.core.hometask.service.aspects.LuckyWinnerAspect;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Artur_Papikyan on 28.04.2016.
 */
@Configuration
@EnableAspectJAutoProxy
public class AspectsConfig {

    @Bean(name = "counterAspect")
    public CounterAspect getCounterAspect() {
        return new CounterAspect();
    }

    @Bean(name = "discountAspect")
    public DiscountAspect getDiscountAspect() {
        return new DiscountAspect();
    }

    @Bean(name = "luckyWinnerAspect")
    public LuckyWinnerAspect getLuckyWinnerAspect() {
        return new LuckyWinnerAspect();
    }

//    @Bean(name = "aspects")
//    public List<Object> getAspectsSet() {
//        List<Object> returnSet = new ArrayList<>();
//
//        returnSet.add(new CounterAspect());
//        returnSet.add(new DiscountAspect());
//        returnSet.add(new LuckyWinnerAspect());
//
//        return returnSet;
//    }

}