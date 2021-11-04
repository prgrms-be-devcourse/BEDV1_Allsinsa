package com.progm.allsinsa.member.domain;

import com.progm.allsinsa.global.domain.BaseTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name="member")
public class Member extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name="email", length = 50, unique = true, nullable = false)
    String email;

    @Column(name="password", length = 50, nullable = false)
    String password;

    @Column(name="name", length = 20, nullable = false)
    String name;

    public Member(String email, String password, String name) {
        this.email = emailValidateCheck(email);
        this.password = passwordValidateCheck(password);
        this.name = name;

    }

    private String emailValidateCheck(String email) {
        Email validateCheck = new Email(email);
        return validateCheck.getEmail();
    }

    private String passwordValidateCheck(String password) {
        Password validatePassword = new Password(password);
        return validatePassword.getPassword();
    }

    public Boolean loginCheck(String inputPassword) {
        return Objects.equals(inputPassword, this.password);
    }

    public void updateMember(String name, String password) {
        this.name = name;
        this.password = passwordValidateCheck(password);
    }

}
