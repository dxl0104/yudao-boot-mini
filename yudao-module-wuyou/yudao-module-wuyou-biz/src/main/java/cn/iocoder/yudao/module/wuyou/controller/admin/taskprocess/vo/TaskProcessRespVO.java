package cn.iocoder.yudao.module.wuyou.controller.admin.taskprocess.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 任务进度 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TaskProcessRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "5272")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "任务id", example = "17070")
    @ExcelProperty("任务id")
    private Long taskId;

    @Schema(description = "设备id", example = "4043")
    @ExcelProperty("设备id")
    private Long deviceId;

    @Schema(description = "进度")
    @ExcelProperty("进度")
    private BigDecimal process;

    @Schema(description = "已采集数量", example = "13150")
    @ExcelProperty("已采集数量")
    private Integer collectCount;

    @Schema(description = "总数量", example = "29248")
    @ExcelProperty("总数量")
    private Integer totalCount;

    @Schema(description = "状态", example = "0")
    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("task_process_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}