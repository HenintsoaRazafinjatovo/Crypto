package mg.crypto.service;

import mg.crypto.models.Crypto;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CryptoService {

    private List<Crypto> cryptos;

    public CryptoService() {
    }

    @Scheduled(fixedRate = 10000) // Exécuter toutes les 10 secondes
    public void updateCryptoValues() throws Exception {
        List<Crypto> cryptos = new Crypto().FindAll();
        for (Crypto crypto : cryptos) {
            Crypto n = new Crypto().findById(crypto.getIdCrypto());
            n.setValInitial(n.getValue());
            n.update();
            System.out.println("Updated " + crypto.getNomCrypto() + " value");
        }
    }

    public List<Crypto> getCryptos() {
        return cryptos;
    }
}