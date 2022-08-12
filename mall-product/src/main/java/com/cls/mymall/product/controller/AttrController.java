package com.cls.mymall.product.controller;

import com.cls.mymall.common.utils.PageUtils;
import com.cls.mymall.common.utils.R;
import com.cls.mymall.product.entity.ProductAttrValueEntity;
import com.cls.mymall.product.service.AttrService;
import com.cls.mymall.product.vo.AttrInfoRespVo;
import com.cls.mymall.product.vo.AttrUpdateRespVo;
import com.cls.mymall.product.vo.AttrVo;
import com.cls.mymall.product.vo.UpdateSpuAttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 商品属性
 *
 * @author chenglongsheng
 * @email 1536463948@qq.com
 * @date 2021-11-16 11:04:15
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    /**
     * 修改商品规格
     */
    @PostMapping("update/{spuId}")
    public R updateSpu(@PathVariable Long spuId, @RequestBody List<UpdateSpuAttrVo> vo) {
        attrService.updateSpu(spuId, vo);
        return R.ok();
    }

    /**
     * 获取spu规格
     */
    @GetMapping("/base/listforspu/{spuId}")
    public R listForSpu(@PathVariable Long spuId) {
        List<ProductAttrValueEntity> data = attrService.listForSpu(spuId);
        return R.ok().put("data", data);
    }

    /**
     * 获取分类销售属性或基本属性
     */
    @GetMapping("/{type}/list/{catelogId}")
    public R saleList(@RequestParam Map<String, Object> params, @PathVariable Long catelogId, @PathVariable String type) {
        PageUtils page = attrService.saleOrBaseList(params, catelogId, type);
        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("product:attr:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     * 查询属性详情
     */
    @RequestMapping("/info/{attrId}")
    // @RequiresPermissions("product:attr:info")
    public R info(@PathVariable("attrId") Long attrId) {
        AttrInfoRespVo attr = attrService.getInfoById(attrId);

        return R.ok().put("attr", attr);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("product:attr:save")
    public R save(@RequestBody AttrVo attr) {
        attrService.saveAttrVo(attr);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("product:attr:update")
    public R update(@RequestBody AttrUpdateRespVo attr) {
        attrService.updateAttr(attr);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("product:attr:delete")
    public R delete(@RequestBody Long[] attrIds) {
        attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
