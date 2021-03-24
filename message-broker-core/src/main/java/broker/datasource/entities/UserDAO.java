package broker.datasource.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name="user")
public class UserDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    private  String username;
    private String address;
    @JsonIgnore
    private String passwd;
    private String type;

    @ManyToMany(fetch = FetchType.EAGER)
            @JoinTable(
                    name = "user_subjects",
                    joinColumns = @JoinColumn(name = "id"),
                    inverseJoinColumns = @JoinColumn(name = "subject_id")
            )
    Set<SubjectDAO> subjects = new HashSet();

    public UserDAO() {};


    public String getUsername() {
        return username;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<SubjectDAO> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<SubjectDAO> subjects) {
        this.subjects = subjects;
    }
}
