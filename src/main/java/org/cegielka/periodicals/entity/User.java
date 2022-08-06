package org.cegielka.periodicals.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 45)
    private String email;
    @Column(length = 45)
    private String password;

    private String repeatPassword;
    @Column
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Collection<Subscription> subscriptions;

    public User(String email, String password, Boolean active) {
        this.email = email;
        this.password = password;
        this.active = active;
    }
}
