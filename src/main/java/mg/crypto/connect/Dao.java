package mg.crypto.connect;

import java.net.URL;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mg.crypto.utils.AnnotationAttribut;
import mg.crypto.utils.Reflect;

public class Dao {
    UtilDb db;

    public UtilDb getDb() {
        return db;
    }

    public void setDb(UtilDb db) {
        this.db = db;
    }

    public Dao() {

    }

    public Dao(UtilDb db) {
        this.db = db;
    }

    static String prepareValues(Object o) {
        List<String> ls = new ArrayList<String>();
        List<AnnotationAttribut> ls_annotation = Reflect.getAnnotationAttributsInsert(o);
        for (AnnotationAttribut s : ls_annotation) {
            ls.add("?");
        }
        return String.join(",", ls);
    }

    static void putValues(Object o, PreparedStatement s) throws Exception {
        Field[] fields = o.getClass().getDeclaredFields();
        List<Field> f = new ArrayList<Field>();
        for (Field field : fields) {
            if (Reflect.getAnnotationAttributInsert(field) != null) {
                f.add(field);
            }
        }
        for (int i = 0; i < f.size(); i++) {
            String meth_name = Reflect.getCatMethodName(f.get(i).getName());
            s.setObject(i + 1, Reflect.executeMethod(o, meth_name, (Object[]) null));
        }
    }

    static Object createObjectColField(Object o, ResultSet rs, List<AnnotationAttribut> ls_col, List<Field> ls_field)
            throws Exception {
        Object to_Add = o.getClass().getConstructor((Class[]) null).newInstance((Object[]) null);
        for (int i = 0; i < ls_field.size(); i++) {
            Object value = rs.getObject(ls_col.get(i).colName());
            /// executer le setters
            String setterName = Reflect.setCatMethodName(ls_field.get(i).getName());
            Reflect.executeMethod(to_Add, setterName, value);
        }
        return to_Add;
    }

    static String prepareCondition(Object o, List<Field> ls_field) throws Exception {
        List<String> ls = new ArrayList<String>();
        ls.add("1=1");
        for (Field field : ls_field) {
            if (!Reflect.isDefaultValue(field, o)) {
                AnnotationAttribut a = field.getAnnotation(AnnotationAttribut.class);
                if (field.getType().equals(String.class)) {
                    ls.add(a.colName() + " LIKE %?%");
                } else {
                    ls.add(a.colName() + "=?");
                }
            }
        }
        return String.join(" AND ", ls);
    }

    static void putCondition(Object o, PreparedStatement s) throws Exception {
        List<Field> ls_field = Reflect.getFieldWithAnnotationAttribut(o);
        for (int i = 0; i < ls_field.size(); i++) {
            if (!Reflect.isDefaultValue(ls_field.get(i), o)) {
                String meth_name = Reflect.getCatMethodName(ls_field.get(i).getName());
                s.setObject(i+1, Reflect.executeMethod(o, meth_name, (Object[]) null));
            }
        }
    }

    static String prepareInterval(Object o_inf, Object o_sup) throws Exception {
        List<Field> attr_col_inf = Reflect.getFieldsWithoutDefaultValue(o_inf);
        List<Field> attr_col_sup = Reflect.getFieldsWithoutDefaultValue(o_sup);
        if (attr_col_inf.size() != attr_col_sup.size()) {
            throw new Exception("Le nombre d'attributs avec valeur ne correspond pas pour les deux objets");
        }
        for (int i = 0; i < attr_col_inf.size(); i++) {
            if (!attr_col_inf.get(i).getName().equals(attr_col_sup.get(i).getName())) {
                throw new Exception("Les attributs des deux objets a valeur ne correspondent pas");
            }
        }
        List<String> lis = new ArrayList<String>();
        for (int i = 0; i < attr_col_inf.size(); i++) {
            String nom_col = Reflect.getAnnotationAttribut(attr_col_inf.get(i)).colName();
            String meth_name = Reflect.getCatMethodName(attr_col_inf.get(i).getName());
            String generate = String.format("(%s>'%s') AND (%s<'%s')", nom_col,
                    Reflect.executeMethod(o_inf, meth_name, (Object[]) null), nom_col,
                    Reflect.executeMethod(o_sup, meth_name, (Object[]) null));
            lis.add(generate);
        }
        return String.join("AND", lis);
    }

    public static Map<String, String> getDatas(String filePath) throws Exception {
        Map<String, String> map = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            while (line != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    map.put(key, value);
                }
            }
        }

        return map;
    }

    public String getQueryForSqlType(Object o, int nb, int offset) throws Exception {
        String type = this.getDb().getSqlType();
        String tableName = Reflect.getAnnotationClass(o).tableName();
        if (type.equals("mysql") || type.equals("postgres")) {
            return String.format("SELECT * FROM %s LIMIT %i OFFSET %i;", tableName, nb, offset);
        } else if (type.equals("sqlserver")) {
            return String.format("SELECT TOP %i * FROM %s OFFSET %i ROWS;", nb, tableName, offset);
        } else if (type.equals("oracle")) {
            return String.format("SELECT * FROM ( SELECT * FROM %s ) WHERE ROWNUM <= %i;", tableName, nb + offset);
        }
        return null;
    }

}
