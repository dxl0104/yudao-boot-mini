package cn.iocoder.yudao.module.wuyou.controller.admin.taskpagedetail.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 页数据采集状态 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TaskPageDetailRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "21694")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "关联任务id", example = "20253")
    @ExcelProperty("关联任务id")
    private Long taskId;

    @Schema(description = "当前页码")
    @ExcelProperty("当前页码")
    private Integer pageNum;

    @Schema(description = "状态", example = "2")
    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("task_page_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer status;

    @Schema(description = "采集器id", example = "12219")
    @ExcelProperty("采集器id")
    private Long deviceId;

    @Schema(description = "分配时间")
    @ExcelProperty("分配时间")
    private LocalDateTime assignedAt;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "超时时间")
    @ExcelProperty("超时时间")
    private LocalDateTime timeout;

}