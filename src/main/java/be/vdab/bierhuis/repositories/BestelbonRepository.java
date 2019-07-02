package be.vdab.bierhuis.repositories;

import be.vdab.bierhuis.domain.Bestelbon;
import be.vdab.bierhuis.domain.Mand;

public interface BestelbonRepository {
    long create(Bestelbon bestelbon, Mand mand);
}
