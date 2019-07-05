package be.vdab.bierhuis.repositories;

import be.vdab.bierhuis.forms.BestelbonForm;
import be.vdab.bierhuis.sessions.Mandje;

/**
 * @version 1.0
 * @author Dominik Claerman
 *
 */

public interface BestelbonRepository {
    long create(BestelbonForm bestelbonFrom, Mandje mandje);
}