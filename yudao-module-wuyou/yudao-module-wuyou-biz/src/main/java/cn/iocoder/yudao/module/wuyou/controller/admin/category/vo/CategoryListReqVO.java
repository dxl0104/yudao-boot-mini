package cn.iocoder.yudao.module.wuyou.controller.admin.category.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 数据类别列表 Request VO")
@Data
public class CategoryListReqVO {

    @Schema(description = "类别名称", example = "芋艿")
    private String categoryName;

    @Schema(description = "中文名称", example = "赵六")
    private String zhName;

    @Schema(description = "级别")
    private Integer level;

    @Schema(description = "父id", example = "18359")
    private Long parentId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}