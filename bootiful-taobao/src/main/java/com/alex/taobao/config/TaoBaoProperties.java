package com.alex.taobao.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * boot-cool-alliance TbKeProperties
 * Created by 2019-02-16
 *
 * @author Alex bob(https://github.com/vnobo)
 */
@Data
@ConfigurationProperties(prefix = "taobao.ke")
public class TaoBaoProperties {

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


}
