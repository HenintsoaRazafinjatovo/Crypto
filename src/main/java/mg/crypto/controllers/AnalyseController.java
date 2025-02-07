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

import mg.crypto.models.Analyse;
import mg.crypto.models.Crypto;
import mg.crypto.models.HistoCrypto;
import mg.crypto.models.MvtFond;

@Controller
public class AnalyseController {


    @GetMapping("/analyse")
    public String analyseForm (Model model)throws Exception{
        List<Crypto> cryptos= new ArrayList<>();
        cryptos= new Crypto().findAll();
        model.addAttribute("cryptos", cryptos);
        return "filtreAnalyse";
    }

    // @GetMapping("/analyseResult")
    // public String analyseResult (Model model,@RequestParam(name = "crypto") int idCrypto,@RequestParam(name = "dateDebut") String dateDebut,@RequestParam(name = "dateFin") String dateFin)throws Exception{
    //     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    //     LocalDateTime localDateTimeDebut = LocalDateTime.parse(dateDebut, formatter);
    //     LocalDateTime localDateTimeFin = LocalDateTime.parse(dateFin, formatter);
    //     Timestamp timestampDebut = Timestamp.valueOf(localDateTimeDebut);
    //     Timestamp timestampFin = Timestamp.valueOf(localDateTimeFin);
    //     MvtFond mvt = new MvtFond();
    //     mvt.setIdUser(0);
    //     mvt.setIdCrypto(idCrypto);
    //     List<MvtFond> mvtFonds= new ArrayList<>();
    //     mvtFonds=mvt.findByDate(timestampDebut, timestampFin);
    //     model.addAttribute("lmvt", mvtFonds);
    //     return "listeMvt";
    // }
    
    @PostMapping("/analyseResult")
    public String analyse(@RequestParam(name = "cryptos", required = false) List<Integer> cryptoIds,
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
    
        if (cryptoIds != null) {
            System.out.println("Selected cryptos: " + cryptoIds.size());
            for (Integer integer : cryptoIds) {
                List<Object> selectedCryptos = new HistoCrypto().getbyinterval(integer,timestampFin,timestampDebut);    
                
                try {
                    String cryptoName = new Crypto().findById(integer).getNomCrypto();
                    analyses.add(new Analyse(cryptoName, analysisType,selectedCryptos,"valeur"));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        for (Analyse analyse : analyses) {
            System.out.println("Analyse: " + analyse.getNomCrypto() + " " + analyse.getType() + " " + analyse.getMontant());
        }
        
        model.addAttribute("analyse", analyses);
         // Return the view name for the analysis result
         return "ResultatAnalyse";
    }

    
    
    
}
