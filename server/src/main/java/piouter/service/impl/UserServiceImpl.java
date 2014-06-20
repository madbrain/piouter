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

    @Override
    @Transactional
    public UserDto getUserWithFollowing(String id){
        User user = userRepository.findOne(id);
        if (user == null) {
            userRepository.save(user = new User(id));
        }
        return getUserDto(user);
    }

    @Override
    @Transactional
    public List<UserDto> getUsersMatching(String pattern) {
        return userRepository.findMatchingIdIgnoreCaseOrderById(makePattern(pattern)).stream()
                .collect(Collectors.mapping(user -> getSimpleUserDto(user), Collectors.toList()));
    }

    @Override
    @Transactional
    public UserDto addFolloweeToUser(String id, String followId) {
        // Oups, I think I have seen a monad...
        UserDto userDto = ResultMonad.make(userRepository.findOne(followId))
                .combine(follow -> ResultMonad.make(userRepository.findOne(id))
                    .combine((User u) -> {
                        u.addFollowing(follow);
                        return ResultMonad.make(getUserDto(u));
                    })).result();
        if (userDto == null) {
            throw new IllegalArgumentException("unknown user");
        }
        return userDto;
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
    public Collection<User> getFollowers(String id){
        return userRepository.findOne(id).getFollowing();
	}

    public ResponseDto create(String id) {
        ResponseDto responseDto;
        if(userRepository.exists(id)){
            responseDto = new ResponseDto(1,"Utilisateur existant");
        } else {
            userRepository.save(new User(id));
            responseDto = new ResponseDto(0,"");
        }
        return responseDto;
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
