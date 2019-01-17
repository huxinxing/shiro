package com.security.demo.service;

import com.security.demo.domain.entity.UserAccountInfo;
import com.security.demo.domain.repository.UserAccountInfoRepository;
import com.security.demo.shiro.JwtToken;
import com.security.demo.util.JwtUtil;
import com.security.demo.util.PasswordUtil;
import com.security.demo.util.RandomUnit;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class AccountService {

    @Autowired
    UserAccountInfoRepository userAccountInfoRepository;

    /**
     * 注册接口
     * @param loginName
     * @param loginPass
     */
    public void register(String loginName,String loginPass) throws Exception {

        UserAccountInfo userAccountInfo = userAccountInfoRepository.findByDisplayName(loginName);
        if (!ObjectUtils.isEmpty(userAccountInfo)){
            throw new Exception("用户名已存在");
        }else {
            userAccountInfo = new UserAccountInfo();
        }

        userAccountInfo.setSalt(RandomUnit.generateRandom(6,RandomUnit.RANDOM_TWO));
        userAccountInfo.setSecurityPassword(PasswordUtil.MD5Salt(loginPass,userAccountInfo.getSalt()));
        userAccountInfo.setDisplayName(loginName);
        userAccountInfo.setModifyName(Timestamp.valueOf(LocalDateTime.now()));
        userAccountInfo.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        userAccountInfoRepository.save(userAccountInfo);

    }


    /**
     * 登录接口
     */
    public String login(String loginName,String loginPass) throws Exception {

        UserAccountInfo userAccountInfo = userAccountInfoRepository.findByDisplayName(loginName);
        if (ObjectUtils.isEmpty(userAccountInfo)){
           throw new Exception("用户不存在，请先注册");
        }

        if (userAccountInfo.getSecurityPassword().equals(PasswordUtil.MD5Salt(loginPass,userAccountInfo.getSalt()))){
            String token = JwtUtil.sign(loginName,PasswordUtil.MD5Salt(loginPass,userAccountInfo.getSalt()));
            SecurityUtils.getSubject().login(new JwtToken(token));
//            SecurityManager securityManager = SecurityUtils.getSecurityManager();
//            Subject subject = SecurityUtils.getSubject();
//            Session session = subject.getSession();

            return token;
        }else {
            throw new Exception("密码错误");
        }

    }

    /**
     * 用户退出接口
     */
    public void loginOut() throws Exception {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }
}
