package cn.iocoder.yudao.module.wuyou.controller.admin.deliveryconfig.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 快递费归档新增/修改 Request VO")
@Data
public class DeliveryConfigSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "24436")
    private Long id;

    @Schema(description = "起始费用")
    private BigDecimal startMoney;

    @Schema(description = "结束费用")
    private BigDecimal endMoney;

    @Schema(description = "归档级别")
    private Integer level;

    @Schema(description = "物流价格")
    private BigDecimal deliveryMoney;

}