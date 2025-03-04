package mg.crypto.models;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.jasper.tagplugins.jstl.Util;

import mg.crypto.connect.GenericDao;
import mg.crypto.connect.UtilDb;
import mg.crypto.utils.AnnotationAttribut;
import mg.crypto.utils.AnnotationClass;
import mg.crypto.utils.Identite;

@AnnotationClass(tableName = "mvt_fond")
public class MvtFond {

    @Identite(colName = "id_mvt_fond")
    @AnnotationAttribut(colName = "id_mvt_fond", insert = false)
    int idMvtFond;
    @AnnotationAttribut(colName = "id_user", insert = true)
    int idUser;
    @AnnotationAttribut(colName = "depot", insert = true)
    double depot;
    @AnnotationAttribut(colName = "retrait", insert = true)
    double retrait;
    @AnnotationAttribut(colName = "dt_mvt", insert = true)
    Timestamp dtMvt;

    @AnnotationAttribut(colName = "etat", insert = true)
    Boolean etat;

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public Boolean getEtat() {
        return etat;
    }

    String typeMvt;

    double montant;

    public void setMontant() {
        if (this.getDepot() != 0) {
            this.montant = this.getDepot();
        } else {
            this.montant = this.getRetrait();

        }
    }

    public double getMontant() {
        return montant;
    }

    @Override
    public String toString() {
        return "MvtFond{" +
                "idMvtFond=" + idMvtFond +
                ", idUser=" + idUser +
                ", depot=" + depot +
                ", retrait=" + retrait +
                ", dtMvt=" + dtMvt +
                ", typeMvt='" + typeMvt + '\'' +
                '}';
    }

    public void setTypeMvt() {
        if (this.getDepot() != 0) {
            setTypeMvt("Depot");
        } else {
            setTypeMvt("Retrait");
        }

    }

    public void setTypeMvt(String typeMvt) {
        this.typeMvt = typeMvt;
    }

    public void setDepot(double depot) {
        this.depot = depot;
    }

    public void setIdMvtFond(int idMvtFond) {
        this.idMvtFond = idMvtFond;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setRetrait(double retrait) {
        this.retrait = retrait;
    }

    public void setRetrait(BigDecimal decimal) {
        this.retrait = decimal.doubleValue();
    }

    public void setDtMvt(java.sql.Timestamp timestamp) {
        this.dtMvt = timestamp;
    }

    public void setDepot(BigDecimal decimal) {
        this.depot = decimal.doubleValue();
    }

    public double getDepot() {
        return depot;
    }

    public Timestamp getDtMvt() {
        return dtMvt;
    }

    public int getIdMvtFond() {
        return idMvtFond;
    }

    public int getIdUser() {
        return idUser;
    }

    public double getRetrait() {
        return retrait;
    }

    public String getTypeMvt() {
        return typeMvt;
    }

    public MvtFond() {
    }

    public MvtFond(int idMvtFond, int user, double depot, double retrait, Timestamp time) {
        this.setIdMvtFond(idMvtFond);
        setDepot(depot);
        setIdUser(user);
        setRetrait(retrait);
        setDtMvt(time);
    }

    public List<MvtFond> findById(int id) throws Exception {
        Connection c=new UtilDb().getConnection();
        String query = "SELECT * FROM mvt_fond WHERE id_user = ? AND etat = true";
        List<MvtFond> fonds = new ArrayList<>();
        try (PreparedStatement stmt = c.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    MvtFond fond = new MvtFond();
                    fond.setIdMvtFond(rs.getInt("id_mvt_fond"));
                    fond.setIdUser(rs.getInt("id_user"));
                    fond.setDepot(rs.getDouble("depot"));
                    fond.setRetrait(rs.getDouble("retrait"));
                    fond.setDtMvt(rs.getTimestamp("dt_mvt"));
                    fond.setEtat(rs.getBoolean("etat"));
                    fond.setTypeMvt();
                    fond.setMontant();
                    fonds.add(fond);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fonds;
    }

    public List<MvtFond> findAll() throws Exception {
        GenericDao dao = new GenericDao(new UtilDb());
        List<Object> list = dao.findAll(new MvtFond());
        List<MvtFond> obj = new ArrayList<>();
        for (Object mvtFond : list) {
            obj.add((MvtFond) mvtFond);
        }
        return obj;
    }

    public MvtFond findByIdMvt(int id) throws Exception {
        Connection c=new UtilDb().getConnection();
        String query = "SELECT * FROM mvt_fond WHERE id_mvt_fond = ?";
        MvtFond fond = null;
        try (PreparedStatement stmt = c.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    fond = new MvtFond();
                    fond.setIdMvtFond(rs.getInt("id_mvt_fond"));
                    fond.setIdUser(rs.getInt("id_user"));
                    fond.setDepot(rs.getDouble("depot"));
                    fond.setRetrait(rs.getDouble("retrait"));
                    fond.setDtMvt(rs.getTimestamp("dt_mvt"));
                    fond.setEtat(rs.getBoolean("etat"));
                    fond.setTypeMvt();
                    fond.setMontant();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fond;
    }

    public void insert() throws Exception {
        GenericDao dao = new GenericDao(new UtilDb());
        dao.save(this);
    }

    public double getFondRestant() throws Exception {
        double montant = 0;
        List<MvtFond> fonds = this.findById(this.getIdUser());
        for (MvtFond fond : fonds) {
            montant += fond.getDepot() - fond.getRetrait();
        }
        return montant;
    }

    public boolean checkFond() throws Exception {
        boolean isSufficient = false;
        String mvt = "Retrait";
        if (this.getTypeMvt().equals(mvt)) {
            String query = "SELECT fond_total FROM vue_fond_total_utilisateur WHERE id_user = ?";

            UtilDb utilDb = new UtilDb();
            try (Connection conn = utilDb.getConnection();
                    PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setInt(1, this.getIdUser());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        double fondTotal = rs.getDouble("fond_total");
                        isSufficient = fondTotal >= this.getRetrait();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isSufficient;
    }

    public void AugmentationFond() throws Exception {
        String mvt = "Depot";
        if (this.getTypeMvt().equals(mvt)) {
            this.setRetrait(0);
            this.insert();
            System.out.println("Depot effectué avec succès.");
        }
    }

    public void FaireRetrait() throws Exception {
        String mvt = "Retrait";
        if (this.getTypeMvt().equals(mvt) && this.checkFond()) {
            this.setDepot(0);
            this.insert();
            System.out.println("Retrait effectué avec succès.");
        } else {
            System.out.println("Retrait non autorisé : fonds insuffisants ou type de mouvement invalide.");
        }
    }

    public List<MvtFond> getAllFondsBeforeDate(Timestamp date) throws Exception {
        List<MvtFond> fonds = new ArrayList<>();
        String query = "SELECT * FROM mvt_fond WHERE dt_mvt <= ?";
        UtilDb utilDb = new UtilDb();
        try (Connection conn = utilDb.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setTimestamp(1, date);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    MvtFond fond = new MvtFond();
                    fond.setIdMvtFond(rs.getInt("id_mvt_fond"));
                    fond.setIdUser(rs.getInt("id_user"));
                    fond.setDepot(rs.getDouble("depot"));
                    fond.setRetrait(rs.getDouble("retrait"));
                    fond.setDtMvt(rs.getTimestamp("dt_mvt"));
                    fond.setTypeMvt();

                    fonds.add(fond);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fonds;
    }

    public List<MvtFond> getFondsByUserAndDate(int idUser, Timestamp date) throws Exception {
        List<MvtFond> fonds = new ArrayList<>();
        String query = "SELECT * FROM mvt_fond WHERE id_user = ? AND dt_mvt <= ?";
        UtilDb utilDb = new UtilDb();
        try (Connection conn = utilDb.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idUser);
            stmt.setTimestamp(2, date);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    MvtFond fond = new MvtFond();
                    fond.setIdMvtFond(rs.getInt("id_mvt_fond"));
                    fond.setIdUser(rs.getInt("id_user"));
                    fond.setDepot(rs.getDouble("depot"));
                    fond.setRetrait(rs.getDouble("retrait"));
                    fond.setDtMvt(rs.getTimestamp("dt_mvt"));
                    fond.setTypeMvt();

                    fonds.add(fond);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fonds;
    }

}
