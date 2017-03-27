package ru.apapikyan.learn.spring.core.hometask.dao.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.apapikyan.learn.spring.core.hometask.domain.EventRating;
import ru.apapikyan.learn.spring.core.hometask.domain.EventV2;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Artur_Papikyan on 05.05.2016.
 */
public class EventRowMapper implements RowMapper<EventV2> {

    public EventV2 mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");

        EventRating eventRating = EventRating.MID;

        String eventString = resultSet.getString("rating");

        //WE CAN ADD CONSTRAINT ON DB LEVEL AND NON NULL COLUMN
        if (eventString != null && !eventString.isEmpty()) {
            eventRating = Enum.valueOf(EventRating.class, eventString);
        }

        Double basePrice = resultSet.getDouble("basePrice");

        return new EventV2(id, name, eventRating, basePrice);
    }
}

