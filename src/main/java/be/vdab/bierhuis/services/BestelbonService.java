package be.vdab.bierhuis.services;

import be.vdab.bierhuis.forms.BestelbonForm;
import be.vdab.bierhuis.sessions.Mandje;

public interface BestelbonService {
    long create(BestelbonForm bestelbonForm, Mandje mandje);
}
