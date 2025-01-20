package cn.iocoder.yudao.module.wuyou.service.taskprocess;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.wuyou.controller.admin.taskprocess.vo.*;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.taskprocess.TaskProcessDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.wuyou.dal.mysql.taskprocess.TaskProcessMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.wuyou.enums.ErrorCodeConstants.*;

/**
 * 任务进度 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TaskProcessServiceImpl implements TaskProcessService {

    @Resource
    private TaskProcessMapper taskProcessMapper;

    @Override
    public Long createTaskProcess(TaskProcessSaveReqVO createReqVO) {
        // 插入
        TaskProcessDO taskProcess = BeanUtils.toBean(createReqVO, TaskProcessDO.class);
        taskProcessMapper.insert(taskProcess);
        // 返回
        return taskProcess.getId();
    }

    @Override
    public void updateTaskProcess(TaskProcessSaveReqVO updateReqVO) {
        // 校验存在
        validateTaskProcessExists(updateReqVO.getId());
        // 更新
        TaskProcessDO updateObj = BeanUtils.toBean(updateReqVO, TaskProcessDO.class);
        taskProcessMapper.updateById(updateObj);
    }

    @Override
    public void deleteTaskProcess(Long id) {
        // 校验存在
        validateTaskProcessExists(id);
        // 删除
        taskProcessMapper.deleteById(id);
    }

    private void validateTaskProcessExists(Long id) {
        if (taskProcessMapper.selectById(id) == null) {
            throw exception(TASK_PROCESS_NOT_EXISTS);
        }
    }

    @Override
    public TaskProcessDO getTaskProcess(Long id) {
        return taskProcessMapper.selectById(id);
    }

    @Override
    public PageResult<TaskProcessDO> getTaskProcessPage(TaskProcessPageReqVO pageReqVO) {
        return taskProcessMapper.selectPage(pageReqVO);
    }

}