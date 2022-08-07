package org.cegielka.periodicals.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="subscriptions")
@Data
@NoArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable= false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "publication_id",nullable= false)
    private Publication publication;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime date;


    public Subscription(User user, Publication publication, LocalDateTime date) {
        this.user = user;
        this.publication = publication;
        this.date = date;
    }
}
