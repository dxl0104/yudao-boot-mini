package cn.iocoder.yudao.module.wuyou.service.discountrules;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.wuyou.controller.admin.discountrules.vo.*;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.discountrules.DiscountRulesDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 折扣规则 Service 接口
 *
 * @author 芋道源码
 */
public interface DiscountRulesService {

    /**
     * 创建折扣规则
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDiscountRules(@Valid DiscountRulesSaveReqVO createReqVO);

    /**
     * 更新折扣规则
     *
     * @param updateReqVO 更新信息
     */
    void updateDiscountRules(@Valid DiscountRulesSaveReqVO updateReqVO);

    /**
     * 删除折扣规则
     *
     * @param id 编号
     */
    void deleteDiscountRules(Long id);

    /**
     * 获得折扣规则
     *
     * @param id 编号
     * @return 折扣规则
     */
    DiscountRulesDO getDiscountRules(Long id);

    /**
     * 获得折扣规则分页
     *
     * @param pageReqVO 分页查询
     * @return 折扣规则分页
     */
    PageResult<DiscountRulesDO> getDiscountRulesPage(DiscountRulesPageReqVO pageReqVO);

}