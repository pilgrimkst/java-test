package com.javatest.refactor.service

import com.javatest.refactor.dao.config.ServiceConfig
import org.springframework.boot.test.ConfigFileApplicationContextInitializer
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = ServiceConfig, initializers = ConfigFileApplicationContextInitializer)
class AddressBookTest extends Specification {
    def "test context is up"() {
        expect:
        true
    }
}
