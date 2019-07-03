package be.vdab.bierhuis.services;

import be.vdab.bierhuis.forms.BestelbonForm;
import be.vdab.bierhuis.repositories.BestelbonRepository;
import be.vdab.bierhuis.sessions.Mandje;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
public class DefaultBestelbonService implements BestelbonService {
    private final BestelbonRepository repository;

    DefaultBestelbonService(BestelbonRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
    public long create(BestelbonForm bestelbonForm, Mandje mandje) {
        return repository.create(bestelbonForm, mandje);
    }
}
