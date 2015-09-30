package com.javatest.refactor.service;

import com.javatest.refactor.dao.AddressDAO;
import com.javatest.refactor.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class AddressBook {

    public static final String PHONE_CODE = "070";

    @Autowired
    private AddressDAO addressDAO;

    public boolean hasMobile(String name) {
        return addressDAO
                .findByName(name)
                .map(p -> isMobilePhone(p.getPhoneNumber()))
                .orElse(false);
    }


    public int getSize() {
        return addressDAO.getAll().size();
    }

    /**
     * Gets the given user's mobile phone number,
     * or null if he doesn't have one.
     */
    public String getMobile(String name) {
        return addressDAO
                .findByName(name)
                .map(Person::getPhoneNumber)
                .orElse(null);
    }

    /**
     * Returns all names in the book truncated to the given length.
     */
    public List<String> getNames(int maxLength) {
        return addressDAO
                .getAll()
                .stream()
                .map(p -> truncateName(p, maxLength))
                .collect(toList());
    }

    /**
     * Returns all people who have mobile phone numbers.
     */
    public List getList() {
        return addressDAO
                .getAll()
                .stream()
                .filter(p -> isMobilePhone(p.getPhoneNumber()))
                .collect(toList());
    }

    private String truncateName(Person p, int maxLength) {
        String name = p.getName();
        if (name.length() > maxLength) {
            name = name.substring(0, maxLength);
        }
        return name;
    }

    private boolean isMobilePhone(String phoneNumber) {
        return phoneNumber.startsWith(PHONE_CODE);
    }

    public void setAddressDAO(AddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }
}
