package piouter.dto;

import java.io.Serializable;
import java.util.Collection;

public class UserDto implements Serializable {
    private final String id;
    private final Collection<UserDto> following;

    public UserDto(String id, Collection<UserDto> following) {
        this.id = id;
        this.following = following;
    }

    public Collection<UserDto> getFollowing() {
        return following;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id='" + id + '\'' +
                '}';
    }
}
