package cn.iocoder.yudao.module.wuyou.dal.mysql.taskpagedetail;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.taskpagedetail.TaskPageDetailDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.wuyou.controller.admin.taskpagedetail.vo.*;

/**
 * 页数据采集状态 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TaskPageDetailMapper extends BaseMapperX<TaskPageDetailDO> {

    default PageResult<TaskPageDetailDO> selectPage(TaskPageDetailPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TaskPageDetailDO>()
                .eqIfPresent(TaskPageDetailDO::getTaskId, reqVO.getTaskId())
                .eqIfPresent(TaskPageDetailDO::getPageNum, reqVO.getPageNum())
                .eqIfPresent(TaskPageDetailDO::getStatus, reqVO.getStatus())
                .eqIfPresent(TaskPageDetailDO::getDeviceId, reqVO.getDeviceId())
                .eqIfPresent(TaskPageDetailDO::getAssignedAt, reqVO.getAssignedAt())
                .betweenIfPresent(TaskPageDetailDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(TaskPageDetailDO::getTimeout, reqVO.getTimeout())
                .orderByDesc(TaskPageDetailDO::getId));
    }

}