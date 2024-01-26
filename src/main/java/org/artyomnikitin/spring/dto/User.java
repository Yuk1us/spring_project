package org.artyomnikitin.spring.dto;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor

@Getter
@Setter
@ToString


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String login;
    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public boolean equals(User user) {
        return this.getLogin().equals(user.getLogin()) &&
               this.getPassword().equals(user.getPassword());
    }

}
