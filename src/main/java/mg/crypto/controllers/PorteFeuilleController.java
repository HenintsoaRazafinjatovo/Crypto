package mg.crypto.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;
import mg.crypto.models.StockCryptoUser;
import mg.crypto.models.StockFondUser;

@RequestMapping("/porteFeuille")
@Controller
public class PorteFeuilleController {
    @GetMapping("/valeur")
    public String valeur(HttpSession session,Model model)throws Exception{
        StockCryptoUser stockCrypto = new StockCryptoUser();
        StockFondUser fondUser = new StockFondUser();
        List<StockFondUser> fondUsers = fondUser.GetAllByIdUser((int)session.getAttribute("idUser"));
        
        List<StockCryptoUser> stockCryptos = stockCrypto.getCryptoByIdUser((int)session.getAttribute("idUser"));
        double valeurTotalCryptos=stockCrypto.getValeurTotalCryptoByIdUser((int)session.getAttribute("idUser"));

        model.addAttribute("valeurTotalCryptos",valeurTotalCryptos);
        model.addAttribute("stockCryptos",stockCryptos);
        
        model.addAttribute("fondUsers",fondUsers);
        return "PorteFeuille";
    }

}
