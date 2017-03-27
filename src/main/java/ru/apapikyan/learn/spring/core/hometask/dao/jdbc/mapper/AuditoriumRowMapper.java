package ru.apapikyan.learn.spring.core.hometask.dao.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.apapikyan.learn.spring.core.hometask.domain.Auditorium;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Artur_Papikyan on 02.05.2016.
 */
public class AuditoriumRowMapper implements RowMapper<Auditorium> {
    public Auditorium mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        long numberOfSeats = resultSet.getInt("numberOfSeats");
        String vipSeats = resultSet.getString("vipSeats");

        return new Auditorium(id, name, numberOfSeats, vipSeats);
    }
}
