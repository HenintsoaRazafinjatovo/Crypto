package mg.crypto.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mvtTransaction")
public class MvtTransactionController {
    @GetMapping
    public String liste(Model m){
        return "liste_mvt";
    }
}
