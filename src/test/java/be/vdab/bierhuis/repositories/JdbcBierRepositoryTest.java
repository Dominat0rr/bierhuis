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

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JdbcBierRepository.class)
@Sql("/insertBieren.sql")
public class JdbcBierRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String BIEREN = "bieren";
    @Autowired
    private JdbcBierRepository repository;

    private long idVanTestBier() {
        return super.jdbcTemplate.queryForObject("select id from bieren where naam = 'test'", Long.class);
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
    public void findAllByBrouwerId() {
        assertThat(repository.findAllByBrouwerId(1))
                .hasSize(super.countRowsInTable(BIEREN))
                .extracting(bier -> bier.getId()).isSorted();
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
        // TODO
    }

    @Test
    public void delete() {
        long id = idVanTestBier();
        repository.delete(id);
        assertThat(super.countRowsInTableWhere(BIEREN, "id=" + id)).isZero();
    }

    @Test
    public void findAantalBieren() {
        // TODO:
    }
}
