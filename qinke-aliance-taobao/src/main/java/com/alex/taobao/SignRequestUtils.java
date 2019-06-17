package com.alex.wxmp.alliance.taobao;

import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import cn.hutool.crypto.digest.MD5;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;

/**
 * boot-cool-alliance SignRequestUtils
 * Created by 2019-02-15
 *
 * @author Alex bob(https://github.com/vnobo)
 */
public class SignRequestUtils {

    /**
     * 请求数据参数加密
     */
    public static String signTopRequest(MultiValueMap<String, String> params, String secret, String signMethod) {

        // 第一步：检查参数是否已经排序
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        // 第二步：把所有参数名和参数值串在一起
        StringBuilder query = new StringBuilder();
        if ("md5".equals(signMethod)) {
            query.append(secret);
        }

        for (String key : keys) {
            Object value = params.getFirst(key);
            query.append(key).append(value);
        }

        // 第三步：使用MD5/HMAC加密
        byte[] bytes;
        if ("hmac".equals(signMethod)) {
            HMac mac = new HMac(HmacAlgorithm.HmacMD5, secret.getBytes());
            String sigin = mac.digestHex(query.toString());
            return sigin.toUpperCase();
        } else {
            query.append(secret);
            MD5 md5 = new MD5();
            bytes = md5.digest(query.toString());
        }

        // 第四步：把二进制转化为大写的十六进制(正确签名应该为32大写字符串，此方法需要时使用)
        return byte2hex(bytes);

    }


    public static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }


}
