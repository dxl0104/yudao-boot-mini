package cn.iocoder.yudao.module.wuyou.controller.admin.taskpagedetail;

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

import cn.iocoder.yudao.module.wuyou.controller.admin.taskpagedetail.vo.*;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.taskpagedetail.TaskPageDetailDO;
import cn.iocoder.yudao.module.wuyou.service.taskpagedetail.TaskPageDetailService;

@Tag(name = "管理后台 - 页数据采集状态")
@RestController
@RequestMapping("/wuyou/task-page-detail")
@Validated
public class TaskPageDetailController {

    @Resource
    private TaskPageDetailService taskPageDetailService;

    @PostMapping("/create")
    @Operation(summary = "创建页数据采集状态")
    @PreAuthorize("@ss.hasPermission('wuyou:task-page-detail:create')")
    public CommonResult<Long> createTaskPageDetail(@Valid @RequestBody TaskPageDetailSaveReqVO createReqVO) {
        return success(taskPageDetailService.createTaskPageDetail(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新页数据采集状态")
    @PreAuthorize("@ss.hasPermission('wuyou:task-page-detail:update')")
    public CommonResult<Boolean> updateTaskPageDetail(@Valid @RequestBody TaskPageDetailSaveReqVO updateReqVO) {
        taskPageDetailService.updateTaskPageDetail(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除页数据采集状态")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('wuyou:task-page-detail:delete')")
    public CommonResult<Boolean> deleteTaskPageDetail(@RequestParam("id") Long id) {
        taskPageDetailService.deleteTaskPageDetail(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得页数据采集状态")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('wuyou:task-page-detail:query')")
    public CommonResult<TaskPageDetailRespVO> getTaskPageDetail(@RequestParam("id") Long id) {
        TaskPageDetailDO taskPageDetail = taskPageDetailService.getTaskPageDetail(id);
        return success(BeanUtils.toBean(taskPageDetail, TaskPageDetailRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得页数据采集状态分页")
    @PreAuthorize("@ss.hasPermission('wuyou:task-page-detail:query')")
    public CommonResult<PageResult<TaskPageDetailRespVO>> getTaskPageDetailPage(@Valid TaskPageDetailPageReqVO pageReqVO) {
        PageResult<TaskPageDetailDO> pageResult = taskPageDetailService.getTaskPageDetailPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TaskPageDetailRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出页数据采集状态 Excel")
    @PreAuthorize("@ss.hasPermission('wuyou:task-page-detail:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportTaskPageDetailExcel(@Valid TaskPageDetailPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TaskPageDetailDO> list = taskPageDetailService.getTaskPageDetailPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "页数据采集状态.xls", "数据", TaskPageDetailRespVO.class,
                        BeanUtils.toBean(list, TaskPageDetailRespVO.class));
    }

}