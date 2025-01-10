package mg.crypto.models;

public class User {
    private int id;
    private String email;
    private String username;
    private String telephone;
    private String password;

    // Default constructor
    public User() {
    }

    // Parameterized constructor
    public User(int id, String email, String username, String telephone, String password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.telephone = telephone;
        this.password = password;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}