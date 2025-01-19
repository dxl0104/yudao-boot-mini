package cn.iocoder.yudao.module.wuyou.controller.admin.task.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 采集任务新增/修改 Request VO")
@Data
public class TaskSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "27451")
    private Long id;

    @Schema(description = "类型", example = "2")
    private Integer taskType;

    @Schema(description = "状态", example = "1")
    private Integer status;

    @Schema(description = "超时时间")
    private LocalDateTime timeoutAt;

    @Schema(description = "页数")
    private Integer pages;

}