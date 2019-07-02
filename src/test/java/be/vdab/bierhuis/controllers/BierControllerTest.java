package be.vdab.bierhuis.controllers;

import be.vdab.bierhuis.domain.Bier;
import be.vdab.bierhuis.services.BierService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BierControllerTest {
    private BierController controller;
    @Mock
    private BierService service;

    @Before
    public void before() {
        when(service.findById(1))
                .thenReturn(Optional.of(new Bier(1, "test", 1,
                        1, 7.4F, BigDecimal.TEN, 10)));
        controller = new BierController(service);
    }

    @Test
    public void bierGebruiktDeThymeleafPaginaBier() {
        assertThat(controller.bier(1).getViewName()).isEqualTo("bier");
    }

    @Test
    public void bierGeeftGevondenBierDoorAanDeThymeleafPagina() {
        Bier bier = (Bier) controller.bier(1).getModel().get("bier");
        assertThat(bier.getId()).isEqualTo(1);
    }

    @Test
    public void bierGeeftOnbestaandBierNietDoorAanDeThymeleafPagina() {
        assertThat(controller.bier(-1).getModel()).doesNotContainKeys("bier");
    }
}
