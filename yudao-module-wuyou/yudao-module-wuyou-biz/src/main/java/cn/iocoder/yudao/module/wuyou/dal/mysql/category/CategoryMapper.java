package cn.iocoder.yudao.module.wuyou.dal.mysql.category;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.category.CategoryDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.wuyou.controller.admin.category.vo.*;

/**
 * 数据类别 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface CategoryMapper extends BaseMapperX<CategoryDO> {

    default List<CategoryDO> selectList(CategoryListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<CategoryDO>()
                .likeIfPresent(CategoryDO::getCategoryName, reqVO.getCategoryName())
                .likeIfPresent(CategoryDO::getZhName, reqVO.getZhName())
                .eqIfPresent(CategoryDO::getLevel, reqVO.getLevel())
                .eqIfPresent(CategoryDO::getParentId, reqVO.getParentId())
                .betweenIfPresent(CategoryDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CategoryDO::getId));
    }

	default CategoryDO selectByParentIdAndZhName(Long parentId, String zhName) {
	    return selectOne(CategoryDO::getParentId, parentId, CategoryDO::getZhName, zhName);
	}

    default Long selectCountByParentId(Long parentId) {
        return selectCount(CategoryDO::getParentId, parentId);
    }

}