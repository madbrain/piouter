package piouter.service;

import piouter.dto.PiouDto;
import piouter.exception.PiouTooLongException;
import piouter.exception.UserNotFoundException;

import java.util.Collection;
import java.util.List;

public interface PiouService {
    Collection<PiouDto> getTimeline(String id);
    Collection<PiouDto> getPublished(String id);
    void piouter(String userId, String message) throws UserNotFoundException, PiouTooLongException;
}
