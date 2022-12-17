package org.psu.lab5.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BinFile {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String mimeType;

    @Lob
    @Column(nullable = false)
    @JsonIgnore
    private byte[] data;
}
