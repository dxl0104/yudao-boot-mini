package cn.iocoder.yudao.module.wuyou.controller.admin.basicdata.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 无忧基础数据 Response VO")
@Data
@ExcelIgnoreUnannotated
public class BasicDataRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "6221")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "url", example = "https://www.iocoder.cn")
    @ExcelProperty("url")
    private String url;

    @Schema(description = "json数据", example = "22222")
    @ExcelProperty("json数据")
    private String dataJson;

    @Schema(description = "快递费")
    @ExcelProperty("快递费")
    private Integer delivery;

    @Schema(description = "分类")
    @ExcelProperty("分类")
    private String category;

}