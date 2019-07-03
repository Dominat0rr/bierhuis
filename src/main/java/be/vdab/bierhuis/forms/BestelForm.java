package be.vdab.bierhuis.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class BestelForm {
    @NotNull(message = "Aantal mag niet leeg zijn")
    @Positive(message = "Aantal moet groter als 0 zijn")
    private Integer aantal;

    public BestelForm(Integer aantal) {
        this.aantal = aantal;
    }

    public Integer getAantal() {
        return aantal;
    }
}
