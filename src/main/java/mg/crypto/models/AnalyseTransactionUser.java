package mg.crypto.models;

import mg.crypto.connect.GenericDao;
import mg.crypto.connect.UtilDb;
import mg.crypto.utils.AnnotationAttribut;
import mg.crypto.utils.AnnotationClass;

@AnnotationClass(tableName ="vue_valeur_portefeuille_utilisateur")
public class AnalyseTransactionUser{
    @AnnotationAttribut(colName = "id_user", insert = true)
    int idUser;
    @AnnotationAttribut(colName = "total_vente_montant", insert = true)
    double totalVente;
    @AnnotationAttribut(colName = "total_achat_montant", insert = true)
    double totalAchat;
    @AnnotationAttribut(colName = "valeur_portefeuille", insert = true)
    double valeurPorteFeuille;

    public AnalyseTransactionUser(){
    }

    public int getIdUser() {
        return idUser;
    }
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public double getTotalVente() {
        return totalVente;
    }
    public void setTotalVente(double totalVente) {
        this.totalVente = totalVente;
    }

    public double getTotalAchat() {
        return totalAchat;
    }
    public void setTotalAchat(double totalAchat) {
        this.totalAchat = totalAchat;
    }

    public double getValeurPorteFeuille() {
        return valeurPorteFeuille;
    }
    public void setValeurPorteFeuille(double valeurPorteFeuille) {
        this.valeurPorteFeuille = valeurPorteFeuille;
    }

    public List<AnalyseTransactionUser> FindAll() throws Exception {
        GenericDao dao = new GenericDao(new UtilDb());
        List<Object> objects = dao.findAll(new AnalyseTransactionUser());
        List<AnalyseTransactionUser> analyse = new ArrayList<>();
        for (Object obj : objects) {
            analyse.add((AnalyseTransactionUser) obj);
        }
        return analyse;
    }

    public AnalyseTransactionUser getValeurTotalTransactionUser(int iduser,Timestamp dtmax){
        AnalyseTransactionUser transaction = new AnalyseTransactionUser();

        String sql = "SELECT " +
                "m.id_user, " +
                "SUM(CASE WHEN m.isVente = FALSE THEN m.montant ELSE 0 END) AS total_achat_montant, " +
                "SUM(CASE WHEN m.isVente = TRUE THEN m.montant ELSE 0 END) AS total_vente_montant, " +
                "(SUM(CASE WHEN m.isVente = FALSE THEN m.montant ELSE 0 END) - " +
                " SUM(CASE WHEN m.isVente = TRUE THEN m.montant ELSE 0 END)) AS valeur_portefeuille " +
                "FROM mvt_transaction m " +
                "WHERE m.date_transaction <= ? " +
                "AND m.id_user = ? " +
                "GROUP BY m.id_user " +
                "ORDER BY m.id_user";

        UtilDb utilDb = new UtilDb();

        try (Connection conn = utilDb.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, dtmax);  
            stmt.setInt(2, iduser);       

            try (ResultSet rs = stmt.executeQuery()) { 
                if (rs.next()) {
                    transaction.setIdUser(rs.getInt("id_user"));
                    transaction.setTotalAchat(rs.getDouble("total_achat_montant"));
                    transaction.setTotalVente(rs.getDouble("total_vente_montant"));
                    transaction.setValeurPorteFeuille(rs.getDouble("valeur_portefeuille"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transaction;
    }

    public List<AnalyseTransactionUser> findBeforeDate(Timestamp dtmax) {
        List<AnalyseTransactionUser> results = new ArrayList<>();
        
        String sql = "SELECT " +
                     "m.id_user, " +
                     "SUM(CASE WHEN m.isVente = FALSE THEN m.montant ELSE 0 END) AS total_achat_montant, " +
                     "SUM(CASE WHEN m.isVente = TRUE THEN m.montant ELSE 0 END) AS total_vente_montant, " +
                     "(SUM(CASE WHEN m.isVente = FALSE THEN m.montant ELSE 0 END) - " +
                     " SUM(CASE WHEN m.isVente = TRUE THEN m.montant ELSE 0 END)) AS valeur_portefeuille " +
                     "FROM mvt_transaction m " +
                     "WHERE m.date_transaction <= ? " +
                     "GROUP BY m.id_user " +
                     "ORDER BY m.id_user";
    
        UtilDb utilDb = new UtilDb();
        try (Connection conn = utilDb.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, dtmax);        
            
            try (ResultSet rs = stmt.executeQuery()) { 
                while (rs.next()) {
                    AnalyseTransactionUser transaction = new AnalyseTransactionUser();
                    
                    transaction.setIdUser(rs.getInt("id_user"));
                    transaction.setTotalAchat(rs.getDouble("total_achat_montant"));
                    transaction.setTotalVente(rs.getDouble("total_vente_montant"));
                    transaction.setValeurPorteFeuille(rs.getDouble("valeur_portefeuille"));

                    results.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return results;
    }
    

}