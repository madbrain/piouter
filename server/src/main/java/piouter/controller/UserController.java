package piouter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import piouter.dto.UserDto;
import piouter.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET,value = "{id}")
    public UserDto getUser(@PathVariable("id") String id){
        return userService.getUserWithFollowing(id);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT,value = "{id}/follow/{followId}")
    public UserDto addFollowee(@PathVariable("id") String id, @PathVariable("followId") String followId){
        return userService.addFolloweeToUser(id, followId);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE,value = "{id}/follow/{followId}")
    public UserDto removeFollowee(@PathVariable("id") String id, @PathVariable("followId") String followId){
        return userService.removeFolloweeToUser(id, followId);
    }
}
