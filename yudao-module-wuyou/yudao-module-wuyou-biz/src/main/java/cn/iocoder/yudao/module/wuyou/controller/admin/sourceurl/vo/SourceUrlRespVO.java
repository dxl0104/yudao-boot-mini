package cn.iocoder.yudao.module.wuyou.controller.admin.sourceurl.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 列表链接 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SourceUrlRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "21784")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "链接地址", example = "https://www.iocoder.cn")
    @ExcelProperty("链接地址")
    private String listUrl;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "总页数")
    @ExcelProperty("总页数")
    private Integer pages;

    private Integer covertTask;

}