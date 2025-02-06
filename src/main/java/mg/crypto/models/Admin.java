package mg.crypto.models;

import mg.crypto.connect.UtilDb;
import mg.crypto.utils.AnnotationAttribut;
import mg.crypto.utils.AnnotationClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.DriverManager;

@AnnotationClass(tableName = "admin")
public class Admin {
    @AnnotationAttribut(colName = "Id_admin", insert = false)
    private int id_admin;
    @AnnotationAttribut(colName = "nom", insert = true)
    private String nom;
    @AnnotationAttribut(colName = "pwd", insert = true)
    private String pwd;

    public int getId_admin() {
        return id_admin;
    }

    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Admin checkAdmin() {
        String nom=this.getNom();
        String pwd=this.getPwd();
        Admin admin = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Connect to the database using UtilDb
            UtilDb utilDb = new UtilDb();
            conn = utilDb.getConnection();

            
            // Prepare the SQL statement with MD5 hashing in SQL
            String sql = "SELECT * FROM admin WHERE nom = ? AND pwd = MD5(?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nom);
            pstmt.setString(2, pwd);

            // Execute the query
            rs = pstmt.executeQuery();

            // Check if a result was returned
            if (rs.next()) {
                admin = new Admin();
                admin.setId_admin(rs.getInt("Id_admin"));
                admin.setNom(rs.getString("nom"));
                admin.setPwd(rs.getString("pwd"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return admin;
    }
}
