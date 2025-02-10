package mg.crypto.models;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;

import mg.crypto.connect.GenericDao;
import mg.crypto.connect.UtilDb;
import mg.crypto.utils.AnnotationAttribut;
import mg.crypto.utils.AnnotationClass;
import mg.crypto.utils.Identite;

@AnnotationClass(tableName = "mvt_transaction")
public class MvtTransaction {
    @Identite(colName = "id_mvt_transaction")
    @AnnotationAttribut(colName = "id_mvt_transaction", insert = false)
    int idMvtTransaction;
    @AnnotationAttribut(colName = "id_user", insert = true)
    int idUser;
    @AnnotationAttribut(colName = "id_cryptomonnaie", insert = true)
    int idCrypto;
    @AnnotationAttribut(colName = "qtt", insert = true)
    int quantite;
    @AnnotationAttribut(colName = "montant", insert = true)
    double montant;
    @AnnotationAttribut(colName = "isVente", insert = true)
    boolean type;
    @AnnotationAttribut(colName = "date_transaction", insert = true)
    Timestamp date;
    private static final String FIRESTORE_URL = "https://firestore.googleapis.com/v1/projects/crypto-4ff95/databases/(default)/documents/mvt_transaction";
    private static final String FIRESTORE_URL2 = "https://firestore.googleapis.com/v1/projects/crypto-4ff95/databases/(default)/documents/user_favoris";
    private static final String FIRESTORE_URL3 = "https://firestore.googleapis.com/v1/projects/crypto-4ff95/databases/(default)/documents/notif_operation";


    public int[] getCryptoFavByUser() throws Exception {
        List<Integer> cryptoIds = new ArrayList<>();
        URL url = new URL(FIRESTORE_URL2 + "?where=id_user=" + this.idUser);
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
                    int idCrypto = fields.get("id_crypto").get("integerValue").asInt();
                    cryptoIds.add(idCrypto);
                }
            }
        } else {
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseMessage());
        }

        return cryptoIds.stream().mapToInt(i -> i).toArray();
    }
    public boolean isFavCryptoUser()throws Exception{
        int[] cryptoIds = getCryptoFavByUser();
        for (int id : cryptoIds) {
            if (id == this.idCrypto) {
                return true;
            }
        }
        return false;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    public MvtTransaction(int idMvtTransaction, int idUser, int idCrypto, int quantite, double montant, boolean type,
            Timestamp date) {
        this.idMvtTransaction = idMvtTransaction;
        this.idUser = idUser;
        this.idCrypto = idCrypto;
        this.quantite = quantite;
        this.montant = montant;
        this.type = type;
        this.date = date;
    }

    public MvtTransaction(int idUser, int idCrypto, int quantite, double montant, boolean type, Timestamp date) {
        this.idUser = idUser;
        this.idCrypto = idCrypto;
        this.quantite = quantite;
        this.montant = montant;
        this.type = type;
        this.date = date;
    }

    public MvtTransaction() {
    }

    public int getIdMvtTransaction() {
        return idMvtTransaction;
    }

    public void setIdMvtTransaction(int idMvtTransaction) {
        this.idMvtTransaction = idMvtTransaction;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public double getMontant() {
        return montant;
    }
    public boolean getType() {
        return type;
    }
    public void setMontant(double montant) {
        this.montant = montant;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getIdCrypto() {
        return idCrypto;
    }

    public void setIdCrypto(int idCrypto) {
        this.idCrypto = idCrypto;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public boolean isType() {
        return type;
    }
    public void setType(boolean type) {
        this.type = type;
    }

    public void insert()throws Exception{
        GenericDao dao = new GenericDao(new UtilDb());
        dao.save(this);
    }

    public MvtTransaction findById(int id) throws Exception {
        GenericDao dao = new GenericDao(new UtilDb());
        MvtTransaction c = new MvtTransaction();
        c.setIdMvtTransaction(id);
        List<Object> objects = dao.findAllWithCriteria(c);
        if (objects.isEmpty()) {
            return null;
        }
        return (MvtTransaction) objects.get(0);
    }

    public void update() throws Exception{
        GenericDao dao = new GenericDao(new UtilDb());
        dao.update(this);
    }

    /* public List<MvtTransaction> findById(int id) throws Exception{
        GenericDao dao= new GenericDao(new UtilDb());
        MvtTransaction f= new MvtTransaction();
        f.setIdUser(id);
        List<MvtTransaction> obj= new ArrayList<>();
        List<Object> mvt= dao.findAllWithCriteria(f);
        for (Object mvtTrans : mvt) {
            ((MvtTransaction)mvtTrans).getType();
            obj.add((MvtTransaction)mvtTrans);
            System.out.println(((MvtTransaction)mvtTrans).getType());
        }
        return obj;
    } */

    public MvtTransaction findVenteByIdUser(int iduser)throws Exception{
        MvtTransaction transaction = new MvtTransaction();

        String sql = "SELECT *  " +
                "FROM mvt_transaction " +
                "WHERE id_user = ? " +
                "AND isVente = true " +
                "ORDER BY date_transaction DESC" +
                " LIMIT 1" ;

        UtilDb utilDb = new UtilDb();

        try (Connection conn = utilDb.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
 
            stmt.setInt(1, iduser);       

            try (ResultSet rs = stmt.executeQuery()) { 
                if (rs.next()) {
                    transaction.setIdMvtTransaction(rs.getInt("id_mvt_transaction"));
                    transaction.setIdUser(rs.getInt("id_user"));
                    transaction.setIdCrypto(rs.getInt("id_cryptomonnaie"));
                    transaction.setMontant(rs.getDouble("montant"));
                    transaction.setType(rs.getBoolean("isVente"));
                    transaction.setQuantite(rs.getInt("qtt"));
                    transaction.setDate(rs.getTimestamp("date_transaction"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transaction;
    }

    public MvtTransaction findAchatByIdUser(int iduser)throws Exception{
        MvtTransaction transaction = new MvtTransaction();

        String sql = "SELECT *  " +
                "FROM mvt_transaction " +
                "WHERE id_user = ? " +
                "AND isVente = false " +
                "ORDER BY date_transaction DESC" +
                " LIMIT 1" ;

        UtilDb utilDb = new UtilDb();

        try (Connection conn = utilDb.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
 
            stmt.setInt(1, iduser);       

            try (ResultSet rs = stmt.executeQuery()) { 
                if (rs.next()) {
                    transaction.setIdMvtTransaction(rs.getInt("id_mvt_transaction"));
                    transaction.setIdUser(rs.getInt("id_user"));
                    transaction.setIdCrypto(rs.getInt("id_cryptomonnaie"));
                    transaction.setMontant(rs.getDouble("montant"));
                    transaction.setType(rs.getBoolean("isVente"));
                    transaction.setQuantite(rs.getInt("qtt"));
                    transaction.setDate(rs.getTimestamp("date_transaction"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transaction;
    }

    public void sendPostVente()throws Exception{
        URL url = new URL(FIRESTORE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setDoOutput(true);

        ObjectNode fields = objectMapper.createObjectNode();
        //colonnes
        fields.set("id_mvt_transaction",createIntegerField(this.getIdMvtTransaction()));
        fields.set("id_cryptomonnaie", createIntegerField(this.getIdCrypto()));
        fields.set("id_user",createIntegerField(this.getIdUser()));
        fields.set("qtt",createIntegerField(this.getQuantite()));
        fields.set("montant",createDoubleField(this.getMontant()));
        fields.set("date_transaction",createTimestampField(this.getDate()));
        fields.set("isVente",createBooleanField(true));

        ObjectNode body = objectMapper.createObjectNode();
        body.set("fields", fields);

        String jsonInputString = objectMapper.writeValueAsString(body);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            String test = conn.getResponseMessage();
            throw new RuntimeException("Failed : HTTP error code : " + test);
        }
    }

    public void vente() throws Exception{
        if(this.isType()){
            this.insert();
            MvtFond mvt= new MvtFond();
            mvt.setDepot(this.getMontant());
            mvt.setIdUser(this.getIdUser());
            mvt.setDtMvt(this.getDate());
            String type="Depot";
            mvt.setTypeMvt(type);
            mvt.setDepot(this.findVenteByIdUser(mvt.getIdUser()).getMontant());
            mvt.AugmentationFond();
            System.out.println("Vente fait avec succès");
        }else{
            System.out.println("Vente n'est pas insérer");
        }
    }

    public void sendPostAchat()throws Exception{
        URL url = new URL(FIRESTORE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setDoOutput(true);

        ObjectNode fields = objectMapper.createObjectNode();
        //colonnes
        fields.set("id_mvt_transaction",createIntegerField(this.getIdMvtTransaction()));
        fields.set("id_cryptomonnaie", createIntegerField(this.getIdCrypto()));
        fields.set("id_user",createIntegerField(this.getIdUser()));
        fields.set("qtt",createIntegerField(this.getQuantite()));
        fields.set("montant",createDoubleField(this.getMontant()));
        fields.set("date_transaction",createTimestampField(this.getDate()));
        fields.set("isVente",createBooleanField(false));

        ObjectNode body = objectMapper.createObjectNode();
        body.set("fields", fields);

        String jsonInputString = objectMapper.writeValueAsString(body);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            String test = conn.getResponseMessage();
            throw new RuntimeException("Failed : HTTP error code : " + test);
        }
    }
    public void sendPostNotifOperation(String message)throws Exception{
        URL url = new URL(FIRESTORE_URL3);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setDoOutput(true);

        ObjectNode fields = objectMapper.createObjectNode();
        //colonnes
        
        fields.set("id_user",createIntegerField(this.getIdUser()));
        fields.set("texte",createStringField(message));
        fields.set("wasFetched", createBooleanField(false));

     

        ObjectNode body = objectMapper.createObjectNode();
        body.set("fields", fields);

        String jsonInputString = objectMapper.writeValueAsString(body);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            String test = conn.getResponseMessage();
            throw new RuntimeException("Failed : HTTP error code : " + test);
        }
    }

    public void achat() throws Exception{
        if(this.isType()){
            System.out.println("Achat n'est pas insérer");
        }else{
            this.insert();
            MvtFond mvt= new MvtFond();
            mvt.setDepot(this.getMontant());
            mvt.setIdUser(this.getIdUser());
            mvt.setDtMvt(this.getDate());
            String type="Retrait";
            mvt.setTypeMvt(type);
            mvt.setRetrait(this.findAchatByIdUser(mvt.getIdUser()).getMontant());
            mvt.FaireRetrait();
            System.out.println("Achat fait avec succès");
        }
    }

    private ObjectNode createStringField(String value) {
        ObjectNode field = objectMapper.createObjectNode();
        field.put("stringValue", value);
        return field;
    }

    private ObjectNode createTimestampField(Timestamp timestamp) {
        ObjectNode field = objectMapper.createObjectNode();
        String formattedTimestamp = DateTimeFormatter.ISO_INSTANT.format(timestamp.toInstant());
        field.put("timestampValue", formattedTimestamp);
        return field;
    }

    private ObjectNode createIntegerField(int value) {
        ObjectNode field = objectMapper.createObjectNode();
        field.put("integerValue", value);
        return field;
    }

    private ObjectNode createDoubleField(double value) {
        ObjectNode field = objectMapper.createObjectNode();
        field.put("doubleValue", value);
        return field;
    }

    private ObjectNode createBooleanField(boolean value) {
        ObjectNode field = objectMapper.createObjectNode();
        field.put("booleanValue", value);
        return field;
    }
    
   
    public List<MvtTransaction> getAllTransactionsBeforeDate(Timestamp date2) throws Exception {
        List<MvtTransaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM mvt_transaction WHERE date_transaction <= ?";
        UtilDb utilDb = new UtilDb();
        try (Connection conn = utilDb.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setTimestamp(1, date2);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    MvtTransaction transaction = new MvtTransaction();
                    transaction.setIdMvtTransaction(rs.getInt("id_mvt_transaction"));
                    transaction.setIdUser(rs.getInt("id_user"));
                    transaction.setIdCrypto(rs.getInt("id_cryptomonnaie"));
                    transaction.setQuantite(rs.getInt("qtt"));
                    transaction.setMontant(rs.getDouble("montant"));
                    transaction.setType(rs.getBoolean("isVente"));
                    transaction.setDate(rs.getTimestamp("date_transaction"));
                    
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public List<MvtTransaction> getTransactionsByUserAndDate(int idUser, Timestamp date) throws Exception {
        List<MvtTransaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM mvt_transaction WHERE id_user = ? AND date_transaction <= ?";
        UtilDb utilDb = new UtilDb();
        try (Connection conn = utilDb.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, idUser);
            stmt.setTimestamp(2, date);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    MvtTransaction transaction = new MvtTransaction();
                    transaction.setIdMvtTransaction(rs.getInt("id_mvt_transaction"));
                    transaction.setIdUser(rs.getInt("id_user"));
                    transaction.setIdCrypto(rs.getInt("id_cryptomonnaie"));
                    transaction.setQuantite(rs.getInt("qtt"));
                    transaction.setMontant(rs.getDouble("montant"));
                    transaction.setType(rs.getBoolean("isVente"));
                    transaction.setDate(rs.getTimestamp("date_transaction"));
                    
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public List<MvtTransaction> getTransactionsByCryptoAndDate(int idCrypto, Timestamp date) throws Exception {
        List<MvtTransaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM mvt_transaction WHERE id_cryptomonnaie = ? AND date_transaction <= ?";
        UtilDb utilDb = new UtilDb();
        try (Connection conn = utilDb.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, idCrypto);
            stmt.setTimestamp(2, date);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    MvtTransaction transaction = new MvtTransaction();
                    transaction.setIdMvtTransaction(rs.getInt("id_mvt_transaction"));
                    transaction.setIdUser(rs.getInt("id_user"));
                    transaction.setIdCrypto(rs.getInt("id_cryptomonnaie"));
                    transaction.setQuantite(rs.getInt("qtt"));
                    transaction.setMontant(rs.getDouble("montant"));
                    transaction.setType(rs.getBoolean("isVente"));
                    transaction.setDate(rs.getTimestamp("date_transaction"));
                    
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

   

    
}
