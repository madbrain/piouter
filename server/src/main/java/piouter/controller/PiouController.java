package piouter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import piouter.dto.PiouDto;
import piouter.dto.ResponseDto;
import piouter.service.PiouService;

import java.util.Collection;

@Controller
@RequestMapping("piou")
public class PiouController {

    @Autowired
    private PiouService piouService;

    @RequestMapping(method = RequestMethod.GET, value = "{userId}")
    @ResponseBody
    public Collection<PiouDto> timeline(@PathVariable("userId") String userId){
        return piouService.getTimeline(userId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "{userId}")
    @ResponseBody
    public ResponseDto piouter(@PathVariable("userId") String userId, @RequestParam("message") String message){
        piouService.piouter(userId,message);
        return new ResponseDto(0,"");
    }
}
