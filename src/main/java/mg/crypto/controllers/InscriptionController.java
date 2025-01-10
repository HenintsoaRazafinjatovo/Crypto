package mg.crypto.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@Controller
public class InscriptionController {
    @GetMapping("/")
    public String home()
    {
        return "form";
    }
}
