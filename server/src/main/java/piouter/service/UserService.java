package piouter.service;

import piouter.dto.UserDto;
import piouter.entity.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    UserDto getUserWithFollowing(String id);

    List<UserDto> getUsersMatching(String pattern);
}
