package cn.iocoder.yudao.module.wuyou.controller.admin.discountrules.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 折扣规则分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DiscountRulesPageReqVO extends PageParam {

    @Schema(description = "满足的最小总价")
    private BigDecimal minAmount;

    @Schema(description = "商品减免金额")
    private BigDecimal discountAmount;

    @Schema(description = "快递费用减免金额", example = "4583")
    private BigDecimal shippingDiscount;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "最大金额")
    private BigDecimal maxAmount;

}