package piouter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import piouter.dto.UserDto;
import piouter.service.UserService;

import java.util.List;

@Controller
@RequestMapping("users")
public class UsersController {
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "{pattern}")
    public List<UserDto> getUser(@PathVariable("pattern") String pattern){
        return userService.getUsersMatching(pattern);
    }
}
