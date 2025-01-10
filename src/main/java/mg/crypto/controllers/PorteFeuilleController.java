package mg.crypto.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import mg.crypto.models.StockCryptoUser;
import mg.crypto.models.StockFondUser;

@Controller
public class PorteFeuilleController {
    @GetMapping("/porteFeuille")
    public String porteFeuille(Model model)throws Exception{
        StockCryptoUser stockCrypto = new StockCryptoUser();
        StockFondUser fondUser = new StockFondUser();
        List<StockFondUser> fondUsers = fondUser.GetAllByIdUser(0);
        List<StockCryptoUser> stockCryptos = stockCrypto.getCryptoByIdUser(0);
        model.addAttribute("stockCryptos",stockCryptos);
        model.addAttribute("fondUsers",fondUsers);
        return "porteFeuille";
    }
}
