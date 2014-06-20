package piouter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import piouter.dto.PiouDto;
import piouter.dto.ResponseDto;
import piouter.exception.PiouTooLongException;
import piouter.exception.UserNotFoundException;
import piouter.service.PiouService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("piou")
public class PiouController {

    @Autowired
    private PiouService piouService;

    @RequestMapping(method = RequestMethod.GET, value = "{userId:.+}")
    @ResponseBody
    public Collection<PiouDto> timeline(@PathVariable("userId") String userId){
        return piouService.getTimeline(userId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "{userId:.+}")
    @ResponseBody
    public ResponseDto piouter(@PathVariable("userId") String userId, @RequestBody PiouDto piouDto){
        ResponseDto responseDto = new ResponseDto(0,"");
        try {
            piouService.piouter(userId,piouDto.getMessage());
        } catch (UserNotFoundException e) {
            responseDto = new ResponseDto(1,"Utilisateur non existant");
        } catch (PiouTooLongException e) {
            responseDto = new ResponseDto(1,"Piou trop long");
        }
        return responseDto;
    }
}
