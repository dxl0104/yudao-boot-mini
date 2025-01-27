package cn.iocoder.yudao.module.wuyou.service.discountrules;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.wuyou.controller.admin.discountrules.vo.*;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.discountrules.DiscountRulesDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.wuyou.dal.mysql.discountrules.DiscountRulesMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.wuyou.enums.ErrorCodeConstants.*;

/**
 * 折扣规则 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class DiscountRulesServiceImpl implements DiscountRulesService {

    @Resource
    private DiscountRulesMapper discountRulesMapper;

    @Override
    public Long createDiscountRules(DiscountRulesSaveReqVO createReqVO) {
        // 插入
        DiscountRulesDO discountRules = BeanUtils.toBean(createReqVO, DiscountRulesDO.class);
        discountRulesMapper.insert(discountRules);
        // 返回
        return discountRules.getId();
    }

    @Override
    public void updateDiscountRules(DiscountRulesSaveReqVO updateReqVO) {
        // 校验存在
        validateDiscountRulesExists(updateReqVO.getId());
        // 更新
        DiscountRulesDO updateObj = BeanUtils.toBean(updateReqVO, DiscountRulesDO.class);
        discountRulesMapper.updateById(updateObj);
    }

    @Override
    public void deleteDiscountRules(Long id) {
        // 校验存在
        validateDiscountRulesExists(id);
        // 删除
        discountRulesMapper.deleteById(id);
    }

    private void validateDiscountRulesExists(Long id) {
        if (discountRulesMapper.selectById(id) == null) {
            throw exception(DISCOUNT_RULES_NOT_EXISTS);
        }
    }

    @Override
    public DiscountRulesDO getDiscountRules(Long id) {
        return discountRulesMapper.selectById(id);
    }

    @Override
    public PageResult<DiscountRulesDO> getDiscountRulesPage(DiscountRulesPageReqVO pageReqVO) {
        return discountRulesMapper.selectPage(pageReqVO);
    }

}