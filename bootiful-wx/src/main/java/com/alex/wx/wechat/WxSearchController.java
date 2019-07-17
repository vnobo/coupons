package com.alex.wx.wechat;

import com.alex.wx.AbstractGenericController;
import com.alex.wx.wechat.service.WxSearchService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("wx/search")
public class WxSearchController extends AbstractGenericController {

    private WxSearchService wxSearchService;

    public WxSearchController(WxSearchService wxSearchService) {
        this.wxSearchService = wxSearchService;
    }

    @GetMapping("{openid}/{id}")
    public Object getDetail(@PathVariable String openid, @PathVariable String id) {
        return this.wxSearchService.findGoodsDetail(openid, id);
    }

    @GetMapping("recommend/{id}")
    public Object getDetail(@PathVariable String id) {
        return this.wxSearchService.goodsRecommend(id);
    }

    @GetMapping("all")
    public Object searchAll(@RequestParam Map<String, String> params,
                            Pageable pageable,
                            PagedResourcesAssembler<JsonNode> assembler) {

        Set<String> keys = params.keySet();
        keys.parallelStream().forEach(k -> {
            String value = params.get(k);
            if (StringUtils.isEmpty(value)) {
                params.remove(k, value);
            }
        });

        Page<JsonNode> bitCoins = this.wxSearchService.taoBaoSearch(params, pageable);
        Link link = linkTo(methodOn(WxSearchController.class).searchAll(params, pageable, assembler)).withSelfRel();


        logger.debug("market callable get end");

        return ResponseEntity.ok(assembler.toResource(bitCoins, link));
    }
}
