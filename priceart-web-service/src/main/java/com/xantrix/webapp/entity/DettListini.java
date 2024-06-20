package com.xantrix.webapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "dettlistini")
@Getter
@Setter
public class DettListini implements Serializable {

    private static final long serialVersionUID = 4688382639095523901L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Size(min = 5, max = 20, message ="{Size.DettListini.codArt.Validation}")
    @NotNull(message = "{NotNull.DettListini.codArt.Validation}")
    @Column(name = "CODART")
    private String codArt;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "IDLIST" , referencedColumnName = "id")
    @JsonBackReference
    private Listini listino;

    @Column(name = "PREZZO")
    @DecimalMin(value = "0.01", message = "{DecimalMin.DettListini.prezzo.Validation}")
    private BigDecimal prezzo;

    public DettListini(String codArt, BigDecimal prezzo, Listini listino) {
        this.codArt = codArt;
        this.prezzo = prezzo;
        this.listino = listino;
    }

    public DettListini() {
    }
}
