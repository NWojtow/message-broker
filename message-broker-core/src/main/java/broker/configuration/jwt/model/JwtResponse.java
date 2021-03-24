package broker.configuration.jwt.model;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    private final String username;

    public JwtResponse(String jwttoken, String userNnme) {
        this.jwttoken = jwttoken;
        this.username = userNnme;
    }
    public String getToken() {
        return this.jwttoken;
    }
    public String getUsername() {
        return this.username;
    }
}
