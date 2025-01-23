package cn.iocoder.yudao.module.wuyou.job;

import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.producturl.ProductUrlDO;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.task.TaskDO;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.taskpagedetail.TaskPageDetailDO;
import cn.iocoder.yudao.module.wuyou.dal.mysql.producturl.ProductUrlMapper;
import cn.iocoder.yudao.module.wuyou.dal.mysql.task.TaskMapper;
import cn.iocoder.yudao.module.wuyou.dal.mysql.taskpagedetail.TaskPageDetailMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


/**
 * 定时更改任务的状态
 */
@Component
public class ChangeTaskStatusJob implements JobHandler {

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private TaskPageDetailMapper taskPageDetailMapper;

    @Resource
    private ProductUrlMapper productUrlMapper;

    @Override
    public String execute(String param) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        //status 为 0 1的任务为待分配和部分分配  改变list的任务状态
        List<TaskDO> taskDOList = taskMapper.selectList(new QueryWrapper<TaskDO>().eq("task_type", 0).in("status", 0, 1));
        for (TaskDO taskDO : taskDOList) {
            Long taskId = taskDO.getId();
            Integer pages = taskDO.getPages();
            List<TaskPageDetailDO> taskPageDetailDOS = taskPageDetailMapper.selectList(new QueryWrapper<TaskPageDetailDO>().eq("task_id", taskId).isNotNull("device_id").isNotNull("assigned_at"));
            if (!taskPageDetailDOS.isEmpty()) {
                //说明列表已经全部进行分配
                if (taskPageDetailDOS.size() == pages) {
                    //待分配以及部分分配变为全部分配
                    if (taskDO.getStatus() == 0 || taskDO.getStatus() == 1) {
                        taskDO.setStatus(2);
                    }
                } else if (taskPageDetailDOS.size() < pages) {
                    if (taskDO.getStatus() == 0) {
                        taskDO.setStatus(1);
                    }
                }
                taskMapper.updateById(taskDO);
            }
        }
        //status=2 表示已全部分配  查询商品详情任务
        List<TaskDO> detailList = taskMapper.selectList(new QueryWrapper<TaskDO>().eq("task_type", 1).in("status", 2));
        for (TaskDO taskDO : detailList) {
            String detailIds = taskDO.getDetailIds();
            List<Long> detailIdsList = objectMapper.readValue(detailIds, new TypeReference<List<Long>>() {
            });
            // 使用 detailIdsArray
            List<ProductUrlDO> list = productUrlMapper.selectList(new QueryWrapper<ProductUrlDO>().in("id", detailIdsList));
            List<ProductUrlDO> completeList = productUrlMapper.selectList(new QueryWrapper<ProductUrlDO>().in("id", detailIdsList).eq("process_flag", 2));
            if (!list.isEmpty() && !completeList.isEmpty()) {
                if (list.size()== completeList.size()){
                    //4表示全部完成
                    taskDO.setStatus(4);
                    taskMapper.updateById(taskDO);
                }
                if (list.size() >completeList.size()){
                    //部分完成
                    taskDO.setStatus(3);
                    taskMapper.updateById(taskDO);
                }
            }
        }
        return null;
    }
}
