package piouter.service;

import piouter.dto.ResponseDto;
import piouter.dto.UserDto;
import piouter.entity.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    UserDto getUserWithFollowing(String id);

    List<UserDto> getUsersMatching(String pattern);

    UserDto addFolloweeToUser(String id, String followId);

    UserDto removeFolloweeToUser(String id, String followId);

    ResponseDto create(String id);

}
