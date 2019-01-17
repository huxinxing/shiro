package com.security.demo.controller;

import com.security.demo.domain.constant.CommonConstant;
import com.security.demo.domain.dto.response.ResponseResult;
import com.security.demo.domain.enums.ResponseStatus;
import com.security.demo.service.AccountService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
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


    @ApiOperation("用户退出")
    @RequestMapping(value = "/loginOut",method = RequestMethod.POST)
    public ResponseResult loginOut(
    ){
        try{
            accountService.loginOut();
            return new ResponseResult(CommonConstant.SUCCESS, ResponseStatus.SUCCESS.getKey(),"注销成功");
        }catch (Exception e){
            log.error("退出失败",e);
            return new ResponseResult(CommonConstant.ERROR,ResponseStatus.Default.getKey(),e.getMessage());
        }
    }


    @ApiOperation("用户未登录")
    @RequestMapping(value = "/notLogin",method = RequestMethod.GET)
    public ResponseResult notLogin(
    ){
        try{
            return new ResponseResult(CommonConstant.SUCCESS,ResponseStatus.SUCCESS.getKey(),"您尚未登录");
        }catch (Exception e){
            log.error("登录异常",e);
            return new ResponseResult(CommonConstant.ERROR,ResponseStatus.Default.getKey(),e.getMessage());
        }
    }

    @ApiOperation("用户没有权限")
    @RequestMapping(value = "/notRole",method = RequestMethod.GET)
    public ResponseResult notRole(
    ){
        try{
            return new ResponseResult(CommonConstant.SUCCESS,ResponseStatus.SUCCESS.getKey(),"您没有权限");
        }catch (Exception e){
            log.error("权限认证异常",e);
            return new ResponseResult(CommonConstant.ERROR,ResponseStatus.Default.getKey(),e.getMessage());
        }
    }


    @RequiresRoles(value={"admin","user"},logical = Logical.OR)
    @ApiOperation("测试数据")
    @RequestMapping(value = "/getMessage",method = RequestMethod.POST)
    public ResponseResult getMessage(
    ){
        try{
            return new ResponseResult(CommonConstant.SUCCESS,ResponseStatus.SUCCESS.getKey(),ResponseStatus.SUCCESS.getValue(),"成功");
        }catch (Exception e){
            log.error("获取数据失败",e);
            return new ResponseResult(CommonConstant.ERROR,ResponseStatus.Default.getKey(),e.getMessage());
        }
    }



}
