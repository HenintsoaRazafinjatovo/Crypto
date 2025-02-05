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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.client.RestTemplate;
import org.springframework.ui.Model;
@RestController
@RequestMapping("/session")
public class SessionController {

    @GetMapping("/set")
    public String setSession(HttpSession session,int idUser) {
        session.setAttribute("idUser", idUser);
        return "Session set";
    }

    @GetMapping("/get")
    public String getSession(HttpSession session) {
        return "User: " + session.getAttribute("idUser");
    }
}
