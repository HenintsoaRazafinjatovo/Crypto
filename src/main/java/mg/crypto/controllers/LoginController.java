package mg.crypto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import mg.crypto.models.Admin;

import org.springframework.web.client.RestTemplate;
import org.springframework.ui.Model;

@Controller
public class LoginController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/login")
    public String home() {
        return "login/connexion"; // Assurez-vous que "inscription" correspond au nom de votre fichier HTML sans l'extension
    }
    @GetMapping("/pin")
    public String code() {
        return "login/codeValidation"; // Assurez-vous que "inscription" correspond au nom de votre fichier HTML sans l'extension
    }
    @PostMapping("/submit_code")
    public String submitCode(@RequestParam("idUser") int idUser,
                             @RequestParam("code") String code,
                             Model model) {
        String url = "http://localhost:5005/api/login/codeValidation";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestJson = String.format("{\"idUser\":%d, \"code\":\"%s\"}", idUser, code);
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("message", "Code validation successful!");
            return "success";
        } else {
            model.addAttribute("message", "Code validation failed: " + response.getBody());
            return "error";
        }
    }
    
    @GetMapping("/login/admin")
    public String index(Model m) throws Exception {
        return "admin/login";
    }

    @PostMapping("/login/admin")
    public String valide(Admin a, Model m,HttpSession session) throws Exception {
        if (a.checkAdmin() != null) {
            session.setAttribute("admin", a.checkAdmin());
            return "redirect:/admin/accueil";
        }
        m.addAttribute("error", "Nom ou mot de passe incorrect");
        return index(m);
    }
}