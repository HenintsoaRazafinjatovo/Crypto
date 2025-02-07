package mg.crypto.models;

import mg.crypto.connect.GenericDao;
import mg.crypto.connect.UtilDb;
import mg.crypto.utils.AnnotationAttribut;
import mg.crypto.utils.AnnotationClass;
import mg.crypto.utils.Identite;

@AnnotationClass(tableName = "config_commission")
public class ConfigCommission {
    @Identite(colName = "id_config")
    @AnnotationAttribut(colName = "id_config", insert = false)
    int idConfigCommission;
    @AnnotationAttribut(colName = "pourcentage", insert = true)
    double pourcentage;
    @AnnotationAttribut(colName = "type_config", insert = true)
    boolean type;

    public void setIdConfigCommission(int idConfigCommission) {
        this.idConfigCommission = idConfigCommission;
    }

    public void setPourcentage(double pourcentage) {
        this.pourcentage = pourcentage;
    }


    public void setType(boolean type) {
        this.type = type;
    }

    public int getIdConfigCommission() {
        return idConfigCommission;
    }

    public double getPourcentage() {
        return pourcentage;
    }
   public boolean getType() {
        return type;
    }
    public void insert() throws Exception{
            GenericDao dao= new GenericDao(new UtilDb());
            dao.save(this);
        }
    public void update() throws Exception{
            GenericDao dao= new GenericDao(new UtilDb());
            dao.update(this);
        }
}
