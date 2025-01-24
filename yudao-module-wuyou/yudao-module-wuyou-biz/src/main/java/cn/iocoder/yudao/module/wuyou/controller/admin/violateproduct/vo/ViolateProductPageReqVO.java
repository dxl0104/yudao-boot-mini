package cn.iocoder.yudao.module.wuyou.controller.admin.violateproduct.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 侵权商品分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ViolateProductPageReqVO extends PageParam {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "url商品链接", example = "https://www.iocoder.cn")
    private String url;

    @Schema(description = "json数据")
    private String dataJson;

    @Schema(description = "快递费")
    private BigDecimal delivery;

    @Schema(description = "分类")
    private String category;

    @Schema(description = "offerId", example = "32701")
    private String offerId;

    @Schema(description = "ean")
    private String ean;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "productId", example = "18846")
    private String productId;

    @Schema(description = "quantity")
    private String quantity;

    @Schema(description = "price", example = "13885")
    private BigDecimal price;

    @Schema(description = "currency")
    private String currency;

    @Schema(description = "一级分类")
    private String mainCategory1;

    @Schema(description = "二级分类")
    private String mainCategory2;

    @Schema(description = "三级分类")
    private String mainCategory3;

    @Schema(description = "imgUrl", example = "https://www.iocoder.cn")
    private String imgUrl;

    @Schema(description = "json", example = "你说的对")
    private String offerDescription;

    @Schema(description = "商品主图", example = "https://www.iocoder.cn")
    private String mainUrl;

    @Schema(description = "侵权词")
    private String violateWord;

}