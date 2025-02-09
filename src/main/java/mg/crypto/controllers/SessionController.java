package mg.crypto.controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpSession;


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
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        // Invalider la session
        session.invalidate();
        return "Logged out successfully!";
    }
}
