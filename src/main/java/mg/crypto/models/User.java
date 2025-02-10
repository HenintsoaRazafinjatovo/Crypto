package mg.crypto.models;

import mg.crypto.connect.GenericDao;
import mg.crypto.connect.UtilDb;

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class User {
    private int id;
    private String email;
    private String username;
    private String telephone;
    private String password;
    private String urlImage;
    private static final String FIRESTORE_URL = "https://firestore.googleapis.com/v1/projects/crypto-4ff95/databases/(default)/documents/users";
    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    // Default constructor
    public User() {
    }

    public List<User> getAllUsers() throws Exception {
        List<User> users = new ArrayList<>();
        URL url = new URL(FIRESTORE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode responseJson = mapper.readTree(conn.getInputStream());
            JsonNode documents = responseJson.get("documents");

            if (documents.isArray()) {
                for (JsonNode document : documents) {
                    JsonNode fields = document.get("fields");
                    int id = fields.get("id_user").get("integerValue").asInt();
                    String email = fields.get("email_user").get("stringValue").asText();
                    String telephone = fields.get("telephone_user").get("stringValue").asText();
                    String password = fields.get("mdp_user").get("stringValue").asText();
                    // String urlImage = fields.get("image").get("stringValue").asText();

                    User user = new User(id, email, username, telephone, password);
                    /* user.setUrlImage(urlImage); */
                    users.add(user);
                }
            }
        } else {
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseMessage());
        }

        return users;
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

    // public User getInfoUser(int idUser) {
    //     FirebaseConfig firebaseConfig = new FirebaseConfig();
    //     try {
    //         return firebaseConfig.getUserById(idUser);
    //     } catch (InterruptedException | ExecutionException e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }


    public double checkPorteFeuilleUser(Timestamp tmp) throws Exception{
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