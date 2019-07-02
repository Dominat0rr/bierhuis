package be.vdab.bierhuis.controllers;

import be.vdab.bierhuis.forms.BestelForm;
import be.vdab.bierhuis.services.BierService;
import be.vdab.bierhuis.sessions.Mandje;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("bieren")
public class BierController {
    private final BierService bierService;
    private final Mandje mandje;

    BierController(BierService bierService, Mandje mandje) {
        this.bierService = bierService;
        this.mandje = mandje;
    }

    @GetMapping("{id}")
    public ModelAndView bier(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("bier");
        //bierService.findById(id).ifPresent(bier -> modelAndView.addObject("bier", bier));
        bierService.findById(id).ifPresent(bier -> {
            modelAndView.addObject(bier);
            Optional<Integer> optionalAantal = mandje.getAantal(id);
            BestelForm form = new BestelForm(optionalAantal.isPresent() ? optionalAantal.get() : null);
            modelAndView.addObject(form);
        });
        return modelAndView;
    }

    @PostMapping("{id}/bestellen")
    ModelAndView bestellen(@PathVariable long id, @Valid BestelForm form, Errors errors) {
        if (errors.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("bier");
            bierService.findById(id).ifPresent(bier -> modelAndView.addObject(bier));
            return modelAndView;
        }
        mandje.voegToe(id, form.getAantal());
        return new ModelAndView("redirect:/mandje");
    }
}
