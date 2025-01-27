package cn.iocoder.yudao.module.wuyou.controller.admin.deliveryempty.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 快递为零商品新增/修改 Request VO")
@Data
public class DeliveryEmptySaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "18013")
    private Long id;

    @Schema(description = "url商品链接", example = "https://www.iocoder.cn")
    private String url;

    @Schema(description = "json数据")
    private String dataJson;

    @Schema(description = "快递费")
    private BigDecimal delivery;

    @Schema(description = "分类")
    private String category;

    @Schema(description = "offerId", example = "21149")
    private String offerId;

    @Schema(description = "ean")
    private String ean;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "productId", example = "780")
    private String productId;

    @Schema(description = "quantity")
    private String quantity;

    @Schema(description = "price", example = "13064")
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

    @Schema(description = "商品主图", example = "https://www.iocoder.cn")
    private String mainUrl;

}