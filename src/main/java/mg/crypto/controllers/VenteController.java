package mg.crypto.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import mg.crypto.models.Crypto;
import mg.crypto.models.MvtTransaction;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/vente")
public class VenteController {

    @GetMapping("/form")
    public String formulaire(Model m) {
        return "form_vente";
    }

    @PostMapping("/faireVente")
    public String formulaire(HttpSession session,
            @RequestParam(name = "idCrypto") int idCrypto,
            @RequestParam(name = "qtt") int qtt) throws Exception {
        int idUser = (int) session.getAttribute("idUser");
        MvtTransaction transaction = new MvtTransaction();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        transaction.setDate(timestamp);
        transaction.setIdUser(idUser);
        transaction.setIdCrypto(idCrypto);
        transaction.setQuantite(qtt);
        transaction.setType(true); // true pour une vente

        transaction.vente(); // Traitement de la vente
        Crypto crypto = new Crypto();
        crypto = crypto.findById(idCrypto);
        // Récupération du montant et de l'ID de la transaction
        transaction.setMontant(transaction.findVenteByIdUser(transaction.getIdUser()).getMontant());
        transaction.setIdMvtTransaction(transaction.findVenteByIdUser(transaction.getIdUser()).getIdMvtTransaction());
        String message = "Vente de " + qtt + " " + crypto.getNomCrypto() + " effectuée avec succès";
        // Envoi de la requête POST de la vente
        transaction.sendPostVente();
        transaction.sendPostNotifOperation(message);

        // Retourner la vue ou rediriger vers le portefeuille
        return "PorteFeuille";
    }

    @PostMapping("/faireAchat")
    public String formulaireAchat(HttpSession session, @RequestParam(name = "idCrypto") int idCrypto,
            @RequestParam(name = "qtt") int qtt) throws Exception {
        int idUser = (int) session.getAttribute("idUser");
        MvtTransaction transaction = new MvtTransaction();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        transaction.setDate(timestamp);
        transaction.setIdUser(idUser);
        transaction.setIdCrypto(idCrypto);
        transaction.setQuantite(qtt);
        transaction.setType(false); // true pour une vente

        transaction.achat(); // Traitement de la vente
        Crypto crypto = new Crypto();
        crypto = crypto.findById(idCrypto);
        // Récupération du montant et de l'ID de la transaction
        transaction.setMontant(transaction.findAchatByIdUser(transaction.getIdUser()).getMontant());
        transaction.setIdMvtTransaction(transaction.findAchatByIdUser(transaction.getIdUser()).getIdMvtTransaction());
        String message = "Achat de " + qtt + " " + crypto.getNomCrypto() + " effectuée avec succès";
        // Envoi de la requête POST de la vente
        transaction.sendPostVente();
        transaction.sendPostNotifOperation(message);

        // Retourner la vue ou rediriger vers le portefeuille
        return "PorteFeuille";
    }

}
