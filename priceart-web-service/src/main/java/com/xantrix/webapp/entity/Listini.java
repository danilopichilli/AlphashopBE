package com.xantrix.webapp.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "listini")
@Getter
@Setter
public class Listini implements Serializable {

    private static final long serialVersionUID = 9090314806116297027L;

    @Id
    @Column(name = "ID")
    private String id;

    @Basic
    @Size(min = 10, max = 30, message = "{Size.Listini.descrizione.Validation}")
    private String descrizione;

    @Column(name = "OBSOLETO")
    @Basic
    private String obsoleto;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "listino")
    @JsonManagedReference
    private Set<DettListini> dettListini = new HashSet<>();

    public Listini(String idList, String descrizione, String obsoleto) {
        this.id = idList;
        this.descrizione = descrizione;
        this.obsoleto = obsoleto;
    }

    public Listini() {
    }
}
