package com.test;

import com.model.Information;
import com.model.Person;
import com.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/7/4
 * Time: 19:38
 * Desc:
 */
@Controller
@RequestMapping("tx")
public class transactionController {

    @Autowired
    private test_transactional_Person retriever;

    @Autowired
    private test_transactional_Information testLimit;

    @Autowired
    private test_tx_Person test_tx_person;

    @RequestMapping(value="getInfo", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getInfoByLimit(@RequestParam("offset") int offset,@RequestParam("limit") int limit){
        List<Information> result = testLimit.getInfo(offset,limit);
        result.stream().forEach(info -> LoggerUtils.getLogger().info("Information {}", info));
        return "done";
    }

    @RequestMapping(value="init", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String initAllTables(){
        testTransaction();
        return "done";
    }

    @RequestMapping(value="clear", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String clearAllTables(){
        retriever.clearPerson();
        return "done";
    }

    private void testTransaction(){
        Person p = new Person();

        p.setId("0x1201");
        p.setName("Perter");
        p.setSex("male");
        p.setSalary(9345.34D);
        retriever.insertPerson(p);

        p.setId("0x1202");
        p.setName("Ken");
        p.setSex("male");
        p.setSalary(6345.53D);
        retriever.insertPerson(p);

        p.setId("0x1203");
        p.setName("Anne");
        p.setSex("female");
        p.setSalary(3245.93D);
        retriever.insertPerson(p);
    }

    @RequestMapping(value="test", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String testTx(){
        Person p = new Person();

        p.setId("0x2");
        p.setName("Andrew");
        p.setSex("male");
        p.setSalary(5345.34D);
        test_tx_person.insertPerson(p, 200);
        return "done";
    }

    @RequestMapping(value="syn", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String testTransactionSynchronization(@RequestParam("id") String id){
        test_tx_person.getPersonById(id);
        return "done";
    }
}
