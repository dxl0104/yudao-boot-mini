package cn.iocoder.yudao.module.wuyou.controller.admin.category.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 数据类别 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CategoryRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "29545")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "类别名称", example = "芋艿")
    @ExcelProperty("类别名称")
    private String categoryName;

    @Schema(description = "中文名称", example = "赵六")
    @ExcelProperty("中文名称")
    private String zhName;

    @Schema(description = "级别")
    @ExcelProperty("级别")
    private Integer level;

    @Schema(description = "父id", example = "18359")
    @ExcelProperty("父id")
    private Long parentId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}