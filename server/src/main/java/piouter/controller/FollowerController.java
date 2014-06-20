package piouter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import piouter.dto.ResponseDto;
import piouter.dto.UserDto;
import piouter.service.UserService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("follower")
public class FollowerController {
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET,value = "{userId:.+}")
    public Collection<UserDto> followers(@PathVariable("userId") String id){
        return userService.getFollowers(id);
    }

}
