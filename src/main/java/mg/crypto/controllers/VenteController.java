package mg.crypto.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vente")
public class VenteController {
    @GetMapping("/form")
    public String formulaire(Model m){
        return "form_vente";
    }
}
