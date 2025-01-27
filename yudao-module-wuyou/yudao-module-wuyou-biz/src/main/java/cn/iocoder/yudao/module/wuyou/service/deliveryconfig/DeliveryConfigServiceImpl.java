package cn.iocoder.yudao.module.wuyou.service.deliveryconfig;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.wuyou.controller.admin.deliveryconfig.vo.*;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.deliveryconfig.DeliveryConfigDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.wuyou.dal.mysql.deliveryconfig.DeliveryConfigMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.wuyou.enums.ErrorCodeConstants.*;

/**
 * 快递费归档 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class DeliveryConfigServiceImpl implements DeliveryConfigService {

    @Resource
    private DeliveryConfigMapper deliveryConfigMapper;

    @Override
    public Long createDeliveryConfig(DeliveryConfigSaveReqVO createReqVO) {
        // 插入
        DeliveryConfigDO deliveryConfig = BeanUtils.toBean(createReqVO, DeliveryConfigDO.class);
        deliveryConfigMapper.insert(deliveryConfig);
        // 返回
        return deliveryConfig.getId();
    }

    @Override
    public void updateDeliveryConfig(DeliveryConfigSaveReqVO updateReqVO) {
        // 校验存在
        validateDeliveryConfigExists(updateReqVO.getId());
        // 更新
        DeliveryConfigDO updateObj = BeanUtils.toBean(updateReqVO, DeliveryConfigDO.class);
        deliveryConfigMapper.updateById(updateObj);
    }

    @Override
    public void deleteDeliveryConfig(Long id) {
        // 校验存在
        validateDeliveryConfigExists(id);
        // 删除
        deliveryConfigMapper.deleteById(id);
    }

    private void validateDeliveryConfigExists(Long id) {
        if (deliveryConfigMapper.selectById(id) == null) {
            throw exception(DELIVERY_CONFIG_NOT_EXISTS);
        }
    }

    @Override
    public DeliveryConfigDO getDeliveryConfig(Long id) {
        return deliveryConfigMapper.selectById(id);
    }

    @Override
    public PageResult<DeliveryConfigDO> getDeliveryConfigPage(DeliveryConfigPageReqVO pageReqVO) {
        return deliveryConfigMapper.selectPage(pageReqVO);
    }

}