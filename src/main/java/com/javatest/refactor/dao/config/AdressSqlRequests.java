package com.javatest.refactor.dao.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "database.address.queries")
public class AdressSqlRequests {
    private String selectPersonsAllSql;
    private String selectPersonByNameSql;
    private String insertPersonSql;

    public String getSelectPersonsAllSql() {
        return selectPersonsAllSql;
    }

    public void setSelectPersonsAllSql(String selectPersonsAllSql) {
        this.selectPersonsAllSql = selectPersonsAllSql;
    }

    public String getSelectPersonByNameSql() {
        return selectPersonByNameSql;
    }

    public void setSelectPersonByNameSql(String selectPersonByNameSql) {
        this.selectPersonByNameSql = selectPersonByNameSql;
    }

    public String getInsertPersonSql() {
        return insertPersonSql;
    }

    public void setInsertPersonSql(String insertPersonSql) {
        this.insertPersonSql = insertPersonSql;
    }
}
