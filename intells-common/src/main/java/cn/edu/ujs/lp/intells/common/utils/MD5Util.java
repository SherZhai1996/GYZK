package cn.edu.ujs.lp.intells.common.utils;


import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {

    public static String md5(String userLoginName,String userPassword){
        return DigestUtils.md5Hex(userLoginName+userPassword);
    }
}
