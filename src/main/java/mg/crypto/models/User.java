package mg.crypto.models;

import mg.crypto.service.FirebaseConfig;
import java.util.concurrent.ExecutionException;

public class User {
    private int id;
    private String email;
    private String username;
    private String telephone;
    private String password;
    private String urlImage;

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    // Default constructor
    public User() {
    }

    // Parameterized constructor
    public User(int id, String email, String username, String telephone, String password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.telephone = telephone;
        this.password = password;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getInfoUser(int idUser) {
        FirebaseConfig firebaseConfig = new FirebaseConfig();
        try {
            return firebaseConfig.getUserById(idUser);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }


    public double checkPorteFeuilleUser(Timestamp tmp){
        double result=0;
        String sql="SELECT " +
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
                stmt.setTimestamp(1, tmp);
            stmt.setInt(2, this.getId());       

            try (ResultSet rs = stmt.executeQuery()) { 
                if (rs.next()) {
                    result=rs.getDouble("valeur_porteFeuille");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    
}