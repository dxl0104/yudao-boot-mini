package cn.iocoder.yudao.module.wuyou.dal.mysql.deliveryempty;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.deliveryempty.DeliveryEmptyDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.wuyou.controller.admin.deliveryempty.vo.*;

/**
 * 快递为零商品 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface DeliveryEmptyMapper extends BaseMapperX<DeliveryEmptyDO> {

    default PageResult<DeliveryEmptyDO> selectPage(DeliveryEmptyPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DeliveryEmptyDO>()
                .betweenIfPresent(DeliveryEmptyDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(DeliveryEmptyDO::getUrl, reqVO.getUrl())
                .eqIfPresent(DeliveryEmptyDO::getDataJson, reqVO.getDataJson())
                .eqIfPresent(DeliveryEmptyDO::getDelivery, reqVO.getDelivery())
                .eqIfPresent(DeliveryEmptyDO::getCategory, reqVO.getCategory())
                .eqIfPresent(DeliveryEmptyDO::getOfferId, reqVO.getOfferId())
                .eqIfPresent(DeliveryEmptyDO::getEan, reqVO.getEan())
                .eqIfPresent(DeliveryEmptyDO::getTitle, reqVO.getTitle())
                .eqIfPresent(DeliveryEmptyDO::getProductId, reqVO.getProductId())
                .eqIfPresent(DeliveryEmptyDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(DeliveryEmptyDO::getPrice, reqVO.getPrice())
                .eqIfPresent(DeliveryEmptyDO::getCurrency, reqVO.getCurrency())
                .eqIfPresent(DeliveryEmptyDO::getMainCategory1, reqVO.getMainCategory1())
                .eqIfPresent(DeliveryEmptyDO::getMainCategory2, reqVO.getMainCategory2())
                .eqIfPresent(DeliveryEmptyDO::getMainCategory3, reqVO.getMainCategory3())
                .eqIfPresent(DeliveryEmptyDO::getImgUrl, reqVO.getImgUrl())
                .eqIfPresent(DeliveryEmptyDO::getMainUrl, reqVO.getMainUrl())
                .orderByDesc(DeliveryEmptyDO::getId));
    }

}