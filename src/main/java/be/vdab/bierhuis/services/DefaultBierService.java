package be.vdab.bierhuis.services;

import be.vdab.bierhuis.domain.Bier;
import be.vdab.bierhuis.repositories.BierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @version 1.0
 * @author Dominik Claerman
 *
 */

@Service
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
public class DefaultBierService implements BierService {
    private final BierRepository repository;

    DefaultBierService(BierRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Bier> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Bier> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public List<Bier> findByIds(Set<Long> ids) {
        return repository.findByIds(ids);
    }

    @Override
    public List<Bier> findAllBierenByBrouwerId(long id) {
        return repository.findAllBierenByBrouwerId(id);
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
    public long create(Bier bier) {
        return repository.create(bier);
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
    public void update(Bier bier) {
        repository.update(bier);
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
    public void updateBesteldAantal(long id, int aantal) {
        repository.updateBesteldAantal(id, aantal);
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
    public void delete(long id) {
        repository.delete(id);
    }

    @Override
    public long findAantalBieren() {
        return repository.findAantalBieren();
    }

    @Override
    public void bestelBier(long id, int aantal) {
        repository.updateBesteldAantal(id, aantal);
    }
}