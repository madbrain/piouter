package piouter.repository;

import org.springframework.data.repository.CrudRepository;
import piouter.entity.User;

import java.util.Collection;

public interface UserRepository extends CrudRepository<User,String> {
}
