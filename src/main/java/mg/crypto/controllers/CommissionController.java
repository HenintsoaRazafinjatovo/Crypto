package mg.crypto.controllers;
import java.io.ObjectInputFilter.Config;
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

import mg.crypto.models.Analyse;
import mg.crypto.models.Commission;
import mg.crypto.models.ConfigCommission;
import mg.crypto.models.Crypto;
import mg.crypto.models.HistoCrypto;
import mg.crypto.models.MvtFond;

@Controller
public class CommissionController {


    @GetMapping("/commissionAnalyse")
    public String commissionAnalyse (Model model)throws Exception{
        List<Crypto> cryptos= new ArrayList<>();
        cryptos= new Crypto().FindAll();
        model.addAttribute("cryptos", cryptos);
        return "filtrecommissionAnalyse";
    }

    @PostMapping("/commissionAnalyseResult")
    public String commissionAnalyseResult(@RequestParam(name = "cryptos", required = false) List<Integer> cryptoIds,
                          @RequestParam(name = "analysisType") String analysisType,
                          @RequestParam(name = "dateDebut") String dateDebut,
                          @RequestParam(name = "dateFin") String dateFin,
                          Model model ) {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                            LocalDateTime localDateTimeDebut = LocalDateTime.parse(dateDebut, formatter);
                            LocalDateTime localDateTimeFin = LocalDateTime.parse(dateFin, formatter);
                            Timestamp timestampDebut = Timestamp.valueOf(localDateTimeDebut);
                            Timestamp timestampFin = Timestamp.valueOf(localDateTimeFin);                    

                            // Process the selected cryptos and analysis type
        List<Analyse> analyses = new ArrayList<>();
        for (Integer cryptoId : cryptoIds) {
            List<Object> selectedCryptos = new Commission().getbyinterval(cryptoId,timestampFin,timestampDebut);    
                
                try {
                    String cryptoName = new Crypto().findById(cryptoId).getNomCrypto();
                    analyses.add(new Analyse(cryptoName, analysisType,selectedCryptos,"valeur"));

                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        for (Analyse analyse : analyses) {
            System.out.println("Analyse: " + analyse.getNomCrypto() + " " + analyse.getType() + " " + analyse.getMontant());
        }
        
        model.addAttribute("analyse", analyses);
         // Return the view name for the analysis result
         return "ResultatAnalyse";
    }

    @GetMapping("/commissionConfig")
    public String updateConfig() {
        return "modifConfig";
    }
    @PostMapping("/commissionConfig")
    public String updateConfig(@RequestParam(name = "config") double commission,@RequestParam(name = "type") int type) {
        ConfigCommission config = new ConfigCommission();
        config.setIdConfigCommission(type);
        config.setPourcentage(commission);
        System.out.println("commission: " + commission + " type: " + type);
        try {
            if (type == 1) {
                config.setType(true);
            } else {
                config.setType(false);
                
            }
            config.update();
        } catch (Exception e) {
            e.printStackTrace();
        
            }
        return "redirect:commissionConfig";    
        }
}
