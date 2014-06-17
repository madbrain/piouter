package piouter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import piouter.dto.ResponseDto;
import piouter.dto.UserDto;
import piouter.entity.User;
import piouter.repository.UserRepository;
import piouter.service.UserService;

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

    @Override
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
            following.forEach(u -> followingDto.add(getUserDto(u)));
            userDto = new UserDto(id,followingDto);
        }
        return userDto;
    }

}
