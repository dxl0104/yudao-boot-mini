package cn.iocoder.yudao.module.wuyou.controller.admin.keyword.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Schema(description = "管理后台 - 无忧侵权词新增/修改 Request VO")
@Data
public class KeywordSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "11273")
    private Long id;

    @Schema(description = "侵权词")
    private String infringementKeyword;

    @Schema(description = "平台")
    private Integer platform;

}