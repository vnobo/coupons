package com.alex.ali.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * boot-cool-alliance TbKeProperties
 * Created by 2019-02-16
 *
 * @author Alex bob(https://github.com/vnobo)
 */
@Data
@ConfigurationProperties(prefix = "coupons.ali")
public class AliProperties {

    /**
     * 设置淘宝联盟的appid
     */
    private String appKey;

    /**
     * 设置淘宝联盟的app secret
     */
    private String appSecret;

    /**
     * 设置淘宝联盟的token
     */
    private String signMethod;

    /**
     * 设置淘宝联盟的EncodingAESKey
     */
    private String apiUrl;

    /**
     * 高佣转链接APIKey
     */
    private String highKey;

    /**
     * 高佣转链接API地址
     */
    private String highApi;

    /**
     * 高佣授权UID
     */
    private String highUid;
    /**
     * 设置微信公众号的服务域名
     */
    private String host;

    /**
     * 设置微信公众号通知接受EMAIL
     */
    private String email;

    /**
     * 设置微信公众号的名称
     */
    private String appName;
    /**
     * 设置微信公众号的appid
     */
    private String appId;

    /**
     * 设置微信公众号的app secret
     */
    private String secret;

    /**
     * 设置微信公众号的token
     */
    private String token;

    /**
     * 设置微信公众号的EncodingAESKey
     */
    private String aesKey;

}
