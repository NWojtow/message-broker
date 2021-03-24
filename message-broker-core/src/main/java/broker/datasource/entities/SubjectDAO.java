package broker.datasource.entities;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="subject")
public class SubjectDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private int subject_id;

    @Expose
    private String subject_type;

    @ManyToMany(mappedBy = "subjects", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    Set<UserDAO> users;

    @OneToMany(mappedBy="subject", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<MessageDAO> messages = new HashSet();

    public SubjectDAO() {
    }

    ;

    public SubjectDAO(String subjectType) {
        this.subject_type = subjectType;
    }

    public Set<MessageDAO> getMessages() {
        return messages;
    }

    public Set<UserDAO> getUsers() {
        return users;
    }
}
