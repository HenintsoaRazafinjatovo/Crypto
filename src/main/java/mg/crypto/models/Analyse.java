package mg.crypto.models;

import java.lang.reflect.Method;
import java.util.List;

public  class Analyse {
    String type;
    String nomCrypto;
    double montant;
    String action;
    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) 
    {
        this.type = type;
    }

    public Analyse(String type) {
        this.type = type;
    }
    public void setMontant(double montant) {
        this.montant = montant;
    }

    public void setNomCrypto(String nomCrypto) {
        this.nomCrypto = nomCrypto;
    }

    public double getMontant() {
        return montant;
    }
    public String getNomCrypto() {
        return nomCrypto;
    }

    public Analyse(String nomCrypto,String type,List<Object> list, String champ) throws Exception {
        this.setType(type);
        this.setNomCrypto(nomCrypto);
        this.setAction(list.get(0).getClass().getSimpleName());
        String getter= "get"+type.substring(0, 1).toUpperCase()+type.substring(1);
        Method meth= Analyse.class.getDeclaredMethod(getter, List.class,String.class);
        this.setMontant((double) meth.invoke(this, list,champ));
    }
    public Analyse() {
    }
    public double getQuartile(List<Object> list,String champ) throws Exception {
        Class<?> clazz= list.get(0).getClass();
        int size= list.size();
        String getter= "get"+champ.substring(0, 1).toUpperCase()+champ.substring(1);
        Method meth= clazz.getDeclaredMethod(getter);
        int quartileIndex= (int) Math.ceil(size*0.25);
        return (double) meth.invoke(list.get(quartileIndex));
        
    }
    public double getMoyenne(List<Object> list,String champ) throws Exception {
        Class<?> clazz= list.get(0).getClass();
        int size= list.size();
        String getter= "get"+champ.substring(0, 1).toUpperCase()+champ.substring(1);
        Method meth= clazz.getDeclaredMethod(getter);
        double somme=0;
        for (Object object : list) {
            somme+= (double) meth.invoke(object);
        }
        return somme/size;
    }
    public Object getMax(List<Object> list,String champ) throws Exception {
        Class<?> clazz= list.get(0).getClass();
        String getter= "get"+champ.substring(0, 1).toUpperCase()+champ.substring(1);
        Method meth= clazz.getDeclaredMethod(getter);
        Object max= list.get(0);
        for (Object object : list) {
            if ((double) meth.invoke(object)>(double) meth.invoke(max)) {
                max=object;
            }
        }
        return meth.invoke(max);
    }

    public Object getMin(List<Object> list,String champ) throws Exception 
        {
            Class<?> clazz= list.get(0).getClass();
            String getter= "get"+champ.substring(0, 1).toUpperCase()+champ.substring(1);
            Method meth= clazz.getDeclaredMethod(getter);
            Object min= list.get(0);
            for (Object object : list) {
                if ((double) meth.invoke(object)<(double) meth.invoke(min)) {
                    min=object;
                }
            }
            return meth.invoke(min);
        }
    public double getEcartType(List<Object> list,String champ) throws Exception {
        
        double moyenne= getMoyenne(list, champ);
        double somme=0;
        Class<?> clazz= list.get(0).getClass();
        String getter= "get"+champ.substring(0, 1).toUpperCase()+champ.substring(1);
        Method meth= clazz.getDeclaredMethod(getter);
        for (Object object : list) {
            somme+= Math.pow((double) meth.invoke(object)-moyenne, 2);
        }
        return Math.sqrt(somme/list.size());
    }
    public double getSomme(List<Object> list,String champ) throws Exception {
        Class<?> clazz= list.get(0).getClass();
        String getter= "get"+champ.substring(0, 1).toUpperCase()+champ.substring(1);
        Method meth= clazz.getDeclaredMethod(getter);
        double somme=0;
        for (Object object : list) {
            somme+= (double) meth.invoke(object);
        }
        return somme;
    }
   
}
