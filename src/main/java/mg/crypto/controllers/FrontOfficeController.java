package mg.crypto.controllers;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import mg.crypto.models.MvtFond;
import mg.crypto.models.MvtTransaction;


@Controller
@RequestMapping("/frontOffice")
public class FrontOfficeController {
@GetMapping("/histoTransactionAll")
public String histoTransactionAll(@RequestParam("date") String dateStr, Model model) throws Exception {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime localDateTime = LocalDateTime.parse(dateStr, formatter);
    Timestamp date = Timestamp.valueOf(localDateTime);

    MvtTransaction mvtTransaction = new MvtTransaction();
    List<MvtTransaction> transactions = mvtTransaction.getAllTransactionsBeforeDate(date);
    model.addAttribute("transactions", transactions);
    return "listeTransaction";
}
@GetMapping("/histoTransactionByUser")
public String histoTransactionByUser(@RequestParam("idUser") int idUser, @RequestParam("date") String dateStr, Model model) throws Exception {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime localDateTime = LocalDateTime.parse(dateStr, formatter);
    Timestamp date = Timestamp.valueOf(localDateTime);

    MvtTransaction mvtTransaction = new MvtTransaction();
    List<MvtTransaction> transactions = mvtTransaction.getTransactionsByUserAndDate(idUser, date);
    model.addAttribute("transactions", transactions);
    return "listeTransaction";
}

@GetMapping("/histoTransactionByCrypto")
public String histoTransactionByCrypto(@RequestParam("idCrypto") int idCrypto, @RequestParam("date") String dateStr, Model model) throws Exception {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime localDateTime = LocalDateTime.parse(dateStr, formatter);
    Timestamp date = Timestamp.valueOf(localDateTime);

    MvtTransaction mvtTransaction = new MvtTransaction();
    List<MvtTransaction> transactions = mvtTransaction.getTransactionsByCryptoAndDate(idCrypto, date);
    model.addAttribute("transactions", transactions);
    return "listeTransaction";
}
@GetMapping("/histoFondAll")
public String histoFondAll(@RequestParam("date") String dateStr, Model model) throws Exception {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime localDateTime = LocalDateTime.parse(dateStr, formatter);
    Timestamp date = Timestamp.valueOf(localDateTime);

    MvtFond mvtFond = new MvtFond();
    List<MvtFond> fonds = mvtFond.getAllFondsBeforeDate(date);
    model.addAttribute("fonds", fonds);
    return "listeFond";
}

@GetMapping("/histoFondByUser")
public String histoFondByUser(@RequestParam("idUser") int idUser, @RequestParam("date") String dateStr, Model model) throws Exception {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime localDateTime = LocalDateTime.parse(dateStr, formatter);
    Timestamp date = Timestamp.valueOf(localDateTime);

    MvtFond mvtFond = new MvtFond();
    List<MvtFond> fonds = mvtFond.getFondsByUserAndDate(idUser, date);
    model.addAttribute("fonds", fonds);
    return "listeFond";
}

}
