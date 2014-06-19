package piouter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import piouter.dto.PiouDto;
import piouter.entity.Piou;
import piouter.entity.User;
import piouter.exception.PiouTooLongException;
import piouter.exception.UserNotFoundException;
import piouter.repository.PiouRepository;
import piouter.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;

public interface PiouService {
    Collection<PiouDto> getTimeline(String id);
    Collection<PiouDto> getPublished(String id);
    void piouter(String userId, String message) throws UserNotFoundException, PiouTooLongException;
}
