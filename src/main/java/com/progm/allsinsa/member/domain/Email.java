package com.progm.allsinsa.member.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Email {

    private final String email;

    public Email(String email) {
        if(!isValidEmail(email))
            throw new IllegalArgumentException("올바르지 않은 Email 형식입니다."+email);
        this.email = email;
    }

    public static boolean isValidEmail(String email) {
        boolean err = false;
        // RFC2822 패턴 참고
        // 출처 : https://regexr.com/2rhq7
        String regex = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.matches()) {
            err = true;
        }
        return err;
    }

    public String getEmail() {
        return email;
    }
}
