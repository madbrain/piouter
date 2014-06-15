package piouter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import piouter.dto.PiouDto;
import piouter.service.PiouService;

import java.util.Collection;

@Controller
@RequestMapping("piou")
public class PiouController {

    @Autowired
    private PiouService piouService;

    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    @ResponseBody
    public Collection<PiouDto> timeline(@PathVariable("id") String id){
        return piouService.getTimeline(id);
    }
}
