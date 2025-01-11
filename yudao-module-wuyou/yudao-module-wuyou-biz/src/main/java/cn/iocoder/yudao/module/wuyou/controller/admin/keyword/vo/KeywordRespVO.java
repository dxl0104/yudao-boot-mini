package cn.iocoder.yudao.module.wuyou.controller.admin.keyword.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 无忧侵权词 Response VO")
@Data
@ExcelIgnoreUnannotated
public class KeywordRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "11273")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "侵权词")
    @ExcelProperty("侵权词")
    private String infringementKeyword;

    @Schema(description = "平台")
    @ExcelProperty("平台")
    private Integer platform;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}