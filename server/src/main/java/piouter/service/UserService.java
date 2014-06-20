package piouter.service;

import piouter.dto.ResponseDto;
import piouter.dto.UserDto;

import java.util.Collection;
import java.util.List;

public interface UserService {
    UserDto getUserWithFollowing(String id);
    ResponseDto create(String id);
    List<UserDto> getUsersMatching(String pattern);
    ResponseDto addFolloweeToUser(String id, String followId);
    UserDto removeFolloweeToUser(String id, String followId);
    Collection<UserDto> getFollowers(String id);
}
