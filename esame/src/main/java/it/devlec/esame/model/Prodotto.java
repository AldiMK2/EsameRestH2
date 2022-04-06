package it.devlec.esame.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Prodotto {
    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    private Date dataDiAcquisto;
    private float prezzo;
    private float ranking;

    public Prodotto() {

    }

    public Prodotto (String nome, Date dataDiAcquisto, Float prezzo) {
        this.nome = nome;
        this.dataDiAcquisto=dataDiAcquisto;
        this.prezzo=prezzo;
    }
    public Prodotto (String nome, Float prezzo) {
        this.nome = nome;
        this.dataDiAcquisto=new Date();
        this.prezzo=prezzo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataDiAcquisto() {
        return dataDiAcquisto;
    }

    public void setDataDiAcquisto(Date dataDiAcquisto) {
        this.dataDiAcquisto = dataDiAcquisto;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    public float getRanking() {
        return ranking;
    }

    public void setRanking(float ranking) {
        this.ranking = ranking;
    }
}