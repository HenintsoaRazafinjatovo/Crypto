package mg.crypto.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import mg.crypto.models.MvtFond;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DepotRetraitService {

    private static final String FIRESTORE_URL = "https://firestore.googleapis.com/v1/projects/crypto-4ff95/databases/(default)/documents:runQuery";
    private final ObjectMapper objectMapper = new ObjectMapper();

    private MvtFond convertJsonToMvtFond(JsonNode jsonNode) {
        MvtFond mvtFond = new MvtFond();
        mvtFond.setIdUser(jsonNode.path("id_user").path("integerValue").asInt());
        mvtFond.setDepot(jsonNode.path("depot").path("integerValue").asDouble());
        mvtFond.setRetrait(jsonNode.path("retrait").path("integerValue").asDouble());
        mvtFond.setDtMvt(Timestamp.from(Instant.parse(jsonNode.path("dt_mvt").path("timestampValue").asText())));
        return mvtFond;
    }

    @Scheduled(fixedRate = 60000)
    public void fetchDocuments() throws Exception {
        URL url = new URL(FIRESTORE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setDoOutput(true);

        ObjectNode structuredQuery = objectMapper.createObjectNode();
        structuredQuery.putArray("from").addObject().put("collectionId", "dmd_fond");
        ObjectNode where = structuredQuery.putObject("where");
        ObjectNode fieldFilter = where.putObject("fieldFilter");
        fieldFilter.putObject("field").put("fieldPath", "wasFetched");
        fieldFilter.put("op", "EQUAL");
        fieldFilter.putObject("value").put("booleanValue", false);

        ObjectNode body = objectMapper.createObjectNode();
        body.set("structuredQuery", structuredQuery);

        String jsonInputString = objectMapper.writeValueAsString(body);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            String response = conn.getResponseMessage();
            throw new RuntimeException("Failed : HTTP error code : " + response);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            JsonNode jsonResponse = objectMapper.readTree(response.toString());
            if (jsonResponse.isArray() && jsonResponse.size() > 0 && jsonResponse.get(0).has("document")) {
                insertDocuments(jsonResponse);
                updateDocuments(jsonResponse);
            }
        }
    }

    private void insertDocuments(JsonNode jsonResponse) throws Exception {
        for (JsonNode document : jsonResponse) {
            JsonNode fields = document.path("document").path("fields");
            MvtFond mvtFond = convertJsonToMvtFond(fields);
            mvtFond.insert();
        }
    }

    private void updateDocuments(JsonNode jsonResponse) throws Exception {
        for (JsonNode document : jsonResponse) {
            String documentName = document.path("document").path("name").asText();
            URL url = new URL("https://firestore.googleapis.com/v1/" + documentName);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("X-HTTP-Method-Override", "PATCH");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            // Get all existing fields and add wasFetched field
            ObjectNode fields = (ObjectNode) document.path("document").path("fields");
            fields.putObject("wasFetched").put("booleanValue", true);

            ObjectNode body = objectMapper.createObjectNode();
            body.set("fields", fields);

            String jsonInputString = objectMapper.writeValueAsString(body);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                String response = conn.getResponseMessage();
                throw new RuntimeException("Failed : HTTP error code : " + response);
            }
        }
    }

    public void insertNotifDmdValidated(MvtFond mvtFond, boolean etat) throws Exception {
        URL url = new URL(
                "https://firestore.googleapis.com/v1/projects/crypto-4ff95/databases/(default)/documents/notif_dmd");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setDoOutput(true);

        String texte;
        if (mvtFond.getDepot() == 0) {
            texte = "Votre retrait de valeur " + mvtFond.getRetrait() + (etat ? " est validé" : " est refusé");
        } else if (mvtFond.getRetrait() == 0) {
            texte = "Votre dépôt de valeur " + mvtFond.getDepot() + (etat ? " est validé" : " est refusé");
        } else {
            texte = "Mouvement de fonds non spécifié";
        }

        ObjectNode fields = objectMapper.createObjectNode();
        fields.putObject("id_user").put("integerValue", mvtFond.getIdUser());
        fields.putObject("wasFetched").put("booleanValue", false);
        fields.putObject("texte").put("stringValue", texte);

        ObjectNode body = objectMapper.createObjectNode();
        body.set("fields", fields);

        String jsonInputString = objectMapper.writeValueAsString(body);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            String response = conn.getResponseMessage();
            throw new RuntimeException("Failed : HTTP error code : " + response);
        }
    }

}