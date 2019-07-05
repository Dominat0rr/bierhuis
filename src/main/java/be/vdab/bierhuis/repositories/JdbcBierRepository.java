package be.vdab.bierhuis.repositories;

import be.vdab.bierhuis.domain.Bier;
import be.vdab.bierhuis.exceptions.BierNietGevondenException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * @version 1.0
 * @author Dominik Claerman
 *
 */

@Repository
public class JdbcBierRepository implements BierRepository {
    private final JdbcTemplate template;
    private final SimpleJdbcInsert insert;
    private final RowMapper<Bier> bierRowMapper = (result, rowNum) ->
            new Bier(result.getLong("id"), result.getString("naam"),
                result.getLong("brouwerid"), result.getLong("soortid"),
                    result.getFloat("alcohol"), result.getBigDecimal("prijs"),
                    result.getLong("besteld"));

    JdbcBierRepository(JdbcTemplate template) {
        this.template = template;
        this.insert = new SimpleJdbcInsert(template);
        insert.withTableName("bieren");
        insert.usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Bier> findAll() {
        String sql = "select id, naam, brouwerid, soortid, alcohol, prijs, besteld from bieren order by id";
        return template.query(sql, bierRowMapper);
    }

    @Override
    public Optional<Bier> findById(long id) {
        try {
            String sql = "select id, naam, brouwerid, soortid, alcohol, prijs, besteld from bieren where id = ?";
            return Optional.of(template.queryForObject(sql, bierRowMapper, id));
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Bier> findByIds(Set<Long> ids) {
        if (ids.isEmpty()) return Collections.emptyList();
        String sql = "select id, naam, brouwerid, soortid, alcohol, prijs, besteld from bieren where id in (";
        StringBuilder builder = new StringBuilder((sql));
        ids.forEach(id -> builder.append("?,"));
        builder.setCharAt(builder.length() - 1, ')');
        builder.append(" order by id");
        return template.query(builder.toString(), ids.toArray(), bierRowMapper);
    }

    @Override
    public List<Bier> findAllBierenByBrouwerId(long id) {
        String sql = "select id, naam, brouwerid, soortid, alcohol, prijs, besteld from bieren  where brouwerid = ? order by id";
        return template.query(sql, bierRowMapper, id);
    }

    @Override
    public long create(Bier bier) {
        Map<String, Object> kolomWaarden = new HashMap<>();
        kolomWaarden.put("naam", bier.getNaam());
        kolomWaarden.put("brouwerid", bier.getBrouwerId());
        kolomWaarden.put("soortid", bier.getSoortId());
        kolomWaarden.put("alcohol", bier.getAlcohol());
        kolomWaarden.put("prijs", bier.getPrijs());
        kolomWaarden.put("besteld", bier.getBesteld());
        Number id = insert.executeAndReturnKey(kolomWaarden);
        return id.longValue();
    }

    @Override
    public void update(Bier bier) {
        String sql = "update bieren set naam = ?, brouwerid = ?, soortid = ?, alcohol = ?, prijs = ?, besteld = ? where id = ?";
        if (template.update(sql, bier.getNaam(), bier.getBrouwerId(), bier.getSoortId(),
                bier.getAlcohol(), bier.getPrijs(), bier.getBesteld(), bier.getId()) == 0) {
            throw new BierNietGevondenException();
        }
    }

    @Override
    public void updateBesteldAantal(long id, int aantal) {
        String sql = "update bieren set besteld = besteld +  ? where id = ?";
        if (template.update(sql, aantal, id) == 0) {
            throw new BierNietGevondenException();
        }
    }

    @Override
    public void delete(long id) {
        template.update("delete from bieren where id = ?", id);
    }

    @Override
    public long findAantalBieren() {
        return template.queryForObject("select count(*) from bieren", Long.class);
    }

    @Override
    public void bestelBier(long id, int aantal) {
        updateBesteldAantal(id, aantal);
    }
}