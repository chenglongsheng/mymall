package com.cls.mymall.product.controller;

import com.cls.mymall.common.utils.PageUtils;
import com.cls.mymall.common.utils.R;
import com.cls.mymall.product.entity.SpuInfoEntity;
import com.cls.mymall.product.service.SpuInfoService;
import com.cls.mymall.product.vo.spuinfo.SpuInfoSaveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * spu信息
 *
 * @author chenglongsheng
 * @email 1536463948@qq.com
 * @date 2021-11-16 11:04:15
 */
@RestController
@RequestMapping("product/spuinfo")
public class SpuInfoController {
    @Autowired
    private SpuInfoService spuInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("product:spuinfo:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = spuInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
//    @RequiresPermissions("product:spuinfo:info")
    public R info(@PathVariable("id") Long id) {
        SpuInfoEntity spuInfo = spuInfoService.getById(id);

        return R.ok().put("spuInfo", spuInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("product:spuinfo:save")
    public R save(@RequestBody SpuInfoSaveVo spuInfo) {
        spuInfoService.saveSpuInfo(spuInfo);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("product:spuinfo:update")
    public R update(@RequestBody SpuInfoEntity spuInfo) {
        spuInfoService.updateById(spuInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("product:spuinfo:delete")
    public R delete(@RequestBody Long[] ids) {
        spuInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
