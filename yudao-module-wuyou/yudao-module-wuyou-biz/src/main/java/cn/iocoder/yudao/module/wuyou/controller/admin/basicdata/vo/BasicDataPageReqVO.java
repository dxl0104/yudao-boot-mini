package cn.iocoder.yudao.module.wuyou.controller.admin.basicdata.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 无忧基础数据分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BasicDataPageReqVO extends PageParam {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "url", example = "https://www.iocoder.cn")
    private String url;

    @Schema(description = "json数据", example = "22222")
    private String dataJson;

    @Schema(description = "快递费")
    private Integer delivery;

    @Schema(description = "分类")
    private String category;

    private List<String> ids;

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

    private String type;



}