package cn.iocoder.yudao.module.wuyou.controller.admin.discountrules.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 折扣规则新增/修改 Request VO")
@Data
public class DiscountRulesSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "24937")
    private Long id;

    @Schema(description = "满足的最小总价")
    private BigDecimal minAmount;

    @Schema(description = "商品减免金额")
    private BigDecimal discountAmount;

    @Schema(description = "快递费用减免金额", example = "4583")
    private BigDecimal shippingDiscount;

    @Schema(description = "最大金额")
    private BigDecimal maxAmount;

}