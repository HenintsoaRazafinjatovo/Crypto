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
public class StockFondUser {
    @AnnotationAttribut(colName = "id_user", insert = false)
    int idUser;
    @AnnotationAttribut(colName = "total_depot", insert = false)
    double depotTotal;
    @AnnotationAttribut(colName = "total_retrait", insert = false)
    double retraitTotal;
    @AnnotationAttribut(colName = "fond_total", insert = false)
    double fondTotal;

    public int getIdUser() {
        return idUser;
    }
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
    
    public double getDepotTotal() {
        return depotTotal;
    }
    public void setDepotTotal(double depotTotal) {
        this.depotTotal = depotTotal;
    }
    
    public double getRetraitTotal() {
        return retraitTotal;
    }
    public void setRetraitTotal(double retraitTotal) {
        this.retraitTotal = retraitTotal;
    }
    
    public double getFondTotal() {
        return fondTotal;
    }
    public void setFondTotal(double fondTotal) {
        this.fondTotal = fondTotal;
    }

    public List<StockFondUser> GetAllByIdUser(int idUser) throws Exception{
        List<StockFondUser> stockFonds = new ArrayList<>();
        String query="SELECT * FROM vue_crypto_par_utilisateur WHERE id_user = ?";
        UtilDb utilDb = new UtilDb();
        try (Connection conn = utilDb.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, idUser);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    StockFondUser stockFond = new StockFondUser();
                    stockFond.setIdUser(rs.getInt("id_user"));
                    stockFond.setDepotTotal(rs.getDouble("total_depot"));
                    stockFond.setRetraitTotal(rs.getDouble("total_retrait"));
                    stockFond.setFondTotal(rs.getDouble("fond_total"));
                    
                    stockFonds.add(stockFond);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stockFonds;
    }

    
}
