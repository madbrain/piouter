package piouter.entity;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Entity
public class User {

    @Id
    private String id;

    @ElementCollection
    private Collection<User> following;

    protected User(){}

    public User(String id) {
        this.id = id;
        this.following = new ArrayList<User>();
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
