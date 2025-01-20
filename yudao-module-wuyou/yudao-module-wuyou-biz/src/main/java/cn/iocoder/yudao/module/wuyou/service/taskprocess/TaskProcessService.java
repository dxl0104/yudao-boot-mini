package cn.iocoder.yudao.module.wuyou.service.taskprocess;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.wuyou.controller.admin.taskprocess.vo.*;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.taskprocess.TaskProcessDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 任务进度 Service 接口
 *
 * @author 芋道源码
 */
public interface TaskProcessService {

    /**
     * 创建任务进度
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTaskProcess(@Valid TaskProcessSaveReqVO createReqVO);

    /**
     * 更新任务进度
     *
     * @param updateReqVO 更新信息
     */
    void updateTaskProcess(@Valid TaskProcessSaveReqVO updateReqVO);

    /**
     * 删除任务进度
     *
     * @param id 编号
     */
    void deleteTaskProcess(Long id);

    /**
     * 获得任务进度
     *
     * @param id 编号
     * @return 任务进度
     */
    TaskProcessDO getTaskProcess(Long id);

    /**
     * 获得任务进度分页
     *
     * @param pageReqVO 分页查询
     * @return 任务进度分页
     */
    PageResult<TaskProcessDO> getTaskProcessPage(TaskProcessPageReqVO pageReqVO);

}