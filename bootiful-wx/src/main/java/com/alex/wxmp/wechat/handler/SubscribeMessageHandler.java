package com.alex.wxmp.wechat.handler;

import cn.hutool.core.util.ObjectUtil;
import com.alex.wxmp.core.Customer.beans.Customer;
import com.alex.wxmp.core.wallet.beans.Wallet;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SubscribeMessageHandler extends AbstractMessageHandler {

    @Override
    public WxMpXmlOutMessage customizeHandle(WxMpXmlMessage wxMessage,
                                             Map<String, Object> context,
                                             WxMpService wxMpService,
                                             WxSessionManager sessionManager) throws WxErrorException {

        String eventKey = wxMessage.getEventKey();

        logger.info("EventKey is {}", eventKey);

        // // 判断用户是否分享关注,是否扫码
        if (StringUtils.isNotBlank(eventKey)) {

            String parentStr = eventKey.substring(eventKey.indexOf("_") + 1);

            logger.info("parent open id is " + parentStr);

            Customer parentCut = customerService.loadByOpenId(parentStr);

            // 不能自己推荐自己
            if (ObjectUtil.isNotNull(parentCut) && !wxMessage.getFromUser().equals(parentCut.getOpenId())) {
                String openid = wxMessage.getFromUser();
                this.updateCustomer(openid, parentCut.getOpenId());

            }

        } else {
            this.updateCustomer(wxMessage.getFromUser(), null);
        }

        wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.TEXT()
                .content("😊你好,感谢关注秦客优果!\n" +
                        "您可以将您心仪的(京东,淘宝)宝贝链接分享给我们，Alex🤖采用人工智能搜索" +
                        "将给您查询最优惠商品信息,获取现金奖励.🎁\n" +
                        "Alex🤖是一个利用大数据分析,比价的优惠机器人,给你全网最优惠的价格买到心仪的宝贝.😊.\n\n" +
                        "如果你在使用中遇到任何问题请发送 @客服+你的问题 联系我们哦.😊\n\n"+
                        "查看新手帮助,点击超级搜索开始吧👇")
                .toUser(wxMessage.getFromUser()).build());

        WxMpKefuMessage.WxArticle article = new WxMpKefuMessage.WxArticle();
        article.setUrl("http://"+this.wxMpProperties.getHost()+"/note");
        article.setPicUrl("https://mmbiz.qpic.cn/mmbiz_jpg/mEEwicIw1icZdZd72VibrIuJGnJZXo95EthLxJ" +
                "gApFQliaeB2libUb2M0qfH7581R5PtuWXwSrF3W5lUbCO8OGOVX6g/64" +
                "0?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1");
        article.setDescription("查看新手帮助,使用不迷路");
        article.setTitle("快速入门，开启省钱之道");
        wxMpService.getKefuService().sendKefuMessage(WxMpKefuMessage.NEWS()
                .addArticle(article)
                .toUser(wxMessage.getFromUser()).build());
        return null;
    }


    /**
     * 更新用户父级
     *
     * @param openid   子
     * @param promoter 父
     */
    private void updateCustomer(String openid, String promoter) {

        Wallet wallet = this.walletService.loadByOpenId(openid);

        if (!StringUtils.isBlank(promoter)) {
            wallet.setPromoter(promoter);
        } else {
            wallet.setPromoter(null);
        }
        this.walletService.asyncSave(wallet);
    }

}
