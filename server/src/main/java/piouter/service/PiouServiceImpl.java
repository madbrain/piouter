package piouter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import piouter.dto.PiouDto;
import piouter.dto.UserDto;
import piouter.entity.Piou;
import piouter.entity.User;
import piouter.repository.PiouRepository;
import piouter.repository.UserRepository;

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
        userDto.getFollowing().forEach(u -> piouDtos.addAll(getPublished(u.getId())));
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

    private PiouDto getPiouDto(Piou piou){
        return new PiouDto(piou.getUser().getId(),piou.getMessage(),piou.getDate());
    }

}
