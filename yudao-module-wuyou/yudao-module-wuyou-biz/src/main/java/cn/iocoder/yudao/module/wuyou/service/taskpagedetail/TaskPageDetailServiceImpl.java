package cn.iocoder.yudao.module.wuyou.service.taskpagedetail;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.wuyou.controller.admin.taskpagedetail.vo.*;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.taskpagedetail.TaskPageDetailDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.wuyou.dal.mysql.taskpagedetail.TaskPageDetailMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.wuyou.enums.ErrorCodeConstants.*;

/**
 * 页数据采集状态 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class TaskPageDetailServiceImpl implements TaskPageDetailService {

    @Resource
    private TaskPageDetailMapper taskPageDetailMapper;

    @Override
    public Long createTaskPageDetail(TaskPageDetailSaveReqVO createReqVO) {
        // 插入
        TaskPageDetailDO taskPageDetail = BeanUtils.toBean(createReqVO, TaskPageDetailDO.class);
        taskPageDetailMapper.insert(taskPageDetail);
        // 返回
        return taskPageDetail.getId();
    }

    @Override
    public void updateTaskPageDetail(TaskPageDetailSaveReqVO updateReqVO) {
        // 校验存在
        validateTaskPageDetailExists(updateReqVO.getId());
        // 更新
        TaskPageDetailDO updateObj = BeanUtils.toBean(updateReqVO, TaskPageDetailDO.class);
        taskPageDetailMapper.updateById(updateObj);
    }

    @Override
    public void deleteTaskPageDetail(Long id) {
        // 校验存在
        validateTaskPageDetailExists(id);
        // 删除
        taskPageDetailMapper.deleteById(id);
    }

    private void validateTaskPageDetailExists(Long id) {
        if (taskPageDetailMapper.selectById(id) == null) {
            throw exception(TASK_PAGE_DETAIL_NOT_EXISTS);
        }
    }

    @Override
    public TaskPageDetailDO getTaskPageDetail(Long id) {
        return taskPageDetailMapper.selectById(id);
    }

    @Override
    public PageResult<TaskPageDetailDO> getTaskPageDetailPage(TaskPageDetailPageReqVO pageReqVO) {
        return taskPageDetailMapper.selectPage(pageReqVO);
    }

}