package cn.iocoder.yudao.module.wuyou.controller.admin.task.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 采集任务 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TaskRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "11255")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "类型", example = "1")
    @ExcelProperty(value = "类型", converter = DictConvert.class)
    @DictFormat("task_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer taskType;

    @Schema(description = "状态", example = "2")
    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("task_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer status;

    @Schema(description = "超时时间")
    @ExcelProperty("超时时间")
    private LocalDateTime timeoutAt;

    @Schema(description = "页数")
    @ExcelProperty("页数")
    private Integer pages;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "优先级")
    @ExcelProperty("优先级")
    private Integer priority;

    @Schema(description = "开始时间")
    @ExcelProperty("开始时间")
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    @ExcelProperty("结束时间")
    private LocalDateTime endTime;

    private String url;

}