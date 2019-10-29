package com.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/10/29
 * Time: 11:47
 * Desc:
 */
@RequestMapping("cache")
@Controller
public class CacheController {

    @Autowired
    private PersonCacheService personCacheService;

    @RequestMapping(value="get", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Person getPersonById(@RequestParam("id") Long id){
        return personCacheService.getById(id);
    }

    @RequestMapping(value="put", method= RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Person updatePersonById(@RequestParam("id") Long id, @RequestParam("name") String name){
        return personCacheService.updatePerson(id, name);
    }

    @RequestMapping(value="delete", method= RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void removePersonById(@RequestParam("id") Long id){
        personCacheService.clearPerson(id);
    }

    @RequestMapping(value="init", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void init(){
        personCacheService.init();
    }

    @RequestMapping(value="clear", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void clear(){
        personCacheService.clearPersons();
    }
}
