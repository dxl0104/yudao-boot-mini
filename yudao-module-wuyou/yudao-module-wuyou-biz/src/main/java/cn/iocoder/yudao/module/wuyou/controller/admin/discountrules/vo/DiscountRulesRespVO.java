package cn.iocoder.yudao.module.wuyou.controller.admin.discountrules.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 折扣规则 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DiscountRulesRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "24937")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "满足的最小总价")
    @ExcelProperty("满足的最小总价")
    private BigDecimal minAmount;

    @Schema(description = "商品减免金额")
    @ExcelProperty("商品减免金额")
    private BigDecimal discountAmount;

    @Schema(description = "快递费用减免金额", example = "4583")
    @ExcelProperty("快递费用减免金额")
    private BigDecimal shippingDiscount;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "最大金额")
    @ExcelProperty("最大金额")
    private BigDecimal maxAmount;

}