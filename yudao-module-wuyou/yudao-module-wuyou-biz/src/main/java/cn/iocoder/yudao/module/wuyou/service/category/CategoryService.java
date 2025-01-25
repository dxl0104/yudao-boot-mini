package cn.iocoder.yudao.module.wuyou.service.category;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.wuyou.controller.admin.category.vo.*;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.category.CategoryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 数据类别 Service 接口
 *
 * @author 芋道源码
 */
public interface CategoryService {

    /**
     * 创建数据类别
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCategory(@Valid CategorySaveReqVO createReqVO);

    /**
     * 更新数据类别
     *
     * @param updateReqVO 更新信息
     */
    void updateCategory(@Valid CategorySaveReqVO updateReqVO);

    /**
     * 删除数据类别
     *
     * @param id 编号
     */
    void deleteCategory(Long id);

    /**
     * 获得数据类别
     *
     * @param id 编号
     * @return 数据类别
     */
    CategoryDO getCategory(Long id);

    /**
     * 获得数据类别列表
     *
     * @param listReqVO 查询条件
     * @return 数据类别列表
     */
    List<CategoryDO> getCategoryList(CategoryListReqVO listReqVO);

}