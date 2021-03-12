package com.alex.ali.core.service;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alex.ali.BaseGenericService;
import com.alex.ali.core.client.TaoBaoClient;
import com.alex.ali.core.exceptions.OrderSyncProgressException;
import com.alex.ali.core.exceptions.AliRestException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Mono;

import java.util.Iterator;
import java.util.Map;

/**
 * boot-cool-alliance TaoBaoServer
 * Created by 2019-02-18
 *
 * @author Alex bob(https://github.com/vnobo)
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class TaoBaoServer extends BaseGenericService {

    private final TaoBaoClient taobaoClient;
    private final ReactorLoadBalancerExchangeFilterFunction lbfunction;

    /**
     * 根据商品ID 获取商品详情
     */
    public JsonNode getItemById(String itemId) {

        MultiValueMap<String, Object> tbParams = new LinkedMultiValueMap<>();
        tbParams.set("method", "taobao.tbk.item.info.get");
        tbParams.set("num_iids", itemId);
        tbParams.set("platform", "2");
        Mono<JsonNode> jsonNodeMono = this.taobaoClient.postForEntity(tbParams);
        JsonNode tbData = jsonNodeMono.block();

        if (ObjectUtil.isNotNull(tbData.get("error_response"))) {
            log.error("get tao bao goods info error. msg: {}", tbData.findPath("msg").asText());
            return null;
        }

        return tbData;
    }

    /**
     * 根据商品ID 获取智能推荐商品详情
     */
    public JsonNode getRecommend(String itemId) {
        MultiValueMap<String, Object> tbParams = new LinkedMultiValueMap<>();
        tbParams.set("method", "taobao.tbk.item.recommend.get");
        tbParams.set("num_iid", itemId);
        tbParams.set("fields", "volume,num_iid,title,pict_url,small_images," +
                "reserve_price,zk_final_price,user_type,provcity,item_url,nick");
        return this.taobaoClient.postForEntity(tbParams).block().findPath("n_tbk_item");
    }


    /**
     * 获取商品口令
     */
    public Map<String, Object> getGoodsPwd(String adzoneId, String itemId) {
        Assert.notNull(adzoneId, "adzoneId 不能为空!");
        Assert.notNull(itemId, "商品ID 不能为空!");
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.set("q", "https://detail.tmall.com/item.htm?id=" + itemId);
        params.set("adzone_id", adzoneId);

        Mono<JsonNode> jsonNodeMono = this.superSearch(params);
        JsonNode tbData = jsonNodeMono.block();
        Iterator<JsonNode> elements = tbData.findPath("map_data").elements();
        if (!elements.hasNext()) {
            throw new AliRestException(500, "获取淘宝商品信息错误!");
        }

        JsonNode tbGoods = elements.next();
        // 商品价格
        double price = tbGoods.findPath("zk_final_price").asDouble();
        if (price >= tbGoods.findPath("coupon_start_fee").asDouble()) {
            price = price - tbGoods.findPath("coupon_amount").asDouble();
        }
        //预估返利价
        double commission = price * tbGoods.findPath("commission_rate").asDouble() / 10000;
        commission = 0.7;// NumberUtil.round(commission * RateType.USER_COMMISSION_RATE.value(), 2).doubleValue();

        String url = tbGoods.findPath("coupon_remain_count").asInt() > 0 ?
                tbGoods.findPath("coupon_share_url").asText() : tbGoods.findPath("url").asText();

        String kouling = this.createTBPwd(tbGoods.findPath("title").asText(), url);

        return Map.of("data", tbGoods,
                "pwd", kouling,
                "commission", commission,
                "price", NumberUtil.round(price, 2));

    }

    /**
     * 个性类别超级搜索
     *
     * @param tbParams 搜索条件
     * @link https://open.taobao.com/api.htm?docId=33947&docType=2
     */
    public Mono<JsonNode> materialSearch(MultiValueMap<String, Object> tbParams) {
        tbParams.set("method", "taobao.tbk.dg.optimus.material");
        tbParams.set("page_size", "100");
        return this.taobaoClient.postForEntity(tbParams);
    }

    /**
     * 超级搜索
     *
     * @param tbParams 搜索条件
     * @link https://open.taobao.com/api.htm?docId=35896&docType=2
     */
    public Mono<JsonNode> superSearch(MultiValueMap<String, Object> tbParams) {
        tbParams.set("method", "taobao.tbk.dg.material.optional");
        tbParams.set("platform", "2");
        return this.taobaoClient.postForEntity(tbParams);
    }


    /**
     * 创建用户商品口令
     *
     * @param title 商品名称
     * @param url   券二合一页面链接
     * @return
     */
    public String createTBPwd(String title, String url) {
        MultiValueMap<String, Object> tbParams = new LinkedMultiValueMap<>();
        tbParams.set("method", "taobao.tbk.tpwd.create");
        tbParams.set("text", title);
        tbParams.set("url", url.contains("http") ? url : "https:" + url);
        Mono<JsonNode> jsonNodeMono = this.taobaoClient.postForEntity(tbParams);

        JsonNode tbData = jsonNodeMono.block();

        if (ObjectUtil.isNotNull(tbData.get("error_response"))) {
            log.error("create tao bao goods pwd error. msg: {}", tbData.findPath("msg").asText());
            return null;
        }
        return tbData.findPath("model").asText();
    }


    /**
     * taobao.tbk.model.get( 淘宝客订单查询 )
     * <p>
     * fields	String	true	tb_trade_parent_id,tb_trade_id,num_iid,item_title,item_num,price,pay_price,seller_nick,
     * seller_shop_title,commission,commission_rate,unid,create_time,earning_time,tk3rd_pub_id,
     * tk3rd_site_id,tk3rd_adzone_id,relation_id,tb_trade_parent_id,tb_trade_id,num_iid,
     * item_title,item_num,price,pay_price,seller_nick,seller_shop_title,
     * commission,commission_rate,unid,create_time,earning_time,
     * tk3rd_pub_id,tk3rd_site_id,tk3rd_adzone_id,special_id,click_time	需返回的字段列表
     * <p>
     * start_time	Date	true	2016-05-23 12:18:22	订单查询开始时间
     * span	Number	false	600	订单查询时间范围，单位：秒，最小60，最大1200，如不填写，默认60。
     * 查询常规订单、三方订单、渠道，及会员订单时均需要设置此参数，直接通过设置page_size,page_no 翻页查询数据即可
     * <p>
     * page_no	Number	false	1	第几页，默认1，1~100
     * <p>
     * page_size	Number	false	20	页大小，默认20，1~100
     * <p>
     * tk_status	Number	false	1	订单状态，1: 全部订单，3：订单结算，12：订单付款， 13：订单失效，14：订单成功；
     * 订单查询类型为‘结算时间’时，只能查订单结算状态
     * <p>
     * order_query_type	String	false	settle_time	订单查询类型，创建时间“create_time”，或结算时间“settle_time”
     * <p>
     * order_scene	Number	false	1	订单场景类型，1:常规订单，2:渠道订单，3:会员运营订单，默认为1，通过设置订单场景类型
     * 媒体可以查询指定场景下的订单信息，例如不设置，或者设置为1，表示查询常规订单，常规订单包含淘宝客所有的订单数据，含渠道，
     * 及会员运营订单，但不包含3方分成，及维权订单
     * <p>
     * order_count_type	Number	false	1	订单数据统计类型，1: 2方订单，2: 3方订单，如果不设置，或者设置为1，表示2方订单
     *
     * @link https://open.taobao.com/api.htm?docId=24527&docType=2
     */
    public Mono<JsonNode> syncOrders(MultiValueMap<String, Object> requestParams) {
        requestParams.set("method", "taobao.tbk.order.details.get");

        log.debug("sync orders params is {}", requestParams);

        Mono<JsonNode> jsonNodeMono = this.taobaoClient.postForEntity(requestParams);

        return jsonNodeMono
                .doOnSuccess(data -> {
                    JsonNode errorNode = data.findValue("error_response");
                    if (!ObjectUtils.isEmpty(errorNode)) {
                        throw new OrderSyncProgressException(500, errorNode.toString());
                    }
                })
                .map(jsonNode -> jsonNode.findValue("data"));

    }
}
