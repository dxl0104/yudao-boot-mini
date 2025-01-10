package cn.iocoder.yudao.module.wuyou.controller.admin.producturl.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 商品url列表 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ProductUrlRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1920")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "url", example = "https://www.iocoder.cn")
    @ExcelProperty("url")
    private String url;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}