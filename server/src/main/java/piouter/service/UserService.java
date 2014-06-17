package piouter.service;

import piouter.dto.ResponseDto;
import piouter.dto.UserDto;
import piouter.entity.User;

import java.util.Collection;

public interface UserService {
    UserDto getUserWithFollowing(String id);
    ResponseDto create(String id);
}
