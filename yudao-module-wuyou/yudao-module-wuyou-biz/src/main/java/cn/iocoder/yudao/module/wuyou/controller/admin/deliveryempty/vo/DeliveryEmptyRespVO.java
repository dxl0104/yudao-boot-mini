package cn.iocoder.yudao.module.wuyou.controller.admin.deliveryempty.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 快递为零商品 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DeliveryEmptyRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "18013")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "快递费")
    @ExcelProperty("快递费")
    private BigDecimal delivery;

    @Schema(description = "标题")
    @ExcelProperty("标题")
    private String title;

    @Schema(description = "price", example = "13064")
    @ExcelProperty("price")
    private BigDecimal price;

    @Schema(description = "一级分类")
    @ExcelProperty("一级分类")
    private String mainCategory1;

    @Schema(description = "二级分类")
    @ExcelProperty("二级分类")
    private String mainCategory2;

    @Schema(description = "三级分类")
    @ExcelProperty("三级分类")
    private String mainCategory3;

    @Schema(description = "商品主图", example = "https://www.iocoder.cn")
    @ExcelProperty("商品主图")
    private String mainUrl;

}