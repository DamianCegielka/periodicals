package org.cegielka.periodicals.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String role;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private Collection<User> users;

    public Role(String role) {
        this.role = role;
    }
}