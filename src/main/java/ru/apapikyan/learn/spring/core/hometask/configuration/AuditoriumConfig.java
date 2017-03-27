package ru.apapikyan.learn.spring.core.hometask.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ru.apapikyan.learn.spring.core.hometask.domain.Auditorium;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Created by Artur_Papikyan on 28.04.2016.
 */
@Configuration
@PropertySource("classpath:data/auditoriums-all.properties")
public class AuditoriumConfig {

    /**
     * Adding this bean is mandatory, to resolve ${} in @Value
     *
     * @return
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Value("${aud1.id}")
    private Long audId1;

    @Value("${aud1.name}")
    private String audName1;

    @Value("${aud1.numberOfSeats}")
    private long audSeats1;

    @Value("#{'${aud1.vipSeats}'.split(',')}")
    private List<Long> audVips1;

    @Value("${aud2.id}")
    private Long audId2;

    @Value("${aud2.name}")
    private String audName2;

    @Value("${aud2.numberOfSeats}")
    private long audSeats2;

    @Value("#{'${aud2.vipSeats}'.split(',')}")
    private List<Long> audVips2;

    @Value("${aud3.id}")
    private Long audId3;

    @Value("${aud3.name}")
    private String audName3;

    @Value("${aud3.numberOfSeats}")
    private long audSeats3;

    @Value("#{'${aud3.vipSeats}'.split(',')}")
    private List<Long> audVips3;

    @Bean
    public Auditorium getFirstAuditorium() {
        return generateAuditorium(audId1, audName1, audSeats1, audVips1);
    }

    @Bean
    public Auditorium getSecondAuditorium() {
        return generateAuditorium(audId2, audName2, audSeats2, audVips2);
    }

    @Bean
    public Auditorium getThirdAuditorium() {
        return generateAuditorium(audId3, audName3, audSeats3, audVips3);
    }

    @Bean(name = "auditoriums")
    public Map<Long, Auditorium> getAuditoriumMap() {
        Map<Long, Auditorium> auditoriums = new TreeMap<>();

        auditoriums.put(audId1, getFirstAuditorium());
        auditoriums.put(audId2, getSecondAuditorium());
        auditoriums.put(audId3, getThirdAuditorium());

        return auditoriums;
    }

    private Auditorium generateAuditorium(Long id, String name, long seats, Collection<Long> vips) {
        Auditorium auditorium = new Auditorium();
        auditorium.setId(id);
        auditorium.setName(name);
        auditorium.setNumberOfSeats(Long.valueOf(seats));

        auditorium.setVipSeats(vips.stream().map(s -> Long.valueOf(s)).collect(Collectors.toSet()));
        return auditorium;
    }

}