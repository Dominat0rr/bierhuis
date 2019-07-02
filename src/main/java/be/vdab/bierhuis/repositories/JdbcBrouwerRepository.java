package be.vdab.bierhuis.repositories;

import be.vdab.bierhuis.domain.Adres;
import be.vdab.bierhuis.domain.Brouwer;
import be.vdab.bierhuis.exceptions.BrouwerNietGevondenException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class JdbcBrouwerRepository implements BrouwerRepository {
    private final JdbcTemplate template;
    private final SimpleJdbcInsert insert;
    private final RowMapper<Brouwer> brouwerRowMapper = (result, rowNum) ->
            new Brouwer(result.getLong("id"), result.getString("naam"),
                    new Adres(result.getString("straat"), result.getString("huisNr"),
                            result.getString("gemeente"), result.getInt("postcode")),
                    result.getLong("omzet"));

    JdbcBrouwerRepository(JdbcTemplate template) {
        this.template = template;
        this.insert = new SimpleJdbcInsert(template);
        insert.withTableName("brouwers");
        insert.usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Brouwer> findAll() {
        String sql = "select id, naam, straat, huisNr, postcode, gemeente, omzet from brouwers order by id";
        return template.query(sql, brouwerRowMapper);
    }

    @Override
    public Optional<Brouwer> findById(long id) {
        try {
            String sql = "select id, naam, straat, huisnr, postcode, gemeente, omzet from brouwers where id = ?";
            return Optional.of(template.queryForObject(sql, brouwerRowMapper, id));
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Brouwer> findByIds(Set<Long> ids) {
        if (ids.isEmpty()) return Collections.emptyList();
        String sql = "select id, naam, straat, huisnr, postcode, gemeente, omzet from brouwers where id in (";
        StringBuilder builder = new StringBuilder((sql));
        ids.forEach(id -> builder.append("?,"));
        builder.setCharAt(builder.length() - 1, ')');
        builder.append(" order by id");
        return template.query(builder.toString(), ids.toArray(), brouwerRowMapper);
    }

    @Override
    public long create(Brouwer brouwer) {
        Map<String, Object> kolomWaarden = new HashMap<>();
        kolomWaarden.put("naam", brouwer.getNaam());
        kolomWaarden.put("straat", brouwer.getAdres().getStraat());
        kolomWaarden.put("huisNr", brouwer.getAdres().getHuisnummer());
        kolomWaarden.put("postcode", brouwer.getAdres().getPostcode());
        kolomWaarden.put("gemeente", brouwer.getAdres().getGemeente());
        kolomWaarden.put("omzet", brouwer.getOmzet());
        Number id = insert.executeAndReturnKey(kolomWaarden);
        return id.longValue();
    }

    @Override
    public void update(Brouwer brouwer) {
        String sql = "update brouwers set naam = ?, straat = ?, huisNr = ?, postcode = ?, gemeente = ?, omzet = ? where id = ?";
        if (template.update(sql, brouwer.getNaam(), brouwer.getAdres().getStraat(), brouwer.getAdres().getHuisnummer(),
                brouwer.getAdres().getPostcode(), brouwer.getAdres().getGemeente(), brouwer.getOmzet(), brouwer.getId()) == 0) {
            throw new BrouwerNietGevondenException();
        }
    }

    @Override
    public void delete(long id) {
        template.update("delete from brouwers where id = ?", id);
    }
}
