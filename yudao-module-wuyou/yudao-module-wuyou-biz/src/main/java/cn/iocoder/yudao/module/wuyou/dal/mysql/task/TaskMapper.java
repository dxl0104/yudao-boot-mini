package cn.iocoder.yudao.module.wuyou.dal.mysql.task;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.task.TaskDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.wuyou.controller.admin.task.vo.*;

/**
 * 采集任务 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TaskMapper extends BaseMapperX<TaskDO> {

    default PageResult<TaskDO> selectPage(TaskPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TaskDO>()
                .eqIfPresent(TaskDO::getTaskType, reqVO.getTaskType())
                .eqIfPresent(TaskDO::getStatus, reqVO.getStatus())
                .eqIfPresent(TaskDO::getTimeoutAt, reqVO.getTimeoutAt())
                .eqIfPresent(TaskDO::getPages, reqVO.getPages())
                .betweenIfPresent(TaskDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(TaskDO::getPriority, reqVO.getPriority())
                .betweenIfPresent(TaskDO::getStartTime, reqVO.getStartTime())
                .betweenIfPresent(TaskDO::getEndTime, reqVO.getEndTime())
                .orderByDesc(TaskDO::getId));
    }

}