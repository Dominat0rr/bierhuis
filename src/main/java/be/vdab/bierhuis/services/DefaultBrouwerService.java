package be.vdab.bierhuis.services;

import be.vdab.bierhuis.domain.Brouwer;
import be.vdab.bierhuis.repositories.BrouwerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
public class DefaultBrouwerService implements BrouwerService {
    private BrouwerRepository repository;

    DefaultBrouwerService(BrouwerRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Brouwer> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Brouwer> findById(long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
    public long create(Brouwer brouwer) {
        return repository.create(brouwer);
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
    public void update(Brouwer brouwer) {
        repository.update(brouwer);
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
    public void delete(long id) {
        repository.delete(id);
    }
}
