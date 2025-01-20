package cn.iocoder.yudao.module.wuyou.controller.admin.collect;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.wuyou.controller.admin.basicdata.vo.BasicDataRespVO;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.task.TaskDO;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.taskpagedetail.TaskPageDetailDO;
import cn.iocoder.yudao.module.wuyou.dal.mysql.task.TaskMapper;
import cn.iocoder.yudao.module.wuyou.dal.mysql.taskpagedetail.TaskPageDetailMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 采集器请求接口")
@RestController
@RequestMapping("/collect")
@Validated
@Slf4j
public class CollectController {

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private TaskPageDetailMapper taskPageDetailMapper;

    @PostMapping("/getData")
    @Operation(summary = "获取需要采集的数据")
    @Parameter(name = "ip", description = "ip", required = true, example = "47.79.20.197")
    public CommonResult<BasicDataRespVO> getBasicData(@RequestParam("ip") String ipAddress) {
        //找到可以进行分配的任务
        List<TaskDO> taskDOList = taskMapper.selectList(new QueryWrapper<TaskDO>().in("status", 0, 1).orderByDesc("create_time"));
        TaskDO taskDO = taskDOList.get(0);
        //采列表
        if (taskDO.getTaskType()==0){
            //根据taskId去分页详情表查询
            Long taskId = taskDO.getId();
            List<TaskPageDetailDO> taskPageDetailDOS = taskPageDetailMapper.selectList();

        }


        return success(null);
    }
}
