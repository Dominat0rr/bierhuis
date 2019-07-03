package be.vdab.bierhuis.repositories;

import be.vdab.bierhuis.domain.Bestelbon;
import be.vdab.bierhuis.forms.BestelbonForm;
import be.vdab.bierhuis.sessions.Mandje;

public interface BestelbonRepository {
    long create(BestelbonForm bestelbonFrom, Mandje mandje);
}
