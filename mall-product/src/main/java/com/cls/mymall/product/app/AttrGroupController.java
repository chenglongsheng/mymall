package com.cls.mymall.product.app;

import com.cls.mymall.common.utils.PageUtils;
import com.cls.mymall.common.utils.R;
import com.cls.mymall.product.entity.AttrEntity;
import com.cls.mymall.product.entity.AttrGroupEntity;
import com.cls.mymall.product.service.AttrGroupService;
import com.cls.mymall.product.vo.AttrRelationDeleteVo;
import com.cls.mymall.product.vo.WithAttrRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 属性分组
 *
 * @author chenglongsheng
 * @email 1536463948@qq.com
 * @date 2021-11-16 11:04:15
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    /**
     * 获取分类下所有分组&关联属性
     */
    @GetMapping("/{catelogId}/withattr")
    public R withAttr(@PathVariable Long catelogId) {
        List<WithAttrRespVo> data = attrGroupService.withAttr(catelogId);
        return R.ok().put("data", data);
    }

    /**
     * 添加属性与分组关联关系
     */
    @PostMapping("/attr/relation")
    public R attrRelation(@RequestBody List<AttrRelationDeleteVo> ids) {
        attrGroupService.attrRelation(ids);
        return R.ok();
    }

    /**
     * 删除属性与分组的关联关系
     */
    @PostMapping("/attr/relation/delete")
    public R attrRelationDelete(@RequestBody List<AttrRelationDeleteVo> ids) {
        attrGroupService.attrRelationDelete(ids);
        return R.ok();
    }

    /**
     * 获取属性分组没有关联的其他属性
     */
    @GetMapping("/{attrgroupId}/noattr/relation")
    public R noAttrRelation(@RequestParam Map<String, Object> params, @PathVariable Long attrgroupId) {
        PageUtils page = attrGroupService.noAttrRelation(params, attrgroupId);
        return R.ok().put("page", page);
    }

    /**
     * 获取属性分组的关联的所有属性
     */
    @GetMapping("/{attrgroupId}/attr/relation")
    public R attrRelationList(@PathVariable Long attrgroupId) {
        List<AttrEntity> attrEntityList = attrGroupService.attrRelationList(attrgroupId);
        return R.ok().put("data", attrEntityList);
    }

    /**
     * 列表
     */
    @RequestMapping("/list/{catelogId}")
//    @RequiresPermissions("product:attrgroup:list")
    public R list(@RequestParam Map<String, Object> params, @PathVariable Long catelogId) {
//        PageUtils page = attrGroupService.queryPage(params);
        PageUtils page = attrGroupService.queryPage(params, catelogId);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
//    @RequiresPermissions("product:attrgroup:info")
    public R info(@PathVariable("attrGroupId") Long attrGroupId) {
        AttrGroupEntity attrGroup = attrGroupService.getAttrGroupInfo(attrGroupId);

        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("product:attrgroup:save")
    public R save(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("product:attrgroup:update")
    public R update(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("product:attrgroup:delete")
    public R delete(@RequestBody Long[] attrGroupIds) {
        attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
