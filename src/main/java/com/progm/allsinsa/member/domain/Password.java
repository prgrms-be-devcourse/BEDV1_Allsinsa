package com.progm.allsinsa.member.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Password {

    private final String password;

    public Password(String password) {
        if(!isValidPassword(password))
            throw new IllegalArgumentException("올바르지 않은 Password 형식입니다.");
        this.password = password;
    }

    public static boolean isValidPassword(String password) {
        boolean err = false;
        // 8글자 이상의 숫자, 소문자, 대문자가 포함되어야 한다.
        // 출처 : https://regexr.com/3bfsi
        String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        if(m.matches()) {
            err = true;
        }
        return err;
    }

    public String getPassword() {
        return password;
    }
}
