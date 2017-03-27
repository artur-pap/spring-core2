package ru.apapikyan.learn.spring.core.hometask.configuration;

/**
 * Created by Artur_Papikyan on 25.04.2016.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import ru.apapikyan.learn.spring.core.hometask.domain.EventRating;
import ru.apapikyan.learn.spring.core.hometask.domain.EventV2;
import ru.apapikyan.learn.spring.core.hometask.service.DiscountService;
import ru.apapikyan.learn.spring.core.hometask.service.EventServiceV2;
import ru.apapikyan.learn.spring.core.hometask.service.impl.DiscountServiceImpl;
import ru.apapikyan.learn.spring.core.hometask.service.logic.BirthdayDiscount;
import ru.apapikyan.learn.spring.core.hometask.service.logic.DiscountStrategy;
import ru.apapikyan.learn.spring.core.hometask.service.logic.Every10thDiscount;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Configuration
@ComponentScan(basePackages = {"ru.apapikyan.learn.spring.core.hometask.*"})
@PropertySource({"classpath:data/discount_base_values.properties"}
        /*,
        "classpath:data/event1.properties",
        "classpath:data/event2.properties",
        "classpath:data/event3.properties"}*/)
public class SpringConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Autowired
    private Environment env;

    @Bean
    @Qualifier("birthdayDiscount")
    public DiscountStrategy getBirthDaDiscountStrategy() {
        return new BirthdayDiscount(Byte.valueOf(env.getProperty("birthday.base.discount")));
    }

    @Bean
    @Qualifier("every10thDiscount")
    public DiscountStrategy getEvery10thDiscount() {
        return new Every10thDiscount(Byte.valueOf(env.getProperty("every10th.base.discount")));
    }

    @Bean(name = "discountStrategiesList")
    public List<DiscountStrategy> getDiscountStrategies() {
        List<DiscountStrategy> strategies = new ArrayList<>();

        strategies.add(getBirthDaDiscountStrategy());
        strategies.add(getEvery10thDiscount());

        return strategies;
    }

    @Autowired
    List<DiscountStrategy> discountStrategiesList;

    @Autowired
    EventServiceV2 eventServiceV2;

    @Bean(name = "discountService")
    public DiscountService getDiscountService() {
        return new DiscountServiceImpl(discountStrategiesList, eventServiceV2);
    }

    @Bean(name = "eventsMap")
    public Map<Long, EventV2> getEventsMap() {
        Map<Long, EventV2> returnMap = new TreeMap<>();

        returnMap.put(1L, new EventV2(1L, "Dead Pool", EventRating.HIGH, 15.0));
        returnMap.put(2L, new EventV2(2L, "Zootopia", EventRating.MID, 10.0));
        returnMap.put(3L, new EventV2(3L, "Jungle Book", EventRating.LOW, 5.0));

        return returnMap;
    }
}