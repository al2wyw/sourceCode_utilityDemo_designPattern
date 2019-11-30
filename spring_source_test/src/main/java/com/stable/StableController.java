package com.stable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/3/13
 * Time: 20:49
 * Desc:
 */
@RequestMapping("stable")
@Controller
public class StableController {

    @Autowired
    private StableService stableService;

    @RequestMapping(value="get", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String get(@RequestParam("i") int i) {
        return stableService.get(i);
    }

    @RequestMapping(value="circuit", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String circuit(@RequestParam("i") int i){
        return stableService.circuit(i);
    }

    @RequestMapping(value="test", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String test(@RequestParam("i") int i){
        return stableService.test(i);
    }
}
