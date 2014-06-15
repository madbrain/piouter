package piouter.dto;

import java.io.Serializable;
import java.util.Date;

public class PiouDto implements Serializable {
    private final String userId;
    private final String message;
    private final Date date;

    public PiouDto(String userId, String message, Date date) {
        this.userId = userId;
        this.message = message;
        this.date = date;
    }

    @Override
    public String toString() {
        return "PiouDto{" +
                "userId='" + userId + '\'' +
                ", message='" + message + '\'' +
                ", date=" + date +
                '}';
    }
}
