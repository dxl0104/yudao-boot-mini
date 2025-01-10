package cn.iocoder.yudao.module.wuyou.service.producturl;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.wuyou.controller.admin.producturl.vo.*;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.producturl.ProductUrlDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 商品url列表 Service 接口
 *
 * @author 芋道源码
 */
public interface ProductUrlService {

    /**
     * 创建商品url列表
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProductUrl(@Valid ProductUrlSaveReqVO createReqVO);

    /**
     * 更新商品url列表
     *
     * @param updateReqVO 更新信息
     */
    void updateProductUrl(@Valid ProductUrlSaveReqVO updateReqVO);

    /**
     * 删除商品url列表
     *
     * @param id 编号
     */
    void deleteProductUrl(Long id);

    /**
     * 获得商品url列表
     *
     * @param id 编号
     * @return 商品url列表
     */
    ProductUrlDO getProductUrl(Long id);

    /**
     * 获得商品url列表分页
     *
     * @param pageReqVO 分页查询
     * @return 商品url列表分页
     */
    PageResult<ProductUrlDO> getProductUrlPage(ProductUrlPageReqVO pageReqVO);

    Long createBatchProductUrl(ProductUrlBatchSaveReqVO createReqVO);
}