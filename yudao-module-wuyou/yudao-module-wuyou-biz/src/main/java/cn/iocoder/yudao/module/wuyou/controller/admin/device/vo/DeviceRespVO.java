package cn.iocoder.yudao.module.wuyou.controller.admin.device.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 采集器信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DeviceRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "11824")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "ip地址")
    @ExcelProperty("ip地址")
    private String ipAddress;

    @Schema(description = "状态", example = "2")
    @ExcelProperty("状态")
    private Integer status;

    @Schema(description = "验证码出现时间")
    @ExcelProperty("验证码出现时间")
    private LocalDateTime captchaTime;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}