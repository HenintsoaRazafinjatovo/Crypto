package mg.crypto.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mg.crypto.connect.UtilDb;
import mg.crypto.utils.AnnotationAttribut;
import mg.crypto.utils.AnnotationClass;

@AnnotationClass(tableName = "vue_crypto_par_utilisateur")
public class StockCryptoUser {
    @AnnotationAttribut(colName = "id_user", insert = false)
    int idUser;
    @AnnotationAttribut(colName = "id_cryptomonnaie", insert = false)
    int idCrypto;
    @AnnotationAttribut(colName = "nom_crypto" , insert = false)
    String nomCrypto;
    @AnnotationAttribut(colName = "total_quantite" , insert = false)
    int qttTotal;
    @AnnotationAttribut(colName = "total_valeur" , insert = false)
    double valeurTotal;


    public StockCryptoUser(){
    }

    public int getIdCrypto() {
        return idCrypto;
    }
    public void setIdCrypto(int idCrypto) {
        this.idCrypto = idCrypto;
    }

    public String getNomCrypto() {
        return nomCrypto;
    }
    public void setNomCrypto(String nomCrypto) {
        this.nomCrypto = nomCrypto;
    }

    public int getIdUser() {
        return idUser;
    }
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getQttTotal() {
        return qttTotal;
    }
    public void setQttTotal(int qttTotal) {
        this.qttTotal = qttTotal;
    }

    public double getValeurTotal() {
        return valeurTotal;
    }
    public void setValeurTotal(double valeurTotal) {
        this.valeurTotal = valeurTotal;
    }
     

    public List<StockCryptoUser> getCryptoByIdUser(int idUser) throws Exception{
        List<StockCryptoUser> stockCryptos = new ArrayList<>();
        String query="SELECT * FROM vue_crypto_par_utilisateur WHERE id_user = ?";
        UtilDb utilDb = new UtilDb();
        try (Connection conn = utilDb.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, idUser);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    StockCryptoUser stockcrypto = new StockCryptoUser();
                    stockcrypto.setIdUser(rs.getInt("id_user"));
                    stockcrypto.setIdCrypto(rs.getInt("id_cryptomonnaie"));
                    stockcrypto.setNomCrypto(rs.getString("nom_crypto"));
                    stockcrypto.setQttTotal(rs.getInt("total_quantite"));
                    stockcrypto.setValeurTotal(rs.getDouble("total_valeur"));
                    
                    stockCryptos.add(stockcrypto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stockCryptos;
    }

    public double getValeurTotalCryptoByIdUser(int idUser) throws Exception{
        double result = 0;
        String query="SELECT SUM(total_valeur) as valeur_total_cryptos FROM vue_crypto_par_utilisateur WHERE id_user = ?";
        UtilDb utilDb = new UtilDb();
        try (Connection conn = utilDb.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, idUser);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    result=rs.getDouble("valeur_total_cryptos");
                    if (rs.wasNull()) { 
                        result = 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
