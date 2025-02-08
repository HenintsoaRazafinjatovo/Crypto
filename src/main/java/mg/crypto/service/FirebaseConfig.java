package mg.crypto.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;

import jakarta.annotation.PostConstruct;
import mg.crypto.models.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initializeFirebase() throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream("src\\main\\resources\\crypto-4ff95-firebase-adminsdk-fbsvc-aac0635094.json");

                FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);
    }
   

    // Méthode pour obtenir une instance de Firestore
    public Firestore getFirestore() {
        return FirestoreClient.getFirestore();
    }

    public User getUserById(int idUser) throws InterruptedException, ExecutionException {
        Firestore db = getFirestore();
        DocumentReference docRef = db.collection("users").document(String.valueOf(idUser));
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            return document.toObject(User.class);
        } else {
            return null;
        }
    }
}