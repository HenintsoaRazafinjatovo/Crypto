package mg.crypto.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class TestController {
    @GetMapping("/")
    public String home()
    {
        return "redirect:/login";
    }
}
