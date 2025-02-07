package mg.crypto.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import mg.crypto.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@Controller
public class InscriptionController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/inscription")
    public String home() {
        return "login/inscription"; // Assurez-vous que "inscription" correspond au nom de votre fichier HTML sans l'extension
    }

}