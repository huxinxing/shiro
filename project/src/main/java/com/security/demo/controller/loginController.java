package com.security.demo.controller;

import com.security.demo.domain.constant.CommonConstant;
import com.security.demo.domain.dto.response.ResponseResult;
import com.security.demo.domain.enums.ResponseStatus;
import com.security.demo.service.AccountService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/account")
public class loginController {


    @Autowired
    AccountService accountService;

    @ApiOperation("用户注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginName", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "loginPass", value = "密码", required = true, dataType = "String", paramType = "query")

    })
    @RequestMapping(value = "register",method = RequestMethod.POST)
    public ResponseResult register(
            @RequestParam(name = "loginName") String loginName,
            @RequestParam(name = "loginPass") String loginPass
    ){
        try{
            accountService.register(loginName,loginPass);
            return new ResponseResult(CommonConstant.SUCCESS, ResponseStatus.SUCCESS.getKey(),ResponseStatus.SUCCESS.getValue());
        }catch (Exception e){
            log.error("注册异常",e);
            return new ResponseResult(CommonConstant.ERROR,ResponseStatus.Default.getKey(),e.getMessage());
        }
    }


    @ApiOperation("登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginName", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "loginPass", value = "密码", required = true, dataType = "String", paramType = "query")

    })
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseResult login(
            @RequestParam(name = "loginName") String loginName,
            @RequestParam(name = "loginPass") String loginPass
    ){
        try{
            String token = accountService.login(loginName,loginPass);
            return new ResponseResult(CommonConstant.SUCCESS, ResponseStatus.SUCCESS.getKey(),ResponseStatus.SUCCESS.getValue(),token);
        }catch (Exception e){
            log.error("登录异常",e);
            return new ResponseResult(CommonConstant.ERROR,ResponseStatus.Default.getKey(),e.getMessage());
        }
    }


}
