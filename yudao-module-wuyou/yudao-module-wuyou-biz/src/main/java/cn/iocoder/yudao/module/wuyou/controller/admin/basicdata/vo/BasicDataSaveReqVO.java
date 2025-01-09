package cn.iocoder.yudao.module.wuyou.controller.admin.basicdata.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 无忧基础数据新增/修改 Request VO")
@Data
public class BasicDataSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "6221")
    private Long id;

    @Schema(description = "url", example = "https://www.iocoder.cn")
    private String url;

    @Schema(description = "json数据", example = "22222")
    private String dataJson;

    @Schema(description = "快递费")
    private Integer delivery;

    @Schema(description = "分类")
    private String category;

}