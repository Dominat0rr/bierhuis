package be.vdab.bierhuis.repositories;

import be.vdab.bierhuis.domain.Brouwer;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @version 1.0
 * @author Dominik Claerman
 *
 */

public interface BrouwerRepository {
    List<Brouwer> findAll();
    Optional<Brouwer> findById(long id);
    List<Brouwer> findByIds(Set<Long> ids);
    long create(Brouwer brouwer);
    void update(Brouwer brouwer);
    void delete(long id);
}
