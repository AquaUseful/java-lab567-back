package org.psu.lab5.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

public class Comment {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String content;


}
