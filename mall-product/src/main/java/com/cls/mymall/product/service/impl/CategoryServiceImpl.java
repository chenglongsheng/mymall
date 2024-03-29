package com.cls.mymall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cls.mymall.common.utils.PageUtils;
import com.cls.mymall.common.utils.Query;
import com.cls.mymall.product.dao.CategoryDao;
import com.cls.mymall.product.entity.CategoryEntity;
import com.cls.mymall.product.service.CategoryBrandRelationService;
import com.cls.mymall.product.service.CategoryService;
import com.cls.mymall.product.vo.CatalogLevel2Vo;
import com.cls.mymall.product.vo.CatalogLevel3Vo;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(new Query<CategoryEntity>().getPage(params), new QueryWrapper<>());

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listTree() {

        List<CategoryEntity> entities = baseMapper.selectList(null);

        return entities.stream().filter(categoryEntity -> categoryEntity.getParentCid() == 0)// 找到所有一级分类
                .peek(menu -> menu.setChildren(getChildren(menu, entities))).sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort()))).collect(Collectors.toList());
    }

    @Override
    public void removeMenusByIds(List<Long> asList) {
        //TODO 防止地址引用
        baseMapper.deleteBatchIds(asList);

    }

    @Override
    public Long[] getAttrGroupPath(Long catelogId) {
        List<Long> attrGroupPath = getAttrGroupPath(catelogId, new ArrayList<>());
        Collections.reverse(attrGroupPath);
        return attrGroupPath.toArray(new Long[attrGroupPath.size()]);
    }

    @Transactional
    @Override
    public void updateDetails(CategoryEntity category) {
        super.updateById(category);
        if (!StringUtils.isEmpty(category.getName())) {
            categoryBrandRelationService.updateCascade(category.getCatId(), category.getName());
        }
    }

    // value分组，可以多个分组。key = "#root.method.name"设置方法名为键名
    @Cacheable(value = "category", key = "#root.method.name")
    @Override
    public List<CategoryEntity> getLevel1Categories() {
        return super.list(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
    }

    @Override
    public Map<String, List<CatalogLevel2Vo>> getCatalogJson() {
        String catalogJson = redisTemplate.opsForValue().get("catalogJson");
        if (StringUtils.isEmpty(catalogJson)) {
            System.out.println("缓存未命中。。。等待查询数据库");
//            return getCatalogJsonFromDbWithLocalLock();
//            return getCatalogJsonFromDbWithRedisLock();
            return getCatalogJsonFromDbWithRedisson();
        } else {
            System.out.println("缓存命中。。。");
            return JSON.parseObject(catalogJson, new TypeReference<Map<String, List<CatalogLevel2Vo>>>() {
            });
        }
    }

    public Map<String, List<CatalogLevel2Vo>> getCatalogJsonFromDbWithRedisson() {
        RLock lock = redissonClient.getLock("catalogJson-lock");
        lock.lock();
        Map<String, List<CatalogLevel2Vo>> dataFromDb = null;
        try {
            dataFromDb = getDataFromDb();
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
        return dataFromDb;
    }

    public Map<String, List<CatalogLevel2Vo>> getCatalogJsonFromDbWithRedisLock() {

        String token = UUID.randomUUID().toString();

        // 向redis中获取锁
        // nx和ex一起
        Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", token, 30, TimeUnit.SECONDS);
        if (lock) {
            System.out.println("获取分布式锁成功");
            Map<String, List<CatalogLevel2Vo>> dataFromDb;
            // 加锁成功执行业务
            // 设置过期时间必须是和加锁同步执行（原子操作）
//            redisTemplate.expire("lock", 30, TimeUnit.SECONDS);
            try {
                dataFromDb = getDataFromDb();

            } finally {
                // 验证和删除操作必须是原子性
//            String lockValue = redisTemplate.opsForValue().get("lock");
//            if (token.equals(lockValue)) {
//                // 删除自己的锁
//                redisTemplate.delete("lock");
//            }
                // 使用lua脚本解锁
                String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                Long execute = redisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class), Collections.singletonList("lock"), token);
            }

            return dataFromDb;
        } else {
            System.out.println("获取分布式锁失败。。。等待重试");
            // 自旋 休眠
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return getCatalogJsonFromDbWithRedisLock();
        }

    }

    private Map<String, List<CatalogLevel2Vo>> getDataFromDb() {
        String catalogJson = redisTemplate.opsForValue().get("catalogJson");
        if (StringUtils.isEmpty(catalogJson)) {
            System.out.println("缓存未命中。。。查询数据。。");
            List<CategoryEntity> CategoryList = super.list();
            List<CategoryEntity> category2 = CategoryList.stream().filter(item -> item.getCatLevel() == 2).collect(Collectors.toList());
            List<CategoryEntity> category3 = CategoryList.stream().filter(item -> item.getCatLevel() == 3).collect(Collectors.toList());
            List<Long> level1Ids = CategoryList.stream().filter(item -> item.getCatLevel() == 1).map(CategoryEntity::getCatId).collect(Collectors.toList());

            Map<String, List<CatalogLevel2Vo>> catalogJsonFromDb = level1Ids.stream().collect(Collectors.toMap(Object::toString, v -> category2.stream().filter(item -> item.getParentCid().equals(v)).map(cate2 -> {
                CatalogLevel2Vo level2Vo = new CatalogLevel2Vo();
                level2Vo.setId(cate2.getCatId().toString());
                level2Vo.setName(cate2.getName());
                level2Vo.setCatalog1Id(cate2.getParentCid().toString());
                List<CatalogLevel3Vo> level3List = category3.stream().filter(item -> item.getParentCid().equals(cate2.getCatId())).map(cate3 -> {
                    CatalogLevel3Vo level3Vo = new CatalogLevel3Vo();
                    level3Vo.setId(cate3.getCatId().toString());
                    level3Vo.setName(cate3.getName());
                    level3Vo.setCatalog2Id(cate2.getCatId().toString());
                    return level3Vo;
                }).collect(Collectors.toList());
                level2Vo.setCatalog3List(level3List);
                return level2Vo;
            }).collect(Collectors.toList())));

            String s = JSON.toJSONString(catalogJsonFromDb);
            redisTemplate.opsForValue().set("catalogJson", s, 1, TimeUnit.DAYS);

            return catalogJsonFromDb;
        }
        System.out.println("缓存命中。。。");
        return JSON.parseObject(catalogJson, new TypeReference<Map<String, List<CatalogLevel2Vo>>>() {
        });
    }

    public Map<String, List<CatalogLevel2Vo>> getCatalogJsonFromDbWithLocalLock() {
        synchronized (this) {
            return getDataFromDb();
        }
    }

    private List<Long> getAttrGroupPath(Long catelogId, List<Long> path) {
        CategoryEntity category = this.getById(catelogId);
        path.add(category.getCatId());
        if (category.getParentCid() != 0) {
            getAttrGroupPath(category.getParentCid(), path);
        }
        return path;
    }

    /**
     * 递归查询当前对象的孩子，直到源数据中没有孩子数据
     *
     * @param root 当前对象
     * @param all  数据源
     * @return 所有孩子集合
     */
    private List<CategoryEntity> getChildren(CategoryEntity root, List<CategoryEntity> all) {

        return all.stream().filter(categoryEntity -> categoryEntity.getParentCid().equals(root.getCatId()))// 等于当前的夫id，即是孩子
                .peek(categoryEntity -> categoryEntity.setChildren(getChildren(categoryEntity, all))).sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort()))).collect(Collectors.toList());
    }

}