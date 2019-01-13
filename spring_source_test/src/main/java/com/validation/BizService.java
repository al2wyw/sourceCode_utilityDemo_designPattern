package com.validation;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/1/13
 * Time: 15:24
 * Desc:
 */
@Service
@Validated
public class BizService {

    public void bizCheck(@NotNull(message = "param is null") Object o){
        System.out.println("biz check of biz service");
    }

    public void bizCheckAgain(@NotNull @Valid BizModel bizModel){
        System.out.println("biz check again of biz service");
    }
}
