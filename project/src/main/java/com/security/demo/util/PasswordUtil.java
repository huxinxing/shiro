package com.security.demo.util;

import com.security.demo.util.byteChange.HexUtil;
import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

@Slf4j
public class PasswordUtil {

    private static String md5Hex(String src){
        try {
            MessageDigest md5 = MessageDigest.getInstance("Md5");
            byte[] bs = md5.digest(src.getBytes());
            return HexUtil.encode(bs);
        }catch (Exception e){
          log.info("加密失败");
        }
        return  null;

    }


    public static String MD5Salt(String password,String salt){
        String psalt = md5Hex(password) + salt;
        return md5Hex(psalt);
    }

}
