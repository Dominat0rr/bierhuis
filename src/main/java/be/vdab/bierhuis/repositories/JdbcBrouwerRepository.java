package be.vdab.bierhuis.repositories;

import be.vdab.bierhuis.domain.Brouwer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcBrouwerRepository implements BrouwerRepository {
    @Override
    public List<Brouwer> findAll() {
        //TODO: findAll has to be implemented
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Brouwer> findById(long id) {
        //TODO: findById has to be implemented
        throw new UnsupportedOperationException();
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
        //TODO: delete has to be implemented
        throw new UnsupportedOperationException();
    }
}
