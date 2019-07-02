package be.vdab.bierhuis.repositories;

import be.vdab.bierhuis.domain.Bier;

import java.util.List;
import java.util.Optional;

public interface BierRepository {
    List<Bier> findAll();
    Optional<Bier> findById(long id);
    List<Bier> findAllByBrouwerId(long id);
    long create(Bier bier);
    void update(Bier bier);
    void updateBesteldAantal(Bier bier);
    void delete(long id);
    long findAantalBieren();
}
