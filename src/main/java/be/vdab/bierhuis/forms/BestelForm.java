package be.vdab.bierhuis.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class BestelForm {
    @NotNull
    @Positive
    private Integer aantal;

    public BestelForm(Integer aantal) {
        this.aantal = aantal;
    }

    public Integer getAantal() {
        return aantal;
    }
}
