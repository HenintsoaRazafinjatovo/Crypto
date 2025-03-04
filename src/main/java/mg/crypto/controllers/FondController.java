package mg.crypto.controllers;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import mg.crypto.models.MvtFond;

@Controller
public class FondController {
    @GetMapping("/etatFond")
    public String etatFond (HttpSession session,Model model)throws Exception{
        MvtFond mvt = new MvtFond();   
        List<MvtFond> mvtFonds= new ArrayList<>();
        mvt.setIdUser((int)session.getAttribute("idUser"));
        double fond=mvt.getFondRestant();
        mvtFonds=mvt.findById((int)session.getAttribute("idUser"));
        model.addAttribute("lmvt", mvtFonds);
        model.addAttribute("fond",fond);
        return "listeMvt";
    }

    @GetMapping("/ajoutFond")
    public String ajoutFond (Model model)throws Exception{
        return "formMvtFond";
    }

    @PostMapping("/ajoutFond")
    public String submitAjout (HttpSession session,@RequestParam(name = "typemvt") String type,@RequestParam(name = "montantmvt") Double montant,@RequestParam(name = "datemvt") String time )throws Exception{
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(time, formatter);
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        MvtFond mvt = new MvtFond();
        mvt.setIdUser((int)session.getAttribute("idUser"));
        
        mvt.setDtMvt(timestamp);
        if (type.equals("depot")) {
            mvt.setDepot(montant);
            mvt.setRetrait(0);    
        }
        else{
            mvt.setRetrait(montant);
            mvt.setDepot(0);
        }
        mvt.insert();
        return "redirect:/ajoutFond";
    }
}
