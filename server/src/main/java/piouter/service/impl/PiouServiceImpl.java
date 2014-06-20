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
    public List<PiouDto> getTimeline(String id) {
        List<PiouDto> piouDtos = new ArrayList<>();
        UserDto userDto = userService.getUserWithFollowing(id);
        if (userDto != null) {
            userDto.getFollowing().forEach(u -> piouDtos.addAll(getPublished(u.getId())));
            Collections.sort(piouDtos, PiouServiceImpl::comparePiouForTimeline);
        }
        return piouDtos;
    }

    @Override
    public List<PiouDto> getPublished(String id) {
        User user = userRepository.findOne(id);
        if(user!=null){
            Collection<Piou> pious = piouRepository.findByUser(user);
            List<PiouDto> piouDtos = new ArrayList<>(pious.size());
            pious.forEach(p -> piouDtos.add(getPiouDto(p)));
            Collections.sort(piouDtos, PiouServiceImpl::comparePiouForTimeline);
            return piouDtos;
        } else {
            return Collections.emptyList();
        }
    }

    private static int comparePiouForTimeline(PiouDto a, PiouDto b) {
        return b.getDate().compareTo(a.getDate());
    }

    @Override
    public void piouter(String userId, String message) throws UserNotFoundException, PiouTooLongException {
        if(message.length()>140){
            throw new PiouTooLongException();
        }
        User user = userRepository.findOne(userId);
        if(user==null){
            throw new UserNotFoundException();
            //user = new User(userId);
            //userRepository.save(user);
        }
        piouRepository.save(new Piou(user,message));
    }

    private PiouDto getPiouDto(Piou piou){
        return new PiouDto(piou.getUser().getId(),piou.getMessage(),piou.getDate());
    }

}
