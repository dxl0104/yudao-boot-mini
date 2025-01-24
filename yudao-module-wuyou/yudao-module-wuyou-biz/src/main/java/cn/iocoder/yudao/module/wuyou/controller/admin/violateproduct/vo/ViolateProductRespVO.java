package cn.iocoder.yudao.module.wuyou.controller.admin.violateproduct.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 侵权商品 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ViolateProductRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "4469")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "url商品链接", example = "https://www.iocoder.cn")
    @ExcelProperty("url商品链接")
    private String url;

    @Schema(description = "json数据")
    @ExcelProperty("json数据")
    private String dataJson;

    @Schema(description = "快递费")
    @ExcelProperty("快递费")
    private BigDecimal delivery;

    @Schema(description = "分类")
    @ExcelProperty("分类")
    private String category;

    @Schema(description = "offerId", example = "32701")
    @ExcelProperty("offerId")
    private String offerId;

    @Schema(description = "ean")
    @ExcelProperty("ean")
    private String ean;

    @Schema(description = "标题")
    @ExcelProperty("标题")
    private String title;

    @Schema(description = "productId", example = "18846")
    @ExcelProperty("productId")
    private String productId;

    @Schema(description = "quantity")
    @ExcelProperty("quantity")
    private String quantity;

    @Schema(description = "price", example = "13885")
    @ExcelProperty("price")
    private BigDecimal price;

    @Schema(description = "currency")
    @ExcelProperty("currency")
    private String currency;

    @Schema(description = "一级分类")
    @ExcelProperty("一级分类")
    private String mainCategory1;

    @Schema(description = "二级分类")
    @ExcelProperty("二级分类")
    private String mainCategory2;

    @Schema(description = "三级分类")
    @ExcelProperty("三级分类")
    private String mainCategory3;

    @Schema(description = "imgUrl", example = "https://www.iocoder.cn")
    @ExcelProperty("imgUrl")
    private String imgUrl;

    @Schema(description = "json", example = "你说的对")
    @ExcelProperty("json")
    private String offerDescription;

    @Schema(description = "商品主图", example = "https://www.iocoder.cn")
    @ExcelProperty("商品主图")
    private String mainUrl;

    @Schema(description = "侵权词")
    @ExcelProperty("侵权词")
    private String violateWord;

    private String violateId;

}