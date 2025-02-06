package mg.crypto.controllers;

import mg.crypto.models.Admin;
import mg.crypto.models.MvtFond;
import mg.crypto.models.MvtFondComplet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/accueil")
    public String accueil(Model m) throws Exception {
        m.addAttribute("fonds", new MvtFondComplet().findAll());
        return "admin/validationTransaction";
    }

    @GetMapping("/logout")
    public String deco(Model m,HttpSession session) throws Exception {
        session.removeAttribute("admin");
        return "redirect:/login/admin";
    }

    @GetMapping("/filterTransactions")
    public String filter(@RequestParam("etat") String etat, @RequestParam("type") String type, Model m)
            throws Exception {
        MvtFondComplet cmp = new MvtFondComplet();
        cmp.setEtatText(etat);
        cmp.setType(type);
        m.addAttribute("fonds", cmp.filter(type, etat));
        return "admin/validationTransaction";
    }

    @PostMapping("/updateEtat")
    public String update(@RequestParam("idMvtFond") int id, @RequestParam("newEtat") boolean newEtat, Model m)
            throws Exception {
        MvtFondComplet mvtFondComplet = new MvtFondComplet();
        mvtFondComplet.updateEtat(id, newEtat);
        return accueil(m);
    }

}