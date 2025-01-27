package cn.iocoder.yudao.module.wuyou.service.deliveryempty;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.wuyou.controller.admin.deliveryempty.vo.*;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.deliveryempty.DeliveryEmptyDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 快递为零商品 Service 接口
 *
 * @author 芋道源码
 */
public interface DeliveryEmptyService {

    /**
     * 创建快递为零商品
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDeliveryEmpty(@Valid DeliveryEmptySaveReqVO createReqVO);

    /**
     * 更新快递为零商品
     *
     * @param updateReqVO 更新信息
     */
    void updateDeliveryEmpty(@Valid DeliveryEmptySaveReqVO updateReqVO);

    /**
     * 删除快递为零商品
     *
     * @param id 编号
     */
    void deleteDeliveryEmpty(Long id);

    /**
     * 获得快递为零商品
     *
     * @param id 编号
     * @return 快递为零商品
     */
    DeliveryEmptyDO getDeliveryEmpty(Long id);

    /**
     * 获得快递为零商品分页
     *
     * @param pageReqVO 分页查询
     * @return 快递为零商品分页
     */
    PageResult<DeliveryEmptyDO> getDeliveryEmptyPage(DeliveryEmptyPageReqVO pageReqVO);

}