package cn.iocoder.yudao.module.wuyou.service.deliveryempty;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.wuyou.controller.admin.deliveryempty.vo.*;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.deliveryempty.DeliveryEmptyDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.wuyou.dal.mysql.deliveryempty.DeliveryEmptyMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.wuyou.enums.ErrorCodeConstants.*;

/**
 * 快递为零商品 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class DeliveryEmptyServiceImpl implements DeliveryEmptyService {

    @Resource
    private DeliveryEmptyMapper deliveryEmptyMapper;

    @Override
    public Long createDeliveryEmpty(DeliveryEmptySaveReqVO createReqVO) {
        // 插入
        DeliveryEmptyDO deliveryEmpty = BeanUtils.toBean(createReqVO, DeliveryEmptyDO.class);
        deliveryEmptyMapper.insert(deliveryEmpty);
        // 返回
        return deliveryEmpty.getId();
    }

    @Override
    public void updateDeliveryEmpty(DeliveryEmptySaveReqVO updateReqVO) {
        // 校验存在
        validateDeliveryEmptyExists(updateReqVO.getId());
        // 更新
        DeliveryEmptyDO updateObj = BeanUtils.toBean(updateReqVO, DeliveryEmptyDO.class);
        deliveryEmptyMapper.updateById(updateObj);
    }

    @Override
    public void deleteDeliveryEmpty(Long id) {
        // 校验存在
        validateDeliveryEmptyExists(id);
        // 删除
        deliveryEmptyMapper.deleteById(id);
    }

    private void validateDeliveryEmptyExists(Long id) {
        if (deliveryEmptyMapper.selectById(id) == null) {
            throw exception(DELIVERY_EMPTY_NOT_EXISTS);
        }
    }

    @Override
    public DeliveryEmptyDO getDeliveryEmpty(Long id) {
        return deliveryEmptyMapper.selectById(id);
    }

    @Override
    public PageResult<DeliveryEmptyDO> getDeliveryEmptyPage(DeliveryEmptyPageReqVO pageReqVO) {
        return deliveryEmptyMapper.selectPage(pageReqVO);
    }

}