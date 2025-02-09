package mg.crypto.controllers;

import mg.crypto.models.Crypto;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

import java.util.List;


@RestController
public class RestCrypto {
    @RequestMapping("/rest/crypto")
    public List<Crypto> chart() throws Exception {
        List<Crypto> cryptos = new Crypto().FindAll();
        return cryptos;
    }
    @PutMapping("/rest/crypto/update")
    public void update(@RequestParam("id") int idCrypto, @RequestParam("val") BigDecimal val) throws Exception {
        Crypto crypto = new Crypto().findById(idCrypto);
        crypto.setValInitial(val);
        crypto.update();
    }
}