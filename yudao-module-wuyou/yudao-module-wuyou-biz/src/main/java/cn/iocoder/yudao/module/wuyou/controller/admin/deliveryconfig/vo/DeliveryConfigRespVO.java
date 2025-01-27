package cn.iocoder.yudao.module.wuyou.controller.admin.deliveryconfig.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 快递费归档 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DeliveryConfigRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "29652")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "起始费用")
    @ExcelProperty("起始费用")
    private BigDecimal startMoney;

    @Schema(description = "结束费用")
    @ExcelProperty("结束费用")
    private BigDecimal endMoney;

    @Schema(description = "归档级别")
    @ExcelProperty("归档级别")
    private Integer level;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}