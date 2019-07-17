package com.alex.wx.wechat.service;

import com.alex.wx.AbstractGenericService;
import com.alex.wx.alliance.taobao.TaoBaoServer;
import com.alex.wx.core.Customer.CustomerService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class WxSearchService extends AbstractGenericService {

    private TaoBaoServer taoBaoServer;
    private CustomerService customerService;

    public WxSearchService(TaoBaoServer taoBaoServer,
                           CustomerService customerService) {
        this.taoBaoServer = taoBaoServer;
        this.customerService = customerService;
    }


    /**
     * 淘宝智能超级搜索
     *
     * @link https://open.taobao.com/api.htm?docId=35896&docType=2
     */
    public Page<JsonNode> taoBaoSearch(Map<String, String> params, Pageable pageable) {
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        //默认值设置
        String openId = params.getOrDefault("openid", null);
        Assert.notNull(openId, "openid is required!");

        // 商品排序
        requestParams.set("sort", params.getOrDefault("sort", "total_sales_des"));
        String adzoneId = this.customerService.loadByOpenId(openId).getPid().getAdZoneId();
        requestParams.set("adzone_id", adzoneId);

        // 页大小，默认20，1~100
        requestParams.set("page_size", String.valueOf(pageable.getPageSize()));
        requestParams.set("page_no", String.valueOf(pageable.getPageNumber()));

        requestParams.set("q", params.getOrDefault("q", "女装").trim());

        //优惠券筛选-是否有优惠券。true表示该商品有优惠券，false或不设置表示不限
        requestParams.set("has_coupon", params.getOrDefault("hasCoupon", "true"));

        // 商品筛选-折扣价范围上限。单位：元
        if (!StringUtils.isEmpty(params.getOrDefault("endPrice", null))) {
            requestParams.set("end_price", params.get("endPrice").trim());
        }

        // 商品筛选-折扣价范围下限。单位：元
        if (!StringUtils.isEmpty(params.getOrDefault("startPrice", null))) {
            requestParams.set("start_price", params.get("startPrice").trim());
        }
        //商品筛选(特定媒体支持)-店铺dsr评分。筛选大于等于当前设置的店铺dsr评分的商品0-50000之间
        if (!StringUtils.isEmpty(params.getOrDefault("startDsr", null))) {
            requestParams.set("start_dsr", params.get("startDsr").trim());
        }

        /* *******************************************************************************
        // 商品筛选-淘客佣金比率下限。如：1234表示12.34%
        requestParams.set("start_tk_rate", params.getOrDefault("startTkRate", "0"));
        // 商品筛选-淘客佣金比率上限。如：1234表示12.34%
        requestParams.set("end_tk_rate", params.getOrDefault("endTkRate", "10000"));
        // 商品筛选-是否海外商品。true表示属于海外商品，false或不设置表示不限
        requestParams.set("is_overseas", params.getOrDefault("isOverseas", "false"));
        //商品筛选-是否包邮。true表示包邮，false或不设置表示不限
        requestParams.set("need_free_shipment", params.getOrDefault("needFreeShipment", "true"));
        //商品筛选-是否加入消费者保障。true表示加入，false或不设置表示不限
        requestParams.set("need_prepay", params.getOrDefault("needPrepay", "true"));

        //商品筛选(特定媒体支持)-退款率是否低于行业均值。True表示大于等于，false或不设置表示不限
        requestParams.set("include_rfd_rate", params.getOrDefault("includeRfdRate", "true"));
        ******************************************************************************* */
        //商品筛选-是否天猫商品。true表示属于天猫商品，false或不设置表示不限
        requestParams.set("is_tmall", params.getOrDefault("isTmall", "false"));
        //商品筛选(特定媒体支持)-成交转化是否高于行业均值。True表示大于等于，false或不设置表示不限
        requestParams.set("include_pay_rate_30", params.getOrDefault("includePayRate30", "false"));
        //商品筛选-好评率是否高于行业均值。True表示大于等于，false或不设置表示不限
        requestParams.set("include_good_rate", params.getOrDefault("includeGoodRate", "false"));

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ObjectUtils.isEmpty(ipAddress)) {
            requestParams.set("ip", request.getRemoteAddr());
        } else {
            requestParams.set("ip", ipAddress);
        }

        JsonNode data = this.taoBaoServer.superSearch(requestParams);

        return new PageImpl<JsonNode>(data.findValues("map_data"), pageable,
                data.findPath("total_results").asInt());
    }

    public Map findGoodsDetail(String openid, String id) {
        String adzoneId = this.customerService.loadByOpenId(openid).getPid().getAdZoneId();
        return this.taoBaoServer.getGoodsPwd(adzoneId, id);
    }


    public JsonNode goodsRecommend(String itemId) {
        return this.taoBaoServer.getRecommend(itemId);
    }
}
