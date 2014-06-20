package piouter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import piouter.dto.ResponseDto;
import piouter.dto.UserDto;
import piouter.service.UserService;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET,value = "{userId:.+}")
    public UserDto getUser(@PathVariable("userId") String id){
        return userService.getUserWithFollowing(id);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT,value = "{userId:.+}")
    public ResponseDto create(@PathVariable("userId") String id) {
        return userService.create(id);
    }

    @RequestMapping(method = RequestMethod.PUT,value = "{userId:.+}/follow/{followId}")
    public UserDto addFollowee(@PathVariable("userId") String id, @PathVariable("followId") String followId){
        return userService.addFolloweeToUser(id, followId);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE,value = "{userId:.+}/follow/{followId}")
    public UserDto removeFollowee(@PathVariable("userId:.+") String id, @PathVariable("followId") String followId){
        return userService.removeFolloweeToUser(id, followId);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "{userId:.+}/find/{pattern}")
    public List<UserDto> findUser(@PathVariable("pattern") String pattern){
        return userService.getUsersMatching(pattern);
    }
}
