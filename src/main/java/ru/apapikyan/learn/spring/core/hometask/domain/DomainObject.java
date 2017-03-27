package ru.apapikyan.learn.spring.core.hometask.domain;

import ru.apapikyan.learn.spring.core.hometask.service.AuditoriumService;

/**
 * @author Yuriy_Tkach
 */
public class DomainObject implements AbstractDomainObject {

    public static final String C_ID = "id";

    private Long id;

    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }
}
