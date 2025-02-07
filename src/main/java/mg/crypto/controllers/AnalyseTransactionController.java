package mg.crypto.controllers;

import mg.crypto.models.AnalyseTransactionUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping("/analyse")
public class AnalyseTransactionController{
    @GetMapping("/transactionTotal")
    public String transactionTotal(Model m){
        AnalyseTransactionUser analyse= new AnalyseTransactionUser();
        List<AnalyseTransactionUser> transactions = analyse.FindAll();

        model.addAttribute("transactions", transactions);
        return "analyse"; 
    }

    @PostMapping("/analyseTransaction")
    public String analyseTransaction(@RequestParam("datemvt") String datemvt, Model model) {
        Timestamp timestamp = Timestamp.valueOf(datemvt + ":00");
        List<AnalyseTransactionUser> transactions = analyse.findBeforeDate(timestamp);

        model.addAttribute("transactions", transactions);

        return "analyse"; 
    }

}