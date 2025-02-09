package mg.crypto.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

@Controller
public class InscriptionController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/inscription")
    public String home() {
        return "login/inscription"; // Assurez-vous que "inscription" correspond au nom de votre fichier HTML sans l'extension
    }

}