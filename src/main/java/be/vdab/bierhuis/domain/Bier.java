package be.vdab.bierhuis.domain;

import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Objects;

public class Bier {
    private long id;
    @NotBlank
    private String naam;
    private long brouwerId;
    private long soortId;
    @NumberFormat (pattern = "0.00")
    private float alcohol;
    @NumberFormat (pattern = "0.00")
    private BigDecimal prijs;
    private long besteld;

    public Bier(long id, @NotBlank String naam, long brouwerId, long soortId, float alcohol, BigDecimal prijs, long besteld) {
        this.id = id;
        this.naam = naam;
        this.brouwerId = brouwerId;
        this.soortId = soortId;
        this.alcohol = alcohol;
        this.prijs = prijs;
        this.besteld = besteld;
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public long getBrouwerId() {
        return brouwerId;
    }

    public long getSoortId() {
        return soortId;
    }

    public float getAlcohol() {
        return alcohol;
    }

    public BigDecimal getPrijs() {
        return prijs;
    }

    public long getBesteld() {
        return besteld;
    }

    @Override
    public String toString() {
        return naam + prijs + besteld;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bier bier = (Bier) o;
        return id == bier.id &&
                brouwerId == bier.brouwerId &&
                soortId == bier.soortId &&
                besteld == bier.besteld &&
                Objects.equals(naam, bier.naam) &&
                Objects.equals(prijs, bier.prijs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, naam, brouwerId, soortId, prijs, besteld);
    }
}
