package com.alex.wxmp.alliance.taobao.config;

import cn.hutool.json.JSONUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * boot-cool-alliance TbKeProperties
 * Created by 2019-02-16
 *
 * @author Alex bob(https://github.com/vnobo)
 */
@ConfigurationProperties(prefix = "tb.ke")
public class TbKeProperties {

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
    private String highAPI;

    /**
     * 高佣授权UID
     */
    private String highUid;

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

    public String getHighKey() {
        return highKey;
    }

    public void setHighKey(String highKey) {
        this.highKey = highKey;
    }

    public String getHighAPI() {
        return highAPI;
    }

    public void setHighAPI(String highAPI) {
        this.highAPI = highAPI;
    }

    public String getHighUid() {
        return highUid;
    }

    public void setHighUid(String highUid) {
        this.highUid = highUid;
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }

}
