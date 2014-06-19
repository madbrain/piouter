package piouter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import piouter.dto.PiouDto;
import piouter.dto.UserDto;
import piouter.entity.Piou;
import piouter.entity.User;
import piouter.exception.UserNotFoundException;
import piouter.repository.PiouRepository;
import piouter.repository.UserRepository;
import piouter.service.PiouService;
import piouter.service.UserService;

import java.util.ArrayList;
import java.util.Collection;

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
        Collection<PiouDto> piouDtos = new ArrayList<PiouDto>();
        UserDto userDto = userService.getUserWithFollowing(id);
        if (userDto != null) {
            userDto.getFollowing().forEach(u -> piouDtos.addAll(getPublished(u.getId())));
            return piouDtos;
        }
        return piouDtos;
    }

    @Override
    public Collection<PiouDto> getPublished(String id) {
        User user = userRepository.findOne(id);
        if(user!=null){
            Collection<Piou> pious = piouRepository.findByUser(user);
            Collection<PiouDto> piouDtos = new ArrayList<PiouDto>(pious.size());
            pious.forEach(p -> piouDtos.add(getPiouDto(p)));
            return piouDtos;
        } else {
            return new ArrayList<PiouDto>(0);
        }
    }

    @Override
    public void piouter(String userId, String message) throws UserNotFoundException {
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
