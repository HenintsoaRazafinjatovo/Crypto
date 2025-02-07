package mg.crypto.models;

import java.util.ArrayList;
import java.util.List;

import mg.crypto.connect.GenericDao;
import mg.crypto.connect.UtilDb;
import mg.crypto.utils.AnnotationAttribut;
import mg.crypto.utils.AnnotationClass;
import mg.crypto.utils.Identite;

// CREATE TABLE cryptomonnaie(
//    id_cryptomonnaie SERIAL,
//    nom_crypto VARCHAR(50)  NOT NULL,
//    PRIMARY KEY(id_cryptomonnaie)
// );
@AnnotationClass(tableName = "cryptomonnaie")
public class Crypto {
      @Identite(colName = "id_cryptomonnaie")
    @AnnotationAttribut(colName = "id_cryptomonnaie",insert = false)
    int idCrypto;
    @AnnotationAttribut(colName = "nom_crypto",insert = true)
    String nomCrypto;
    
    public void setIdCrypto(int idCrypto) {
        this.idCrypto = idCrypto;
    }

    public void setNomCrypto(String nomCrypto) {
        this.nomCrypto = nomCrypto;
    }

    public int getIdCrypto() {
        return idCrypto;
    }


    public String getNomCrypto() {
        return nomCrypto;
    }


     public Crypto findById(int id) throws Exception
            {
                GenericDao dao= new GenericDao(new UtilDb());
                Crypto crypto= new Crypto();
                crypto.setIdCrypto(id);
                List<Crypto> obj= new ArrayList<>();
                List<Object> mvt= dao.findAllWithCriteria(crypto);
                for (Object mvtFond : mvt) {

                    obj.add((Crypto)mvtFond);
                }
                return obj.get(0);
            }
     public List<Crypto> findAll() throws Exception
            {
                GenericDao dao= new GenericDao(new UtilDb());
                Crypto crypto= new Crypto();
                List<Crypto> obj= new ArrayList<>();
                List<Object> mvt= dao.findAll(crypto);
                for (Object mvtFond : mvt) {

                    obj.add((Crypto)mvtFond);
                }
                return obj;
            }
}
