package mg.crypto.models;


import java.util.ArrayList;
import java.util.List;


import mg.crypto.connect.GenericDao;
import mg.crypto.connect.UtilDb;
import mg.crypto.utils.AnnotationAttribut;
import mg.crypto.utils.AnnotationClass;
import mg.crypto.utils.Identite;

@AnnotationClass(tableName = "cryptomonnaie")
public class Crypto {
    @Identite(colName = "id_crypto")
    @AnnotationAttribut(colName = "id_cryptomonnaie", insert = false)
    int idCrypto;
    @AnnotationAttribut(colName = "nom_crypto" , insert = true)
    String nomCrypto;

    public int getIdCrypto() {
        return idCrypto;
    }

    public void setIdCrypto(int idCrypto) {
        this.idCrypto = idCrypto;
    }

    public String getNomCrypto() {
        return nomCrypto;
    }

    public void setNomCrypto(String nomCrypto) {
        this.nomCrypto = nomCrypto;
    }

    public Crypto(){
    }

    public Crypto(String nomCrypto){
        this.nomCrypto=nomCrypto;
    }

    
    List<Crypto> FindAll()throws Exception{
        GenericDao dao= new GenericDao(new UtilDb());
        List<Object> objects =dao.findAll(new Crypto());
        List<Crypto> crypto = new ArrayList<>();
        for(Object obj: objects){
            crypto.add((Crypto) obj);
        }
        return crypto;
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
}
