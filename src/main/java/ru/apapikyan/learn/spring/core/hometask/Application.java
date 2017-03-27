package ru.apapikyan.learn.spring.core.hometask;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.apapikyan.learn.spring.core.hometask.configuration.DataSourceConfig;
import ru.apapikyan.learn.spring.core.hometask.configuration.SpringConfiguration;
import ru.apapikyan.learn.spring.core.hometask.domain.Auditorium;
import ru.apapikyan.learn.spring.core.hometask.domain.EventRating;
import ru.apapikyan.learn.spring.core.hometask.domain.EventV2;
import ru.apapikyan.learn.spring.core.hometask.service.AuditoriumService;
import ru.apapikyan.learn.spring.core.hometask.service.EventServiceV2;

import java.util.*;

/**
 * Here we will test and show spring java annotated configuration
 * <p/>
 * Created by Artur_Papikyan on 25.04.2016.
 */
public class Application {
    public static final String C_SPRING_PROFILE_DEV = "dev";
    public static final String C_SPRING_PROFILE_TEST = "test";

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class, DataSourceConfig.class);

        AuditoriumService auditoriumService = context.getBean(AuditoriumService.class);
        auditoriumService.save(new Auditorium("Some Auditorium", 120L, new HashSet<Long>((Arrays.asList(1L, 2L, 3L)))));

        EventServiceV2 eventService = context.getBean(EventServiceV2.class);
        eventService.save(new EventV2("Some EventV2", EventRating.HIGH, 125.25));
        Collection<EventV2> events = eventService.getAll();

        System.out.println("stop");

        //System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, C_SPRING_PROFILE_DEV);
        //ApplicationContext context = new AnnotationConfigApplicationContext(SOME STRING CONFIGURATION CLASS);


        //final ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfiguration.class);//, anotherconfig.class);
        //final SomeClass someClass = ctx.getBean(SomeClass.class);
    }
}
