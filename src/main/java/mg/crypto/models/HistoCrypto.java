package mg.crypto.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import mg.crypto.connect.GenericDao;
import mg.crypto.connect.UtilDb;
import mg.crypto.utils.AnnotationAttribut;
import mg.crypto.utils.AnnotationClass;

@AnnotationClass(tableName = "Histo_crypto")
public class HistoCrypto {
    @AnnotationAttribut(colName = "Id_Histo_crypto", insert = false)
    int idHistoCrypto;
    @AnnotationAttribut(colName = "date_historique", insert = true)
    Timestamp dateHistorique;
    @AnnotationAttribut(colName = "valeur", insert = true)
    double valeur;
    @AnnotationAttribut(colName = "id_cryptomonnaie", insert = true)
    int idCrypto;
    public int getIdHistoCrypto() {
        return idHistoCrypto;
    }
    public void setIdHistoCrypto(int idHistoCrypto) {
        this.idHistoCrypto = idHistoCrypto;
    }
    public Timestamp getDateHistorique() {
        return dateHistorique;
    }
    public void setDateHistorique(Timestamp dateHistorique) {
        this.dateHistorique = dateHistorique;
    }
    public double getValeur() {
        return valeur;
    }
    public void setValeur(double valeur) {
        this.valeur = valeur;
    }
    public int getIdCrypto() {
        return idCrypto;
    }
    public void setIdCrypto(int idCrypto) {
        this.idCrypto = idCrypto;
    }

    public List<HistoCrypto> findAll(Connection c) throws Exception{
        GenericDao g=new GenericDao(new UtilDb());
        List<Object> obj=g.findAll(c,this);
        List<HistoCrypto> res=new ArrayList<>();
        for (Object object : obj) {
            res.add((HistoCrypto)object);
        }
        return res;
    }

    public  List<HistoCrypto> findLastTenByCrypto(Connection conn,int cryptoId) throws Exception {
        List<HistoCrypto> histoCryptos = new ArrayList<>();
        String query = "SELECT * FROM Histo_crypto WHERE id_cryptomonnaie = ? ORDER BY date_historique DESC LIMIT 6";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, cryptoId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    HistoCrypto histoCrypto = new HistoCrypto();
                    histoCrypto.setIdHistoCrypto(rs.getInt("Id_Histo_crypto"));
                    histoCrypto.setDateHistorique(rs.getTimestamp("date_historique"));
                    histoCrypto.setValeur(rs.getDouble("valeur"));
                    histoCrypto.setIdCrypto(rs.getInt("id_cryptomonnaie"));
                    histoCryptos.add(histoCrypto);
                }
            }
        }
        return histoCryptos;
    }

    public  HistoCrypto findLastByCrypto(Connection conn,int cryptoId) throws Exception {
        String query = "SELECT * FROM Histo_crypto WHERE id_cryptomonnaie = ? ORDER BY date_historique DESC LIMIT 1";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, cryptoId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    HistoCrypto histoCrypto = new HistoCrypto();
                    histoCrypto.setIdHistoCrypto(rs.getInt("Id_Histo_crypto"));
                    histoCrypto.setDateHistorique(rs.getTimestamp("date_historique"));
                    histoCrypto.setValeur(rs.getDouble("valeur"));
                    histoCrypto.setIdCrypto(rs.getInt("id_cryptomonnaie"));
                    return histoCrypto;
                }
            }
        }
        return null;
    }


    public  List<HistoCrypto> findLastTenEachCrypto(Connection conn) throws Exception {
        List<HistoCrypto> histoCryptos = new ArrayList<>();
        String query = "SELECT * FROM Histo_crypto WHERE (id_cryptomonnaie, date_historique) IN " +
                       "(SELECT id_cryptomonnaie, date_historique FROM Histo_crypto ORDER BY date_historique DESC LIMIT 6)";
        
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                HistoCrypto histoCrypto = new HistoCrypto();
                histoCrypto.setIdHistoCrypto(rs.getInt("Id_Histo_crypto"));
                histoCrypto.setDateHistorique(rs.getTimestamp("date_historique"));
                histoCrypto.setValeur(rs.getDouble("valeur"));
                histoCrypto.setIdCrypto(rs.getInt("id_cryptomonnaie"));
                histoCryptos.add(histoCrypto);
            }
        }
        return histoCryptos;
    }

    public void insert(Connection c) throws Exception{
        GenericDao g=new GenericDao(new UtilDb());
        g.save(c,this);
    }
}
