package com.xantrix.webapp.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ARTICOLI")
@Getter
@Setter
public class Articoli implements Serializable {


    private static final long serialVersionUID = 291353626011036772L;

    @Id
    @Column(name="CODART")
    @Size( min = 5, max = 20, message = "{Size.Articoli.codArt.Validation}")
    @NotNull( message = "{NotNull.Articoli.codArt.Validation}")
    private String codArt;

    @Column(name = "DESCRIZIONE")
    @Size( min = 6, max = 80, message = "{Size.Articoli.descrizione.Validation}")
    @NotNull( message = "{NotNull.Articoli.descrizione.Validation}")
    private String descrizione;

    @Column(name = "UM")
    @NotNull(message = "{NotNull.Articoli.um.Validation}")
    private String um;

    @Column(name = "CODSTAT")
    private String codStat;

    @Column(name = "PZCART")
    @NotNull(message = "{NotNull.Articoli.pzCart.Validation}")
    @Max(value = 99, message = "{Max.Articoli.pzCart.Validation}")
    private Integer pzCart;

    @Column(name = "PESONETTO")
    @DecimalMin(value = "0.01",message = "{Min.Articoli.pesoNetto.Validation}")
    @DecimalMax(value = "100", message = "{Max.Articoli.pesoNetto.Validation}")
    private Double pesoNetto;

    @Column(name = "IDSTATOART")
    @NotNull(message = "{NotNull.Articoli.idStatoArt.Validation}")
    private String idStatoArt;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATACREAZIONE")
    private Date dataCreaz;

    @Transient // significa che questo campo e svincolato dalle colonne della tabella articoli
    private BigDecimal prezzo;

    @Transient
    private BigDecimal promo;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "articoli", orphanRemoval = true) //orphanRemoval in caso di eliminazione degli articoli questo si ripercuoterà sulle classi collegate
    @JsonManagedReference // serve al codificatore JSON perche' quando si leggono i dati nel db questi vengono serializzati in formato JSON
    private Set<Barcode> barcode = new HashSet<>();

    @OneToOne(mappedBy = "articoli", cascade = CascadeType.ALL, orphanRemoval = true)
    private Ingredienti ingredienti;

    @ManyToOne
    @JoinColumn(name = "IDIVA", referencedColumnName = "idIva")
    private Iva iva;

    @ManyToOne
    @JoinColumn(name = "IDFAMASS", referencedColumnName = "id")
    @NotNull(message = "{NotNull.Articoli.famAssort.Validation}")
    private FamAssort famAssort;
}
