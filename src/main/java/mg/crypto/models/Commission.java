package mg.crypto.models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import mg.crypto.connect.UtilDb;
import mg.crypto.utils.AnnotationAttribut;
import mg.crypto.utils.Identite;

public class Commission {
    @Identite(colName = "id_cryptomonnaie")
    @AnnotationAttribut(colName = "id_commission", insert = false)
    int idCommission;
    @AnnotationAttribut(colName = "dt_commission", insert = true)
    Date dateCommission;
    @AnnotationAttribut(colName = "type_commission", insert = true)
    boolean typeCommission;
    @AnnotationAttribut(colName = "montant_commission", insert = true)
    double valeur;
    @AnnotationAttribut(colName = "id_cryptomonnaie", insert = true)
    int id_crypto;

    Crypto crypto;

    public void setCrypto(Crypto crypto) {
        this.crypto = crypto;
    }

    public void setDateCommission(Date dateCommission) {
        this.dateCommission = dateCommission;
    }

    public void setIdCommission(int idCommission) {
        this.idCommission = idCommission;
    }

    public void setId_crypto(int id_crypto) {
        this.id_crypto = id_crypto;
    }

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }

    public double getValeur() {
        return valeur;
    }
    public void setTypeCommission(boolean typeCommission) {
        this.typeCommission = typeCommission;
    }
    public Crypto getCrypto() {
        return crypto;
    }

    public Date getDateCommission() {
        return dateCommission;
    }
    public int getIdCommission() {
        return idCommission;
    }
    public int getId_crypto() {
        return id_crypto;
    }

    public List<Object> getbyinterval(int id_crypto,Timestamp max,Timestamp min)
        {   Connection c=null;
            PreparedStatement ps=null;
            ResultSet res=null;
            List<Object> histoCryptoList=new ArrayList<>(); 
            try  {
                c= new UtilDb().getConnection();                
                String query = "SELECT * FROM commission WHERE id_cryptomonnaie = ? AND dt_commission <= ? AND dt_commission >= ?";
                ps = c.prepareStatement(query);
                ps.setInt(1, id_crypto);
                ps.setTimestamp(2, max);
                ps.setTimestamp(3, min);
                res = ps.executeQuery();
                System.out.println( "ity ny ps"+ ps);
                
                int idcrypto=id_crypto;
                Crypto crypto = new Crypto().findById(idcrypto);
                
                while (res.next()) {
                    Commission commission= new Commission();
                    commission.setIdCommission(res.getInt("id_commission"));
                    commission.setDateCommission(res.getDate("dt_commission"));
                    commission.setValeur(res.getDouble("valeur_commission"));
                    commission.setId_crypto(res.getInt("id_cryptomonnaie"));
                    commission.setCrypto(crypto);
                    histoCryptoList.add(commission);
                    
                    // Assuming you have a method to set Crypto object
                    // histoCrypto.setCrypto(res.getObject("crypto", Crypto.class));
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
    
}
