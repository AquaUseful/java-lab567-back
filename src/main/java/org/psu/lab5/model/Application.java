package org.psu.lab5.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Application {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String doctorName;

    @Column(nullable = false)
    private String service;

    @OneToOne(optional = true)
    @JoinColumn(name = "file_id")
    @JsonIgnore
    private BinFile attachment;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;
}
