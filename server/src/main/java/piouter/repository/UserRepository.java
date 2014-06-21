package piouter.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import piouter.entity.User;

import java.util.Collection;
import java.util.List;

public interface UserRepository extends CrudRepository<User,String> {

    @Query("SELECT u FROM User u WHERE LOWER(u.id) LIKE ?1 ORDER BY u.id")
    List<User> findMatchingIdIgnoreCaseOrderById(String pattern);

    @Query("select u from User u join u.following f where f.id=?1")
    Collection<User> followers(String id);
}
