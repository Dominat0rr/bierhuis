package be.vdab.bierhuis.repositories;

import be.vdab.bierhuis.domain.Bier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcBierRepository implements BierRepository {
    @Override
    public List<Bier> findAll() {
        //TODO: findAll has to be implemented
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Bier> findById(long id) {
        //TODO: findById has to be implemented
        throw new UnsupportedOperationException();
    }

    @Override
    public long create(Bier bier) {
        //TODO: create has to be implemented
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Bier bier) {
        //TODO: update has to be implemented
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        //TODO: delete has to be implemented
        throw new UnsupportedOperationException();
    }

    @Override
    public long findAantalBieren() {
        //TODO: findAantalBieren has to be implemented
        throw new UnsupportedOperationException();
    }
}
