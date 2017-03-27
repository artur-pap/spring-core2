package ru.apapikyan.learn.spring.core.hometask.dao.jdbc;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.apapikyan.learn.spring.core.hometask.dao.AuditoriumDAO;
import ru.apapikyan.learn.spring.core.hometask.dao.jdbc.mapper.AuditoriumRowMapper;
import ru.apapikyan.learn.spring.core.hometask.domain.Auditorium;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by Artur_Papikyan on 02.05.2016.
 */
@Repository
public class AuditoriumDbDAOImpl extends AbstractDbDAO implements AuditoriumDAO {

    @Nullable
    @Override
    public Auditorium create(@Nonnull Auditorium object) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO auditorium(name, seats, vipseats) " +
                "VALUES ( :name, :seats, :vipseats)";

        //TODO INVESTIGATE: WHY KEY COLUMN IS NOT IDENTIFIED AUTOMATICALLY
        getNamedParameterJdbcTemplate().update(sql, getParams(object), keyHolder, new String[] {"id"});

        object.setId(keyHolder.getKey().longValue());

        return object;
    }

    @Nullable
    @Override
    public Auditorium update(@Nonnull Auditorium object) {
        String sql = "UPDATE auditorium SET " +
                "name=:name," +
                "seats=:rating," +
                "vipseats=:baseprice " +
                "WHERE id=:id";

        getNamedParameterJdbcTemplate().update(sql, getParams(object));

        //TODO REFACTOR ABSTRACT DAO TO RETURN VOID ON UPDATE AND DELETE
        return object;
    }

    @Nullable
    @Override
    public Auditorium delete(@Nonnull Auditorium object) {
        String sql = "DELETE FROM auditorium WHERE id= :id";

        getNamedParameterJdbcTemplate().update(sql, new MapSqlParameterSource("id", object.getId()));

        //TODO REFACTOR ABSTRACT DAO TO RETURN VOID ON UPDATE AND DELETE
        return null;
    }

    @Nullable
    @Override
    public Auditorium getByName(@Nonnull String name) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", name);

        String sql = "SELECT * FROM auditorium WHERE name=?";

        Auditorium result = null;

        try {
            result = getNamedParameterJdbcTemplate().queryForObject(sql, params, new AuditoriumRowMapper());
        } catch (EmptyResultDataAccessException e) {
            // do nothing, return null
        }

        return result;
    }

    @Nullable
    @Override
    public Auditorium getById(@Nonnull Long id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);

        String sql = "SELECT * FROM auditorium WHERE id=?";

        Auditorium result = null;
        try {
            result = getNamedParameterJdbcTemplate().queryForObject(sql, params, new AuditoriumRowMapper());
        } catch (EmptyResultDataAccessException e) {
            // do nothing, return null
        }

        return result;
    }

    @Nonnull
    @Override
    public Set<Auditorium> getAll() {
        String sql = "SELECT * FROM auditorium";

        List<Auditorium> records = getNamedParameterJdbcTemplate().query(sql, new AuditoriumRowMapper());

        return new HashSet<Auditorium>(records);
    }


    private MapSqlParameterSource getParams(@Nonnull Auditorium object) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        new MapSqlParameterSource();
        params.addValue("id", object.getId());
        params.addValue("name", object.getName());
        params.addValue("seats", object.getNumberOfSeats());
        params.addValue("vipseats", object.getVipSeatsAsString());

        return params;
    }
}
