package cn.iocoder.yudao.module.wuyou.controller.admin.producturl.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 商品url列表新增/修改 Request VO")
@Data
public class ProductUrlSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1920")
    private Long id;

    @Schema(description = "url", example = "https://www.iocoder.cn")
    private String url;

}