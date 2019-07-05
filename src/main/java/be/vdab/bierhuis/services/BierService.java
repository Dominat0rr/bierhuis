package be.vdab.bierhuis.services;

import be.vdab.bierhuis.domain.Bier;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @version 1.0
 * @author Dominik Claerman
 *
 */

public interface BierService {
    List<Bier> findAll();
    Optional<Bier> findById(long id);
    List<Bier> findByIds(Set<Long> ids);
    List<Bier> findAllBierenByBrouwerId(long id);
    long create(Bier bier);
    void update(Bier bier);
    void updateBesteldAantal(long id, int aantal);
    void delete(long id);
    long findAantalBieren();
    void bestelBier(long id, int aantal);
}
