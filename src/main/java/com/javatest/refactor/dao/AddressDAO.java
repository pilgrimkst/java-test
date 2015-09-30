package com.javatest.refactor.dao;

import com.javatest.refactor.dao.config.AdressSqlRequests;
import com.javatest.refactor.dao.mappers.PersonPreparedStatementSetter;
import com.javatest.refactor.dao.mappers.PersonRowMapper;
import com.javatest.refactor.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

@Component
public class AddressDAO {
    public static final Logger logger = LoggerFactory.getLogger(AddressDAO.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AdressSqlRequests requests;

    @Autowired
    private PersonRowMapper personRowMapper;

    @Cacheable(value = "default", keyGenerator = "keyGenerator", unless = "#result.isPresent==false")
    public Optional<Person> findByName(String name) {
        checkArgument(!StringUtils.isEmpty(name), "Name can't be empty");
        logger.debug("Getting person by name {}", name);
        List<Person> xs = jdbcTemplate.query(requests.getSelectPersonByNameSql(), personRowMapper, name);
        logger.debug("Returned {} persons for query {}", xs, requests.getSelectPersonByNameSql());
        return xs.size() == 1 ? Optional.of(xs.get(0)) : Optional.empty();
    }

    public List<Person> getAll() {
        logger.debug("Getting all persons from database");
        List<Person> query = jdbcTemplate.query(requests.getSelectPersonsAllSql(), personRowMapper);
        logger.debug("Fetched {}", query);
        return query;
    }

    public void addPerson(Person p) {
        checkArgument(p != null, "Person can't be null");
        logger.debug("Inserting person {} to database", p);
        int affected = jdbcTemplate.update(requests.getInsertPersonSql(), new PersonPreparedStatementSetter(p));
        logger.debug("Total rows affected: {}", affected);
    }
}
