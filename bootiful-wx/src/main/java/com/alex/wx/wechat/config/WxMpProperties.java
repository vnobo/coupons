package com.alex.wx.wechat.config;

import cn.hutool.json.JSONUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * boot-cool-alliance WxMpProperties
 * Created by 2019-02-14
 *
 * @author Alex bob(https://github.com/vnobo)
 */

@ConfigurationProperties(prefix = "wx.mp")
public class WxMpProperties {

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

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
