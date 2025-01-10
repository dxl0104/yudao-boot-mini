package cn.iocoder.yudao.module.wuyou.service.producturl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.wuyou.controller.admin.producturl.vo.*;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.producturl.ProductUrlDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.wuyou.dal.mysql.producturl.ProductUrlMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.wuyou.enums.ErrorCodeConstants.*;

/**
 * 商品url列表 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
public class ProductUrlServiceImpl implements ProductUrlService {

    @Resource
    private ProductUrlMapper productUrlMapper;

    @Override
    public Long createProductUrl(ProductUrlSaveReqVO createReqVO) {
        // 插入
        ProductUrlDO productUrl = BeanUtils.toBean(createReqVO, ProductUrlDO.class);
        try {
            productUrlMapper.insert(productUrl);
        }catch (Exception e){
            log.info("url 重复{}",createReqVO.getUrl());
        }
        // 返回
        return productUrl.getId();
    }

    @Override
    public Long createBatchProductUrl(ProductUrlBatchSaveReqVO createReqVO) {
        List<String> productList = createReqVO.getProductList();
        for (String url : productList) {
            ProductUrlDO productUrlDO = new ProductUrlDO();
            productUrlDO.setUrl(url);
            try {
                productUrlMapper.insert(productUrlDO);
            }catch (Exception e){
                log.info("url 重复{}",url);
            }
        }
        return null;
    }

    @Override
    public void updateProductUrl(ProductUrlSaveReqVO updateReqVO) {
        // 校验存在
        validateProductUrlExists(updateReqVO.getId());
        // 更新
        ProductUrlDO updateObj = BeanUtils.toBean(updateReqVO, ProductUrlDO.class);
        productUrlMapper.updateById(updateObj);
    }

    @Override
    public void deleteProductUrl(Long id) {
        // 校验存在
        validateProductUrlExists(id);
        // 删除
        productUrlMapper.deleteById(id);
    }

    private void validateProductUrlExists(Long id) {
        if (productUrlMapper.selectById(id) == null) {
            throw exception(PRODUCT_URL_NOT_EXISTS);
        }
    }

    @Override
    public ProductUrlDO getProductUrl(Long id) {
        return productUrlMapper.selectById(id);
    }

    @Override
    public PageResult<ProductUrlDO> getProductUrlPage(ProductUrlPageReqVO pageReqVO) {
        return productUrlMapper.selectPage(pageReqVO);
    }

}