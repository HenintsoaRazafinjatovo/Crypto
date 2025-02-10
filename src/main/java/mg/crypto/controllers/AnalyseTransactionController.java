package mg.crypto.controllers;

import mg.crypto.models.AnalyseTransactionUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping("/analyse")
public class AnalyseTransactionController{
    @GetMapping("/transactionTotal")
    public String transactionTotal(Model m) throws Exception{
        AnalyseTransactionUser analyse= new AnalyseTransactionUser();
        List<AnalyseTransactionUser> transactions = analyse.FindAll();

        m.addAttribute("transactions", transactions);
        return "analyse"; 
    }

    @PostMapping("/analyseTransaction")
    public String analyseTransaction(@RequestParam("datemvt") String datemvt, Model model) throws Exception {
        Timestamp timestamp = Timestamp.valueOf(datemvt + ":00");
        AnalyseTransactionUser analyse= new AnalyseTransactionUser();

        List<AnalyseTransactionUser> transactions = analyse.findBeforeDate(timestamp);

        model.addAttribute("transactions", transactions);

        return "analyse"; 
    }

}