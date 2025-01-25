package cn.iocoder.yudao.module.wuyou.controller.admin.category.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 数据类别新增/修改 Request VO")
@Data
public class CategorySaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "29545")
    private Long id;

    @Schema(description = "类别名称", example = "芋艿")
    private String categoryName;

    @Schema(description = "中文名称", example = "赵六")
    private String zhName;

    @Schema(description = "级别")
    private Integer level;

    @Schema(description = "父id", example = "18359")
    private Long parentId;

}