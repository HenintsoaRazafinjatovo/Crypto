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
import mg.crypto.utils.Identite;

// CREATE TABLE Histo_crypto(
//    Id_Histo_crypto SERIAL,
//    date_historique TIMESTAMP NOT NULL,
//    valeur NUMERIC(15,2)   NOT NULL,
//    id_cryptomonnaie INTEGER NOT NULL,
//    PRIMARY KEY(Id_Histo_crypto),
//    FOREIGN KEY(id_cryptomonnaie) REFERENCES cryptomonnaie(id_cryptomonnaie)
// );

public class HistoCrypto {
     @Identite(colName = "id_histo_crypto")
    @AnnotationAttribut(colName = "id_histo_crypto",insert = false)
    int idHistoCrypto;
    @AnnotationAttribut(colName = "id_crypto",insert =true)
    int idCrypto;
    @AnnotationAttribut(colName = "date_historique",insert =true)
    Timestamp dateHistorique;
    @AnnotationAttribut(colName = "valeur",insert =true)
    double valeur;
    Crypto crypto;

    public void setCrypto(Crypto crypto) {
        this.crypto = crypto;
    }


    


    
    public void setDateHistorique(Timestamp dateHistorique) {
        this.dateHistorique = dateHistorique;
    }
    public void setIdCrypto(int idCrypto) {
        this.idCrypto = idCrypto;
    }


    public void setIdHistoCrypto(int idHistoCrypto) {
        this.idHistoCrypto = idHistoCrypto;
    }

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }

    public Crypto getCrypto() {
        return crypto;
    }

    public Timestamp getDateHistorique() {
        return dateHistorique;
    }


    public int getIdCrypto() {
        return idCrypto;
    }

    public int getIdHistoCrypto() {
        return idHistoCrypto;
    }

    public double getValeur() {
        return valeur;
    }

    public List<Object> getbyinterval(int id_crypto,Timestamp max,Timestamp min)
        {   Connection c=null;
            PreparedStatement ps=null;
            ResultSet res=null;
            List<Object> histoCryptoList=new ArrayList<>(); 
            try  {
                c= new UtilDb().getConnection();                
                String query = "SELECT * FROM histo_crypto WHERE id_cryptomonnaie = ? AND date_historique <= ? AND date_historique >= ?";
                ps = c.prepareStatement(query);
                ps.setInt(1, id_crypto);
                ps.setTimestamp(2, max);
                ps.setTimestamp(3, min);
                res = ps.executeQuery();
                System.out.println( "ity ny ps"+ ps);
                
                int idcrypto=id_crypto;
                Crypto crypto = new Crypto().findById(idcrypto);
                
                while (res.next()) {
                    HistoCrypto histoCrypto = new HistoCrypto();
                    histoCrypto.setIdHistoCrypto(res.getInt("id_histo_crypto"));
                    histoCrypto.setValeur(res.getDouble("valeur"));
                    histoCrypto.setDateHistorique(res.getTimestamp("date_historique"));
                    histoCrypto.setIdCrypto(id_crypto);
                    histoCrypto.setCrypto(crypto);
                    // Assuming you have a method to set Crypto object
                    // histoCrypto.setCrypto(res.getObject("crypto", Crypto.class));
                    histoCryptoList.add(histoCrypto);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (res != null) res.close();
                    if (ps != null) ps.close();
                    if (c != null) c.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return histoCryptoList;
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
