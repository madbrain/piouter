package piouter.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PiouDto implements Serializable {
    private String userId;
    private String message;
    private Date date;

    public PiouDto(String userId, String message, Date date) {
        this.userId = userId;
        this.message = message;
        this.date = date;
    }

    public PiouDto(){
    }

    public String getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(Date date) {
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
