package cn.iocoder.yudao.module.wuyou.controller.admin.taskprocess.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 任务进度新增/修改 Request VO")
@Data
public class TaskProcessSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "5272")
    private Long id;

    @Schema(description = "任务id", example = "17070")
    private Long taskId;

    @Schema(description = "设备id", example = "4043")
    private Long deviceId;

    @Schema(description = "进度")
    private BigDecimal process;

    @Schema(description = "已采集数量", example = "13150")
    private Integer collectCount;

    @Schema(description = "总数量", example = "29248")
    private Integer totalCount;

    @Schema(description = "状态", example = "0")
    private Integer status;

}