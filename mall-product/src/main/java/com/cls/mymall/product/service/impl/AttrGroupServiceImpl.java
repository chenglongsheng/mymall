package com.cls.mymall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cls.mymall.common.utils.PageUtils;
import com.cls.mymall.common.utils.Query;
import com.cls.mymall.product.dao.AttrGroupDao;
import com.cls.mymall.product.entity.AttrGroupEntity;
import com.cls.mymall.product.service.AttrGroupService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catelogId) {
        String key = (String) params.get("key");

        IPage<AttrGroupEntity> page;
        if (catelogId == 0) {
            page = this.page(new Query<AttrGroupEntity>().getPage(params), null);
        } else {
            page = this.page(new Query<AttrGroupEntity>().getPage(params),
                    new QueryWrapper<AttrGroupEntity>()
                            .eq("catelog_id", catelogId)
                            .and(StringUtils.hasLength(key), (i) -> i
                                    .eq("attr_group_id", key)
                                    .or()
                                    .like("attr_group_name", key)
                                    .or()
                                    .like("descript", key)));
        }
        return new PageUtils(page);
    }

}