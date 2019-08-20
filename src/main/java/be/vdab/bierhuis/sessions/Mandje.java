package be.vdab.bierhuis.sessions;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.util.*;

/**
 * @version 1.0
 * @author Dominik Claerman
 *
 */

@Component
@SessionScope
public class Mandje implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<Long, Integer> bieren = new LinkedHashMap<>(); // <id, aantal>

    public void voegToe(long id, int aantal) {
        if (this.bevat(id)) {
            bieren.put(id, bieren.get(id) + aantal);
        }
        else bieren.put(id, aantal);
    }

    public boolean bevat(long id) {
        return bieren.containsKey(id);
    }

    public boolean isGevuld() {
        return !bieren.isEmpty();
    }

    public boolean isLeeg() {
        return bieren.isEmpty();
    }

    public Optional<Integer> getAantal(long id) {
        return Optional.ofNullable(bieren.get(id));
    }

    public Set<Long> getIds() {
        return bieren.keySet();
    }

    public void maakLeeg() {
        bieren.clear();
    }

    public Map<Long, Integer> getBieren() {
        return Collections.unmodifiableMap(bieren);
    }

    public void verwijder(long id) {
        bieren.remove(id);
    }
}
