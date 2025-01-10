package mg.crypto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.client.RestTemplate;
import org.springframework.ui.Model;

@Controller
public class LoginController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/submit_login")
    public String submitLogin(@RequestParam("email") String email,
                              @RequestParam("password") String password,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        String url = "http://localhost:5000/api/loginController/auth";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestJson = String.format("{\"email\":\"%s\", \"password\":\"%s\"}", email, password);
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();
            if (responseBody != null && responseBody.contains("votre pin a ete envoyé")) {
                return "redirect:/codevalidation";
            } else {
                model.addAttribute("message", "Login successful!");
                return "success";
            }
        } else {
            model.addAttribute("message", "Login failed: " + response.getBody());
            return "error";
        }
    }

    @PostMapping("/submit_code")
    public String submitCode(@RequestParam("idUser") int idUser,
                             @RequestParam("code") String code,
                             Model model) {
        String url = "http://localhost:5005/api/logincontroller/codeValidation";

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
}