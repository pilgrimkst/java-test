package com.javatest.refactor;

public class PhoneNumber {
    private Num number;

    public PhoneNumber(String number) {
        this.number = new Num(number);
    }

    public String getNumber() {
        return number.getNumber();
    }

}
