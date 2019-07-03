package be.vdab.bierhuis.controllers;

import be.vdab.bierhuis.domain.Bier;
import be.vdab.bierhuis.forms.BestelbonForm;
import be.vdab.bierhuis.services.BestelbonService;
import be.vdab.bierhuis.services.BierService;
import be.vdab.bierhuis.sessions.Mandje;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("mandje")
public class MandjeController {
    private final Mandje mandje;
    private final BierService bierService;
    private final BestelbonService bestelbonService;
    private Map<Bier, Integer> bieren = new HashMap<>();

    MandjeController(Mandje mandje, BierService bierService, BestelbonService bestelbonService) {
        this.mandje = mandje;
        this.bierService = bierService;
        this.bestelbonService = bestelbonService;
    }

    @NumberFormat(pattern = "0.00")
    private BigDecimal getTotaalPrijs() {
        BigDecimal prijs = BigDecimal.ZERO;

        for (Map.Entry<Bier, Integer> entry : bieren.entrySet()) {
            Bier key = entry.getKey();
            Integer value = entry.getValue();

            BigDecimal prijsPerSoort = key.getPrijs().multiply(BigDecimal.valueOf(value));
            prijs = prijs.add(prijsPerSoort);
        }
        return prijs;
    }

    @GetMapping
    public ModelAndView mandje() {
        ModelAndView modelAndView = new ModelAndView("mandje");
        BestelbonForm bestelbonform = new BestelbonForm(null, null, null, null, 0);
        modelAndView.addObject("bestelbonform", bestelbonform);

        if (mandje.isLeeg()) return modelAndView;
        mandje.getBieren().forEach((id, aantal) -> {
            bieren.put(bierService.findById(id).get(), aantal);
        });
        bieren.forEach((bier, aantal) -> System.out.println(bier.getId() + " " + bier.getPrijs()));
        modelAndView.addObject("bieren", bieren);
        modelAndView.addObject("totaalPrijs", getTotaalPrijs());
        return modelAndView;
    }

    @PostMapping
    public String voegToe(long id, int aantal) {
        mandje.voegToe(id, aantal);
        return "redirect:/mandje";
    }

    @PostMapping("bestellen")
    public ModelAndView toevoegen(@Valid BestelbonForm form, Errors errors) {
        if (mandje.isLeeg()) return new ModelAndView("redirect:/");
        if(errors.hasErrors()) return new ModelAndView("mandje", "mandje", mandje);
        bestelbonService.create(form, mandje);
        return new ModelAndView("redirect:/mandje");
    }
//
//    @GetMapping("besteld/{id}")
//    ModelAndView besteld(@PathVariable long id) {
//        return new ModelAndView("besteld").addObject(mandje);
//    }

//    @GetMapping("verwijder")
//    String verwijder(@RequestParam long id, RedirectAttributes redirect){
//        mandje.remove(id);
//        return "redirect:/mandje";
//    }
}
