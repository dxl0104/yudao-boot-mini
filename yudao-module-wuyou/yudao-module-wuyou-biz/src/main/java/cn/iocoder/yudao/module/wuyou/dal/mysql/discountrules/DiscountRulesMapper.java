package cn.iocoder.yudao.module.wuyou.dal.mysql.discountrules;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.discountrules.DiscountRulesDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.wuyou.controller.admin.discountrules.vo.*;

/**
 * 折扣规则 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface DiscountRulesMapper extends BaseMapperX<DiscountRulesDO> {

    default PageResult<DiscountRulesDO> selectPage(DiscountRulesPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DiscountRulesDO>()
                .eqIfPresent(DiscountRulesDO::getMinAmount, reqVO.getMinAmount())
                .eqIfPresent(DiscountRulesDO::getDiscountAmount, reqVO.getDiscountAmount())
                .eqIfPresent(DiscountRulesDO::getShippingDiscount, reqVO.getShippingDiscount())
                .betweenIfPresent(DiscountRulesDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(DiscountRulesDO::getMaxAmount, reqVO.getMaxAmount())
                .orderByDesc(DiscountRulesDO::getId));
    }

}