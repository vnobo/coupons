package com.alex.wx.alliance.jd.config;

import cn.hutool.json.JSONUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * boot-cool-alliance TbKeProperties
 * Created by 2019-02-16
 *
 * @author Alex bob(https://github.com/vnobo)
 */
@ConfigurationProperties(prefix = "jd.ke")
public class JdProperties {

    /**
     * 设置京东联盟的appid
     */
    private String appKey;

    /**
     * 设置京东联盟的app secret
     */
    private String appSecret;

    /**
     * 设置京东联盟的token
     */
    private String signMethod;

    /**
     * 设置京东联盟的EncodingAESKey
     */
    private String apiUrl;


    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getSignMethod() {
        return signMethod;
    }

    public void setSignMethod(String signMethod) {
        this.signMethod = signMethod;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }

}
