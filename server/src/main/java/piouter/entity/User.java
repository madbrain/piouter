package piouter.entity;

import javax.persistence.*;
import java.util.*;

@Entity
public class User {

    @Id
    private String id;

    @ManyToMany
    private Set<User> following;

    protected User(){}

    public User(String id) {
        this.id = id;
        this.following = new HashSet<>();
    }

    public void addFollowing(User following){
        this.following.add(following);
    }

    public void removeFollowing(User following) {
        this.following.remove(following);
    }

    public String getId() {
        return id;
    }

    public Collection<User> getFollowing() {
        return Collections.unmodifiableCollection(following);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                '}';
    }

}
