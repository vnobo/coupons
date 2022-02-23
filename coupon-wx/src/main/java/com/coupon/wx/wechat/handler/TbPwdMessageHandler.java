package com.coupon.wx.wechat.handler;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * boot-cool-alliance TbPwdMessageHandler
 * Created by 2019-02-18
 *
 * @author Alex bob(https://github.com/vnobo)
 */
@Component
public class TbPwdMessageHandler extends AbstractMessageHandler {


    @Override
    public WxMpXmlOutMessage customizeHandle(WxMpXmlMessage wxMessage,
                                             Map<String, Object> context,
                                             WxMpService wxMpService,
                                             WxSessionManager sessionManager) throws WxErrorException {

      /*  // 京东
        if (ReUtil.contains(Pattern.compile("(.*(.jd.com).*)"), wxMessage.getContent())) {
            String reg = ReUtil.get(Pattern.compile("[/](\\d+)[.html]"), wxMessage.getContent(), 0);
            String id = ReUtil.get(Pattern.compile("\\d+"), reg, 0);

            this.logger.info("jd goods id is {}", id);

            JsonNode goodsNode = this.jdServer.getItemInfo(id);

            JsonNode jsonNode = this.jdServer.getJdURL(wxMessage.getContent(), wxMessage.getFromUser());
            if (ObjectUtil.isNull(jsonNode)) {
                String q = goodsNode.findPath("goodsName").asText();
                String url = "https://" + this.wxMpProperties.getHost() + "/search?q=" + q + "&openid=" + wxMessage.getFromUser();
                return WxMpXmlOutMessage.TEXT()
                        .content("亲,发送的宝贝暂时没有优惠信息哦!\n" +
                                "点击链接👇查看更多关于: " + q + "\n" +
                                wxMpService.shortUrl(url))
                        .fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser()).build();
            }

            double price = goodsNode.findPath("wlUnitPrice").asDouble() > -1 ? goodsNode.findPath("wlUnitPrice").asDouble() : 0;
            double rate = NumberUtil.round(price * goodsNode.findPath("commisionRatioWl").asDouble() / 100 * RateType.USER_COMMISSION_RATE.value(), 2).doubleValue();

            return WxMpXmlOutMessage.TEXT().content("【" + goodsNode.findPath("goodsName").asText() + "】\n" +
                    "-----------------\n" +
                    "券后价: " + price + "元 " + "奖金: " + rate + "元\n" +
                    "-----------------\n" +
                    "点击链接直接购买👇\n" + wxMpService.shortUrl(jsonNode.findPath("clickURL").asText()))
                    .fromUser(wxMessage.getToUser())
                    .toUser(wxMessage.getFromUser()).build();

        } else if (ReUtil.contains(Pattern.compile("([（|💰|💴|(|￥|$|€]([A-Za-z0-9]{11})[）|💰|💴|)|￥|$|€])|(.*(.taobao.com|.tmall.com|喵口令).*)"), wxMessage.getContent())) {
            String message = wxMessage.getContent();
            String itemId = this.highComClient.getGoodsId(message);
            this.logger.info("get item Id is:{} ", itemId);

            if (StringUtils.isEmpty(itemId)) {
                return this.pbSearch(wxMessage);
            }

            JsonNode tbGoods = this.taoBaoServer.getItemById(itemId);

            // 不存在智能推荐
            if (ObjectUtil.isNull(tbGoods)) {
                String q = ReUtil.get("【([^】].*)】", message, 0);
                q = q.replace("【", "").replace("】", "");
                String url = "https://" + this.wxMpProperties.getHost() + "/search?q=" + q + "&openid=" + wxMessage.getFromUser();
                WxMpXmlOutNewsMessage.Item article = new WxMpXmlOutNewsMessage.Item();
                article.setUrl(url);
                article.setTitle("亲,发送的宝贝暂时没有优惠信息哦!");
                article.setDescription("点击查看更多关于: " + q);

                return WxMpXmlOutMessage.NEWS().addArticle(article)
                        .fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser()).build();
            }

            //商品信息存在
            Customer customer = customerService.loadByOpenId(wxMessage.getFromUser());
            Map result = this.taoBaoServer.getGoodsPwd(customer.getPid().getAdZoneId(), itemId);
            tbGoods = (JsonNode) result.get("data");
            String url = "https://" + this.wxMpProperties.getHost() + "/detail/" + itemId + "?openid=" + wxMessage.getFromUser();
            return WxMpXmlOutMessage.TEXT().content("【" + tbGoods.findPath("title").asText() + "】\n" +
                    "-----------------\n" +
                    "券后价: " + result.get("price") + "元 " + "奖金: " + result.get("commission") + "元\n" +
                    "【推荐理由】: " + tbGoods.findPath("item_description").asText() + " \n" +
                    "-----------------\n" +
                    "復·制这段描述" + result.get("pwd").toString() + "后到👉手机淘♂寳👈购买♀\n" +
                    "点击链接查看详情👇\n" + wxMpService.shortUrl(url))
                    .fromUser(wxMessage.getToUser())
                    .toUser(wxMessage.getFromUser()).build();

        } else {
            return this.pbSearch(wxMessage);
        }*/
        return null;
    }

    /*private WxMpXmlOutMessage pbSearch(WxMpXmlMessage wxMessage) {
        List<String> sentenceList = HanLP.extractKeyword(wxMessage.getContent(), 6);
        String url = "https://" + this.wxMpProperties.getHost() + "/search?q=" + StringUtils.collectionToDelimitedString(sentenceList, "")
                + "&openid=" + wxMessage.getFromUser();
        WxMpXmlOutNewsMessage.Item article = new WxMpXmlOutNewsMessage.Item();
        article.setUrl(url);
        article.setDescription("亲,发送淘宝或者京东的分享链接,获取精确商品优惠信息哦,点击立即查询");
        article.setTitle("查询: " + wxMessage.getContent());

        return WxMpXmlOutMessage.NEWS()
                .addArticle(article)
                .fromUser(wxMessage.getToUser())
                .toUser(wxMessage.getFromUser()).build();
    }*/
}