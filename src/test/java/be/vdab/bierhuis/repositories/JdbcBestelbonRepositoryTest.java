package be.vdab.bierhuis.repositories;

import be.vdab.bierhuis.forms.BestelbonForm;
import be.vdab.bierhuis.services.BierService;
import be.vdab.bierhuis.sessions.Mandje;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @version 1.0
 * @author Dominik Claerman
 *
 */

@RunWith(SpringRunner.class)
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({ JdbcBestelbonRepository.class, JdbcBierRepository.class })
@Sql("/insertBestelbonnen.sql")
@Sql("/insertBieren.sql")
public class JdbcBestelbonRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String BESTELBONNEN = "bestelbonnen";
    private static final String BESTELBONLIJNEN = "bestelbonlijnen";
    @Autowired
    private JdbcBestelbonRepository repository;
    @Autowired
    private JdbcBierRepository bierRepository;

    private long idVanTestBestelbon() {
        return super.jdbcTemplate.queryForObject("select id from bestelbonnen where naam = 'test'", Long.class);
    }

    private long idVanTestBier() {
        return super.jdbcTemplate.queryForObject("select id from bieren where naam='test'", Long.class);
    }

//    @Test
//    public void create() {
//        int aantalBestelbonnen = super.countRowsInTable(BESTELBONNEN);
//        int aantalBestelbonLijnen = super.countRowsInTable(BESTELBONLIJNEN);
//        BestelbonForm form = new BestelbonForm("andre", "testStraat", "205", "Lubbeek", 1000);
//        Mandje mandje = new Mandje();
//        long idBier = idVanTestBier();
//        mandje.voegToe(idBier, 10);
//        long id = repository.create(form, mandje);
//        assertNotEquals(0, id);
//        assertEquals(aantalBestelbonnen + 1, super.countRowsInTable(BESTELBONNEN));
//        assertEquals(1, super.countRowsInTableWhere(BESTELBONNEN, "id=" + id));
//        assertEquals(aantalBestelbonLijnen + 1, super.countRowsInTable(BESTELBONLIJNEN));
//    }
}