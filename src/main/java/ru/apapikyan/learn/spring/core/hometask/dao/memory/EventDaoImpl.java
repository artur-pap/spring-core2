package ru.apapikyan.learn.spring.core.hometask.dao.memory;

import org.springframework.stereotype.Repository;
import ru.apapikyan.learn.spring.core.hometask.dao.EventDAO;
import ru.apapikyan.learn.spring.core.hometask.domain.EventRating;
import ru.apapikyan.learn.spring.core.hometask.domain.EventV2;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Artur_Papikyan on 20.04.2016.
 */
@Repository
public class EventDAOImpl implements EventDAO {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Resource(name = "eventsMap")
    private Map<Long, EventV2> records;

    private List<Properties> eventsList;

    private EventDAOImpl() {
    }

    public EventDAOImpl(List<Properties> eventProps) {
        for (Properties props : eventProps) {
            String name = props.getProperty(EventV2.C_NAME);
            EventRating rating = EventRating.valueOf(props.getProperty(EventV2.C_RATING));

            Double dBasePrice = Double.parseDouble(props.getProperty(EventV2.C_BASE_PRICE));
            Double basePrice = Double.valueOf(dBasePrice);

            this.create(new EventV2(name, rating, basePrice));
        }
    }

    @Nullable
    @Override
    public EventV2 create(@Nonnull EventV2 object) {
        object.setId(generateId());

        records.put(object.getId(), object);

        return this.getById(object.getId());
    }

    @Nullable
    @Override
    public EventV2 update(@Nonnull EventV2 object) {
        if (!records.containsValue(object) || object.getId() == null) {
            System.out.println(String.format("ERROR! Can't update NON existing object [%s]\r\nCreate First!", object));
            return null;
        }

        records.put(object.getId(), object);

        return this.getById(object.getId());
    }

    @Nullable
    @Override
    public EventV2 delete(@Nonnull EventV2 object) {
        if (!records.containsValue(object) || object.getId() == null) {
            System.out.println(String.format("ERROR! Can't delete NON existing object [%s]\r\nCreate First!", object));
            return null;
        }

        return records.remove(object.getId());
    }

    @Nullable
    @Override
    public EventV2 getById(@Nonnull Long id) {
        return records.get(id);
    }

    @Nullable
    @Override
    public Set<EventV2> getAll() {
        return new HashSet<EventV2>(records.values());
    }

    private Long generateId() {
        Set<Long> ids = records.keySet();
        if (ids.isEmpty()) {
            return 1L;
        }
        return Collections.max(ids) + 1;
    }
}
