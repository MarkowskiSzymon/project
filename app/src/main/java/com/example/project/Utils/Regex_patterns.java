package com.example.project.Utils;

import android.text.Editable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex_patterns {
    Pattern pattern;
    Matcher matcher;

    public boolean isValidCardNumber(final Editable cardNumber) {
        final String CARDNUMBER_PATTERN = "^([0-9]).{6,7}$";

        pattern = Pattern.compile(CARDNUMBER_PATTERN);
        matcher = pattern.matcher(cardNumber);
        return matcher.matches();
    }

    public boolean isValidCardCode(final Editable cardCode) {
        final String CARDCODE_PATTERN = "^([0-9]).{3,3}$";
        pattern = Pattern.compile(CARDCODE_PATTERN);
        matcher = pattern.matcher(cardCode);
        return matcher.matches();
    }

    public boolean isValidEmail(final Editable email) {
        final String EMAIL_PATTERN = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isValidPhoneNumber(final Editable phone) {
        final String PHONE_PATTERN = "^(?:\\(?\\?)?(?:[-\\.\\(\\)\\s]*(\\d)){9}\\)?$";
        pattern = Pattern.compile(PHONE_PATTERN);
        matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public boolean isValidName(final Editable name) {
        final String NAME_PATTERN = "^[a-zA-Z]+$";
        pattern = Pattern.compile(NAME_PATTERN);
        matcher = pattern.matcher(name);
        return matcher.matches();
    }

    public boolean isValidZipCode(final Editable zipCode) {
        final String ZIPCODE_PATTERN = "^^(?:\\(?\\?)?(?:[-\\.\\(\\)\\s]*(\\d)){5}\\)?$";
        pattern = Pattern.compile(ZIPCODE_PATTERN);
        matcher = pattern.matcher(zipCode);
        return matcher.matches();
    }

    public boolean isPasswordValid(final Editable password) {
        final String PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

}
