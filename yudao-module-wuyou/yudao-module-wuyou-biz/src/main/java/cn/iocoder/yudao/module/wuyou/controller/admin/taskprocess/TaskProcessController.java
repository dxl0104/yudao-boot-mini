package cn.iocoder.yudao.module.wuyou.controller.admin.taskprocess;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.wuyou.controller.admin.taskprocess.vo.*;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.taskprocess.TaskProcessDO;
import cn.iocoder.yudao.module.wuyou.service.taskprocess.TaskProcessService;

@Tag(name = "管理后台 - 任务进度")
@RestController
@RequestMapping("/wuyou/task-process")
@Validated
public class TaskProcessController {

    @Resource
    private TaskProcessService taskProcessService;

    @PostMapping("/create")
    @Operation(summary = "创建任务进度")
    @PreAuthorize("@ss.hasPermission('wuyou:task-process:create')")
    public CommonResult<Long> createTaskProcess(@Valid @RequestBody TaskProcessSaveReqVO createReqVO) {
        return success(taskProcessService.createTaskProcess(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新任务进度")
    @PreAuthorize("@ss.hasPermission('wuyou:task-process:update')")
    public CommonResult<Boolean> updateTaskProcess(@Valid @RequestBody TaskProcessSaveReqVO updateReqVO) {
        taskProcessService.updateTaskProcess(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除任务进度")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('wuyou:task-process:delete')")
    public CommonResult<Boolean> deleteTaskProcess(@RequestParam("id") Long id) {
        taskProcessService.deleteTaskProcess(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得任务进度")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('wuyou:task-process:query')")
    public CommonResult<TaskProcessRespVO> getTaskProcess(@RequestParam("id") Long id) {
        TaskProcessDO taskProcess = taskProcessService.getTaskProcess(id);
        return success(BeanUtils.toBean(taskProcess, TaskProcessRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得任务进度分页")
    @PreAuthorize("@ss.hasPermission('wuyou:task-process:query')")
    public CommonResult<PageResult<TaskProcessRespVO>> getTaskProcessPage(@Valid TaskProcessPageReqVO pageReqVO) {
        PageResult<TaskProcessDO> pageResult = taskProcessService.getTaskProcessPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TaskProcessRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出任务进度 Excel")
    @PreAuthorize("@ss.hasPermission('wuyou:task-process:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportTaskProcessExcel(@Valid TaskProcessPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TaskProcessDO> list = taskProcessService.getTaskProcessPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "任务进度.xls", "数据", TaskProcessRespVO.class,
                        BeanUtils.toBean(list, TaskProcessRespVO.class));
    }

}