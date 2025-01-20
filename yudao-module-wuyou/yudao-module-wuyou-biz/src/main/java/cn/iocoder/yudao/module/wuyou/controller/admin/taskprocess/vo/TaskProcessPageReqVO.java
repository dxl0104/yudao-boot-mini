package cn.iocoder.yudao.module.wuyou.controller.admin.taskprocess.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 任务进度分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TaskProcessPageReqVO extends PageParam {

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

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}