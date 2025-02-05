package mg.crypto.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import mg.crypto.connect.GenericDao;
import mg.crypto.connect.UtilDb;
import mg.crypto.utils.AnnotationAttribut;
import mg.crypto.utils.AnnotationClass;
import mg.crypto.utils.Identite;

@AnnotationClass(tableName = "mvt_fond")
public class MvtFond  {

    @Identite(colName = "id_client")
    @AnnotationAttribut(colName = "id_mvt_fond",insert = false)
    int idMvtFond;
    @AnnotationAttribut(colName = "id_user",insert=true)
    int idUser;

    @AnnotationAttribut(colName = "depot",insert=true)
    double depot;
    @AnnotationAttribut(colName = "retrait",insert=true)
    double retrait;

    @AnnotationAttribut(colName = "dt_mvt",insert = true)
    Timestamp dtMvt;

    String typeMvt;
    public String getTypeMvt() {
        return typeMvt;
    }

    double montant;

    public void setMontant() {
        if (this.getDepot()!=0) {
            this.montant=this.getDepot();
        }
        else{
            this.montant=this.getRetrait();
            
        }
    }

    public double getMontant() {
        return montant;
    }

   public void setTypeMvt()
        {   
            if (this.getDepot()!=0) {
                setTypeMvt("Depot");
            }
            else{
                setTypeMvt("Retrait");
            }

        }
    public void setTypeMvt(String typeMvt) {
        this.typeMvt = typeMvt;
    }

    public void setDepot(double depot) {
        this.depot = depot;
    }

    public void setIdMvtFond(int idMvtFond) {
        this.idMvtFond = idMvtFond;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setRetrait(double retrait) {
        this.retrait = retrait;
    }

    public void setDtMvt(Timestamp dtMvt) {
        this.dtMvt = dtMvt;
    }

    public double getDepot() {
        return depot;
    }

    public Timestamp getDtMvt() {
        return dtMvt;
    }
    public int getIdMvtFond() {
        return idMvtFond;
    }
    public int getIdUser() {
        return idUser;
    }

    public double getRetrait() {
        return retrait;
    }

    public MvtFond()
        {

        }

    public MvtFond(int idMvtFond,int user,double depot,double retrait,Timestamp time)
        {
            this.setIdMvtFond(idMvtFond);
            setDepot(depot);
            setIdUser(user);
            setRetrait(retrait);
            setDtMvt(time);
        }

        

        public List<MvtFond>findAll() throws Exception
            {
                GenericDao dao= new GenericDao(new UtilDb());
                List<Object> list=dao.findAll(new MvtFond());
                List<MvtFond> obj= new ArrayList<>();
                for (Object mvtFond : list) {
                    obj.add((MvtFond)mvtFond);
                }
                return obj;
            } 
        
        public List<MvtFond> findById(int id) throws Exception
            {
                GenericDao dao= new GenericDao(new UtilDb());
                MvtFond f= new MvtFond();
                f.setIdUser(id);
                List<MvtFond> obj= new ArrayList<>();
                List<Object> mvt= dao.findAllWithCriteria(f);
                for (Object mvtFond : obj) {
                    ((MvtFond)mvtFond).setTypeMvt();
                    mvt.add((MvtFond)mvtFond);
                }
                return obj;
            }
        
        public void insert() throws Exception{
            GenericDao dao= new GenericDao(new UtilDb());
            dao.save(dao);
        }

        public double getFondRestant() throws Exception
            {   
               double montant=0;
               List<MvtFond> fonds =this.findById(this.getIdUser());
                for ( MvtFond fond : fonds) {
                        montant+=fond.getFondRestant()-fond.getRetrait();
                }
                return montant;
            }


    
}
