package broker.datasource.entities;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="message")
public class MessageDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "message_id")

    @Expose
    private int message_id;
    @Expose
    private String message;
    @Expose
    private Timestamp expiration_date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="subject_id")
    private SubjectDAO subject;

    public MessageDAO(){};

    public MessageDAO(String message, Timestamp expiration_date, SubjectDAO subject) {
        this.message = message;
        this.expiration_date = expiration_date;
        this.subject = subject;
    }

    public int getMessageId() {
        return message_id;
    }

    public Timestamp getExpiration_date() {
        return expiration_date;
    }

    public String getMessage() {
        return message;
    }
}
