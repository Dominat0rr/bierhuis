package be.vdab.bierhuis.controllers;

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

    MandjeController(Mandje mandje, BierService bierService) {
        this.mandje = mandje;
        this.bierService = bierService;
    }

    @GetMapping
    public ModelAndView mandje() {
        return new ModelAndView("mandje");
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
}
