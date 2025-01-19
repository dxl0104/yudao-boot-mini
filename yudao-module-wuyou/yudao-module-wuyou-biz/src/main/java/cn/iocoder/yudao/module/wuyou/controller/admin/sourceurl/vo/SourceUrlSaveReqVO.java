package cn.iocoder.yudao.module.wuyou.controller.admin.sourceurl.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 列表链接新增/修改 Request VO")
@Data
public class SourceUrlSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "21784")
    private Long id;

    @Schema(description = "链接地址", example = "https://www.iocoder.cn")
    private String listUrl;

    @Schema(description = "总页数")
    private Integer pages;

}