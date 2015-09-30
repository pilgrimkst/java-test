package com.javatest.refactor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringApplicationConfiguration
@ComponentScan("com.javatest.config")
@EnableAutoConfiguration
@PropertySource(value = {
        "classpath:/service.properties"
})
public class AddressBookApplication {
    public static final Logger logger = LoggerFactory.getLogger(AddressBookApplication.class);

    public static void main(String[] args) {
        logger.info("Starting  application");
        new SpringApplication(AddressBookApplication.class).run(args);
        logger.info("application terminated");
    }
}
