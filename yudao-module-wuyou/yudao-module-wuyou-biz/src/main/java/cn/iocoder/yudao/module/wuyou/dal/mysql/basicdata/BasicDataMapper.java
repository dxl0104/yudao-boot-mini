package cn.iocoder.yudao.module.wuyou.dal.mysql.basicdata;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.wuyou.controller.admin.basicdata.vo.BasicDataPageReqVO;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.basicdata.BasicDataDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 无忧基础数据 Mapper
 *
 * @author admin234
 */
@Mapper
public interface BasicDataMapper extends BaseMapperX<BasicDataDO> {

    default PageResult<BasicDataDO> selectPage(BasicDataPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<BasicDataDO>()
                .betweenIfPresent(BasicDataDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(BasicDataDO::getUrl, reqVO.getUrl())
                .eqIfPresent(BasicDataDO::getDataJson, reqVO.getDataJson())
                .eqIfPresent(BasicDataDO::getDelivery, reqVO.getDelivery())
                .eqIfPresent(BasicDataDO::getCategory, reqVO.getCategory())
                .orderByDesc(BasicDataDO::getId));
    }

}