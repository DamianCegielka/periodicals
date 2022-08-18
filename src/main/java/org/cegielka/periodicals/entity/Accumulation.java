package org.cegielka.periodicals.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "accumulations")
@Getter
@Setter
@NoArgsConstructor
public class Accumulation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String accumulation;

    @OneToMany(mappedBy = "accumulation")
    private Collection<Publication> publications;


}