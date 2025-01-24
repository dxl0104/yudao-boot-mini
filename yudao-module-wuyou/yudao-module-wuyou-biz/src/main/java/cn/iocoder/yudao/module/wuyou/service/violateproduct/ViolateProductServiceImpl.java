package cn.iocoder.yudao.module.wuyou.service.violateproduct;

import cn.iocoder.yudao.module.wuyou.dal.dataobject.basicdata.BasicDataDO;
import cn.iocoder.yudao.module.wuyou.dal.mysql.basicdata.BasicDataMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.wuyou.controller.admin.violateproduct.vo.*;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.violateproduct.ViolateProductDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.wuyou.dal.mysql.violateproduct.ViolateProductMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.wuyou.enums.ErrorCodeConstants.*;

/**
 * 侵权商品 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ViolateProductServiceImpl implements ViolateProductService {

    @Resource
    private ViolateProductMapper violateProductMapper;

    @Resource
    private BasicDataMapper basicDataMapper;

    @Override
    public Long createViolateProduct(ViolateProductSaveReqVO createReqVO) {
        // 插入
        ViolateProductDO violateProduct = BeanUtils.toBean(createReqVO, ViolateProductDO.class);
        violateProductMapper.insert(violateProduct);
        // 返回
        return violateProduct.getId();
    }

    @Override
    public void updateViolateProduct(ViolateProductSaveReqVO updateReqVO) {
        // 校验存在
        validateViolateProductExists(updateReqVO.getId());
        // 更新
        ViolateProductDO updateObj = BeanUtils.toBean(updateReqVO, ViolateProductDO.class);
        violateProductMapper.updateById(updateObj);
    }

    @Override
    public void deleteViolateProduct(Long id) {
        // 校验存在
        validateViolateProductExists(id);
        // 删除
        violateProductMapper.deleteById(id);
    }

    private void validateViolateProductExists(Long id) {
        if (violateProductMapper.selectById(id) == null) {
            throw exception(VIOLATE_PRODUCT_NOT_EXISTS);
        }
    }

    @Override
    public ViolateProductDO getViolateProduct(Long id) {
        return violateProductMapper.selectById(id);
    }

    @Override
    public PageResult<ViolateProductDO> getViolateProductPage(ViolateProductPageReqVO pageReqVO) {
        return violateProductMapper.selectPage(pageReqVO);
    }

    @Override
    public void recoverViolateProduct(Long id) {
        // 校验存在
        validateViolateProductExists(id);

        ViolateProductDO violateProductDO = violateProductMapper.selectById(id);
        BasicDataDO basicDataDO = new BasicDataDO();
        BeanUtils.copyProperties(violateProductDO,basicDataDO);
        basicDataMapper.insert(basicDataDO);
        // 删除
        violateProductMapper.deleteById(id);

    }
}