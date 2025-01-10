package cn.iocoder.yudao.module.wuyou.dal.mysql.producturl;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.producturl.ProductUrlDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.wuyou.controller.admin.producturl.vo.*;

/**
 * 商品url列表 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ProductUrlMapper extends BaseMapperX<ProductUrlDO> {

    default PageResult<ProductUrlDO> selectPage(ProductUrlPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ProductUrlDO>()
                .eqIfPresent(ProductUrlDO::getUrl, reqVO.getUrl())
                .eq(ProductUrlDO::getProcessFlag,0)
                .betweenIfPresent(ProductUrlDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ProductUrlDO::getId));
    }

}