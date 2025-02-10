package mg.crypto.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import mg.crypto.connect.UtilDb;
import mg.crypto.models.Crypto;
import mg.crypto.models.HistoCrypto;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class CryptoService {

    private static final String FIRESTORE_URL = "https://firestore.googleapis.com/v1/projects/crypto-4ff95/databases/(default)/documents/historique_crypto";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CryptoService() {
    }

    /* @Scheduled(fixedRate = 60000) // Exécuter toutes les 10 secondes
    public void updateCryptoValues() throws Exception {
        Connection c = new UtilDb().getConnection();
        List<Crypto> cryptos = new Crypto().FindAll();
        for (Crypto crypto : cryptos) {
            HistoCrypto h = new HistoCrypto();
            h.setIdCrypto(crypto.getIdCrypto());
            h.setDateHistorique(new Timestamp(System.currentTimeMillis()));
            h.setValeur(crypto.getValue().doubleValue());
            h.insert(c);
            System.out.println("Updated " + crypto.getNomCrypto() + " value");
            sendPostRequest(h, crypto);
        }
    } */

    private void sendPostRequest(HistoCrypto histoCrypto, Crypto crypto) throws Exception {
        URL url = new URL(FIRESTORE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setDoOutput(true);

        ObjectNode fields = objectMapper.createObjectNode();
        fields.set("date_historique", createTimestampField(histoCrypto.getDateHistorique()));
        fields.set("id_crypto", createIntegerField(crypto.getIdCrypto()));
        fields.set("nom_crypto", createStringField(crypto.getNomCrypto()));
        fields.set("valeur", createDoubleField(histoCrypto.getValeur()));

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

    private ObjectNode createStringField(String value) {
        ObjectNode field = objectMapper.createObjectNode();
        field.put("stringValue", value);
        return field;
    }

    private ObjectNode createDoubleField(double value) {
        ObjectNode field = objectMapper.createObjectNode();
        field.put("doubleValue", value);
        return field;
    }
}