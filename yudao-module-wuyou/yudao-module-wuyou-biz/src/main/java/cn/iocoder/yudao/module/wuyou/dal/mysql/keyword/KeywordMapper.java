package cn.iocoder.yudao.module.wuyou.dal.mysql.keyword;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.keyword.KeywordDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.wuyou.controller.admin.keyword.vo.*;

/**
 * 无忧侵权词 Mapper
 *
 * @author admin234
 */
@Mapper
public interface KeywordMapper extends BaseMapperX<KeywordDO> {

    default PageResult<KeywordDO> selectPage(KeywordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<KeywordDO>()
                .eqIfPresent(KeywordDO::getInfringementKeyword, reqVO.getInfringementKeyword())
                .eqIfPresent(KeywordDO::getPlatform, reqVO.getPlatform())
                .betweenIfPresent(KeywordDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(KeywordDO::getId));
    }

}