package com.xantrix.webapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "INGREDIENTI")
@Data
public class Ingredienti implements Serializable {

    private static final long serialVersionUID = -6597932485001138522L;

    @Id
    @Column(name = "CODART")
    private String codArt;

    @Column(name = "INFO")
    private String info;

    @OneToOne
    @PrimaryKeyJoinColumn //si basa sull'intersezione delle due chiavi primarie
    @JsonIgnore // e' equivalente al JsonBackReference
    private Articoli articoli;
}
