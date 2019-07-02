package be.vdab.bierhuis.domain;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class Adres {
    @NotBlank
    private String straat;
    @NotBlank
    private String huisnummer;
    @NotBlank
    private String gemeente;
    @Range(min = 1000, max = 9999)
    private int postcode;

    public Adres(String straat, String huisnummer, String gemeente, int postcode) {
        this.straat = straat;
        this.huisnummer = huisnummer;
        this.gemeente = gemeente;
        this.postcode = postcode;
    }

    public String getStraat() {
        return straat;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public String getGemeente() {
        return gemeente;
    }

    public int getPostcode() {
        return postcode;
    }

    public String getAdres() {
        return straat + ' ' + huisnummer + ' ' + gemeente + ' ' + postcode;
    }

    @Override
    public String toString() {
        return straat + huisnummer + gemeente + postcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adres adres = (Adres) o;
        return postcode == adres.postcode &&
                Objects.equals(straat, adres.straat) &&
                Objects.equals(huisnummer, adres.huisnummer) &&
                Objects.equals(gemeente, adres.gemeente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(straat, huisnummer, gemeente, postcode);
    }
}