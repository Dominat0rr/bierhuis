package be.vdab.bierhuis.services;

import be.vdab.bierhuis.domain.Brouwer;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BrouwerService {
    List<Brouwer> findAll();
    Optional<Brouwer> findById(long id);
    List<Brouwer> findByIds(Set<Long> ids);
    long create(Brouwer brouwer);
    void update(Brouwer brouwer);
    void delete(long id);
}
