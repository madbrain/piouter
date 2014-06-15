package piouter.repository;

import org.springframework.data.repository.CrudRepository;
import piouter.entity.Piou;
import piouter.entity.User;

import java.util.Collection;

public interface PiouRepository extends CrudRepository<Piou,Long>{
    Collection<Piou> findByUser(User user);
    Collection<Piou> findByUser(String user);
}
