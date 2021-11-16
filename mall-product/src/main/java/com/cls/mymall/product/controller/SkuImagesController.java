package com.cls.mymall.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cls.mymall.product.entity.SkuImagesEntity;
import com.cls.mymall.product.service.SkuImagesService;
import com.cls.mymall.common.utils.PageUtils;
import com.cls.mymall.common.utils.R;


/**
 * sku图片
 *
 * @author chenglongsheng
 * @email 1536463948@qq.com
 * @date 2021-11-16 11:04:15
 */
@RestController
@RequestMapping("product/skuimages")
public class SkuImagesController {
    @Autowired
    private SkuImagesService skuImagesService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("product:skuimages:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = skuImagesService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
//    @RequiresPermissions("product:skuimages:info")
    public R info(@PathVariable("id") Long id) {
        SkuImagesEntity skuImages = skuImagesService.getById(id);

        return R.ok().put("skuImages", skuImages);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("product:skuimages:save")
    public R save(@RequestBody SkuImagesEntity skuImages) {
        skuImagesService.save(skuImages);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("product:skuimages:update")
    public R update(@RequestBody SkuImagesEntity skuImages) {
        skuImagesService.updateById(skuImages);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("product:skuimages:delete")
    public R delete(@RequestBody Long[] ids) {
        skuImagesService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
