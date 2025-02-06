package mg.crypto.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.Annotation;
import java.util.ArrayList;
import java.util.List;

public class Reflect {
    public static String getClassName(Object o){
        Class c=o.getClass();
        return c.getName();
    }

    public static String getSimpleClassName(Object o){
        Class c=o.getClass();
        return c.getSimpleName();
    }

    public static List<String> getLsAttributs(Object o){
        Field[] fields=o.getClass().getDeclaredFields();
        List<String> res=new ArrayList<String>();
        for (Field field : fields) {
            res.add(field.getName());
        }
        return res;
    }
    public static boolean isDefaultValue(Field field, Object object) throws Exception {
        Object value = executeMethod(object,getCatMethodName(field.getName()),(Object[]) null);
        
        // Check if the field's value is equal to the default value for its type
        if (value == null) {
            return true; // Default value for reference types is null
        } else if (field.getType().isPrimitive()) {
            // For primitive types, compare with default values directly
            if (value instanceof Boolean) {
                return ((Boolean) value).equals(false); // Default value for boolean is false
            } else if (value instanceof Byte) {
                return ((Byte) value).byteValue() == 0; // Default value for byte is 0
            } else if (value instanceof Short) {
                return ((Short) value).shortValue() == 0; // Default value for short is 0
            } else if (value instanceof Integer) {
                return ((Integer) value).intValue() == 0; // Default value for int is 0
            } else if (value instanceof Long) {
                return ((Long) value).longValue() == 0L; // Default value for long is 0L
            } else if (value instanceof Float) {
                return ((Float) value).floatValue() == 0.0f; // Default value for float is 0.0f
            } else if (value instanceof Double) {
                return ((Double) value).doubleValue() == 0.0d; // Default value for double is 0.0d
            } else if (value instanceof Character) {
                return ((Character) value).charValue() == '\u0000'; // Default value for char is '\u0000'
            }
        }
        // For other types, we cannot reliably determine default value, so return false
        return false;
    }

    public static Class changeToPrimitive(Class o){
        if(o.getSimpleName().equals("Integer")){
            return int.class;
        }
        else if(o.getSimpleName().equals("Double")){
            return double.class;
        }
        else if(o.getSimpleName().equals("Boolean")){
            return boolean.class;
        }
        else if(o.getSimpleName().equals("Byte")){
            return byte.class;
        }
        else if(o.getSimpleName().equals("Float")){
            return float.class;
        }
        else if(o.getSimpleName().equals("Short")){
            return short.class;
        }
        else if(o.getSimpleName().equals("Long")){
            return long.class;
        }
        else if(o.getSimpleName().equals("Void")){
            return void.class;
        }
        return o;
    }

    public static Object executeMethod(Object o , String methodName , Object... parameters) throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException{
        Class c=o.getClass();
        Method m=null;
        if(parameters!=null){
            List<Class> ls=new ArrayList<Class>();
            for (Object object : parameters) {
                ls.add(changeToPrimitive(object.getClass()));
            }
            m=c.getMethod(methodName, ls.toArray(new Class[0]));
        }
        else{
            m=c.getMethod(methodName,(Class[])null);
        }
        Object res=m.invoke(o, parameters);
        return res;
    }
    public static Object executeMethod2(String className,Object o , String methodName , Object... parameters) throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException, ClassNotFoundException{
        Class c=o.getClass();
        Method m=null;
        if(parameters!=null){
            List<Class> ls=new ArrayList<Class>();
            for (Object object : parameters) {
                ls.add(changeToPrimitive(Class.forName(className)));
            }
            m=c.getMethod(methodName, ls.toArray(new Class[0]));
        }
        else{
            m=c.getMethod(methodName,(Class[])null);
        }
        Object res=m.invoke(o, parameters);
        return res;
    }

    public static AnnotationClass getAnnotationClass(Object e)
    {
        AnnotationClass res=null;
        if(e.getClass().isAnnotationPresent(AnnotationClass.class)){
            AnnotationClass a=e.getClass().getAnnotation(AnnotationClass.class);
            res=a;
        }
        return res;
    }

    public static AnnotationAttribut getAnnotationAttribut(Field f) {
        AnnotationAttribut res=null;
        if(f.isAnnotationPresent(AnnotationAttribut.class)){
            AnnotationAttribut a=f.getAnnotation(AnnotationAttribut.class);
            res=a;
        }
        return res;
    }

    public static AnnotationAttribut getAnnotationAttributInsert(Field f) {
        AnnotationAttribut res=null;
        if(f.isAnnotationPresent(AnnotationAttribut.class ) && 
        f.getAnnotation(AnnotationAttribut.class).insert()){
            AnnotationAttribut a=f.getAnnotation(AnnotationAttribut.class);
            res=a;
        }
        return res;
    }

    public static List<AnnotationAttribut> getAnnotationAttributs(Object e) {
        List<AnnotationAttribut> ls=new ArrayList<AnnotationAttribut>();
        Field[] fields=e.getClass().getDeclaredFields();
        for (Field field : fields) {
            if(getAnnotationAttribut(field)!=null){
                ls.add(getAnnotationAttribut(field));
            }
        }
        return ls;
    }

    public static List<AnnotationAttribut> getAnnotationAttributsInsert(Object e) {
        List<AnnotationAttribut> ls=new ArrayList<AnnotationAttribut>();
        Field[] fields=e.getClass().getDeclaredFields();
        for (Field field : fields) {
            if(getAnnotationAttributInsert(field)!=null){
                ls.add(getAnnotationAttribut(field));
            }
        }
        return ls;
    }

    public static List<Field> getFieldWithAnnotationAttribut(Object e) {
        List<Field> ls=new ArrayList<Field>();
        Field[] fields=e.getClass().getDeclaredFields();
        for (Field field : fields) {
            if(getAnnotationAttribut(field)!=null){
                ls.add(field);
            }
        }
        return ls;
    }
    
    static public String getCatMethodName(String attributeName)
    {
        String get="get";
        String firstLetter=attributeName.substring(0, 1).toUpperCase();
        String rest=attributeName.substring(1);
        String res=firstLetter.concat(rest);    
        String methodName=get.concat(res);
        return methodName;
    }
    static public String setCatMethodName(String attributeName)
    {
        String set="set";
        String firstLetter=attributeName.substring(0, 1).toUpperCase();
        String rest=attributeName.substring(1);
        String res=firstLetter.concat(rest);    
        String methodName=set.concat(res);
        return methodName;
    }

    static public List<Field> getFieldsWithoutDefaultValue(Object o) throws Exception{
        Field[] fields=o.getClass().getDeclaredFields();
        List<Field> res=new ArrayList<Field>();
        for(Field field : fields){
            if(!isDefaultValue(field, o)){
                res.add(field);
            }
        }
        return res;
    }

}
