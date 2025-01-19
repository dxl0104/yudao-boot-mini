package cn.iocoder.yudao.module.wuyou.controller.admin.taskpagedetail.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 页数据采集状态分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TaskPageDetailPageReqVO extends PageParam {

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

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "超时时间")
    private LocalDateTime timeout;

}