package cn.iocoder.yudao.module.wuyou.job;

import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.task.TaskDO;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.taskpagedetail.TaskPageDetailDO;
import cn.iocoder.yudao.module.wuyou.dal.mysql.task.TaskMapper;
import cn.iocoder.yudao.module.wuyou.dal.mysql.taskpagedetail.TaskPageDetailMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

    @Override
    public String execute(String param) throws Exception {
        //status 为 0 1的任务为待分配和部分分配  先只改变list的任务状态  detail后续进行补充
        List<TaskDO> taskDOList = taskMapper.selectList(new QueryWrapper<TaskDO>().eq("task_type", 0).in("status", 0, 1));
        for (TaskDO taskDO : taskDOList) {
            Long taskId = taskDO.getId();
            Integer pages = taskDO.getPages();
            List<TaskPageDetailDO> taskPageDetailDOS = taskPageDetailMapper.selectList(new QueryWrapper<TaskPageDetailDO>().eq("task_id", taskId).isNotNull("device_id").isNotNull("assigned_at"));
            if (!taskPageDetailDOS.isEmpty()) {
                //说明列表已经全部进行分配
                if (taskPageDetailDOS.size()==pages){
                    //待分配以及部分分配变为全部分配
                    if (taskDO.getStatus()==0 || taskDO.getStatus()==1){
                        taskDO.setStatus(2);
                    }
                }
                else if (taskPageDetailDOS.size() < pages){
                    if (taskDO.getStatus()==0){
                        taskDO.setStatus(1);
                    }
                }
                taskMapper.updateById(taskDO);
            }
        }










        return null;
    }
}
