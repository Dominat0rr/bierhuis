package be.vdab.bierhuis.repositories;

import be.vdab.bierhuis.domain.Adres;
import be.vdab.bierhuis.domain.Brouwer;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
        insert.withTableName("pizzas");
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
            String sql = "select id, naam, id, naam, straat, huisnr, postcode, gemeente, omzet from brouwers where id = ?";
            return Optional.of(template.queryForObject(sql, brouwerRowMapper, id));
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public long create(Brouwer brouwer) {
        //TODO: create has to be implemented
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Brouwer brouwer) {
        //TODO: update has to be implemented
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        template.update("delete from brouwers where id = ?", id);
    }
}
