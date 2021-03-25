package broker.configuration.jwt.entities;

public class UserDTO {
    private String username;
    private String password;
    private String address;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }
}
