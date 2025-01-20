package cn.iocoder.yudao.module.wuyou.dal.mysql.taskprocess;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.taskprocess.TaskProcessDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.wuyou.controller.admin.taskprocess.vo.*;

/**
 * 任务进度 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TaskProcessMapper extends BaseMapperX<TaskProcessDO> {

    default PageResult<TaskProcessDO> selectPage(TaskProcessPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TaskProcessDO>()
                .eqIfPresent(TaskProcessDO::getTaskId, reqVO.getTaskId())
                .eqIfPresent(TaskProcessDO::getDeviceId, reqVO.getDeviceId())
                .eqIfPresent(TaskProcessDO::getProcess, reqVO.getProcess())
                .eqIfPresent(TaskProcessDO::getCollectCount, reqVO.getCollectCount())
                .eqIfPresent(TaskProcessDO::getTotalCount, reqVO.getTotalCount())
                .eqIfPresent(TaskProcessDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(TaskProcessDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TaskProcessDO::getId));
    }

}