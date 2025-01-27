package cn.iocoder.yudao.module.wuyou.controller.admin.basicdata.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WuyouKeyWordResVO {
    @JsonProperty("iTotalRecords")
    private int iTotalRecords;

    @JsonProperty("iTotalDisplayRecords")
    private int iTotalDisplayRecords;

    @JsonProperty("records")
    private int records;

    @JsonProperty("page")
    private int page;

    @JsonProperty("total")
    private int total;

    @JsonProperty("rows")
    private List<Row> rows;
}
