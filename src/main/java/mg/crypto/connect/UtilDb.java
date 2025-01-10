package mg.crypto.connect;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class UtilDb {
    String drivername;
    String url;
    String user;
    String password;
    String sqlType;

    public Connection getConnection() throws Exception {
        Class.forName(this.getDrivername());
        Connection toSet = DriverManager.getConnection(this.getUrl(), this.getUser(), this.getPassword());
        return toSet;
    }

    // Constructeur//
    public UtilDb() {
        this.setDrivername("org.postgresql.Driver");
        this.setUrl("jdbc:postgresql://localhost:5432/cryptomonnaie");
        this.setUser("postgres");
        this.setPassword("Goldroger12");
        this.setSqlType("postgres");
    }

    public String getDrivername() {
        return drivername;
    }

    public void setDrivername(String drivername) {
        this.drivername = drivername;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSqlType() {
        return sqlType;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }

}
