package cn.iocoder.yudao.module.wuyou.controller.admin.device.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 采集器信息新增/修改 Request VO")
@Data
public class DeviceSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "11824")
    private Long id;

    @Schema(description = "ip地址")
    private String ipAddress;

    @Schema(description = "状态", example = "2")
    private Integer status;

    @Schema(description = "验证码出现时间")
    private LocalDateTime captchaTime;

}