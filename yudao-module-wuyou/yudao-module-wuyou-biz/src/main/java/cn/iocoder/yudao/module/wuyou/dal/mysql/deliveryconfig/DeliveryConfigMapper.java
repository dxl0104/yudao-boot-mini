package cn.iocoder.yudao.module.wuyou.dal.mysql.deliveryconfig;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.deliveryconfig.DeliveryConfigDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.wuyou.controller.admin.deliveryconfig.vo.*;

/**
 * 快递费归档 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface DeliveryConfigMapper extends BaseMapperX<DeliveryConfigDO> {

    default PageResult<DeliveryConfigDO> selectPage(DeliveryConfigPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DeliveryConfigDO>()
                .eqIfPresent(DeliveryConfigDO::getStartMoney, reqVO.getStartMoney())
                .eqIfPresent(DeliveryConfigDO::getEndMoney, reqVO.getEndMoney())
                .eqIfPresent(DeliveryConfigDO::getLevel, reqVO.getLevel())
                .betweenIfPresent(DeliveryConfigDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DeliveryConfigDO::getId));
    }

}