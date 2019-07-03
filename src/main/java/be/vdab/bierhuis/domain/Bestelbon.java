package be.vdab.bierhuis.domain;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

public class Bestelbon {
    private final long id;
    @NotBlank
    private final String naam;
    @Valid
    private final Adres adres;

    public Bestelbon(long id, String naam, @Valid Adres adres) {
        this.id = id;
        this.naam = naam;
        this.adres = adres;
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public Adres getAdres() {
        return adres;
    }
}