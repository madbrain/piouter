package piouter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import piouter.dto.PiouDto;
import piouter.dto.UserDto;
import piouter.entity.Piou;
import piouter.entity.User;
import piouter.exception.PiouTooLongException;
import piouter.exception.UserNotFoundException;
import piouter.repository.PiouRepository;
import piouter.repository.UserRepository;
import piouter.service.PiouService;
import piouter.service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class PiouServiceImpl implements PiouService {

    @Autowired
    private UserService userService;

    @Autowired
    private PiouRepository piouRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Collection<PiouDto> getTimeline(String id) {
        List<PiouDto> piouDtos = new ArrayList<>();
        UserDto userDto = userService.getUserWithFollowing(id);
        if (userDto != null) {
            Collection<UserDto> following = userDto.getFollowing();
            Collection<UserDto> usersInTimeline = new ArrayList<UserDto>(following.size()+1);
            usersInTimeline.addAll(following);
            usersInTimeline.add(userDto);
            usersInTimeline.forEach(u -> piouDtos.addAll(getPublished(u.getId())));
        }
        return piouDtos;
    }

    @Override
    public Collection<PiouDto> getPublished(String id) {
        User user = userRepository.findOne(id);
        if(user!=null){
            Collection<Piou> pious = piouRepository.findByUser(user);
            List<PiouDto> piouDtos = new ArrayList<>(pious.size());
            pious.forEach(p -> piouDtos.add(getPiouDto(p)));
            return piouDtos;
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional
    public void piouter(String userId, String message) throws UserNotFoundException, PiouTooLongException {
        if(message.length()>140){
            throw new PiouTooLongException();
        }
        User user = userRepository.findOne(userId);
        if(user==null){
            throw new UserNotFoundException();
        }
        piouRepository.save(new Piou(user,message));
    }

    private PiouDto getPiouDto(Piou piou){
        return new PiouDto(piou.getUser().getId(),piou.getMessage(),piou.getDate());
    }

}
