package com.aukusto.book;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "BOOK")
public class BookEntity extends PanacheEntity {

    public String title;
    public String author;
    public int publicatedOn;
}
