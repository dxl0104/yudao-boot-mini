package cn.iocoder.yudao.module.wuyou.dal.mysql.sourceurl;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.sourceurl.SourceUrlDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.wuyou.controller.admin.sourceurl.vo.*;

/**
 * 列表链接 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface SourceUrlMapper extends BaseMapperX<SourceUrlDO> {

    default PageResult<SourceUrlDO> selectPage(SourceUrlPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SourceUrlDO>()
                .eqIfPresent(SourceUrlDO::getListUrl, reqVO.getListUrl())
                .betweenIfPresent(SourceUrlDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(SourceUrlDO::getPages, reqVO.getPages())
                .orderByDesc(SourceUrlDO::getId));
    }

}