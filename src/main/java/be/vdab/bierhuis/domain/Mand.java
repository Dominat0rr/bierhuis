package be.vdab.bierhuis.domain;

import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Mand {
    private final Map<Bier, Integer> bieren = new HashMap<>();

    public void add(Bier bier, int aantal) {
        bieren.put(bier, aantal);
    }

    public Map<Bier, Integer> getBieren() {
        return Collections.unmodifiableMap(bieren);
    }

    @NumberFormat(pattern = "0.00")
    public BigDecimal getPrijs() {
        BigDecimal prijs = BigDecimal.ZERO;

        for (Map.Entry<Bier, Integer> entry : bieren.entrySet()) {
            Bier key = entry.getKey();
            Integer value = entry.getValue();

            BigDecimal prijsPerSoort = key.getPrijs().multiply(BigDecimal.valueOf(value));
            prijs.add(prijsPerSoort);
        }
        return prijs;
    }

    public boolean isLeeg() {
        return bieren.isEmpty();
    }
}
