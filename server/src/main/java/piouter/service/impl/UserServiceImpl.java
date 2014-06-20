package piouter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import piouter.dto.ResponseDto;
import piouter.dto.UserDto;
import piouter.entity.User;
import piouter.repository.UserRepository;
import piouter.service.UserService;
import piouter.service.impl.monad.ResultMonad;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserDto getUserWithFollowing(String id){
        return getUserDto(userRepository.findOne(id));
    }

    @Override
    public ResponseDto create(String id) {
        ResponseDto responseDto;
        if (userRepository.exists(id)) {
            responseDto = new ResponseDto(1, "Utilisateur existant");
        } else {
            userRepository.save(new User(id));
            responseDto = new ResponseDto(0, "");
        }
        return responseDto;
    }

    @Transactional
    public List<UserDto> getUsersMatching(String pattern) {
        return userRepository.findMatchingIdIgnoreCaseOrderById(makePattern(pattern)).stream()
                .collect(Collectors.mapping(user -> getSimpleUserDto(user), Collectors.toList()));
    }

    @Override
    @Transactional
    public ResponseDto addFolloweeToUser(String id, String followId) {
        ResponseDto responseDto = new ResponseDto(0,"");
        // Oups, I think I have seen a monad...
        UserDto userDto = ResultMonad.make(userRepository.findOne(followId))
                .combine(follow -> ResultMonad.make(userRepository.findOne(id))
                    .combine((User u) -> {
                        u.addFollowing(follow);
                        return ResultMonad.make(getUserDto(u));
                    })).result();
        if (userDto == null) {
            responseDto = new ResponseDto(1,"Unknown user");
        }
        return responseDto;
    }

    @Override
    @Transactional
    public UserDto removeFolloweeToUser(String id, String followId) {
        User user = userRepository.findOne(id);
        if (user == null) {
            throw new IllegalArgumentException("unknown user " + id);
        }
        User following = userRepository.findOne(followId);
        if (following == null) {
            throw new IllegalArgumentException("unknown user " + followId);
        }
        user.removeFollowing(following);
        return getUserDto(user);
    }

    @Transactional
    public Collection<UserDto> getFollowers(String id){
        Collection<User> followers = userRepository.followers(id);
        Collection<UserDto> followersDto = new ArrayList<>(followers.size());
        followers.forEach(f -> followersDto.add(new UserDto(f.getId(), Collections.EMPTY_LIST)));
        return followersDto;
    }

    private UserDto getUserDto(User user){
        UserDto userDto = null;
        if(user!=null){
            String id = user.getId();
            Collection<User> following = user.getFollowing();
            Collection<UserDto> followingDto = new ArrayList<UserDto>(following.size());
            following.forEach(u -> followingDto.add(getSimpleUserDto(u)));
            userDto = new UserDto(id,followingDto);
        }
        return userDto;
    }

    private UserDto getSimpleUserDto(User user) {
        if(user!=null){
            return new UserDto(user.getId(), Collections.emptyList());
        }
        return null;
    }

    private static String makePattern(String pattern) {
        return "%" + pattern.toLowerCase() + "%";
    }

}
