package mg.crypto.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mg.crypto.connect.GenericDao;
import mg.crypto.connect.UtilDb;
import mg.crypto.utils.AnnotationAttribut;
import mg.crypto.utils.AnnotationClass;
import mg.crypto.utils.Identite;

@AnnotationClass(tableName = "cryptomonnaie")
public class Crypto {
    @Identite(colName = "id_cryptomonnaie")
    @AnnotationAttribut(colName = "id_cryptomonnaie", insert = false)
    int idCrypto;
    @AnnotationAttribut(colName = "nom_crypto", insert = true)
    String nomCrypto;
    @AnnotationAttribut(colName = "val_initial", insert = true)
    BigDecimal valInitial;

    public BigDecimal getValInitial() {
        return valInitial;
    }

    public void setValInitial(BigDecimal valInitial) {
        this.valInitial = valInitial;
    }

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

    public Crypto() {
    }

    public Crypto(String nomCrypto) {
        this.nomCrypto = nomCrypto;
    }

    public Crypto(String nomCrypto, BigDecimal valInitiale) {
        this.nomCrypto = nomCrypto;
        this.valInitial = valInitiale;
    }

    public List<Crypto> FindAll() throws Exception {
        GenericDao dao = new GenericDao(new UtilDb());
        List<Object> objects = dao.findAll(new Crypto());
        List<Crypto> crypto = new ArrayList<>();
        for (Object obj : objects) {
            crypto.add((Crypto) obj);
        }
        return crypto;
    }
    
  

    public void update() throws Exception {
        GenericDao dao = new GenericDao(new UtilDb());
        dao.update(this);
    }

    public Crypto findById(int id) throws Exception {
        GenericDao dao = new GenericDao(new UtilDb());
        Crypto c = new Crypto();
        c.setIdCrypto(id);
        List<Object> objects = dao.findAllWithCriteria(c);
        if (objects.isEmpty()) {
            return null;
        }
        return (Crypto) objects.get(0);
    }

    public List<Crypto> mock() throws Exception {
        List<Crypto> ls = new ArrayList<>();
        Crypto c = new Crypto("jean", new BigDecimal("10000"));
        Crypto ce = new Crypto("naej", new BigDecimal("13000"));
        Crypto ca = new Crypto("rohy", new BigDecimal("12000"));
        ls.add(ce);
        ls.add(c);
        ls.add(ca);
        return ls;
    }

    public BigDecimal getValue() {
        Random random = new Random();
        BigDecimal percentage = BigDecimal.valueOf(random.nextDouble() * 10);
        boolean add = random.nextBoolean();
        if (add) {
            return valInitial.add(valInitial.multiply(percentage).divide(BigDecimal.valueOf(100)));
        } else {
            return valInitial.subtract(valInitial.multiply(percentage).divide(BigDecimal.valueOf(100)));
        }
    }
}
