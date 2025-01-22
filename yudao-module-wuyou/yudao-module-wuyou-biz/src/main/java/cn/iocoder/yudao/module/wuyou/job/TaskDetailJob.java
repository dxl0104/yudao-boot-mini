package cn.iocoder.yudao.module.wuyou.job;

import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.task.TaskDO;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.taskpagedetail.TaskPageDetailDO;
import cn.iocoder.yudao.module.wuyou.dal.mysql.task.TaskMapper;
import cn.iocoder.yudao.module.wuyou.dal.mysql.taskpagedetail.TaskPageDetailMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class TaskDetailJob implements JobHandler {
    @Resource
    private TaskMapper taskMapper;

    @Resource
    private TaskPageDetailMapper taskPageDetailMapper;

    @Override
    public String execute(String param) throws Exception {
        List<TaskDO> taskDOS = taskMapper.selectList(new QueryWrapper<TaskDO>().eq("is_resolve",0).eq("task_type",0));
        if (!taskDOS.isEmpty()) {
            //将任务进行分解
            for (TaskDO taskDO : taskDOS) {
                ArrayList<TaskPageDetailDO> taskPageDetailDOS = getTaskPageDetailDOS(taskDO);
                Boolean aBoolean = taskPageDetailMapper.insertBatch(taskPageDetailDOS);
                if (aBoolean){
                    taskDO.setIsResolve(1);
                    taskMapper.updateById(taskDO);
                }
            }
        }
        return "操作成功";
    }

    private static ArrayList<TaskPageDetailDO> getTaskPageDetailDOS(TaskDO taskDO) {
        Integer page = taskDO.getPages();
        ArrayList<TaskPageDetailDO> taskPageDetailDOS = new ArrayList<>();
        //创建
        for (int i = 1; i <= page; i++) {
            TaskPageDetailDO taskPageDetailDO = new TaskPageDetailDO();
            taskPageDetailDO.setTaskId(taskDO.getId());
            taskPageDetailDO.setPageNum(i);
            taskPageDetailDO.setStatus(0);
            taskPageDetailDO.setUrl(taskDO.getUrl());
            taskPageDetailDOS.add(taskPageDetailDO);
        }
        return taskPageDetailDOS;
    }
}
