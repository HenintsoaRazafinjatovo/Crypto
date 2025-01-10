package mg.crypto.controllers;

import mg.crypto.models.Crypto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
public class RestCrypto {
    @RequestMapping("/rest/crypto")
    public List<Crypto> chart() throws Exception {
        List<Crypto> cryptos = new Crypto().mock();
        return cryptos;
    }
    @PutMapping("/rest/crypto/update")
    public void update(@RequestParam("id") int idCrypto, @RequestParam("val") double val) throws Exception {
        Crypto crypto = new Crypto().findById(idCrypto);
        crypto.setValInitial(val);
        crypto.update();
    }
}