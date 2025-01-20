package cn.iocoder.yudao.module.wuyou.job;

import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.sourceurl.SourceUrlDO;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.task.TaskDO;
import cn.iocoder.yudao.module.wuyou.dal.mysql.sourceurl.SourceUrlMapper;
import cn.iocoder.yudao.module.wuyou.dal.mysql.task.TaskMapper;
import cn.iocoder.yudao.module.wuyou.service.sourceurl.SourceUrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 店铺链接转换为任务
 */
@Component
@Slf4j
public class TaskJob implements JobHandler {

    @Resource
    private SourceUrlService sourceUrlService;

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private SourceUrlMapper sourceUrlMapper;

    @Override
    public String execute(String param) {
        try {
            List<SourceUrlDO> sourceUrlList = sourceUrlService.getSourceUrlList();
            if (!sourceUrlList.isEmpty()) {
                for (SourceUrlDO sourceUrlDO : sourceUrlList) {
                    String url = sourceUrlDO.getListUrl();
                    Integer pages = sourceUrlDO.getPages();
                    TaskDO taskDO = new TaskDO();
                    taskDO.setTaskType(0);
                    taskDO.setUrl(url);
                    taskDO.setPages(pages);
                    taskMapper.insert(taskDO);
                    sourceUrlDO.setConvertTask(1);
                    sourceUrlMapper.updateById(sourceUrlDO);
                }
            }

        } catch (Exception e) {
            log.info("店铺转换为任务报错", e);
            return e.getMessage();
        }

        return "操作成功";
    }
}
