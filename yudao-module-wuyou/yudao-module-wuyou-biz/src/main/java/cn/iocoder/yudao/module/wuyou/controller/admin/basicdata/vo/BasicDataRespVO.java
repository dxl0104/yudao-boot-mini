package cn.iocoder.yudao.module.wuyou.controller.admin.basicdata.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 无忧基础数据 Response VO")
@Data
@ExcelIgnoreUnannotated
public class BasicDataRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "6221")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "url", example = "https://www.iocoder.cn")
    @ExcelProperty("url")
    private String url;

    @Schema(description = "快递费")
    @ExcelProperty("快递费")
    private BigDecimal delivery;

    @Schema(description = "分类")
    @ExcelProperty("分类")
    private String category;

    @Schema(description = "url", example = "https://www.iocoder.cn")
    @ExcelProperty("json")
    private String dataJson;

    private String title;

    private String mainUrl;

    private BigDecimal price;

    /*
     * 主要商品一级类型
     * */
    private String mainCategory1;

    /*
     * 主要商品二级类型
     * */
    private String mainCategory2;

    /*
     * 主要商品三级类型
     * */
    private String mainCategory3;

    private String productId;


}