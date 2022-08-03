package org.cegielka.periodicals.entity;

import javax.persistence.*;

@Entity
@Table(name="publications")
public class Publication {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length=45)
    private String title;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false, length=45)
    private String topic;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Publication(){}
    public Publication(String title, Long price, String topic) {
        this.title= title;
        this.price = price;
        this.topic = topic;
    }
}
