package com.cls.mymall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cls.mymall.common.utils.PageUtils;
import com.cls.mymall.common.utils.Query;
import com.cls.mymall.coupon.entity.MemberPriceEntity;
import com.cls.mymall.coupon.entity.SkuFullReductionEntity;
import com.cls.mymall.coupon.entity.SkuLadderEntity;
import com.cls.mymall.coupon.entity.SpuBoundsEntity;
import com.cls.mymall.product.dao.SpuInfoDao;
import com.cls.mymall.product.entity.*;
import com.cls.mymall.product.feign.CouponFeignService;
import com.cls.mymall.product.service.*;
import com.cls.mymall.product.vo.SpuInfoVo;
import com.cls.mymall.product.vo.spuinfo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SpuInfoDescService spuInfoDescService;

    @Autowired
    private SpuImagesService spuImagesService;

    @Autowired
    private CouponFeignService couponFeignService;

    @Autowired
    private ProductAttrValueService productAttrValueService;

    @Autowired
    private AttrService attrService;

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    private SkuImagesService skuImagesService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        String key = (String) params.get("key");
        Long catelogId = (Long) params.get("catelogId");
        Long brandId = (Long) params.get("brandId");
        Integer status = (Integer) params.get("status");

        QueryWrapper<SpuInfoEntity> queryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(key)) {
            queryWrapper.eq("id", key).or().like("spu_name", key).or().like("spu_description", key);
        }
        if (catelogId != null) {
            queryWrapper.eq("catalog_id", catelogId);
        }
        if (brandId != null) {
            queryWrapper.eq("brand_id", brandId);
        }
        if (status != null) {
            queryWrapper.eq("publish_status", status);
        }

        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                queryWrapper
        );

        List<SpuInfoEntity> records = page.getRecords();
        List<SpuInfoVo> collect = records.stream().map(item -> {
            SpuInfoVo vo = new SpuInfoVo();
            BeanUtils.copyProperties(item, vo);
            if (item.getBrandId() != null) {
                vo.setBrandName(brandService.getById(item.getBrandId()).getName());
            }
            if (item.getCatalogId() != null) {
                vo.setCatalogName(categoryService.getById(item.getCatalogId()).getName());
            }
            return vo;
        }).collect(Collectors.toList());

        return new PageUtils(collect, (int) page.getTotal(), (int) page.getSize(), (int) page.getCurrent());
    }

    @Transactional
    @Override
    public void saveSpuInfo(SpuInfoSaveVo spuInfo) {
        // 1 保存 spu信息
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(spuInfo, spuInfoEntity);
        Date date = new Date();
        spuInfoEntity.setCreateTime(date);
        spuInfoEntity.setUpdateTime(date);
        super.save(spuInfoEntity);
        // 2 保存 spu信息介绍
        List<String> decript = spuInfo.getDecript();
        if (decript != null) {
            for (String s : decript) {
                SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
                spuInfoDescEntity.setSpuId(spuInfoEntity.getId());
                spuInfoDescEntity.setDecript(s);
                spuInfoDescService.save(spuInfoDescEntity);
            }
        }
        // 3 保存 spu图片
        List<String> images = spuInfo.getImages();
        if (images != null) {
            for (String image : images) {
                SpuImagesEntity spuImagesEntity = new SpuImagesEntity();
                spuImagesEntity.setSpuId(spuInfoEntity.getId());
                spuImagesEntity.setImgUrl(image);
                spuImagesService.save(spuImagesEntity);
            }
        }
        // 4 保存 商品spu积分设置
        BoundsVo bounds = spuInfo.getBounds();
        SpuBoundsEntity spuBoundsEntity = new SpuBoundsEntity();
        spuBoundsEntity.setSpuId(spuInfoEntity.getId());
        spuBoundsEntity.setBuyBounds(bounds.getBuyBounds());
        spuBoundsEntity.setGrowBounds(bounds.getGrowBounds());
        couponFeignService.save(spuBoundsEntity);
        // 5 spu属性值
        List<BaseAttr> baseAttrs = spuInfo.getBaseAttrs();
        if (baseAttrs != null) {
            for (BaseAttr baseAttr : baseAttrs) {
                ProductAttrValueEntity productAttrValueEntity = new ProductAttrValueEntity();
                AttrEntity attrEntity = attrService.getById(baseAttr.getAttrId());
                productAttrValueEntity.setSpuId(spuInfoEntity.getId());
                productAttrValueEntity.setAttrId(baseAttr.getAttrId());
                productAttrValueEntity.setAttrName(attrEntity.getAttrName());
                productAttrValueEntity.setAttrValue(baseAttr.getAttrValues());
                productAttrValueEntity.setAttrSort(0);
                productAttrValueEntity.setQuickShow(baseAttr.getShowDesc());
                if (!baseAttr.getShowDesc().equals(attrEntity.getShowDesc())) {
                    attrEntity.setShowDesc(baseAttr.getShowDesc());
                    attrService.updateById(attrEntity);
                }
                productAttrValueService.save(productAttrValueEntity);
            }
        }
        // 6sku信息
        List<SkuVo> skus = spuInfo.getSkus();
        if (skus != null) {
            for (SkuVo skuVo : skus) {
                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
                skuInfoEntity.setSpuId(spuInfoEntity.getId());
                skuInfoEntity.setSkuName(skuVo.getSkuName());
//            skuInfoEntity.setSkuDesc();
                skuInfoEntity.setCatalogId(spuInfo.getCatalogId());
                skuInfoEntity.setBrandId(spuInfo.getBrandId());
                for (ImageVo image : skuVo.getImages()) {
                    if (image.getDefaultImg() == 1) {
                        skuInfoEntity.setSkuDefaultImg(image.getImgUrl());
                        break;
                    }
                }
                skuInfoEntity.setSkuTitle(skuVo.getSkuTitle());
                skuInfoEntity.setSkuSubtitle(skuVo.getSkuSubtitle());
                skuInfoEntity.setPrice(skuVo.getPrice());
                skuInfoEntity.setSaleCount(skuVo.getFullCount().longValue());
                skuInfoService.save(skuInfoEntity);
                // 7 保存sku销售属性&值
                List<SkuAttrVo> attr = skuVo.getAttr();
                if (attr != null) {
                    for (SkuAttrVo skuAttrVo : attr) {
                        SkuSaleAttrValueEntity skuSaleAttrValueEntity = new SkuSaleAttrValueEntity();
                        skuSaleAttrValueEntity.setSkuId(skuInfoEntity.getSkuId());
                        skuSaleAttrValueEntity.setAttrId(skuAttrVo.getAttrId());
                        skuSaleAttrValueEntity.setAttrName(skuAttrVo.getAttrName());
                        skuSaleAttrValueEntity.setAttrValue(skuAttrVo.getAttrValue());
                        skuSaleAttrValueEntity.setAttrSort(0);
                        skuSaleAttrValueService.save(skuSaleAttrValueEntity);
                    }
                }
                // 8保存sku图片
                List<ImageVo> skuVoImages = skuVo.getImages();
                if (skuVoImages != null) {
                    for (ImageVo skuVoImage : skuVoImages) {
                        SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                        skuImagesEntity.setSkuId(skuInfoEntity.getSkuId());
                        skuImagesEntity.setImgUrl(skuVoImage.getImgUrl());
                        skuImagesEntity.setDefaultImg(skuImagesEntity.getDefaultImg());
                        skuImagesEntity.setImgSort(0);
                        skuImagesService.save(skuImagesEntity);
                    }
                }
                // 9保存商品阶梯价格
                SkuLadderEntity skuLadderEntity = new SkuLadderEntity();
                skuLadderEntity.setSkuId(skuInfoEntity.getSkuId());
                skuLadderEntity.setFullCount(skuVo.getFullCount());
                skuLadderEntity.setDiscount(skuVo.getDiscount());
                skuLadderEntity.setPrice(skuVo.getPrice().multiply(skuVo.getDiscount()));
//                skuLadderEntity.setAddOther();
                couponFeignService.save(skuLadderEntity);
                // 10保存商品满减信息
                SkuFullReductionEntity skuFullReductionEntity = new SkuFullReductionEntity();
                skuFullReductionEntity.setSkuId(skuInfoEntity.getSkuId());
                skuFullReductionEntity.setFullPrice(skuVo.getFullPrice());
                skuFullReductionEntity.setReducePrice(skuVo.getReducePrice());
                couponFeignService.save(skuFullReductionEntity);
                // 11保存商品会员价格
                List<MemberPriceVo> memberPriceList = skuVo.getMemberPrice();
                if (memberPriceList != null) {
                    for (MemberPriceVo memberPriceVo : memberPriceList) {
                        MemberPriceEntity memberPriceEntity = new MemberPriceEntity();
                        memberPriceEntity.setSkuId(skuInfoEntity.getSkuId());
                        memberPriceEntity.setMemberLevelId(memberPriceVo.getId());
                        memberPriceEntity.setMemberLevelName(memberPriceVo.getName());
                        memberPriceEntity.setMemberPrice(memberPriceVo.getPrice());
                        couponFeignService.save(memberPriceEntity);
                    }
                }
            }
        }
    }

}