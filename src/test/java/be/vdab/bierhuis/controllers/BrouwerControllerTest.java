package be.vdab.bierhuis.controllers;

import be.vdab.bierhuis.domain.Adres;
import be.vdab.bierhuis.domain.Brouwer;
import be.vdab.bierhuis.services.BierService;
import be.vdab.bierhuis.services.BrouwerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @version 1.0
 * @author Dominik Claerman
 *
 */

@RunWith(MockitoJUnitRunner.class)
public class BrouwerControllerTest {
    private BrouwerController controller;
    @Mock
    private BrouwerService brouwerService;
    @Mock
    private BierService bierService;

    @Before
    public void before() {
        when(brouwerService.findById(1))
                .thenReturn(Optional.of(new Brouwer(1, "test",
                        new Adres("testStraat", "10", "Leuven", 3000),
                        200_000)));
        controller = new BrouwerController(brouwerService, bierService);
    }

    // Brouwers
    @Test
    public void brouwersGebruiktDeThymeleafPaginaBrouwers() {
        assertThat(controller.brouwers().getViewName()).isEqualTo("brouwers");
    }

    @Test
    public void brouwersGeeftBrouwersDoorAanDeThymeleafPagina() {
        assertThat(controller.brouwers().getModel().get("brouwers")).isInstanceOf(List.class);
    }

    @Test
    public void brouwerGebruiktDeThymeleafPaginaBrouwer() {
        assertThat(controller.brouwer(1).getViewName()).isEqualTo("brouwer");
    }

    // Brouwer
    @Test
    public void brouwerGeeftGevondenBrouwerDoorAanDeThymeleafPagina() {
        Brouwer brouwer = (Brouwer) controller.brouwer(1).getModel().get("brouwer");
        assertThat(brouwer.getId()).isEqualTo(1);
    }

    @Test
    public void brouwerGeeftOnbestaandeBrouwerNietDoorAanDeThymeleafPagina() {
        assertThat(controller.brouwer(-1).getModel()).doesNotContainKeys("brouwer");
    }
}
