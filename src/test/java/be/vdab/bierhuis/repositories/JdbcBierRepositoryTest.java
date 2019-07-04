package be.vdab.bierhuis.repositories;

import be.vdab.bierhuis.domain.Bier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JdbcBierRepository.class)
@Sql("/insertBrouwer.sql")
@Sql("/insertBieren.sql")
public class JdbcBierRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String BIEREN = "bieren";
    @Autowired
    private JdbcBierRepository repository;

    private long idVanTestBier() {
        return super.jdbcTemplate.queryForObject("select id from bieren where naam = 'test'", Long.class);
    }

    private long idVanTestBrouwer() {
        return super.jdbcTemplate.queryForObject("select id from brouwers where naam='test'", Long.class);
    }

    @Test
    public void findAll() {
        assertThat(repository.findAll())
                .hasSize(super.countRowsInTable(BIEREN))
                .extracting(bier -> bier.getId()).isSorted();
    }

    @Test
    public void findById() {
        assertThat(repository.findById(idVanTestBier()).get().getNaam()).isEqualTo("test");
    }

    @Test
    public void findByOnbestaandeId() {
        assertThat(repository.findById(-1)).isEmpty();
    }

    @Test
    public void findByIdsMetLegeVerzamelingIdsMoetLegeVerzamelingBierenTerugGeven() {
        assertThat(repository.findByIds(Collections.emptySet()).isEmpty());
    }

    @Test
    public void findAllBierenByBrouwerId() {
        long idBrouwer = idVanTestBrouwer();
        List<Bier> bieren = repository.findAllBierenByBrouwerId(idBrouwer);
        assertEquals(super.countRowsInTableWhere(BIEREN, "brouwerid=" + idBrouwer), bieren.size());
        bieren.stream().map(bier -> bier.getNaam()).reduce((vorigeNaam, naam) -> {
            assertTrue(naam.compareToIgnoreCase(vorigeNaam) >= 0);
            return naam;
        });
    }

    @Test
    public void findAllBierenByOnbestaandBrouwerId() {
        assertThat(repository.findAllBierenByBrouwerId(-1)).isEmpty();
    }

    @Test
    public void create() {
        long id = repository.create(new Bier(0, "test2", 6, 2, 4.3F, BigDecimal.ONE, 5));
        assertThat(id).isPositive();
        assertThat(super.countRowsInTableWhere(BIEREN, "id=" + id)).isOne();
    }

    @Test
    public void update() {
        long id = idVanTestBier();
        Bier bier = new Bier(id, "test", 1, 2, 7.4F, BigDecimal.ONE, 0);
        repository.update(bier);
        assertThat(super.jdbcTemplate.queryForObject(
                "select prijs from bieren where id = ?", BigDecimal.class, id)).isOne();
    }

    @Test
    public void updateBesteldAantal() {
        long id = idVanTestBier();
        Bier bier = new Bier(id, "test", 1, 2, 7.4F, BigDecimal.valueOf(22), 1);
        repository.update(bier);
        assertThat(super.jdbcTemplate.queryForObject(
                "select besteld from bieren where id = ?", Long.class, id)).isOne();
    }

    @Test
    public void delete() {
        long id = idVanTestBier();
        repository.delete(id);
        assertThat(super.countRowsInTableWhere(BIEREN, "id=" + id)).isZero();
    }

    @Test
    public void findAantalBieren() {
        assertThat(super.countRowsInTable(BIEREN)).isEqualTo(repository.findAantalBieren());
    }

    @Test
    public void bestelBier() {
        long id = idVanTestBier();
        repository.bestelBier(id, 10);
        assertEquals(10, super.jdbcTemplate
                .queryForObject("select besteld from bieren where id=" + id, Long.class).longValue());
    }
}
