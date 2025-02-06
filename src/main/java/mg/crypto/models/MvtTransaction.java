package mg.crypto.models;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import mg.crypto.connect.GenericDao;
import mg.crypto.connect.UtilDb;
import mg.crypto.utils.AnnotationAttribut;
import mg.crypto.utils.AnnotationClass;
import mg.crypto.utils.Identite;

@AnnotationClass(tableName = "mvt_transaction")
public class MvtTransaction {
    @Identite(colName = "id_mvt_transaction")
    @AnnotationAttribut(colName = "id_mvt_transaction", insert = false)
    int idMvtTransaction;
    @AnnotationAttribut(colName = "id_user", insert = true)
    int idUser;
    @AnnotationAttribut(colName = "id_cryptomonnaie", insert = true)
    int idCrypto;
    @AnnotationAttribut(colName = "qtt", insert = true)
    int quantite;
    @AnnotationAttribut(colName = "montant", insert = true)
    double montant;
    @AnnotationAttribut(colName = "isVente", insert = true)
    boolean type;
    @AnnotationAttribut(colName = "date_transaction", insert = true)
    Timestamp date;

    public MvtTransaction(int idMvtTransaction, int idUser, int idCrypto, int quantite, double montant, boolean type,
            Timestamp date) {
        this.idMvtTransaction = idMvtTransaction;
        this.idUser = idUser;
        this.idCrypto = idCrypto;
        this.quantite = quantite;
        this.montant = montant;
        this.type = type;
        this.date = date;
    }

    public MvtTransaction(int idUser, int idCrypto, int quantite, double montant, boolean type, Timestamp date) {
        this.idUser = idUser;
        this.idCrypto = idCrypto;
        this.quantite = quantite;
        this.montant = montant;
        this.type = type;
        this.date = date;
    }

    public MvtTransaction() {
    }

    public int getIdMvtTransaction() {
        return idMvtTransaction;
    }

    public void setIdMvtTransaction(int idMvtTransaction) {
        this.idMvtTransaction = idMvtTransaction;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getIdCrypto() {
        return idCrypto;
    }

    public void setIdCrypto(int idCrypto) {
        this.idCrypto = idCrypto;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public boolean isType() {
        return type;
    }
    public void setType(boolean type) {
        this.type = type;
    }

    public void insert()throws Exception{
        GenericDao dao = new GenericDao(new UtilDb());
        dao.save(this);
    }

    public void update() throws Exception{
        GenericDao dao = new GenericDao(new UtilDb());
        dao.update(this);
    }

    public void vente() throws Exception{
        if(this.isType()){
            this.update();
            MvtFond mvt= new MvtFond();
            mvt.setDepot(this.getMontant());
            mvt.setIdUser(this.getIdUser());
            mvt.setDtMvt(this.getDate());
            String type="Depot";
            mvt.setTypeMvt(type);
            mvt.AugmentationFond();
            System.out.println("Vente fait avec succès");
        }else{
            System.out.println("Vente n'est pas insérer");
        }
    }
   
    public List<MvtTransaction> getAllTransactionsBeforeDate(Timestamp date2) throws Exception {
        List<MvtTransaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM mvt_transaction WHERE date_transaction <= ?";
        UtilDb utilDb = new UtilDb();
        try (Connection conn = utilDb.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setTimestamp(1, date2);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    MvtTransaction transaction = new MvtTransaction();
                    transaction.setIdMvtTransaction(rs.getInt("id_mvt_transaction"));
                    transaction.setIdUser(rs.getInt("id_user"));
                    transaction.setIdCrypto(rs.getInt("id_cryptomonnaie"));
                    transaction.setQuantite(rs.getInt("qtt"));
                    transaction.setMontant(rs.getDouble("montant"));
                    transaction.setType(rs.getBoolean("isVente"));
                    transaction.setDate(rs.getTimestamp("date_transaction"));
                    
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public List<MvtTransaction> getTransactionsByUserAndDate(int idUser, Timestamp date) throws Exception {
        List<MvtTransaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM mvt_transaction WHERE id_user = ? AND date_transaction <= ?";
        UtilDb utilDb = new UtilDb();
        try (Connection conn = utilDb.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, idUser);
            stmt.setTimestamp(2, date);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    MvtTransaction transaction = new MvtTransaction();
                    transaction.setIdMvtTransaction(rs.getInt("id_mvt_transaction"));
                    transaction.setIdUser(rs.getInt("id_user"));
                    transaction.setIdCrypto(rs.getInt("id_cryptomonnaie"));
                    transaction.setQuantite(rs.getInt("qtt"));
                    transaction.setMontant(rs.getDouble("montant"));
                    transaction.setType(rs.getBoolean("isVente"));
                    transaction.setDate(rs.getTimestamp("date_transaction"));
                    
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public List<MvtTransaction> getTransactionsByCryptoAndDate(int idCrypto, Timestamp date) throws Exception {
        List<MvtTransaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM mvt_transaction WHERE id_cryptomonnaie = ? AND date_transaction <= ?";
        UtilDb utilDb = new UtilDb();
        try (Connection conn = utilDb.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, idCrypto);
            stmt.setTimestamp(2, date);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    MvtTransaction transaction = new MvtTransaction();
                    transaction.setIdMvtTransaction(rs.getInt("id_mvt_transaction"));
                    transaction.setIdUser(rs.getInt("id_user"));
                    transaction.setIdCrypto(rs.getInt("id_cryptomonnaie"));
                    transaction.setQuantite(rs.getInt("qtt"));
                    transaction.setMontant(rs.getDouble("montant"));
                    transaction.setType(rs.getBoolean("isVente"));
                    transaction.setDate(rs.getTimestamp("date_transaction"));
                    
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }
}
