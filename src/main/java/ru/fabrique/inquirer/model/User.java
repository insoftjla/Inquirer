package ru.fabrique.inquirer.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "email", callSuper = false)
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    private Long anonymousId;
    private String username;
    private String email;
    private String password;

    public User(Long anonymousId) {
        this.anonymousId = anonymousId;
    }
}
