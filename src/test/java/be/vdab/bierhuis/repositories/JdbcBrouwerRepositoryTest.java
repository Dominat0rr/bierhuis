package be.vdab.bierhuis.repositories;

import be.vdab.bierhuis.domain.Adres;
import be.vdab.bierhuis.domain.Brouwer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JdbcBrouwerRepository.class)
@Sql("/insertBrouwer.sql")
public class JdbcBrouwerRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String BROUWERS = "brouwers";
    @Autowired
    private JdbcBrouwerRepository repository;

    private long idVanTestBrouwer() {
        return super.jdbcTemplate.queryForObject("select id from brouwers where naam = 'test'", Long.class);
    }

    @Test
    public void findAll() {
        assertThat(repository.findAll())
                .hasSize(super.countRowsInTable(BROUWERS))
                .extracting(brouwer -> brouwer.getId()).isSorted();
    }

    @Test
    public void findById() {
        assertThat(repository.findById(idVanTestBrouwer()).get().getNaam()).isEqualTo("test");
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
    public void create() {
        long id = repository.create(new Brouwer(0, "test2",
                new Adres("testStraat", "101", "Linden", 3210),
                2500000));
        assertThat(id).isPositive();
        assertThat(super.countRowsInTableWhere(BROUWERS, "id=" + id)).isOne();
    }

    @Test
    public void update() {
        long id = idVanTestBrouwer();
        Brouwer brouwer = new Brouwer(id, "test",
                new Adres("testStraat", "101", "Linden", 3210),
                1);
        repository.update(brouwer);
        assertThat(super.jdbcTemplate.queryForObject(
                "select omzet from brouwers where id = ?", Long.class, id)).isOne();
    }

    @Test
    public void delete() {
        long id = idVanTestBrouwer();
        repository.delete(id);
        assertThat(super.countRowsInTableWhere(BROUWERS, "id=" + id)).isZero();
    }
}
