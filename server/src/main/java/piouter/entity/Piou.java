package piouter.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Piou {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @OneToOne
    @JoinColumn(name="user")
    private User user;
    private String message;
    private Date date;

    public Piou(User user, String message) {
        this.user = user;
        this.message = message;
        this.date = new Date();
    }

    protected Piou() {}

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Piou{" +
                "user=" + user +
                ", message='" + message + '\'' +
                '}';
    }
}