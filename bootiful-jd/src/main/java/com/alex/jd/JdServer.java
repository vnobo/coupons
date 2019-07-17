package com.alex.wx.alliance.jd;

import cn.hutool.json.JSONUtil;
import com.alex.wx.AbstractGenericService;
import com.alex.wx.RestServerException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * rebate-alliance JdServer
 * Created by 2019-02-26
 *
 * @author Alex bob(https://github.com/vnobo)
 */
@Service
public class JdServer extends AbstractGenericService {

    private JdClient jdClient;


    public JdServer(JdClient jdClient) {
        this.jdClient = jdClient;
    }

    /**
     * @param page      页码，返回第几页结果
     * @param type      订单时间查询类型(1：下单时间，2：完成时间，3：更新时间)
     * @param startTime 查询时间，建议使用分钟级查询，格式：yyyyMMddHH、yyyyMMddHHmm或yyyyMMddHHmmss，如201811031212 的查询范围从12:12:00--12:12:59
     * @return
     */
    public JsonNode getOrders(int page, int type, LocalDateTime startTime) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("method", "jd.union.open.model.query");
        params.set("param_json", JSONUtil.toJsonStr(Map.of("orderReq",
                Map.of("pageNo", page,
                        "pageSize", "500",
                        "type", type,
                        "time", startTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")))
                ))
        );
        JsonNode jsonNode = this.jdClient.postForEntity(params);

        String result = jsonNode.findPath("result").asText();

        if (!StringUtils.isEmpty(result)) {
            try {
                return this.objectMapper.readTree(result);
            } catch (IOException e) {
                throw new JdServiceException(500, "get orders  to json error");
            }
        } else {
            return null;
        }
    }


    /**
     * 京粉精选商品查询接口
     *
     * @param eliteId 频道id：
     *                1-好券商品,2-京粉APP-jingdong.大咖推荐,3-小程序-jingdong.好券商品,
     *                4-京粉APP-jingdong.主题街1-jingdong.服装运动,5-京粉APP-jingdong.主题街2-jingdong.精选家电,
     *                6-京粉APP-jingdong.主题街3-jingdong.超市,7-京粉APP-jingdong.主题街4-jingdong.居家生活,10-9.9专区,
     *                11-品牌好货-jingdong.潮流范儿,12-品牌好货-jingdong.精致生活,13-品牌好货-jingdong.数码先锋,
     *                14-品牌好货-jingdong.品质家电,15-京仓配送,16-公众号-jingdong.好券商品,17-公众号-jingdong.9.9,
     *                18-公众号-jingdong.京仓京配
     */
    public JsonNode getSuperiorGoods(int eliteId, int page) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("method", "jd.union.open.goods.jingfen.query ");
        params.set("param_json", JSONUtil.toJsonStr(Map.of("goodsReq",
                Map.of("eliteId", eliteId,
                        "pageIndex", page,
                        "pageSize", 10,
                        "sort", "desc",
                        "sortName", "goodComments")
                ))
        );
        JsonNode jsonNode = this.jdClient.postForEntity(params);
        String result = jsonNode.findPath("result").asText();
        if (!StringUtils.isEmpty(result)) {
            try {
                return this.objectMapper.readTree(result);
            } catch (IOException e) {
                throw new JdServiceException(500, "Get superior goods to json  error");
            }
        } else {
            return null;
        }
    }

    public JsonNode getJdURL(String url, String openId) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("method", "jd.union.open.promotion.common.get");
        params.set("param_json", JSONUtil.toJsonStr(Map.of("promotionCodeReq",
                Map.of("materialId", url,
                        "siteId", "1711812579",
                        "positionId", 1712181013,
                        "ext1", openId)
                ))
        );
        JsonNode jsonNode = this.jdClient.postForEntity(params);
        String result = jsonNode.findPath("result").asText();
        if (!StringUtils.isEmpty(result)) {
            try {
                return this.objectMapper.readTree(result);
            } catch (IOException e) {
                throw new JdServiceException(500, "getJdURL to json  error");
            }
        } else {
            return null;
        }
    }

    public JsonNode getItemInfo(String id) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("method", "jd.union.open.goods.promotiongoodsinfo.query");
        params.set("param_json", JSONUtil.toJsonStr(Map.of("skuIds", id)));
        JsonNode jsonNode = this.jdClient.postForEntity(params);

        String result = jsonNode.findPath("result").asText();
        if (!StringUtils.isEmpty(result)) {
            try {
                return this.objectMapper.readTree(result);
            } catch (IOException e) {
                throw new JdServiceException(500, "getItemInfo  to json error");
            }
        } else {
            return null;
        }
    }

    public class JdServiceException extends RestServerException {

        JdServiceException(int code, String msg) {
            super(code, msg);
        }
    }
}
