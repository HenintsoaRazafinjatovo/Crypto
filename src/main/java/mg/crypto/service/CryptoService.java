package mg.crypto.service;

import mg.crypto.connect.UtilDb;
import mg.crypto.models.Crypto;
import mg.crypto.models.HistoCrypto;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

@Service
public class CryptoService {

    private List<Crypto> cryptos;

    public CryptoService() {
    }

    @Scheduled(fixedRate = 10000) // Exécuter toutes les 10 secondes
    public void updateCryptoValues() throws Exception {
        Connection c=new UtilDb().getConnection();
        List<Crypto> cryptos = new Crypto().FindAll();
        for (Crypto crypto : cryptos) {
            HistoCrypto h=new HistoCrypto();
            h.setIdCrypto(crypto.getIdCrypto());
            h.setDateHistorique(new Timestamp(System.currentTimeMillis()));
            h.setValeur(crypto.getValue().doubleValue());
            h.insert(c);
            System.out.println("Updated " + crypto.getNomCrypto() + " value");
        }
    }

    public List<Crypto> getCryptos() {
        return cryptos;
    }
}