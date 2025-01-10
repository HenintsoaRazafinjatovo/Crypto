package mg.crypto.connect;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import mg.crypto.utils.AnnotationAttribut;
import mg.crypto.utils.Identite;
import mg.crypto.utils.Reflect;

public class GenericDao extends Dao {
    public GenericDao(UtilDb db) {
        super(db);
    }
    public void save(Connection c ,Object o) throws Exception{
        PreparedStatement s=null;
        try{
                ///nom de la table
            String table_name=Reflect.getAnnotationClass(o).tableName();
                ///nom des colonnes 
            List<String> ls=new ArrayList<String>();
            for (AnnotationAttribut a : Reflect.getAnnotationAttributsInsert(o)) {
                ls.add(a.colName());
            }
            String ls_col=String.join(",",ls);
                ///preparation values
            String values=prepareValues(o);
            ///preparation statement
            String query=String.format("INSERT INTO %s (%s) VALUES (%s)", table_name,ls_col,values);
            s=c.prepareStatement(query);
            putValues(o, s);
            ///lancement query
            int rs=s.executeUpdate();
        }
        catch(Exception ex){
            throw ex;
        }
        finally{
            if(s!=null){
                s.close();
            }
        } 
    }
    public void save(Object o) throws Exception{
        Connection c=null;
        try{
            UtilDb db=this.getDb();
            c=db.getConnection();
            save(c, o);
        }
        catch(Exception ex){
            throw ex;
        }
        finally{
            if(c!=null){
                c.close();
            }
        } 
    }

    public List<Object> findAll(Object o) throws Exception{
        Connection c=null;
        try{
            UtilDb db=this.getDb();
            c=db.getConnection();
            return findAll(c, o);
        }
        catch(Exception ex){
            throw ex;
        }
        finally{
            if(c!=null){
                c.close();
            }
        } 
    }

    public List<Object> findAll(Connection c, Object o) throws Exception{
        PreparedStatement s=null;
        ResultSet rs=null;
        try{
        ///nom de la table
            String table_name=Reflect.getAnnotationClass(o).tableName();
        ///annotations des colonnes
            List<AnnotationAttribut> ls_col=Reflect.getAnnotationAttributs(o);
        ///attributs qui ont annotation
            List<Field> ls_field=Reflect.getFieldWithAnnotationAttribut(o);
        ///preparation statement
            String query=String.format("SELECT * FROM %s",table_name);
            s=c.prepareStatement(query);
        ///lancement query
            rs=s.executeQuery();
        ///initialisation resultat
            List<Object> res=new ArrayList<Object>();
            while (rs.next()) {
            ///création de l'objet a partir des attributs et colonnes
                Object to_Add=createObjectColField(o, rs, ls_col, ls_field);
                res.add(to_Add);
            }
            return res;
        }
        catch(Exception ex){
            throw ex;
        }
        finally{
            if(s!=null){
                s.close();
            }
            if(rs!=null){
                rs.close();
            }
        } 
    }

    public List<Object> findAllWithCriteria(Object o) throws Exception{
        Connection c=null;
        try{
            UtilDb db=this.getDb();
            c=db.getConnection();
            return findAllWithCriteria(c, o);
        }
        catch(Exception ex){
            throw ex;
        }
        finally{
            if(c!=null){
                c.close();
            }
        } 
    }

    public List<Object> findAllWithCriteria(Connection c ,Object o) throws Exception{
        PreparedStatement s=null;
        ResultSet rs=null;
        try{
        ///nom de la table
            String table_name=Reflect.getAnnotationClass(o).tableName();
        ///annotations des colonnes
            List<AnnotationAttribut> ls_col=Reflect.getAnnotationAttributs(o);
        ///attributs qui ont annotation
            List<Field> ls_field=Reflect.getFieldWithAnnotationAttribut(o);
        ///preparation condition
            String conditions=prepareCondition(o,ls_field);
        ///preparation statement
            String query=String.format("SELECT * FROM %s WHERE %s",table_name,conditions);
            s=c.prepareStatement(query);
        ///mettre les datas sur les ?
            putCondition(o, s);
        ///lancement query
            rs=s.executeQuery();
        ///initialisation resultat
            List<Object> res=new ArrayList<Object>();
            while (rs.next()) {
            ///création de l'objet a partir des attributs et colonnes
                Object to_Add=createObjectColField(o, rs, ls_col, ls_field);
                res.add(to_Add);
            }
            return res;
        }
        catch(Exception ex){
            throw ex;
        }
        finally{
            if(s!=null){
                s.close();
            }
            if(rs!=null){
                rs.close();
            }
        } 
    }

    public List<Object> findWithinInterval(Object o_inf,Object o_sup) throws Exception{
        Connection c=null;
        try{
            UtilDb db=this.getDb();
            c=db.getConnection();
            return findWithinInterval(c, o_inf, o_sup);
        }
        catch(Exception ex){
            throw ex;
        }
        finally{
            if(c!=null){
                c.close();
            }
        } 
    }

    public List<Object> findWithinInterval(Connection c ,Object o_inf,Object o_sup) throws Exception{
        PreparedStatement s=null;
        ResultSet rs=null;
        try{
        ///verification mm nom de table
            if(!Reflect.getAnnotationClass(o_inf).tableName().equals(Reflect.getAnnotationClass(o_sup).tableName())){
                throw new Exception("Table différents , recherche ac interval impossible");
            }
        ///nom de la table
            String table_name=Reflect.getAnnotationClass(o_inf).tableName();
        ///preparation interval & statement
            String conditions=prepareInterval(o_inf,o_sup);
            String query=String.format("SELECT * FROM %s WHERE %s",table_name,conditions);
            System.out.println(query);
            s=c.prepareStatement(query);
        ///lancement query
            rs=s.executeQuery();
        ///initialisation resultat
            List<Object> res=new ArrayList<Object>();
        ///annotations des colonnes
            List<AnnotationAttribut> ls_col=Reflect.getAnnotationAttributs(o_inf);
        ///attributs qui ont annotation
            List<Field> ls_field=Reflect.getFieldWithAnnotationAttribut(o_inf);
            while (rs.next()) {
            ///création de l'objet a partir des attributs et colonnes
                Object to_Add=createObjectColField(o_inf, rs, ls_col, ls_field);
                res.add(to_Add);
            }
            return res;
        }
        catch(Exception ex){
            throw ex;
        }
        finally{
            if(s!=null){
                s.close();
            }
            if(rs!=null){
                rs.close();
            }
        } 
    }

    public List<Object> findAllPagination(Object o, int nb, int offset) throws Exception {
        Connection c = null;
        try {
            UtilDb db = this.getDb();
            c = db.getConnection();
            return findAllPagination(c, o, nb, offset);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }

    public List<Object> findAllPagination(Connection c ,Object o, int nb, int offset) throws Exception {
        PreparedStatement s = null;
        ResultSet rs = null;
        try {
            // Générer la requête SQL en fonction du type de base de données
            String query = getQueryForSqlType(o, nb, offset);
            s = c.prepareStatement(query);
            // Exécuter la requête
            rs = s.executeQuery();
            // Récupérer les annotations des colonnes et les champs
            List<AnnotationAttribut> ls_col = Reflect.getAnnotationAttributs(o);
            List<Field> ls_field = Reflect.getFieldWithAnnotationAttribut(o);
            // Initialiser la liste des résultats paginés
            List<Object> res = new ArrayList<Object>();
            // Créer des objets à partir des résultats de la requête
            while (rs.next()) {
                Object to_Add = createObjectColField(o, rs, ls_col, ls_field);
                res.add(to_Add);
            }
            return res;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (s != null) {
                s.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
    }

    public void update(Connection c, Object o) throws Exception {
        PreparedStatement s = null;
        try {
            String table_name = Reflect.getAnnotationClass(o).tableName();
            List<String> setClauses = new ArrayList<>();
            List<Field> fields = Reflect.getFieldWithAnnotationAttribut(o);
            for (Field field : fields) {
                if (Reflect.getAnnotationAttributInsert(field) != null) {
                    setClauses.add(Reflect.getAnnotationAttribut(field).colName() + " = ?");
                }
            }
            String setClause = String.join(", ", setClauses);
            String whereClause = prepareWhereClause(o);
            String query = String.format("UPDATE %s SET %s WHERE %s", table_name, setClause, whereClause);
            s = c.prepareStatement(query);
            putValues(o, s);
            putIdentiteValues(o, s, fields.size());
            s.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }

    public void update(Object o) throws Exception {
        Connection c = null;
        try {
            UtilDb db = this.getDb();
            c = db.getConnection();
            update(c, o);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }

    public void delete(Connection c, Object o) throws Exception {
        PreparedStatement s = null;
        try {
            String table_name = Reflect.getAnnotationClass(o).tableName();
            String whereClause = prepareWhereClause(o);
            String query = String.format("DELETE FROM %s WHERE %s", table_name, whereClause);
            s = c.prepareStatement(query);
            putIdentiteValues(o, s, 0);
            s.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }

    public void delete(Object o) throws Exception {
        Connection c = null;
        try {
            UtilDb db = this.getDb();
            c = db.getConnection();
            delete(c, o);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }

    private String prepareWhereClause(Object o) throws Exception {
        List<String> whereClauses = new ArrayList<>();
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Identite.class)) {
                Identite identite = field.getAnnotation(Identite.class);
                whereClauses.add(identite.colName() + " = ?");
            }
        }
        return String.join(" AND ", whereClauses);
    }

    private void putIdentiteValues(Object o, PreparedStatement s, int startIndex) throws Exception {
        Field[] fields = o.getClass().getDeclaredFields();
        int index = startIndex;
        for (Field field : fields) {
            if (field.isAnnotationPresent(Identite.class)) {
                String meth_name = Reflect.getCatMethodName(field.getName());
                s.setObject(index++, Reflect.executeMethod(o, meth_name, (Object[]) null));
            }
        }
    }
    

    
}
