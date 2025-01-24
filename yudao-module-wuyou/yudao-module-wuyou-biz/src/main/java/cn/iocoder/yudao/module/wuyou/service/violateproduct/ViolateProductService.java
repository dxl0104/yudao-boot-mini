package cn.iocoder.yudao.module.wuyou.service.violateproduct;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.wuyou.controller.admin.violateproduct.vo.*;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.violateproduct.ViolateProductDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 侵权商品 Service 接口
 *
 * @author 芋道源码
 */
public interface ViolateProductService {

    /**
     * 创建侵权商品
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createViolateProduct(@Valid ViolateProductSaveReqVO createReqVO);

    /**
     * 更新侵权商品
     *
     * @param updateReqVO 更新信息
     */
    void updateViolateProduct(@Valid ViolateProductSaveReqVO updateReqVO);

    /**
     * 删除侵权商品
     *
     * @param id 编号
     */
    void deleteViolateProduct(Long id);

    /**
     * 获得侵权商品
     *
     * @param id 编号
     * @return 侵权商品
     */
    ViolateProductDO getViolateProduct(Long id);

    /**
     * 获得侵权商品分页
     *
     * @param pageReqVO 分页查询
     * @return 侵权商品分页
     */
    PageResult<ViolateProductDO> getViolateProductPage(ViolateProductPageReqVO pageReqVO);

    /**
     * 恢复侵权商品
     *
     * @param id 编号
     */
    void recoverViolateProduct(Long id);
}