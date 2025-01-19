package cn.iocoder.yudao.module.wuyou.controller.admin.taskpagedetail.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 页数据采集状态新增/修改 Request VO")
@Data
public class TaskPageDetailSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "21694")
    private Long id;

    @Schema(description = "关联任务id", example = "20253")
    private Long taskId;

    @Schema(description = "当前页码")
    private Integer pageNum;

    @Schema(description = "状态", example = "2")
    private Integer status;

    @Schema(description = "采集器id", example = "12219")
    private Long deviceId;

    @Schema(description = "分配时间")
    private LocalDateTime assignedAt;

    @Schema(description = "超时时间")
    private LocalDateTime timeout;

}