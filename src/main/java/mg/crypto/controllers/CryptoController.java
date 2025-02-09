package mg.crypto.controllers;

import mg.crypto.connect.UtilDb;
import mg.crypto.models.Crypto;
import mg.crypto.models.HistoCrypto;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping("/crypto")
public class CryptoController {
    @RequestMapping("/chart")
    public String chart(Model m) throws Exception {
        List<Crypto> cryptos = new Crypto().FindAll();
        m.addAttribute("cryptos", cryptos);
        return "chart_crypto";
    }

    @RequestMapping("/getValues")
    @ResponseBody
    public Map<String, List<HistoCrypto>> getValues() throws Exception {
        Map<String, List<HistoCrypto>> values = new HashMap<>();
        Connection c= new UtilDb().getConnection();
        List<Crypto> ls=new Crypto().FindAll();
        for (Crypto crypto : ls) {
            values.put(crypto.getNomCrypto(), new HistoCrypto().findLastTenByCrypto(c, crypto.getIdCrypto()));
        }
        return values;
    }
}