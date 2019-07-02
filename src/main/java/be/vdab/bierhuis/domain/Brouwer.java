package be.vdab.bierhuis.domain;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class Brouwer {
    private long id;
    @NotBlank
    private String naam;
    @Valid
    private Adres adres;
    private long omzet;

    public Brouwer(long id, @NotBlank String naam, @Valid Adres adres) {
        this.id = id;
        this.naam = naam;
        this.adres = adres;
    }

    public Brouwer(long id, @NotBlank String naam, @Valid Adres adres, long omzet) {
        this.id = id;
        this.naam = naam;
        this.adres = adres;
        this.omzet = omzet;
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

    public long getOmzet() {
        return omzet;
    }

    @Override
    public String toString() {
        return naam + adres.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brouwer brouwer = (Brouwer) o;
        return id == brouwer.id &&
                omzet == brouwer.omzet &&
                Objects.equals(naam, brouwer.naam) &&
                Objects.equals(adres, brouwer.adres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, naam, adres, omzet);
    }
}
