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
        return "inscription"; // Assurez-vous que "inscription" correspond au nom de votre fichier HTML sans l'extension
    }

    @PostMapping("/submit_registration")
    public String submitRegistration(@RequestParam("username") String username,
                                     @RequestParam("email") String email,
                                     @RequestParam("telephone") String telephone,
                                     @RequestParam("password") String password,
                                     RedirectAttributes redirectAttributes) {
        // Create a new User object
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setTelephone(telephone);
        user.setPassword(password);

        String apiUrl = "http://localhost:5005/api/register/register";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<>(user, headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            redirectAttributes.addFlashAttribute("message", "Registration successful!");
            return "redirect:/connexion";
        } else {
            redirectAttributes.addFlashAttribute("message", "Registration failed: " + response.getBody());
        }
        return "redirect:/inscription";
    }
}