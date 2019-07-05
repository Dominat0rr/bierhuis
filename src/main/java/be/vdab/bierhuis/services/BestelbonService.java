package be.vdab.bierhuis.services;

import be.vdab.bierhuis.forms.BestelbonForm;
import be.vdab.bierhuis.sessions.Mandje;

/**
 * @version 1.0
 * @author Dominik Claerman
 *
 */

public interface BestelbonService {
    long create(BestelbonForm bestelbonForm, Mandje mandje);
}
