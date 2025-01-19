package cn.iocoder.yudao.module.wuyou.service.taskpagedetail;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.wuyou.controller.admin.taskpagedetail.vo.*;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.taskpagedetail.TaskPageDetailDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 页数据采集状态 Service 接口
 *
 * @author 芋道源码
 */
public interface TaskPageDetailService {

    /**
     * 创建页数据采集状态
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTaskPageDetail(@Valid TaskPageDetailSaveReqVO createReqVO);

    /**
     * 更新页数据采集状态
     *
     * @param updateReqVO 更新信息
     */
    void updateTaskPageDetail(@Valid TaskPageDetailSaveReqVO updateReqVO);

    /**
     * 删除页数据采集状态
     *
     * @param id 编号
     */
    void deleteTaskPageDetail(Long id);

    /**
     * 获得页数据采集状态
     *
     * @param id 编号
     * @return 页数据采集状态
     */
    TaskPageDetailDO getTaskPageDetail(Long id);

    /**
     * 获得页数据采集状态分页
     *
     * @param pageReqVO 分页查询
     * @return 页数据采集状态分页
     */
    PageResult<TaskPageDetailDO> getTaskPageDetailPage(TaskPageDetailPageReqVO pageReqVO);

}