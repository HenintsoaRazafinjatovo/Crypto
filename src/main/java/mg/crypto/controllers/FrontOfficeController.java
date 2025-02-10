package mg.crypto.controllers;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mg.crypto.models.Crypto;

// import com.google.cloud.storage.Acl.User;

import mg.crypto.models.MvtFond;
import mg.crypto.models.User;

import mg.crypto.models.MvtTransaction;


@Controller
@RequestMapping("/frontOffice")
public class FrontOfficeController {
@GetMapping("/histoTransactionAll")
public String histoTransactionAll(@RequestParam(name = "datemvt",required = false) String dateStr, Model model) throws Exception {
    // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    User user = new User();
    List<User> users = user.getAllUsers();
    model.addAttribute("users", users);  

    Crypto crypto = new Crypto();
    List<Crypto> cryptos = crypto.FindAll();
    model.addAttribute("cryptos", cryptos);  


    if(dateStr != null){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd['T'][' ']HH:mm[:ss]");
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, formatter);
        Timestamp date = Timestamp.valueOf(localDateTime);
    
        MvtTransaction mvtTransaction = new MvtTransaction();
        List<MvtTransaction> transactions = mvtTransaction.getAllTransactionsBeforeDate(date);
        model.addAttribute("transactions", transactions);  
    }
    
    return "historiqueTransaction";
}
@PostMapping("/histoTransactionByUser")
public String histoTransactionByUser(@RequestParam("idUser") int idUser, @RequestParam("date") String dateStr, Model model) throws Exception {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime localDateTime = LocalDateTime.parse(dateStr, formatter);
    Timestamp date = Timestamp.valueOf(localDateTime);

    MvtTransaction mvtTransaction = new MvtTransaction();
    List<MvtTransaction> transactions = mvtTransaction.getTransactionsByUserAndDate(idUser, date);
    model.addAttribute("transactions", transactions);
    return "historiqueTransactionUser";
}

@PostMapping("/histoTransactionByCrypto")
public String histoTransactionByCrypto(@RequestParam("idCrypto") int idCrypto, @RequestParam("date") String dateStr, Model model) throws Exception {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime localDateTime = LocalDateTime.parse(dateStr, formatter);
    Timestamp date = Timestamp.valueOf(localDateTime);

    MvtTransaction mvtTransaction = new MvtTransaction();
    List<MvtTransaction> transactions = mvtTransaction.getTransactionsByCryptoAndDate(idCrypto, date);
    model.addAttribute("transactions", transactions);
    return "historiqueTransaction";
}
@GetMapping("/histoFondAll")
public String histoFondAll(@RequestParam(name = "datemvt",required = false) String dateStr, Model model) throws Exception {
    
    User user = new User();
    List<User> users = user.getAllUsers();
    model.addAttribute("users", users);
    if(dateStr != null){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd['T'][' ']HH:mm[:ss]");
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, formatter);
        Timestamp date = Timestamp.valueOf(localDateTime);
        
    MvtFond mvtFond = new MvtFond();
    List<MvtFond> fonds = mvtFond.getAllFondsBeforeDate(date);
    model.addAttribute("fonds", fonds);

    
    }

    return "historiqueFond";
}

@PostMapping("/histoFondByUser")
public String histoFondByUser(@RequestParam("idUser") int idUser, @RequestParam("date") String dateStr, Model model) throws Exception {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime localDateTime = LocalDateTime.parse(dateStr, formatter);
    Timestamp date = Timestamp.valueOf(localDateTime);

    MvtFond mvtFond = new MvtFond();
    List<MvtFond> fonds = mvtFond.getFondsByUserAndDate(idUser, date);
    model.addAttribute("fonds", fonds);
    return "historiqueFondUser";
}

}
