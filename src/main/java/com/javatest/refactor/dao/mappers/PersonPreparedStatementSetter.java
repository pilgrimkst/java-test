package com.javatest.refactor.dao.mappers;

import com.javatest.refactor.model.Person;
import org.springframework.jdbc.core.PreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PersonPreparedStatementSetter implements PreparedStatementSetter {
    private final Person person;

    public PersonPreparedStatementSetter(Person person) {
        this.person = person;
    }

    @Override
    public void setValues(PreparedStatement ps) throws SQLException {
        ps.setLong(1, System.currentTimeMillis());
        ps.setString(2, person.getName());
        ps.setString(3, person.getPhoneNumber());
    }
}
