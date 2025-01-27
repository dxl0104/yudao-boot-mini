package cn.iocoder.yudao.module.wuyou.service.deliveryconfig;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.wuyou.controller.admin.deliveryconfig.vo.*;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.deliveryconfig.DeliveryConfigDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 快递费归档 Service 接口
 *
 * @author 芋道源码
 */
public interface DeliveryConfigService {

    /**
     * 创建快递费归档
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDeliveryConfig(@Valid DeliveryConfigSaveReqVO createReqVO);

    /**
     * 更新快递费归档
     *
     * @param updateReqVO 更新信息
     */
    void updateDeliveryConfig(@Valid DeliveryConfigSaveReqVO updateReqVO);

    /**
     * 删除快递费归档
     *
     * @param id 编号
     */
    void deleteDeliveryConfig(Long id);

    /**
     * 获得快递费归档
     *
     * @param id 编号
     * @return 快递费归档
     */
    DeliveryConfigDO getDeliveryConfig(Long id);

    /**
     * 获得快递费归档分页
     *
     * @param pageReqVO 分页查询
     * @return 快递费归档分页
     */
    PageResult<DeliveryConfigDO> getDeliveryConfigPage(DeliveryConfigPageReqVO pageReqVO);

}