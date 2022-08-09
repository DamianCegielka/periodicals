package org.cegielka.periodicals.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "publications")
@Getter
@Setter
@NoArgsConstructor
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 45)
    private String title;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false, length = 45)
    private String topic;

    @ManyToOne
    @JoinColumn(name = "accumulation_id")
    private Accumulation accumulation;

    @Column
    String Description;

    public Publication(String title, Long price, String topic) {
        this.title = title;
        this.price = price;
        this.topic = topic;
    }
}
