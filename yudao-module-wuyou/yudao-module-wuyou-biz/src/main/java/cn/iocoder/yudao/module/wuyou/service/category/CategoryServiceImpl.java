package cn.iocoder.yudao.module.wuyou.service.category;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.wuyou.controller.admin.category.vo.*;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.category.CategoryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.wuyou.dal.mysql.category.CategoryMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.wuyou.enums.ErrorCodeConstants.*;

/**
 * 数据类别 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public Long createCategory(CategorySaveReqVO createReqVO) {
        // 校验父id的有效性
        validateParentCategory(null, createReqVO.getParentId());
        // 校验中文名称的唯一性
        validateCategoryZhNameUnique(null, createReqVO.getParentId(), createReqVO.getZhName());

        // 插入
        CategoryDO category = BeanUtils.toBean(createReqVO, CategoryDO.class);
        categoryMapper.insert(category);
        // 返回
        return category.getId();
    }

    @Override
    public void updateCategory(CategorySaveReqVO updateReqVO) {
        // 校验存在
        validateCategoryExists(updateReqVO.getId());
        // 校验父id的有效性
        validateParentCategory(updateReqVO.getId(), updateReqVO.getParentId());
        // 校验中文名称的唯一性
        validateCategoryZhNameUnique(updateReqVO.getId(), updateReqVO.getParentId(), updateReqVO.getZhName());

        // 更新
        CategoryDO updateObj = BeanUtils.toBean(updateReqVO, CategoryDO.class);
        categoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteCategory(Long id) {
        // 校验存在
        validateCategoryExists(id);
        // 校验是否有子数据类别
        if (categoryMapper.selectCountByParentId(id) > 0) {
            throw exception(CATEGORY_EXITS_CHILDREN);
        }
        // 删除
        categoryMapper.deleteById(id);
    }

    private void validateCategoryExists(Long id) {
        if (categoryMapper.selectById(id) == null) {
            throw exception(CATEGORY_NOT_EXISTS);
        }
    }

    private void validateParentCategory(Long id, Long parentId) {
        if (parentId == null || CategoryDO.PARENT_ID_ROOT.equals(parentId)) {
            return;
        }
        // 1. 不能设置自己为父数据类别
        if (Objects.equals(id, parentId)) {
            throw exception(CATEGORY_PARENT_ERROR);
        }
        // 2. 父数据类别不存在
        CategoryDO parentCategory = categoryMapper.selectById(parentId);
        if (parentCategory == null) {
            throw exception(CATEGORY_PARENT_NOT_EXITS);
        }
        // 3. 递归校验父数据类别，如果父数据类别是自己的子数据类别，则报错，避免形成环路
        if (id == null) { // id 为空，说明新增，不需要考虑环路
            return;
        }
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            // 3.1 校验环路
            parentId = parentCategory.getParentId();
            if (Objects.equals(id, parentId)) {
                throw exception(CATEGORY_PARENT_IS_CHILD);
            }
            // 3.2 继续递归下一级父数据类别
            if (parentId == null || CategoryDO.PARENT_ID_ROOT.equals(parentId)) {
                break;
            }
            parentCategory = categoryMapper.selectById(parentId);
            if (parentCategory == null) {
                break;
            }
        }
    }

    private void validateCategoryZhNameUnique(Long id, Long parentId, String zhName) {
        CategoryDO category = categoryMapper.selectByParentIdAndZhName(parentId, zhName);
        if (category == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的数据类别
        if (id == null) {
            throw exception(CATEGORY_ZH_NAME_DUPLICATE);
        }
        if (!Objects.equals(category.getId(), id)) {
            throw exception(CATEGORY_ZH_NAME_DUPLICATE);
        }
    }

    @Override
    public CategoryDO getCategory(Long id) {
        return categoryMapper.selectById(id);
    }

    @Override
    public List<CategoryDO> getCategoryList(CategoryListReqVO listReqVO) {
        return categoryMapper.selectList(listReqVO);
    }

}