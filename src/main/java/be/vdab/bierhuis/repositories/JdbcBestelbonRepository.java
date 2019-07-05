package be.vdab.bierhuis.repositories;

import be.vdab.bierhuis.domain.Bier;
import be.vdab.bierhuis.forms.BestelbonForm;
import be.vdab.bierhuis.services.BierService;
import be.vdab.bierhuis.sessions.Mandje;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @version 1.0
 * @author Dominik Claerman
 *
 */

@Repository
public class JdbcBestelbonRepository implements BestelbonRepository {
    private final SimpleJdbcInsert insertBestelbon;
    private final SimpleJdbcInsert insertBestelbonLijn;
    private final BierService bierService;

    JdbcBestelbonRepository(JdbcTemplate template, BierService bierService) {
        this.insertBestelbon = new SimpleJdbcInsert(template);
        insertBestelbon.withTableName("bierhuis.bestelbonnen");
        insertBestelbon.usingGeneratedKeyColumns("id");
        this.insertBestelbonLijn = new SimpleJdbcInsert(template);
        insertBestelbonLijn.withTableName("bierhuis.bestelbonlijnen");
        this.bierService = bierService;
    }

    @Override
    public long create(BestelbonForm form, Mandje mandje) {
        Map<String, Object> kolomWaarden = new HashMap<>();
        kolomWaarden.put("naam", form.getNaam());
        kolomWaarden.put("straat", form.getStraat());
        kolomWaarden.put("huisNr", form.getHuisnummer());
        kolomWaarden.put("postcode", form.getPostcode());
        kolomWaarden.put("gemeente", form.getGemeente());
        long bestelbonId = insertBestelbon.executeAndReturnKey(kolomWaarden).longValue();
        for (Map.Entry<Long, Integer> entry : mandje.getBieren().entrySet()) {
            Long id = entry.getKey();
            Integer aantal = entry.getValue();
            Optional<Bier> bier = bierService.findById(id);
            BigDecimal prijs = bier.get().getPrijs();
            createBestelbonLijn(bestelbonId, id, aantal, prijs);
        }
        return bestelbonId;
    }

    private void createBestelbonLijn(long bestelbonId, long bierId, int aantal, BigDecimal prijs) {
        Map<String, Object> kolomWaarden = new HashMap<>();
        kolomWaarden.put("bestelbonid", bestelbonId);
        kolomWaarden.put("bierid", bierId);
        kolomWaarden.put("aantal", aantal);
        kolomWaarden.put("prijs", prijs);
        insertBestelbonLijn.execute(kolomWaarden);
        bierService.updateBesteldAantal(bierId, aantal);
    }
}