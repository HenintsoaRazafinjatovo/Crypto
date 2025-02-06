package mg.crypto.models;

import java.math.BigDecimal;
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

@AnnotationClass(tableName = "v_mvt_fond_complet")
public class MvtFondComplet {
    @AnnotationAttribut(colName = "id_mvt_fond", insert = false)
    private int idMvtFond;
    @AnnotationAttribut(colName = "id_user", insert = true)
    private int idUser;
    @AnnotationAttribut(colName = "type", insert = false)
    private String type;
    @AnnotationAttribut(colName = "montant", insert = false)
    private BigDecimal montant;
    @AnnotationAttribut(colName = "dt_mvt", insert = true)
    private Timestamp dtMvt;
    @AnnotationAttribut(colName = "etatText", insert = false)
    private String etatText;

    // Getters and setters

    public int getIdMvtFond() {
        return idMvtFond;
    }

    public void setIdMvtFond(int idMvtFond) {
        this.idMvtFond = idMvtFond;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public Timestamp getDtMvt() {
        return dtMvt;
    }

    public void setDtMvt(Timestamp dtMvt) {
        this.dtMvt = dtMvt;
    }

    public String getEtatText() {
        return etatText;
    }

    public void setEtatText(String etatText) {
        this.etatText = etatText;
    }

    public List<MvtFondComplet> findAll() throws Exception {
        GenericDao g = new GenericDao(new UtilDb());
        List<Object> o = g.findAll(this);
        return (List<MvtFondComplet>) (List<?>) o;
    }

    public List<MvtFondComplet> filter(String type, String etat) throws Exception {
        List<String> ls = new ArrayList();
        ls.add("1=1");
        if (type != null && !type.isEmpty()) {
            ls.add("type = '" + type + "'");
        }

        if (etat != null && !etat.isEmpty()) {
            ls.add("etatText = '" + etat + "'");
        }
        //convertit la liste en String[]
        String[] tab = new String[ls.size()];
        tab = ls.toArray(tab);
        String finalCondition=String.join(" AND ",tab);
        String query = "SELECT * FROM v_mvt_fond_complet WHERE "+finalCondition;
        UtilDb utilDb = new UtilDb();
        try (Connection conn = utilDb.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
            List<MvtFondComplet> result = new ArrayList<>();
            while (rs.next()) {
                MvtFondComplet mvt = new MvtFondComplet();
                mvt.setIdMvtFond(rs.getInt("id_mvt_fond"));
                mvt.setIdUser(rs.getInt("id_user"));
                mvt.setType(rs.getString("type"));
                mvt.setMontant(rs.getBigDecimal("montant"));
                mvt.setDtMvt(rs.getTimestamp("dt_mvt"));
                mvt.setEtatText(rs.getString("etatText"));
                result.add(mvt);
            }
            return result;
            }
        } catch (SQLException e) {
            throw e;
        }

    }

    public void updateEtat(int idMvtFond, Boolean newEtat) throws Exception {
        String query = "UPDATE mvt_fond SET etat = ? WHERE id_mvt_fond = ?";
        UtilDb utilDb = new UtilDb();
        try (Connection conn = utilDb.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setBoolean(1, newEtat);
            stmt.setInt(2, idMvtFond);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Erreur lors de la mise à jour de l'état du mouvement de fonds.");
        }
    }
}