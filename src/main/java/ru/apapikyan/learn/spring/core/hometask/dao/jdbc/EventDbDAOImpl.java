package ru.apapikyan.learn.spring.core.hometask.dao.jdbc;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.apapikyan.learn.spring.core.hometask.dao.EventDAO;
import ru.apapikyan.learn.spring.core.hometask.dao.jdbc.mapper.EventRowMapper;
import ru.apapikyan.learn.spring.core.hometask.domain.EventV2;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by Artur_Papikyan on 05.05.2016.
 */
@Repository
public class EventDbDAOImpl extends AbstractDbDAO implements EventDAO {

    @Nullable
    @Override
    public EventV2 create(@Nonnull EventV2 object) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO event(name, rating, baseprice) " +
                "VALUES ( :name, :rating, :baseprice)";

        //TODO INVESTIGATE: WHY KEY COLUMN IS NOT IDENTIFIED AUTOMATICALLY
        getNamedParameterJdbcTemplate().update(sql, getParams(object), keyHolder, new String[] {"id"});

        object.setId(keyHolder.getKey().longValue());

        return object;
    }

    @Nullable
    @Override
    public EventV2 update(@Nonnull EventV2 object) {
        String sql = "UPDATE event SET " +
                "name=:name," +
                "rating=:rating," +
                "baseprice=:baseprice " +
                "WHERE id=:id";

        getNamedParameterJdbcTemplate().update(sql, getParams(object));

        //TODO REFACTOR ABSTRACT DAO TO RETURN VOID ON UPDATE AND DELETE
        return object;
    }

    @Nullable
    @Override
    public EventV2 delete(@Nonnull EventV2 object) {
        String sql = "DELETE FROM event WHERE id= :id";

        getNamedParameterJdbcTemplate().update(sql, new MapSqlParameterSource("id", object.getId()));

        //TODO REFACTOR ABSTRACT DAO TO RETURN VOID ON UPDATE AND DELETE
        return null;
    }

    @Nullable
    @Override
    public EventV2 getById(@Nonnull Long id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);

        String sql = "SELECT * FROM event WHERE id=?";

        EventV2 result = null;
        try {
            result = getNamedParameterJdbcTemplate().queryForObject(sql, params, new EventRowMapper());
        } catch (EmptyResultDataAccessException e) {
            // do nothing, return null
        }

        return result;
    }

    @Nonnull
    @Override
    public Set<EventV2> getAll() {
        String sql = "SELECT * FROM event";

        List<EventV2> records = getNamedParameterJdbcTemplate().query(sql, new EventRowMapper());

        return new HashSet<EventV2>(records);
    }


    private MapSqlParameterSource getParams(@Nonnull EventV2 object) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(EventV2.C_ID, object.getId());
        params.addValue(EventV2.C_NAME, object.getName());
        params.addValue(EventV2.C_RATING, object.getRating().toString());
        params.addValue(EventV2.C_BASE_PRICE, object.getBasePrice());

        return params;
    }
}
