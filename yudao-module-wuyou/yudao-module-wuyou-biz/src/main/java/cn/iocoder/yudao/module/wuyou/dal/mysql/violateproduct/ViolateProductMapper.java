package cn.iocoder.yudao.module.wuyou.dal.mysql.violateproduct;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.violateproduct.ViolateProductDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.wuyou.controller.admin.violateproduct.vo.*;

/**
 * 侵权商品 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ViolateProductMapper extends BaseMapperX<ViolateProductDO> {

    default PageResult<ViolateProductDO> selectPage(ViolateProductPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ViolateProductDO>()
                .betweenIfPresent(ViolateProductDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(ViolateProductDO::getUrl, reqVO.getUrl())
                .eqIfPresent(ViolateProductDO::getDataJson, reqVO.getDataJson())
                .eqIfPresent(ViolateProductDO::getDelivery, reqVO.getDelivery())
                .eqIfPresent(ViolateProductDO::getCategory, reqVO.getCategory())
                .eqIfPresent(ViolateProductDO::getOfferId, reqVO.getOfferId())
                .eqIfPresent(ViolateProductDO::getEan, reqVO.getEan())
                .eqIfPresent(ViolateProductDO::getTitle, reqVO.getTitle())
                .eqIfPresent(ViolateProductDO::getProductId, reqVO.getProductId())
                .eqIfPresent(ViolateProductDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(ViolateProductDO::getPrice, reqVO.getPrice())
                .eqIfPresent(ViolateProductDO::getCurrency, reqVO.getCurrency())
                .eqIfPresent(ViolateProductDO::getMainCategory1, reqVO.getMainCategory1())
                .eqIfPresent(ViolateProductDO::getMainCategory2, reqVO.getMainCategory2())
                .eqIfPresent(ViolateProductDO::getMainCategory3, reqVO.getMainCategory3())
                .eqIfPresent(ViolateProductDO::getImgUrl, reqVO.getImgUrl())
                .eqIfPresent(ViolateProductDO::getOfferDescription, reqVO.getOfferDescription())
                .eqIfPresent(ViolateProductDO::getMainUrl, reqVO.getMainUrl())
                .eqIfPresent(ViolateProductDO::getViolateWord, reqVO.getViolateWord())
                .orderByDesc(ViolateProductDO::getId));
    }

}