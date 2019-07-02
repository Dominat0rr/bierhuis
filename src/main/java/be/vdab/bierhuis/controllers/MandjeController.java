package be.vdab.bierhuis.controllers;

import be.vdab.bierhuis.domain.Adres;
import be.vdab.bierhuis.domain.Bestelbon;
import be.vdab.bierhuis.domain.Mand;
import be.vdab.bierhuis.services.BierService;
import be.vdab.bierhuis.sessions.Mandje;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("mandje")
public class MandjeController {
    private final Mandje mandje;
    private final BierService bierService;
    //private final BestelbonService bestelbonService; // TODO

    MandjeController(Mandje mandje, BierService bierService) {
        this.mandje = mandje;
        this.bierService = bierService;
    }

    private Mand maakMand() {
        Mand mand = new Mand();
        bierService.findByIds(mandje.getIds()).forEach(bier -> mand.add(bier, mandje.getAantal(bier.getId()).get()));
        return mand;
    }

    @GetMapping
    public ModelAndView mandje() {
        if (!mandje.isGevuld()) return new ModelAndView("redirect:/");
        return new ModelAndView("mandje").addObject(maakMand()).addObject(new Bestelbon(0, null, new Adres(null, null, null, 0)));
    }

//    @GetMapping
//    public ModelAndView toonMandje() {
//        ModelAndView modelAndView = new ModelAndView("mandje");
//        if (mandje.isGevuld()) {
//            modelAndView.addObject("bierenInMandje");
//        }
//        return modelAndView;
//    }
//
//    public @PostMapping
//    String voegToe(long id, int aantal) {
//        mandje.voegToe(id, aantal);
//        return "redirect:/mandje";
//    }

//    @PostMapping
//    ModelAndView bestel(@Valid Bestelbon bestelbon, Errors errors, RedirectAttributes redirect) {
//        System.out.println(errors);
//        if (mandje.isLeeg()) {
//            return new ModelAndView("redirect/");
//        }
//        Mand mand = maakMand();
//        if (errors.hasErrors()) {
//            return new ModelAndView("mandje").addObject(mand);
//        }
//        long id = bestelbonService.create(bestelbon, mand);
//        mandje.maakLeeg();
//        redirect.addAttribute("id", id);
//        return new ModelAndView("redirect:/mandje/besteld/{id}");
//    }
//
//    @GetMapping("besteld/{id}")
//    ModelAndView besteld(@PathVariable long id) {
//        return new ModelAndView("besteld").addObject(mandje);
//    }
}
