package cn.iocoder.yudao.module.wuyou.service.task;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.wuyou.controller.admin.task.vo.*;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.task.TaskDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 采集任务 Service 接口
 *
 * @author 芋道源码
 */
public interface TaskService {

    /**
     * 创建采集任务
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTask(@Valid TaskSaveReqVO createReqVO);

    /**
     * 更新采集任务
     *
     * @param updateReqVO 更新信息
     */
    void updateTask(@Valid TaskSaveReqVO updateReqVO);

    /**
     * 删除采集任务
     *
     * @param id 编号
     */
    void deleteTask(Long id);

    /**
     * 获得采集任务
     *
     * @param id 编号
     * @return 采集任务
     */
    TaskDO getTask(Long id);

    /**
     * 获得采集任务分页
     *
     * @param pageReqVO 分页查询
     * @return 采集任务分页
     */
    PageResult<TaskDO> getTaskPage(TaskPageReqVO pageReqVO);

}