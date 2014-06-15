package piouter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import piouter.dto.UserDto;
import piouter.entity.User;
import piouter.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserDto getUserWithFollowing(String id){
        return getUserDto(userRepository.findOne(id));
    }

    private UserDto getUserDto(User user){
        UserDto userDto = null;
        if(user!=null){
            String id = user.getId();
            Collection<User> following = user.getFollowing();
            Collection<UserDto> followingDto = new ArrayList<UserDto>(following.size());
            following.forEach(u -> followingDto.add(getUserDto(u)));
            userDto = new UserDto(id,followingDto);
        }
        return userDto;
    }

    @Transactional
    public Collection<User> getFollowers(String id){
        return userRepository.findOne(id).getFollowing();
    }

}
